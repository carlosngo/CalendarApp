package View;

import Model.*;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
//import java.awt.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class CalendarView {
    public int yearBound, monthBound, dayBound, yearToday, monthToday;
    public TreeMap<Date, TreeSet<Model.Event>> events;
    private EventIO io;

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
            CustomPanel pane = new CustomPanel(i + "");
            pane.addRow("CCS Day!", Color.YELLOW);
            // Instantiate a CustomPanel and add in model.

            modelCalendarTable.setValueAt(pane, row, column);
//            modelCalendarTable.setValueAt("Day\n" + i, row, column);
        }

        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new DateRenderer());
    }

    public CalendarView()
    {
        io = new EventIO();
        events = new TreeMap<>();

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
        monthLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        yearLabel = new JLabel ("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("<<");
        btnNext = new JButton (">>");
        btnAdd = new JButton ("+ New Event");
        btnExport = new JButton ("Export Events");
        btnImport = new JButton ("Import Events");

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
                // insert pop-up here

                int col = calendarTable.getSelectedColumn();
                int row = calendarTable.getSelectedRow();
            }
        });

        scrollCalendarTable = new JScrollPane(calendarTable);
        calendarPanel = new JPanel(null);

//        calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));

        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
        btnImport.addActionListener(new btnImport_Action());
        btnExport.addActionListener(new btnExport_Action());

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

        }
    }
    class btnImport_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                    "PSV / CSV File", "csv", "psv"));
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String filename = file.getName();
                String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
                if (extension.equals(".csv")) {
                    ArrayList<Model.Event> list = io.importCSV(file);
                    for (Model.Event ev : list) {
                        Date d = ev.getDate();
                        if (events.containsKey(d)) {
                            events.get(d).add(ev);
                        } else {
                            TreeSet<Model.Event> toAdd = new TreeSet<>();

                            toAdd.add(ev);
                            events.put(d, toAdd);
                        }
                    }
                } else if (extension.equals(".psv")) {
                    ArrayList<Model.Event> list = io.importPSV(file);
                    for (Model.Event ev : list) {
                        Date d = ev.getDate();
                        if (events.containsKey(d)) {
                            events.get(d).add(ev);
                        } else {
                            TreeSet<Model.Event> toAdd = new TreeSet<>();
                            toAdd.add(ev);
                            events.put(d, toAdd);
                        }
                    }
                }
            }
        }
    }
    class btnExport_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                    "PSV / CSV File", "csv", "psv"));
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String filename = file.getName();
                String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
                if (extension.equals(".csv")) {
                    io.exportCSV(file, events);
                } else if (extension.equals(".psv")) {
                    io.exportPSV(file, events);
                }
            }
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
