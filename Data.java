import java.text.*;
import java.util.*;

/*
 * The model
 * Stores a list of all the days with tasks to-do
 */
public class Data {

	ArrayList<Day> days;
	private Day currentDay;
	private Calendar cal;
	private ToDoListFrame toDoFrame;
	private CalendarFrame calendarFrame;

	public Data() {
		cal = Calendar.getInstance(); // calendar obj with current time and date
		currentDay = new Day(cal);
		days = new ArrayList<>();
		calendarFrame = new CalendarFrame(this);
		toDoFrame = new ToDoListFrame(this);
		
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

		// reset currentday task array
		currentDay.resetTasks();

		Iterator<Day> iterDay = getDays();
		Day temp = null;
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if (currentDay.getToday().equals(temp.getToday())) {
				// add selected days tasks to currentday tasks with iterstring
				Iterator<String> iterString = temp.getTasks();
				while (iterString.hasNext())
					currentDay.addTask(iterString.next());
			}
		}
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

	public ToDoListFrame getToDoFrame() {
		return toDoFrame;
	}

	// Add a currentDay to days arrayList
	public void addDay() {
		try {
			if (currentDay instanceof Cloneable) {
				Day newDay = new Day(Calendar.getInstance());
				newDay = (Day) currentDay.clone();
				days.add(newDay);
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Add, Edit, Delete task of currentDay
	 */
	public void addTask(String taskToBeAdded) {
		// checks if the current day already exists in the days arrayList
		// if yes, add new task to day that already exists
		// if not, add a new day (with currentDay's date) to day arrayList
		Iterator<Day> iterDay = getDays();
		Day temp = null;
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if ((currentDay.getToday().getTime().equals(temp.getToday().getTime()))) {
				temp.addTask(taskToBeAdded);
				return;
			}
		}
		currentDay.addTask(taskToBeAdded);
		addDay(); // add a new day into days arrayList
	}

	public void editTask(String old, String updated) {
		// find day with a date that matches currentDay's date
		// edit that day's task
		Iterator<Day> iterDay = getDays();
		Day temp = null;
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if (currentDay.getToday().equals(temp.getToday()))
				temp.editTask(old, updated);
		}
	}

	public void deleteTask(String taskSelected) {
		// find day with a date that matches currentDay's date
		// remove it from days arrayList
		Iterator<Day> iterDay = getDays();
		Day temp = null;
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if (currentDay.getToday().equals(temp.getToday()))
				temp.removeTask(taskSelected);
		}
	}

	/*
	 * Export all task in a specific month to a text file
	 *  Text file name in format MONTH_YEAR.txt
	 */
	public void export() {
		// generate the a filename in the format of month_year.txt as a String
		String fileName = currentDay.getMonth() + "_" + currentDay.getYear() + ".txt";

		String content = "";
		Iterator<Day> iterDay = getDays();
		Day temp = null;
		// iterate thru arrayList of days, check if a day has month that matches month
		// of currentDay
		// if T, iterate thru tasks arrayList of that day and append each task to content
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if(temp.getYear() == currentDay.getYear()) {
				if (temp.getMonth().equals(currentDay.getMonth())) {
					Iterator<String> iterString = temp.getTasks();
					while (iterString.hasNext())
						content += "-" + iterString.next() + "\r\n";
				}
			}
		}
		WriteToFile writer = new WriteToFile(fileName, content);
		System.out.printf("File %s is written\n", fileName);
	}
}