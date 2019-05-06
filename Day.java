import java.util.*;

/*
 * A day, each day contains a list of tasks
 */
public class Day {

	private ArrayList<String> tasks;
	Iterator<String> iterString;
	private Calendar today;

	public Day(Calendar c) {
		today = Calendar.getInstance();
		today.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		tasks = new ArrayList<>();
	}

	/*
	 * Setters & Getters
	 */
	public Calendar getToday() {
		return today;
	}
	public int getDay() {
		return today.get(Calendar.DAY_OF_MONTH);	}

	public void setDay(int day) {
		today.set(Calendar.DAY_OF_MONTH, day);
	}

	public String getMonth() {
		int i = today.get(Calendar.MONTH);
		String s = null;
		switch (i) {
		case 0:
			s = "January";
		case 1:
			s = "February";
			break;
		case 2:
			s = "March";
			break;
		case 3:
			s = "April";
			break;
		case 4:
			s = "May";
			break;
		case 5:
			s = "June";
			break;
		case 6:
			s = "July";
			break;
		case 7:
			s = "August";
			break;
		case 8:
			s = "September";
			break;
		case 9:
			s = "October";
			break;
		case 10:
			s = "November";
			break;
		case 11:
			s = "December";
			break;
		default:
			s = "invalid month";
		}
		return s;		
	}

	public void setMonth(int month) {
		today.set(Calendar.MONTH, month);
	}

	public int getYear() {
		return today.get(Calendar.YEAR);
	}

	public void setYear(int year) {
		today.set(Calendar.YEAR, year);
	}

	public Iterator<String> getTasks() {
		return new Iterator<String>() {
			public boolean hasNext() {
				return current < tasks.size();
			}

			public String next() {
				return tasks.get(current++);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

			private int current = 0;
		};
	}

	public String getDayOfWeek() {
		int i = today.get(Calendar.DAY_OF_WEEK);
		String s = null;
		switch (i) {
		case 1:
			s = "Sunday";
			break;
		case 2:
			s = "Monday";
			break;
		case 3:
			s = "Tuesday";
			break;
		case 4:
			s = "Wednesday";
			break;
		case 5:
			s = "Thursday";
			break;
		case 6:
			s = "Friday";
			break;
		case 7:
			s = "Saturday";
			break;
		default:
			s = "invalid day of the week";
		}
		return s;
	}	

		/*
		 * Add, Remove, Edit from tasks arrayList
		 */
		public void addTask(String aTask) {
			tasks.add(aTask);
		}

		public void removeTask(String taskSelected) {
			tasks.remove(taskSelected);
		}

		public void editTask(String old, String updated) {
			// traverse arrayList of tasks and find the one that match the selected task
			// replace old task with updated task
			iterString = getTasks();
			int index = 0;
			while(iterString.hasNext()) {
				String temp = iterString.next();
				if(temp.equals(old))
					tasks.set(index, updated);
				index++;
			}
		}

}