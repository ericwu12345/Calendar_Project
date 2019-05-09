import java.awt.*;
import java.util.*;
import javax.swing.*;


/*
 * A view
 * Displays all the tasks in a month in a window
 */
public class MonthlyTasksFrame {
	private Data model;
	private JFrame frame;

	public MonthlyTasksFrame(Data m) {
		model = m;
		makeAFrame();
		frame.setVisible(false);

	}

	public void makeNewFrame() {
		frame.setVisible(false);
		makeAFrame();
	}

	public void makeAFrame() {
		frame = new JFrame("Monthly Tasks");

		/*
		 *  Label displaying month
		 */
		JLabel month = new JLabel(model.getCurrentDay().getMonth() + "'s To Do List:");
		month.setFont(new Font("Times New Roman", Font.ITALIC, 18));

		/*
		 *  List displaying monthly tasks
		 */
		DefaultListModel listModel = new DefaultListModel();
		String content = "";
		Iterator<Day> iterDay = model.getDays();
		Day temp = null;
		while (iterDay.hasNext()) {
			temp = iterDay.next();
			if(temp.getYear() == model.getCurrentDay().getYear()) {
				if (temp.getMonth().equals(model.getCurrentDay().getMonth())) {
					Iterator<String> iterString = temp.getTasks();
					while (iterString.hasNext())
						listModel.addElement(iterString.next());
				}
			}
		}
		JList list = new JList(listModel);
		JScrollPane scrollPane = new JScrollPane(list);
		list.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		scrollPane.setPreferredSize(new Dimension(225, 290));
		JPanel listPanel = new JPanel();
		listPanel.setBackground(Color.ORANGE);
		listPanel.add(scrollPane);

		/*
		 * Add components to frame
		 */
		frame.setLayout(new BorderLayout());
		frame.add(month, BorderLayout.NORTH);
		frame.add(listPanel, BorderLayout.CENTER);

		/*
		 * Frame settings
		 */
		frame.setSize(250, 350);
		frame.setLocation(1200, 275);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}