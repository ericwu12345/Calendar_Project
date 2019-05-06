
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.text.DateFormatSymbols;
import java.time.Month;
import java.util.*;

/*
 * A view
 * Displays the calendar in a window
 */
public class CalendarFrame {

    private Data model;
    private static Calendar c = Calendar.getInstance();
    private static int currentYear;
    private static int currentMonthOfYear;
    private static int currentDayOfMonth;

    public CalendarFrame(Data m) {
        model = m;
        JFrame frame = new JFrame("Calendar Frame");
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        frame.setLayout(new BorderLayout());

        currentYear = c.get(Calendar.YEAR);
        currentMonthOfYear = c.get(Calendar.MONTH);
        currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        JTextField year = new JTextField(Integer.toString(currentYear));
        String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
        JLabel todayDate = new JLabel(today);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(todayDate);

        String month = Month.of(currentMonthOfYear + 1).name();
        JLabel currentMonth = new JLabel(month);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(7, 7));
        JLabel days;
        JLabel sunday = new JLabel("Sun");
        JLabel monday = new JLabel("Mon");
        JLabel tuesday = new JLabel("Tue");
        JLabel wednesday = new JLabel("Wed");
        JLabel thursday = new JLabel("Thu");
        JLabel friday = new JLabel("Fri");
        JLabel saturday = new JLabel("Sat");

        middlePanel.add(sunday);
        middlePanel.add(monday);
        middlePanel.add(tuesday);
        middlePanel.add(wednesday);
        middlePanel.add(thursday);
        middlePanel.add(friday);
        middlePanel.add(saturday);

