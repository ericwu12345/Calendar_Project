import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.util.Calendar;
import java.time.Month;

/*
 * A view
 * Displays the calendar in a window
 */
public class CalendarFrame {

    private Data model;
    private MonthlyTasksFrame monthFrame;
    private static Calendar c = Calendar.getInstance();
    private static int currentYear;       //keep track of current year
    private static int currentMonthOfYear;  //keep track of current month
    private static int currentDayOfMonth;   //keep track of current day
    
    private static JLabel placeHolder;   //keep track of the currently selected date label
    private static int selectedYear;    //keep track of the currently selected Year
    private static int selectedMonth;   //keep track of the currently selected Month
    private static int selectedDay;     //keep track of the currently selected Day

    public CalendarFrame(Data m) {
        model = m;
        monthFrame = new MonthlyTasksFrame(m);
        JFrame frame = new JFrame("Calendar Frame");
        frame.setLayout(new BorderLayout());  //border layout for the frame
        
        JPanel topPanel = new JPanel();     //top panel of the calendar frame
        topPanel.setBackground(Color.orange);   //orange color!
        topPanel.setLayout(new FlowLayout());   //Flow layout formatting

        currentYear = c.get(Calendar.YEAR);  //set currentYear
        currentMonthOfYear = c.get(Calendar.MONTH); //set currentMonth
        currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH); //set current day
        selectedYear = currentYear;  //initially current year is also the selected year
        selectedMonth = currentMonthOfYear;  //same
        selectedDay = currentDayOfMonth;  //same
        
