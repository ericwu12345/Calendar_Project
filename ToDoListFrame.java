import java.awt.*;
import java.util.*;
import javax.swing.*;

/*
 * A view
 * Displays To-Do list in a window
 */
public class ToDoListFrame {
	private Data model;
	Iterator<String> iterString;

	public ToDoListFrame(Data m) {
		model = m;

		JFrame frame = new JFrame("To Do List");
		frame.setLayout(new BorderLayout());
		
		// Displays currentDate 
		JLabel todayDate = new JLabel(model.getCurrentDate());

		/*
		 * Create a ListModel that takes Strings from the task arrayList and displays them
		 */
		DefaultListModel listModel = new DefaultListModel();
		while (model.getCurrentDay().getTasks().hasNext())
			listModel.addElement(model.getCurrentDay().getTasks().next());
		JList list = new JList(listModel);
		JScrollPane scrollPane = new JScrollPane(list);

		JTextField text = new JTextField();

		/*
		 * Create buttons
		 */
		JButton deleteButton = new JButton("Delete Task");
		deleteButton.addActionListener(event -> {
			int selected = list.getSelectedIndex();
			String taskSelected = list.getSelectedValue().toString();
			if (selected >= 0) // if list is not empty
				listModel.removeElementAt(selected); // update display	
			model.deleteTask(taskSelected); // remove task from task arrayList
		});

		JButton editButton = new JButton("Edit Task");
		editButton.addActionListener(event -> {
			int selected = list.getSelectedIndex();
			String taskSelected = list.getSelectedValue().toString();
			if (text.getText().equals("")) // if text field is empty, do nothing
				return;
			try {
				model.editTask(taskSelected, text.getText()); // update task arrayList
				listModel.removeElementAt(selected);	// update display
				listModel.add(selected, text.getText()); // update display				
				text.setText("");	// clear text field
			} catch (Exception e) {
				text.setText("");
			}
		});

		JButton addButton = new JButton("Add Task");
		addButton.addActionListener(event -> {
			try {
				model.addTask(text.getText()); // add new task to task arrayList
				listModel.addElement(text.getText()); // add new task to display
				text.setText(""); 
			} catch (Exception e) {
				text.setText("");
			}
		});

		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(event -> {
			model.export();
		});

		/*
		 *  Adding buttons to a panel
		 */
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(deleteButton);
		buttonPanel.add(editButton);
		buttonPanel.add(addButton);
		buttonPanel.add(exportButton);

		/*
		 *  Adding textfield and buttons to the same panel
		 */
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(text, BorderLayout.NORTH);
		panel.add(buttonPanel, BorderLayout.CENTER);

		/*
		 *  Adding all components to the frame
		 */
		frame.add(todayDate, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);

		frame.setSize(400, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}