        Calendar c1 = Calendar.getInstance();
        c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), 1);
        int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
        int dayCounter = 1;


        for (int i = 0; i < 7; i++) {
            if (startOfMonth > i + 1) {
                days = new JLabel("");
                middlePanel.add(days);
            } else {
                String day = Integer.toString(dayCounter);
                final int currentDay = dayCounter;
                days = new JLabel(day);
                days.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        currentDayOfMonth = currentDay;
                        String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                        JLabel todayDate = new JLabel(today);
                        m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                        bottomPanel.removeAll();
                        bottomPanel.repaint();
                        bottomPanel.revalidate();

                        bottomPanel.add(todayDate);
                        bottomPanel.repaint();
                        bottomPanel.revalidate();
                    }
                });
                dayCounter++;
                middlePanel.add(days);
            }
        }

        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (dayCounter <= numberOfDays) {
                    final int currentDay = dayCounter;
                    String day = Integer.toString(dayCounter);
                    dayCounter++;
                    days = new JLabel(day);
                    days.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            currentDayOfMonth = currentDay;
                            String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                            JLabel todayDate = new JLabel(today);
                            m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                            bottomPanel.removeAll();
                            bottomPanel.repaint();
                            bottomPanel.revalidate();

                            bottomPanel.add(todayDate);
                            bottomPanel.repaint();
                            bottomPanel.revalidate();
                        }
                    });
                    middlePanel.add(days);
                } else {
                    days = new JLabel("");
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
        rightArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentMonthOfYear = (currentMonthOfYear + 1) % 12;
                c.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                JTextField year = new JTextField(Integer.toString(currentYear));
                String month = Month.of(currentMonthOfYear + 1).name();
                JLabel currentMonth = new JLabel(month);

                String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                JLabel todayDate = new JLabel(today);

                bottomPanel.removeAll();
                bottomPanel.repaint();
                bottomPanel.revalidate();

                bottomPanel.add(todayDate);
                bottomPanel.repaint();
                bottomPanel.revalidate();

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

                JLabel days;

                middlePanel.add(sunday);
                middlePanel.add(monday);
                middlePanel.add(tuesday);
                middlePanel.add(wednesday);
                middlePanel.add(thursday);
                middlePanel.add(friday);
                middlePanel.add(saturday);

                Calendar c1 = Calendar.getInstance();

                c1.set(currentYear, currentMonthOfYear, 1);
                int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
                int dayCounter = 1;

                for (int i = 0; i < 7; i++) {
                    if (startOfMonth > i + 1) {
                        days = new JLabel("");
                        middlePanel.add(days);
                    } else {
                        String day = Integer.toString(dayCounter);
                        final int currentDay = dayCounter;
                        days = new JLabel(day);
                        days.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                currentDayOfMonth = currentDay;
                                String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                                JLabel todayDate = new JLabel(today);
                                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                bottomPanel.removeAll();
                                bottomPanel.repaint();
                                bottomPanel.revalidate();

                                bottomPanel.add(todayDate);
                                bottomPanel.repaint();
                                bottomPanel.revalidate();
                            }
                        });
                        dayCounter++;
                        middlePanel.add(days);
                    }
                }

                for (int i = 2; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (dayCounter <= numberOfDays) {
                            final int currentDay = dayCounter;
                            String day = Integer.toString(dayCounter);
                            dayCounter++;
                            days = new JLabel(day);
                            days.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    currentDayOfMonth = currentDay;
                                    String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                                    JLabel todayDate = new JLabel(today);
                                    m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                    bottomPanel.removeAll();
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();

                                    bottomPanel.add(todayDate);
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();
                                }
                            });
                            middlePanel.add(days);
                        } else {
                            days = new JLabel("");
                            middlePanel.add(days);
                        }
                    }
                }

                middlePanel.repaint();
                middlePanel.revalidate();
            }
        });

        leftArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (currentMonthOfYear == 0) {
                    currentMonthOfYear = 11;
                } else {
                    currentMonthOfYear = (currentMonthOfYear - 1) % 12;
                }
                c.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                JTextField year = new JTextField(Integer.toString(currentYear));
                String month = Month.of(currentMonthOfYear + 1).name();
                JLabel currentMonth = new JLabel(month);

                String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                JLabel todayDate = new JLabel(today);

                bottomPanel.removeAll();
                bottomPanel.repaint();
                bottomPanel.revalidate();

                bottomPanel.add(todayDate);
                bottomPanel.repaint();
                bottomPanel.revalidate();

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

                JLabel days;

                middlePanel.add(sunday);
                middlePanel.add(monday);
                middlePanel.add(tuesday);
                middlePanel.add(wednesday);
                middlePanel.add(thursday);
                middlePanel.add(friday);
                middlePanel.add(saturday);

                Calendar c1 = Calendar.getInstance();

                c1.set(currentYear, currentMonthOfYear, 1);
                int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
                int dayCounter = 1;

                for (int i = 0; i < 7; i++) {
                    if (startOfMonth > i + 1) {
                        days = new JLabel("");
                        middlePanel.add(days);
                    } else {
                        String day = Integer.toString(dayCounter);
                        final int currentDay = dayCounter;
                        days = new JLabel(day);
                        days.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                currentDayOfMonth = currentDay;
                                String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                                JLabel todayDate = new JLabel(today);
                                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                bottomPanel.removeAll();
                                bottomPanel.repaint();
                                bottomPanel.revalidate();

                                bottomPanel.add(todayDate);
                                bottomPanel.repaint();
                                bottomPanel.revalidate();
                            }
                        });
                        dayCounter++;
                        middlePanel.add(days);
                    }
                }

                for (int i = 2; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (dayCounter <= numberOfDays) {
                            final int currentDay = dayCounter;
                            String day = Integer.toString(dayCounter);
                            dayCounter++;
                            days = new JLabel(day);
                            days.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    currentDayOfMonth = currentDay;
                                    String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                                    JLabel todayDate = new JLabel(today);
                                    m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                    bottomPanel.removeAll();
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();

                                    bottomPanel.add(todayDate);
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();
                                }
                            });
                            middlePanel.add(days);
                        } else {
                            days = new JLabel("");
                            middlePanel.add(days);
                        }
                    }
                }

                middlePanel.repaint();
                middlePanel.revalidate();
            }
        });

        upArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentYear++;
                c.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                String month = Month.of(currentMonthOfYear + 1).name();
                JLabel currentMonth = new JLabel(month);
                JTextField year = new JTextField(Integer.toString(currentYear));

                String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                JLabel todayDate = new JLabel(today);

                bottomPanel.removeAll();
                bottomPanel.repaint();
                bottomPanel.revalidate();

                bottomPanel.add(todayDate);
                bottomPanel.repaint();
                bottomPanel.revalidate();

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

                JLabel days;

                middlePanel.add(sunday);
                middlePanel.add(monday);
                middlePanel.add(tuesday);
                middlePanel.add(wednesday);
                middlePanel.add(thursday);
                middlePanel.add(friday);
                middlePanel.add(saturday);

                Calendar c1 = Calendar.getInstance();

                c1.set(currentYear, currentMonthOfYear, 1);
                int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
                int dayCounter = 1;

                for (int i = 0; i < 7; i++) {
                    if (startOfMonth > i + 1) {
                        days = new JLabel("");
                        middlePanel.add(days);
                    } else {
                        String day = Integer.toString(dayCounter);
                        final int currentDay = dayCounter;
                        days = new JLabel(day);
                        days.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                currentDayOfMonth = currentDay;
                                String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                                JLabel todayDate = new JLabel(today);
                                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                bottomPanel.removeAll();
                                bottomPanel.repaint();
                                bottomPanel.revalidate();

                                bottomPanel.add(todayDate);
                                bottomPanel.repaint();
                                bottomPanel.revalidate();
                            }
                        });
                        dayCounter++;
                        middlePanel.add(days);
                    }
                }

                for (int i = 2; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (dayCounter <= numberOfDays) {
                            final int currentDay = dayCounter;
                            String day = Integer.toString(dayCounter);
                            dayCounter++;
                            days = new JLabel(day);
                            days.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    currentDayOfMonth = currentDay;
                                    String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                                    JLabel todayDate = new JLabel(today);
                                    m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                    bottomPanel.removeAll();
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();

                                    bottomPanel.add(todayDate);
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();
                                }
                            });
                            middlePanel.add(days);
                        } else {
                            days = new JLabel("");
                            middlePanel.add(days);
                        }
                    }
                }

                middlePanel.repaint();
                middlePanel.revalidate();
            }
        });

        downArrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentYear--;
                c.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                String month = Month.of(currentMonthOfYear + 1).name();
                JLabel currentMonth = new JLabel(month);
                JTextField year = new JTextField(Integer.toString(currentYear));

                String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                JLabel todayDate = new JLabel(today);

                bottomPanel.removeAll();
                bottomPanel.repaint();
                bottomPanel.revalidate();

                bottomPanel.add(todayDate);
                bottomPanel.repaint();
                bottomPanel.revalidate();

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

                JLabel days;

                middlePanel.add(sunday);
                middlePanel.add(monday);
                middlePanel.add(tuesday);
                middlePanel.add(wednesday);
                middlePanel.add(thursday);
                middlePanel.add(friday);
                middlePanel.add(saturday);

                Calendar c1 = Calendar.getInstance();

                c1.set(currentYear, currentMonthOfYear, 1);
                int numberOfDays = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
                int startOfMonth = c1.get(Calendar.DAY_OF_WEEK);
                int dayCounter = 1;

                for (int i = 0; i < 7; i++) {
                    if (startOfMonth > i + 1) {
                        days = new JLabel("");
                        middlePanel.add(days);
                    } else {
                        String day = Integer.toString(dayCounter);
                        final int currentDay = dayCounter;
                        days = new JLabel(day);
                        days.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                currentDayOfMonth = currentDay;
                                String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                                JLabel todayDate = new JLabel(today);
                                m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                bottomPanel.removeAll();
                                bottomPanel.repaint();
                                bottomPanel.revalidate();

                                bottomPanel.add(todayDate);
                                bottomPanel.repaint();
                                bottomPanel.revalidate();
                            }
                        });
                        dayCounter++;
                        middlePanel.add(days);
                    }
                }

                for (int i = 2; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (dayCounter <= numberOfDays) {
                            final int currentDay = dayCounter;
                            String day = Integer.toString(dayCounter);
                            dayCounter++;
                            days = new JLabel(day);
                            days.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    currentDayOfMonth = currentDay;
                                    String today = "Today: " + Integer.toString(currentMonthOfYear + 1) + "/" + Integer.toString(currentDayOfMonth) + "/" + Integer.toString(currentYear);
                                    JLabel todayDate = new JLabel(today);
                                    m.setCurrentDay(currentYear, currentMonthOfYear, currentDayOfMonth);
                                    bottomPanel.removeAll();
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();

                                    bottomPanel.add(todayDate);
                                    bottomPanel.repaint();
                                    bottomPanel.revalidate();
                                }
                            });
                            middlePanel.add(days);
                        } else {
                            days = new JLabel("");
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
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