        JTextField year = new JTextField(Integer.toString(currentYear));   
        String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth)
                + "/" + Integer.toString(currentYear);
        JLabel todayDate = new JLabel(today);

        JPanel bottomPanel = new JPanel();  //bottom Panel which has today's date
        bottomPanel.setBackground(Color.orange); 
        bottomPanel.add(todayDate); 

        String month = Month.of(currentMonthOfYear + 1).name(); 
        JLabel currentMonth = new JLabel(month);                    //Label of the current month
        currentMonth.addMouseListener(new MouseAdapter() {          //which would be displayed on the top
            @Override                                               //also have listener that displays
            public void mouseClicked(MouseEvent e) {                //all the tasks of that much
                monthFrame.makeNewFrame();
            }
        });

        JPanel middlePanel = new JPanel();          //the panel with the days of week and days
        middlePanel.setLayout(new GridLayout(7, 7));  //this panel has a gridlayout
        JLabel sunday = new JLabel("Sun");     //the days of the week
        JLabel monday = new JLabel("Mon");
        JLabel tuesday = new JLabel("Tue");
        JLabel wednesday = new JLabel("Wed");
        JLabel thursday = new JLabel("Thu");
        JLabel friday = new JLabel("Fri");
        JLabel saturday = new JLabel("Sat");

        middlePanel.add(sunday);  //adding them to the panel
        middlePanel.add(monday);
        middlePanel.add(tuesday);
        middlePanel.add(wednesday);
        middlePanel.add(thursday);
        middlePanel.add(friday);
        middlePanel.add(saturday);

        Calendar c1 = Calendar.getInstance(); //using this to get the day of week the month starts 
        c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), 1);      //and number of days in month
        Calendar c2 = Calendar.getInstance();   //getting the previous month for the greyed out days
        if (c2.get(Calendar.MONTH) == 0) {  //if that month is Januaray, we get the December the year before.
            c2.set(c2.get(Calendar.YEAR - 1), c2.get(Calendar.DECEMBER), 1);
        } else {   //otherwise we just get the month prior on the same year
            c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH) - 1, 1);
        }
        int monthPrevious = c2.getActualMaximum(Calendar.DAY_OF_MONTH);  //the number of days in the last month
        int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);  //the number of days in this current month
        int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);    //getting the day of week the current month starts
        int dayCounter = 1;     //counter to keep track of the current month's days
        int nextCounter = 1;    //counter to keep track of last month's days
        int previousDays = monthPrevious - startOfMonth + 2;  //the first greyed out date
        
        //adding the correct days to the calendar
        for (int i = 0; i < 7; i++) {       
            final JLabel days;
            if (startOfMonth > i + 1) {  //check for greyed out dates
                String day = Integer.toString(previousDays);
                previousDays++;
                days = new JLabel(day);
                days.setForeground(Color.GRAY);
                middlePanel.add(days);
            } else { //otherwise it is a normal date
                String day = Integer.toString(dayCounter);
                final int currentDay = dayCounter;
                days = new JLabel(day);
                days.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        placeHolder.setForeground(Color.BLACK);  //replace the old highlighted date
                        placeHolder = days;     //set placeholder to the current date
                        currentDayOfMonth = currentDay;
                        selectedDay = currentDayOfMonth;
                        days.setForeground(Color.RED); //change the current date to be red
                        m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                        m.getToDoFrame().updateCurrentDate();
                    }
                });
                if (currentDayOfMonth == currentDay) { //set the current date's label to be red
                    placeHolder = days;
                    days.setForeground(Color.RED);
                }
                dayCounter++;
                middlePanel.add(days);
            }
        }
        
        //adding the rest of the days to the calender
        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                final JLabel days;
                if (dayCounter <= numberOfDays) {  //while there is still more days to add in the current month
                    final int currentDay = dayCounter;
                    String day = Integer.toString(dayCounter);
                    dayCounter++;
                    days = new JLabel(day);
                    days.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            placeHolder.setForeground(Color.BLACK);
                            placeHolder = days;
                            currentDayOfMonth = currentDay;
                            selectedDay = currentDayOfMonth;
                            m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                            m.getToDoFrame().updateCurrentDate();
                            days.setForeground(Color.RED);
                        }
                    });
                    if (currentDayOfMonth == currentDay) {
                        placeHolder = days;
                        days.setForeground(Color.RED);
                    }
                    middlePanel.add(days);
                } else {
                    String day = Integer.toString(nextCounter);
                    nextCounter++;
                    days = new JLabel(day);
                    days.setForeground(Color.GRAY);
                    middlePanel.add(days);
                }
            }
        }

        Box arrowKeys = Box.createVerticalBox();
        JButton upArrow = new BasicArrowButton(BasicArrowButton.NORTH);
        JButton downArrow = new BasicArrowButton(BasicArrowButton.SOUTH);
        arrowKeys.add(upArrow);
        arrowKeys.add(downArrow);

        JButton leftArrow = new BasicArrowButton(BasicArrowButton.WEST);
        JButton rightArrow = new BasicArrowButton(BasicArrowButton.EAST);

        rightArrow.addActionListener(new ActionListener() {   ///go to next month
            @Override
            public void actionPerformed(ActionEvent event) {
                if (currentMonthOfYear == 11) {  //if the month if December, goto to January and increment year
                    currentMonthOfYear = 0;
                    currentYear++;
                } else {  //otherwise just increase the month
                    currentMonthOfYear = (currentMonthOfYear + 1) % 12;
                }
                c.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                JTextField year = new JTextField(Integer.toString(currentYear));
                String month = Month.of(currentMonthOfYear + 1).name();
                JLabel currentMonth = new JLabel(month);
                currentMonth.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        monthFrame.makeNewFrame();
                    }
                });

                topPanel.removeAll();
                topPanel.repaint();
                topPanel.revalidate();

                topPanel.add(leftArrow);
                topPanel.add(currentMonth);
                topPanel.add(rightArrow);
                topPanel.add(year);
                topPanel.add(arrowKeys);
                topPanel.repaint();
                topPanel.revalidate();

                middlePanel.removeAll();
                middlePanel.repaint();
                middlePanel.revalidate();

                middlePanel.add(sunday);
                middlePanel.add(monday);
                middlePanel.add(tuesday);
                middlePanel.add(wednesday);
                middlePanel.add(thursday);
                middlePanel.add(friday);
                middlePanel.add(saturday);

                Calendar c1 = Calendar.getInstance();
                c1.set(currentYear, currentMonthOfYear, 1);
                Calendar c2 = Calendar.getInstance();
                if (currentMonthOfYear == 0) {  //if the month we are looking at is January, the previous month is December of last year
                    c2.set(currentYear - 1, c2.get(Calendar.DECEMBER), 1);
                } else {  //otherwise we just decrement the month to get the previous month
                    c2.set(currentYear, currentMonthOfYear - 1, 1);
                }
                int monthPrevious = c2.getActualMaximum(Calendar.DAY_OF_MONTH);
                int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
                int dayCounter = 1;
                int nextCounter = 1;
                int previousDays = monthPrevious - startOfMonth + 2;

                for (int i = 0; i < 7; i++) {
                    final JLabel days;
                    if (startOfMonth > i + 1) {
                        String day = Integer.toString(previousDays);
                        previousDays++;
                        days = new JLabel(day);
                        days.setForeground(Color.GRAY);
                        middlePanel.add(days);
                    } else {
                        String day = Integer.toString(dayCounter);
                        final int currentDay = dayCounter;
                        days = new JLabel(day);
                        days.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                currentDayOfMonth = currentDay;
                                placeHolder.setForeground(Color.BLACK);
                                placeHolder = days;
                                days.setForeground(Color.RED);
                                selectedDay = currentDayOfMonth;
                                selectedMonth = currentMonthOfYear;
                                selectedYear = currentYear;
                                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                m.getToDoFrame().updateCurrentDate();
                            }
                        });
                        if (selectedDay == currentDay && selectedMonth == currentMonthOfYear && selectedYear == currentYear) {
                            days.setForeground(Color.RED);
                            placeHolder = days;
                        }
                        dayCounter++;
                        middlePanel.add(days);
                    }
                }

                for (int i = 2; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        final JLabel days;
                        if (dayCounter <= numberOfDays) {
                            final int currentDay = dayCounter;
                            String day = Integer.toString(dayCounter);
                            dayCounter++;
                            days = new JLabel(day);
                            days.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    currentDayOfMonth = currentDay;
                                    placeHolder.setForeground(Color.BLACK);
                                    placeHolder = days;
                                    days.setForeground(Color.RED);
                                    selectedDay = currentDayOfMonth;
                                    selectedMonth = currentMonthOfYear;
                                    selectedYear = currentYear;
                                    m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                    m.getToDoFrame().updateCurrentDate();
                                }
                            });
                            if (selectedDay == currentDay && selectedMonth == currentMonthOfYear && selectedYear == currentYear) {
                                days.setForeground(Color.RED);
                                placeHolder = days;
                            }
                            middlePanel.add(days);
                        } else {
                            String day = Integer.toString(nextCounter);
                            nextCounter++;
                            days = new JLabel(day);
                            days.setForeground(Color.GRAY);
                            middlePanel.add(days);
                        }
                    }
                }

                middlePanel.repaint();
                middlePanel.revalidate();
            }
        });

        leftArrow.addActionListener(new ActionListener() {  //go the previous month
            @Override
            public void actionPerformed(ActionEvent event) {
                if (currentMonthOfYear == 0) {  //if the month is January, we have the previous month is December of the year before
                    currentMonthOfYear = 11; //December
                    currentYear--;
                } else { //otherwise we just decrement the month to get previous month
                    currentMonthOfYear = (currentMonthOfYear - 1) % 12;
                }
                c.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                JTextField year = new JTextField(Integer.toString(currentYear));
                String month = Month.of(currentMonthOfYear + 1).name();
                JLabel currentMonth = new JLabel(month);
                currentMonth.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        monthFrame.makeNewFrame();
                    }
                });

                topPanel.removeAll();
                topPanel.repaint();
                topPanel.revalidate();

                topPanel.add(leftArrow);
                topPanel.add(currentMonth);
                topPanel.add(rightArrow);
                topPanel.add(year);
                topPanel.add(arrowKeys);
                topPanel.repaint();
                topPanel.revalidate();

                middlePanel.removeAll();
                middlePanel.repaint();
                middlePanel.revalidate();

                middlePanel.add(sunday);
                middlePanel.add(monday);
                middlePanel.add(tuesday);
                middlePanel.add(wednesday);
                middlePanel.add(thursday);
                middlePanel.add(friday);
                middlePanel.add(saturday);

                Calendar c1 = Calendar.getInstance();
                c1.set(currentYear, currentMonthOfYear, 1);
                Calendar c2 = Calendar.getInstance();
                if (currentMonthOfYear == 0) {
                    c2.set(currentYear - 1, c2.get(Calendar.DECEMBER), 1);
                } else {
                    c2.set(currentYear, currentMonthOfYear - 1, 1);
                }
                int monthPrevious = c2.getActualMaximum(Calendar.DAY_OF_MONTH);
                int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
                int dayCounter = 1;
                int nextCounter = 1;
                int previousDays = monthPrevious - startOfMonth + 2;

                for (int i = 0; i < 7; i++) {
                    final JLabel days;
                    if (startOfMonth > i + 1) {
                        String day = Integer.toString(previousDays);
                        previousDays++;
                        days = new JLabel(day);
                        days.setForeground(Color.GRAY);
                        middlePanel.add(days);
                    } else {
                        String day = Integer.toString(dayCounter);
                        final int currentDay = dayCounter;
                        days = new JLabel(day);
                        days.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                currentDayOfMonth = currentDay;
                                placeHolder.setForeground(Color.BLACK);
                                placeHolder = days;
                                days.setForeground(Color.RED);
                                selectedDay = currentDayOfMonth;
                                selectedMonth = currentMonthOfYear;
                                selectedYear = currentYear;
                                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                m.getToDoFrame().updateCurrentDate();
                            }
                        });
                        if (selectedDay == currentDay && selectedMonth == currentMonthOfYear && selectedYear == currentYear) {
                            days.setForeground(Color.RED);
                            placeHolder = days;
                        }
                        dayCounter++;
                        middlePanel.add(days);
                    }
                }

                for (int i = 2; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        final JLabel days;
                        if (dayCounter <= numberOfDays) {
                            final int currentDay = dayCounter;
                            String day = Integer.toString(dayCounter);
                            dayCounter++;
                            days = new JLabel(day);
                            days.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    currentDayOfMonth = currentDay;
                                    placeHolder.setForeground(Color.BLACK);
                                    placeHolder = days;
                                    days.setForeground(Color.RED);
                                    selectedDay = currentDayOfMonth;
                                    selectedMonth = currentMonthOfYear;
                                    selectedYear = currentYear;
                                    m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                    m.getToDoFrame().updateCurrentDate();
                                }
                            });
                            if (selectedDay == currentDay && selectedMonth == currentMonthOfYear && selectedYear == currentYear) {
                                days.setForeground(Color.RED);
                                placeHolder = days;
                            }
                            middlePanel.add(days);
                        } else {
                            String day = Integer.toString(nextCounter);
                            nextCounter++;
                            days = new JLabel(day);
                            days.setForeground(Color.GRAY);
                            middlePanel.add(days);
                        }
                    }
                }

                middlePanel.repaint();
                middlePanel.revalidate();
            }
        });

        upArrow.addActionListener(new ActionListener() {  //increase year
            @Override
            public void actionPerformed(ActionEvent event) {
                currentYear++; //increase year counter
                c.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                String month = Month.of(currentMonthOfYear + 1).name();
                JLabel currentMonth = new JLabel(month);
                currentMonth.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        monthFrame.makeNewFrame();
                    }
                });
                JTextField year = new JTextField(Integer.toString(currentYear));

                topPanel.removeAll();
                topPanel.repaint();
                topPanel.revalidate();

                topPanel.add(leftArrow);
                topPanel.add(currentMonth);
                topPanel.add(rightArrow);
                topPanel.add(year);
                topPanel.add(arrowKeys);
                topPanel.repaint();
                topPanel.revalidate();

                middlePanel.removeAll();
                middlePanel.repaint();
                middlePanel.revalidate();

                middlePanel.add(sunday);
                middlePanel.add(monday);
                middlePanel.add(tuesday);
                middlePanel.add(wednesday);
                middlePanel.add(thursday);
                middlePanel.add(friday);
                middlePanel.add(saturday);

                Calendar c1 = Calendar.getInstance();
                c1.set(currentYear, currentMonthOfYear, 1);
                Calendar c2 = Calendar.getInstance();
                if (currentMonthOfYear == 0) {
                    c2.set(currentYear - 1, c2.get(Calendar.DECEMBER), 1);
                } else {
                    c2.set(currentYear, currentMonthOfYear - 1, 1);
                }
                int monthPrevious = c2.getActualMaximum(Calendar.DAY_OF_MONTH);
                int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
                int dayCounter = 1;
                int nextCounter = 1;
                int previousDays = monthPrevious - startOfMonth + 2;

                for (int i = 0; i < 7; i++) {
                    final JLabel days;
                    if (startOfMonth > i + 1) {
                        String day = Integer.toString(previousDays);
                        previousDays++;
                        days = new JLabel(day);
                        days.setForeground(Color.GRAY);
                        middlePanel.add(days);
                    } else {
                        String day = Integer.toString(dayCounter);
                        final int currentDay = dayCounter;
                        days = new JLabel(day);
                        days.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                currentDayOfMonth = currentDay;
                                placeHolder.setForeground(Color.BLACK);
                                placeHolder = days;
                                days.setForeground(Color.RED);
                                selectedDay = currentDayOfMonth;
                                selectedMonth = currentMonthOfYear;
                                selectedYear = currentYear;
                                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                m.getToDoFrame().updateCurrentDate();
                            }
                        });
                        if (selectedDay == currentDay && selectedMonth == currentMonthOfYear && selectedYear == currentYear) {
                            days.setForeground(Color.RED);
                            placeHolder = days;
                        }
                        dayCounter++;
                        middlePanel.add(days);
                    }
                }

                for (int i = 2; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        final JLabel days;
                        if (dayCounter <= numberOfDays) {
                            final int currentDay = dayCounter;
                            String day = Integer.toString(dayCounter);
                            dayCounter++;
                            days = new JLabel(day);
                            days.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    currentDayOfMonth = currentDay;
                                    placeHolder.setForeground(Color.BLACK);
                                    placeHolder = days;
                                    days.setForeground(Color.RED);
                                    selectedDay = currentDayOfMonth;
                                    selectedMonth = currentMonthOfYear;
                                    selectedYear = currentYear;
                                    m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                    m.getToDoFrame().updateCurrentDate();
                                }
                            });
                            if (selectedDay == currentDay && selectedMonth == currentMonthOfYear && selectedYear == currentYear) {
                                days.setForeground(Color.RED);
                                placeHolder = days;
                            }
                            middlePanel.add(days);
                        } else {
                            String day = Integer.toString(nextCounter);
                            nextCounter++;
                            days = new JLabel(day);
                            days.setForeground(Color.GRAY);
                            middlePanel.add(days);
                        }
                    }
                }
                middlePanel.repaint();
                middlePanel.revalidate();
            }
        });

        downArrow.addActionListener(new ActionListener() {  //decrement years
            @Override
            public void actionPerformed(ActionEvent event) {
                currentYear--;
                c.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                String month = Month.of(currentMonthOfYear + 1).name();
                JLabel currentMonth = new JLabel(month);
                currentMonth.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        monthFrame.makeNewFrame();
                    }
                });
                JTextField year = new JTextField(Integer.toString(currentYear));

                topPanel.removeAll();
                topPanel.repaint();
                topPanel.revalidate();

                topPanel.add(leftArrow);
                topPanel.add(currentMonth);
                topPanel.add(rightArrow);
                topPanel.add(year);
                topPanel.add(arrowKeys);
                topPanel.repaint();
                topPanel.revalidate();

                middlePanel.removeAll();
                middlePanel.repaint();
                middlePanel.revalidate();

                middlePanel.add(sunday);
                middlePanel.add(monday);
                middlePanel.add(tuesday);
                middlePanel.add(wednesday);
                middlePanel.add(thursday);
                middlePanel.add(friday);
                middlePanel.add(saturday);

                Calendar c1 = Calendar.getInstance();
                c1.set(currentYear, currentMonthOfYear, 1);
                Calendar c2 = Calendar.getInstance();
                if (currentMonthOfYear == 0) {
                    c2.set(currentYear - 1, c2.get(Calendar.DECEMBER), 1);
                } else {
                    c2.set(currentYear, currentMonthOfYear - 1, 1);
                }
                int monthPrevious = c2.getActualMaximum(Calendar.DAY_OF_MONTH);
                int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
                int dayCounter = 1;
                int nextCounter = 1;
                int previousDays = monthPrevious - startOfMonth + 2;

                for (int i = 0; i < 7; i++) {
                    final JLabel days;
                    if (startOfMonth > i + 1) {
                        String day = Integer.toString(previousDays);
                        previousDays++;
                        days = new JLabel(day);
                        days.setForeground(Color.GRAY);
                        middlePanel.add(days);
                    } else {
                        String day = Integer.toString(dayCounter);
                        final int currentDay = dayCounter;
                        days = new JLabel(day);
                        days.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                currentDayOfMonth = currentDay;
                                placeHolder.setForeground(Color.BLACK);
                                placeHolder = days;
                                days.setForeground(Color.RED);
                                selectedDay = currentDayOfMonth;
                                selectedMonth = currentMonthOfYear;
                                selectedYear = currentYear;
                                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                m.getToDoFrame().updateCurrentDate();
                            }
                        });
                        if (selectedDay == currentDay && selectedMonth == currentMonthOfYear && selectedYear == currentYear) {
                            days.setForeground(Color.RED);
                            placeHolder = days;
                        }
                        dayCounter++;
                        middlePanel.add(days);
                    }
                }

                for (int i = 2; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        final JLabel days;
                        if (dayCounter <= numberOfDays) {
                            final int currentDay = dayCounter;
                            String day = Integer.toString(dayCounter);
                            dayCounter++;
                            days = new JLabel(day);
                            days.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    currentDayOfMonth = currentDay;
                                    placeHolder.setForeground(Color.BLACK);
                                    placeHolder = days;
                                    days.setForeground(Color.RED);
                                    selectedDay = currentDayOfMonth;
                                    selectedMonth = currentMonthOfYear;
                                    selectedYear = currentYear;
                                    m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                    m.getToDoFrame().updateCurrentDate();
                                }
                            });
                            if (selectedDay == currentDay && selectedMonth == currentMonthOfYear && selectedYear == currentYear) {
                                days.setForeground(Color.RED);
                                placeHolder = days;
                            }
                            middlePanel.add(days);
                        } else {
                            String day = Integer.toString(nextCounter);
                            nextCounter++;
                            days = new JLabel(day);
                            days.setForeground(Color.GRAY);
                            middlePanel.add(days);
                        }
                    }
                }
                middlePanel.repaint();
                middlePanel.revalidate();
            }
        });

        topPanel.add(leftArrow);
        topPanel.add(currentMonth);
        topPanel.add(rightArrow);
        topPanel.add(year);
        topPanel.add(arrowKeys);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(400, 300);
		frame.setLocation(300, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
