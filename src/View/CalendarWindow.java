package View;

import Controller.*;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
//import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Calendar;


public class CalendarWindow {
    public int yearBound, monthBound, dayBound, yearToday, monthToday;
    private CalendarController controller;

    /**** Swing Components ****/
    public JLabel monthLabel, yearLabel;
    public JButton btnPrev, btnNext, btnAdd, btnImport, btnExport;
    public JComboBox cmbYear;
    public JFrame frmMain;
    public Container pane;
    public JScrollPane scrollCalendarTable;
    public JPanel calendarPanel;

    /**** Calendar Table Components ***/
    public JTable calendarTable;
    public DefaultTableModel modelCalendarTable;

    public void refreshCalendar(int month, int year)
    {
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som, i, j;

        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= yearBound-10)
            btnPrev.setEnabled(false);
        if (month == 11 && year >= yearBound+100)
            btnNext.setEnabled(false);

        monthLabel.setText(months[month]);
        monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 360, 50);

        cmbYear.setSelectedItem(""+year);

        for (i = 0; i < 6; i++)
            for (j = 0; j < 7; j++)
                modelCalendarTable.setValueAt(null, i, j);

        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); // number of days
        som = cal.get(GregorianCalendar.DAY_OF_WEEK); // day of start of month

//        System.out.println("nod = " + nod + "\nsom = " + som);
        for (i = 1; i <= nod; i++)
        {
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            Calendar c = Calendar.getInstance();
            c.set(year, month, i, 0, 0, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date d = c.getTime();
            ArrayList<String> eventNames = controller.getEventNames(d);
            ArrayList<Color> eventColors = controller.getEventColors(d);
            StringBuilder sb = new StringBuilder();
            sb.append("<html>" + i + " <br/>");
//            CustomPanel pane = new CustomPanel(i + "", 3);
            for (j = 0; j < eventNames.size() && j < 3; j++) {
//                pane.addRow(eventNames.get(j), eventColors.get(j));
                Color clr = eventColors.get(j);
                String rgbCode = "rgb(" + clr.getRed() + "," + clr.getGreen() + "," + clr.getBlue() + ")";
                String name = eventNames.get(j);
                sb.append("<font color='" + rgbCode + "'><b>" + name + "</b></font><br/>");
            }
            sb.append("</html>");
            modelCalendarTable.setValueAt(sb.toString(), row, column);
//            modelCalendarTable.setValueAt(pane, row, column);
        }

//        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new DateRenderer());
        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
    }

    // OLD CONSTRUCTOR
    public CalendarWindow()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {}

        frmMain = new JFrame ("Calendar Application");
        frmMain.setSize(660, 750);
        pane = frmMain.getContentPane();
        pane.setLayout(null);
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        monthLabel = new JLabel ("January");
        yearLabel = new JLabel ("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("<<");
        btnNext = new JButton (">>");
        modelCalendarTable = new DefaultTableModel()
        {
            public boolean isCellEditable(int rowIndex, int mColIndex)
            {
                return true;
            }
        };

        calendarTable = new JTable(modelCalendarTable);
        calendarTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
                int col = calendarTable.getSelectedColumn();
                int row = calendarTable.getSelectedRow();
            }
        });

        scrollCalendarTable = new JScrollPane(calendarTable);
        calendarPanel = new JPanel(null);

        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));

        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        cmbYear.addActionListener(new cmbYear_Action());

        pane.add(calendarPanel);
        calendarPanel.add(monthLabel);
        calendarPanel.add(yearLabel);
        calendarPanel.add(cmbYear);
        calendarPanel.add(btnPrev);
        calendarPanel.add(btnNext);
        calendarPanel.add(scrollCalendarTable);

        calendarPanel.setBounds(0, 0, 640, 670);
        monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 200, 50);
        yearLabel.setBounds(20, 610, 160, 40);
        cmbYear.setBounds(460, 610, 160, 40);
        btnPrev.setBounds(20, 50, 100, 50);
        btnNext.setBounds(520, 50, 100, 50);
        scrollCalendarTable.setBounds(20, 100, 600, 500);

        frmMain.setResizable(false);
        frmMain.setVisible(true);

        GregorianCalendar cal = new GregorianCalendar();
        dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        monthBound = cal.get(GregorianCalendar.MONTH);
        yearBound = cal.get(GregorianCalendar.YEAR);
        monthToday = monthBound;
        yearToday = yearBound;

        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            modelCalendarTable.addColumn(headers[i]);
        }

        calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);

        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        calendarTable.setRowHeight(76);
        modelCalendarTable.setColumnCount(7);
        modelCalendarTable.setRowCount(6);

        for (int i = yearBound-100; i <= yearBound+100; i++)
        {
            cmbYear.addItem(String.valueOf(i));
        }

        refreshCalendar (monthBound, yearBound); //Refresh calendar
    }

    // NEW CONSTRUCTOR
    public CalendarWindow(CalendarController controller)
    {
        this.controller = controller;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {}

        frmMain = new JFrame ("Calendar Application");
        frmMain.setSize(660, 750);
        pane = frmMain.getContentPane();
        pane.setLayout(null);
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        monthLabel = new JLabel ("January");
        monthLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        yearLabel = new JLabel ("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("<<");
        btnNext = new JButton (">>");
//        btnAdd = new JButton ("<html><font color='white'>+ New Event</font></html>");
        btnAdd = new JButton("+ New Event");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 11));
