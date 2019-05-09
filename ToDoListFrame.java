import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
/*
 * A view
 * Displays a daily To-Do list in a window
 */
public class ToDoListFrame {
	private Data model;
	private JFrame frame;
	private DefaultListModel listModel;
	private JList list;
	private JScrollPane scrollPane;
	private JTextField text;
	private JButton deleteButton;
	private JButton editButton;
	private JButton addButton;
	private JButton exportButton;
	private static final DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

	public ToDoListFrame(Data m) {
		model = m;

		/*
		 * Create buttons
		 */
		deleteButton = new JButton("Delete Task");
		deleteButton.addActionListener(event -> {
			int selected = list.getSelectedIndex();
			String taskSelected = list.getSelectedValue().toString();
			if (selected >= 0) // if list is not empty
				listModel.removeElementAt(selected); // update display
			model.deleteTask(taskSelected); // remove task from task arrayList
		});

		editButton = new JButton("Edit Task");
		editButton.addActionListener(event -> {
			int selected = list.getSelectedIndex();
			String taskSelected = list.getSelectedValue().toString();
			if (text.getText().equals("")) // if text field is empty, do nothing
				return;
			try {
				model.editTask(taskSelected, text.getText()); // update task arrayList
				listModel.removeElementAt(selected); // update display
				listModel.add(selected, text.getText()); // update display
				text.setText(""); // clear text field
			} catch (Exception e) {
				text.setText("");
			}
		});

		addButton = new JButton("Add Task");
		addButton.addActionListener(event -> {
			if (text.getText().equals("")) // if text field is empty, do nothing
				return;
			try {
				model.addTask(text.getText()); // add new task to task arrayList
				listModel.addElement(text.getText()); // add new task to display
				text.setText("");
			} catch (Exception e) {
				text.setText("");
			}
		});

		exportButton = new JButton("Export");
		exportButton.addActionListener(event -> {
			model.export();
		});

		makeAFrame();
	}

	public void updateCurrentDate() {
		frame.setVisible(false);
		frame = new JFrame("Daily To Do List");
		frame.setLayout(new BorderLayout());

		String dayOfWeek = model.getCurrentDay().getDayOfWeek();
		String date = formatter.format(model.getCurrentDay().getToday().getTime());
		JLabel todayDate = new JLabel(dayOfWeek + ", " + date);
		todayDate.setFont(new Font("Default", Font.BOLD, 20));

		Iterator<Day> iterDay = model.getDays();
		Day temp = null;
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if ((model.getCurrentDay().getToday().equals(temp.getToday()))) {
				Iterator<String> iterString = model.getCurrentDay().getTasks();
				while (iterString.hasNext())
					listModel.addElement(iterString.next());
			} else {
				listModel.clear();
			}
		}

		makeAFrame();
	}

	public void makeAFrame() {
		frame = new JFrame("Daily To Do List");
		frame.setLayout(new BorderLayout());

		// Displays currentDate
		String dayOfWeek = model.getCurrentDay().getDayOfWeek();
		String date = formatter.format(model.getCurrentDay().getToday().getTime());
		JLabel todayDate = new JLabel(dayOfWeek + ", " + date);
		todayDate.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		
		/*
		 * Create a ListModel that takes Strings from the task arrayList and displays
		 * them
		 */
		listModel = new DefaultListModel();
		Iterator<Day> iterDay = model.getDays();
		Day temp = null;
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if ((model.getCurrentDay().getToday().getTime().equals(temp.getToday().getTime()))) {
				Iterator<String> iterString = temp.getTasks();
				while (iterString.hasNext()) {
					listModel.addElement(iterString.next());
				}
			}
		}
		list = new JList(listModel);
		scrollPane = new JScrollPane(list);
		list.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		scrollPane.setPreferredSize(new Dimension(375, 185));
		JPanel listPanel = new JPanel();
		listPanel.setBackground(Color.ORANGE);
		listPanel.add(scrollPane);

		text = new JTextField();

		/*
		 * Adding buttons to a panel
		 */
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(deleteButton);
		buttonPanel.add(editButton);
		buttonPanel.add(addButton);
		buttonPanel.add(exportButton);

		/*
		 * Adding textfield and buttons to the same panel
		 */
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(text, BorderLayout.NORTH);
		panel.add(buttonPanel, BorderLayout.CENTER);

		/*
		 * Adding all components to the frame
		 */
		frame.add(todayDate, BorderLayout.NORTH);
		frame.add(listPanel, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);

		/*
		 * Frame settings
		 */
		frame.setSize(400, 300);
		frame.setLocation(750, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}