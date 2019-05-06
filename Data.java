import java.text.*;
import java.util.*;

/*
 * The model
 * Stores a list of all the days with tasks to-do
 */
public class Data {

	private ArrayList<Day> days;
	Iterator<Day> iterDay;
	Iterator<String> iterString;
	private Day currentDay;
	private Calendar cal;
	private static final DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

	public Data() {
		cal = Calendar.getInstance(); // calendar obj with current time and date
		currentDay = new Day(cal);
		days = new ArrayList<>();		
		ToDoListFrame toDoFrame = new ToDoListFrame(this);
		CalendarFrame calendarFrame = new CalendarFrame(this);
	}
	
	/*
	 * Setters & Getters
	 */	
	public Day getCurrentDay() {
		return currentDay;
	}
	
	public void setCurrentDay(int year, int month, int day) {
		currentDay.setYear(year);
		currentDay.setMonth(month);
		currentDay.setDay(day);
	}

	public Iterator<Day> getDays() {

		return new Iterator<Day>() {
			public boolean hasNext() {
				return current < days.size();
			}

			public Day next() {
				return days.get(current++);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

			private int current = 0;
		};
	}
	
	
	// Return a String in the format of WEEKDAY, MONTH-DAY-YEAR
	public String getCurrentDate() {
		String dayOfWeek = currentDay.getDayOfWeek();
		return dayOfWeek + ", " + formatter.format(currentDay.getToday().getTime());
	}
	
	// Add a currentDay to days arrayList
	public void addDay() {
		days.add(currentDay);
	}

	/*
	 * Add, Edit, Delete task of currentDay 
	 */
	public void addTask(String taskToBeAdded) {
		currentDay.addTask(taskToBeAdded);
	}

	public void editTask(String old, String updated) {
		currentDay.editTask(old, updated);
	}

	public void deleteTask(String taskSelected) {
		currentDay.removeTask(taskSelected);
	}
	
	// Export all task in a specific month to a text file
	// Text file name in format MONTH_YEAR.txt
	public void export() {
		// generate the a filename in the format of month_year.txt as a String
		String fileName = currentDay.getMonth() + "_" + currentDay.getYear() + ".txt";

		String content = "";
		iterDay = getDays();
		Day temp = null;
		// iterate thru arrayList of days, check if a day has month that matches month of currentDay
		// if T, iterate thru tasks arrayList of that day and append each task to content
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if (temp.getMonth() == currentDay.getMonth())
				iterString = temp.getTasks();
				while (iterString.hasNext()) 
					content += iterString.next() + "\r\n";
		}
		WriteToFile writer = new WriteToFile(fileName, content);
		System.out.printf("File %s is written", fileName);
	}

}