//        btnAdd.setBackground(Color.BLUE);
//        btnAdd.setForeground(Color.WHITE);
        btnAdd.setOpaque(true);
        btnAdd.setBorderPainted(false);
        btnExport = new JButton ("Export Events");
        btnExport.setFont(new Font("Arial", Font.PLAIN, 11));
        btnImport = new JButton ("Import Events");
        btnImport.setFont(new Font("Arial", Font.PLAIN, 11));

        modelCalendarTable = new DefaultTableModel()
        {
            public boolean isCellEditable(int rowIndex, int mColIndex)
            {
                return false;
            }
        };

        calendarTable = new JTable(modelCalendarTable);
        calendarTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
                Point p = evt.getPoint();
                int col = calendarTable.getSelectedColumn();
                int row = calendarTable.getSelectedRow();
                String s = (String)calendarTable.getModel().getValueAt(row, col);
                if (s != null) {
                    int day = Integer.parseInt(s.substring(6, 8).trim());
                    Calendar c = Calendar.getInstance();
                    c.set(yearToday, monthToday, day, 0, 0, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    if (controller.isViewDateWindowOpen() || controller.isAddEventWindowOpen()) {
                        if (controller.isAddEventWindowOpen()) controller.closeAddEventWindow();
                        if (controller.isViewDateWindowOpen()) controller.closeViewDateWindow();
                    } else {
                        Rectangle neighborCell = calendarTable.getCellRect(row + 1, col + 1, true);
//                        p.translate(currentCell.x, currentCell.y);
                        Point frmPt = frmMain.getLocation();
                        Point pt = new Point(frmPt.x + neighborCell.x + 22, frmPt.y + neighborCell.y + 70);
                        controller.openViewDateWindow(c.getTime(), pt);
                    }
                } else {
                    if (controller.isAddEventWindowOpen()) controller.closeAddEventWindow();
                    if (controller.isViewDateWindowOpen()) controller.closeViewDateWindow();
                }
            }
        });

        scrollCalendarTable = new JScrollPane(calendarTable);
        calendarPanel = new JPanel(null);

//        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));

        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        btnImport.addActionListener(new btnImport_Action());
        btnExport.addActionListener(new btnExport_Action());
        btnAdd.addActionListener(new btnAdd_Action());

        cmbYear.addActionListener(new cmbYear_Action());

        pane.add(calendarPanel);
        calendarPanel.add(monthLabel);
        calendarPanel.add(yearLabel);
        calendarPanel.add(cmbYear);
        calendarPanel.add(btnPrev);
        calendarPanel.add(btnNext);
        calendarPanel.add(btnAdd);
        calendarPanel.add(btnImport);
        calendarPanel.add(btnExport);
        calendarPanel.add(scrollCalendarTable);
        calendarPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller.isViewDateWindowOpen()) controller.closeViewDateWindow();
                if (controller.isAddEventWindowOpen()) controller.closeAddEventWindow();
            }
        });

        calendarPanel.setBounds(0, 0, 640, 670);
        monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 200, 50);
        yearLabel.setBounds(375, 610, 85, 40);
        cmbYear.setBounds(460, 610, 160, 40);
        btnPrev.setBounds(20, 50, 100, 50);
        btnNext.setBounds(520, 50, 100, 50);
        btnAdd.setBounds(20, 610, 100, 40);
        btnImport.setBounds(140, 610, 100, 40);
        btnExport.setBounds(260, 610, 100, 40);
        scrollCalendarTable.setBounds(20, 100, 600, 500);

        frmMain.setResizable(false);
        frmMain.setVisible(true);

        GregorianCalendar cal = new GregorianCalendar();
        dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        monthBound = cal.get(GregorianCalendar.MONTH);
        yearBound = cal.get(GregorianCalendar.YEAR);
        System.out.println(yearBound);
        monthToday = monthBound;
        yearToday = yearBound;

        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            modelCalendarTable.addColumn(headers[i]);
        }

        calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);

//        calendarTable.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        calendarTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));

        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        calendarTable.setRowHeight(76);
        modelCalendarTable.setColumnCount(7);
        modelCalendarTable.setRowCount(6);

        for (int i = yearBound-100; i <= yearBound+100; i++)
        {
            cmbYear.addItem(String.valueOf(i));
        }

        refreshCalendar (monthBound, yearBound); //Refresh calendar
    }

    class btnPrev_Action implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            if (monthToday == 0)
            {
                monthToday = 11;
                yearToday -= 1;
            }
            else
            {
                monthToday -= 1;
            }
            refreshCalendar(monthToday, yearToday);
        }
    }
    class btnNext_Action implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            if (monthToday == 11)
            {
                monthToday = 0;
                yearToday += 1;
            }
            else
            {
                monthToday += 1;
            }
            refreshCalendar(monthToday, yearToday);
        }
    }
    class btnAdd_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.openAddEventWindow();
        }
    }
    class btnImport_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.importEvents();
            refreshCalendar(monthToday, yearToday);
        }
    }
    class btnExport_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.exportEvents();
        }
    }
    class cmbYear_Action implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            if (cmbYear.getSelectedItem() != null)
            {
                String b = cmbYear.getSelectedItem().toString();
                yearToday = Integer.parseInt(b);
                refreshCalendar(monthToday, yearToday);
            }
        }
    }
}
