package View;

import Controller.*;

import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.Calendar;

public class CalendarProgram {
    public int yearBound, monthBound, dayBound, yearToday, monthToday;
    private CalendarController controller;

    /**** Swing Components ****/
    public JLabel monthLabel, monthLabel2, yearLabel;
    public JButton btnPrev, btnNext, btnAdd, btnImport, btnExport, btnFB, btnSMS;
    public JComboBox cmbYear, cmbMonth;
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
        cmbMonth.setSelectedIndex(month);

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
            ArrayList<String> eventNames = controller.getEventNames(c);
            ArrayList<Color> eventTextColors = controller.getEventTextColors(c);
            ArrayList<Color> eventBackgroundColors = controller.getEventBackgroundColors(c);
            StringBuilder sb = new StringBuilder();
            sb.append("<html>" + i + " <br/>");
            for (j = 0; j < eventNames.size() && j < 3; j++) {
                Color txtClr = eventTextColors.get(j);
                Color backClr = eventBackgroundColors.get(j);
                String fontClr = "rgb(" + txtClr.getRed() + "," + txtClr.getGreen() + "," + txtClr.getBlue() + ")";
                String backgroundClr = String.format("#%02x%02x%02x", backClr.getRed(), backClr.getGreen(), backClr.getBlue());
                String name = eventNames.get(j);
//                sb.append("<font color='" + rgbCode + "'><b>" + name + "</b></font><br/>");
                sb.append("<p style=\"background-color: " +  backgroundClr + "\"><font color='" + fontClr + "'><b>" + name + "</b></font></p>");

            }
            sb.append("</html>");
            modelCalendarTable.setValueAt(sb.toString(), row, column);
        }

//        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new DateRenderer());
        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
    }

    // OLD CONSTRUCTOR
    public CalendarProgram()
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
    public CalendarProgram(CalendarController controller)
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
        yearLabel = new JLabel ("Year:");
        cmbYear = new JComboBox();
        String[] monthOptions = {"January", "February", "March", "April",
                "May", "June", "July", "August", "September",
                "October", "November", "December"};
        monthLabel2 = new JLabel ("Month:");
        cmbMonth = new JComboBox(monthOptions);
        btnPrev = new JButton ("<<");
        btnNext = new JButton (">>");
        btnAdd = new JButton("+ New Event");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setOpaque(true);
        btnAdd.setBorderPainted(false);
        btnExport = new JButton ("");
        btnExport.setOpaque(false);
        btnExport.setContentAreaFilled(false);
        btnExport.setBorderPainted(false);
        btnImport = new JButton ("");
        btnImport.setOpaque(false);
        btnImport.setContentAreaFilled(false);
        btnImport.setBorderPainted(false);
        btnFB = new JButton("");
        btnFB.setOpaque(false);
        btnFB.setContentAreaFilled(false);
        btnFB.setBorderPainted(false);
        btnSMS = new JButton("");
        btnSMS.setOpaque(false);
        btnSMS.setContentAreaFilled(false);
        btnSMS.setBorderPainted(false);
        try {
            URL resource = getClass().getClassLoader().getResource("export.png");
            File img = Paths.get(resource.toURI()).toFile();
            btnExport.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 20, 30)));
            resource = getClass().getClassLoader().getResource("import.png");
            img = Paths.get(resource.toURI()).toFile();
            btnImport.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 20, 30)));
            resource = getClass().getClassLoader().getResource("fb.png");
            img = Paths.get(resource.toURI()).toFile();
            btnFB.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
            resource = getClass().getClassLoader().getResource("sms.png");
            img = Paths.get(resource.toURI()).toFile();
            btnSMS.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 30, 30)));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


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
                Point p = evt.getLocationOnScreen();
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
                        controller.openViewDateWindow(c, new Point(p.x, p.y));
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
        btnFB.addActionListener(new btnFB_Action());
        btnSMS.addActionListener(new btnSMS_Action());

        //((JLabel)cmbYear.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbYear.addActionListener(new cmbYear_Action());

        //((JLabel)cmbMonth.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        cmbMonth.addActionListener(new cmbMonth_Action());

        pane.add(calendarPanel);
        calendarPanel.add(monthLabel);
        calendarPanel.add(yearLabel);
        calendarPanel.add(monthLabel2);
        calendarPanel.add(cmbMonth);
        calendarPanel.add(cmbYear);
        calendarPanel.add(btnPrev);
        calendarPanel.add(btnNext);
        calendarPanel.add(btnAdd);
        calendarPanel.add(btnImport);
        calendarPanel.add(btnExport);
        calendarPanel.add(btnFB);
        calendarPanel.add(btnSMS);
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
        btnPrev.setBounds(20, 50, 100, 50);
        btnNext.setBounds(520, 50, 100, 50);
        btnAdd.setBounds(20, 610, 130, 40);
        btnImport.setBounds(150, 610, 40, 40);
        btnExport.setBounds(180, 610, 40, 40);
        btnFB.setBounds(235, 610, 40, 40);
        btnSMS.setBounds(275, 610, 40, 40);
        monthLabel2.setBounds(340, 610, 50, 40);
        cmbMonth.setBounds(380, 610, 118, 40);
        yearLabel.setBounds(503, 610, 35, 40);
        cmbYear.setBounds(533, 610, 90, 40);
        scrollCalendarTable.setBounds(20, 100, 600, 500);

        frmMain.setResizable(false);
        frmMain.setVisible(true);
        frmMain.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.saveEvents();
            }
        });

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

    class btnFB_Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.openFBWindow();
        }
    }

    class btnSMS_Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.openSMSWindow();
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

    class cmbMonth_Action implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            if (cmbMonth.getSelectedItem() != null)
            {
                //String b = cmbMonth.getSelectedIndex().toString();
                monthToday = cmbMonth.getSelectedIndex();
                refreshCalendar(monthToday, yearToday);
            }
        }
    }
}
