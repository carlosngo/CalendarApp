package View;

import Controller.*;
import Model.Event;
import Model.Holiday;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class AddEventWindow extends JFrame implements ActionListener {
    private CalendarController controller;

    // SWING COMPONENTS
    private JButton cancel, save;
    private JTextField eventName;
    private JComboBox interval, type, mon, day, yr;
    private ColorChooserButton chooseClr;

    public AddEventWindow(CalendarController controller){
        this.controller = controller;
        init();
    }

    public AddEventWindow(CalendarController controller, Calendar c) {
        this(controller);
        mon.setSelectedIndex(c.get(Calendar.MONTH) + 1);
        day.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH));
        yr.setSelectedItem(c.get(Calendar.YEAR) + "");
        save.setEnabled(true);
    }

    public AddEventWindow(CalendarController controller, Calendar d, String name, Color c, Color backClr) {
        this(controller, d);
        eventName.setText(name);
        chooseClr.setSelectedColor(c);
        if (backClr.getRGB() == Holiday.COLOR_CODE.getRGB()) type.setSelectedIndex(1);
        save.setEnabled(true);


        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.removeEvent(d, name, c);
            }
        });
    }

    public void init(){
        /*
        JPanel motherPnl = new JPanel();
        motherPnl.setLayout(new OverlayLayout(motherPnl));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        */

        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
//        p.setBackground(new Color(11,102,35));

        JLabel title = new JLabel("Event Details");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Abril Fatface", Font.PLAIN, 36));
//        title.setForeground(new Color(255, 255, 255));
        p.add(title);

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.setOpaque(false);
        JLabel eventNameLabel = new JLabel("Event Name:");
//        eventNameLabel.setForeground(new Color(255, 255, 255));
        p1.add(eventNameLabel);
        eventName = new JTextField("", 20);
        eventName.setFont(new Font("Abril Fatface", Font.PLAIN, 18));
        eventName.addActionListener(this);
        eventName.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if (eventName.getText().isEmpty())
                    save.setEnabled(false);
            }
            public void removeUpdate(DocumentEvent e) {
                if (eventName.getText().isEmpty())
                    save.setEnabled(false);
            }
            public void changedUpdate(DocumentEvent e) {
                if (eventName.getText().isEmpty())
                    save.setEnabled(false);
            }
        });
        p1.add(eventName);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.setOpaque(false);
        JLabel colorLabel = new JLabel("Color: ");
//        colorLabel.setForeground(new Color(255, 255, 255));
        p2.add(colorLabel);


        chooseClr = new ColorChooserButton(Color.BLACK);
        //color.setOpaque(true);
        //color.setBackground(new Color(15, 25, 0));
        //color.setForeground(new Color(255, 255, 255));
        p2.add(chooseClr);
        p2.add(Box.createRigidArea(new Dimension(30, 0)));

        JLabel intervalLabel = new JLabel("Repeat: ");
//        intervalLabel.setForeground(new Color(255, 255, 255));
        Object[] intervalOptions = {"Never", "Daily", "Weekly", "Monthly", "Yearly"};
        interval = new JComboBox(intervalOptions);
        p2.add(intervalLabel);
        p2.add(interval);
        p2.add(Box.createRigidArea(new Dimension(30, 0)));

        JLabel typeLabel = new JLabel("Type: ");
        Object[] typeOptions = {"Normal", "Holiday"};
        type = new JComboBox(typeOptions);
        type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((String)type.getSelectedItem()).equals("Holiday")) {
                    interval.setSelectedIndex(Event.YEARLY_EVENT);
                    interval.setEnabled(false);
                } else {
                    interval.setSelectedIndex(Event.ONE_TIME_EVENT);
                    interval.setEnabled(true);
                }
            }
        });
        p2.add(typeLabel);
        p2.add(type);


        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        p3.setOpaque(false);
        JLabel date = new JLabel("Date (MM/DD/YY):");
//        date.setForeground(new Color(255, 255, 255));
        p3.add(date);
        String[] months = {"Month", "January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};
        mon = new JComboBox(months);
        //mon.setForeground(new Color(255, 255, 255));
        mon.addActionListener(this);
        p3.add(mon);
        String[] days = new String[32];
        days[0] = "Day";
        for (int i = 1; i <= 31; i++) {
            days[i] = Integer.toString(i);
        }
        day = new JComboBox(days);
        day.addActionListener(this);
        p3.add(day);
        String[] years = new String[101];
        years[0] = "Year";
        for (int i = 1; i <= 100; i++) {
            years[i] = Integer.toString(1999 + i);
        }
        yr = new JComboBox(years);
        yr.addActionListener(this);
        p3.add(yr);

        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout());
        p4.setOpaque(false);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Abril Fatface", Font.PLAIN, 20));
        //cancel.setBackground(new Color(15,25,0));
        //cancel.setForeground(Color.GRAY);
        cancel.addActionListener(this);
        p4.add(cancel);

        save = new JButton("Save");
        save.setEnabled(false);
        save.setFont(new Font("Abril Fatface", Font.PLAIN, 20));
//        save.setBackground(Color.BLUE);
//        save.setBackground(Color.WHITE);
        save.addActionListener(this);
        p4.add(save);

        JPanel p5 = new JPanel();

        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0, 5)));
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(p4);


        /*
        content.add(p);
        content.add(Box.createRigidArea(new Dimension(30, 0)));

        content.setAlignmentX(0.5f);
        content.setAlignmentY(0.5f);
        motherPnl.add(content);

        Dimension sizeSource = Toolkit.getDefaultToolkit().getScreenSize();
        JLabel corkpic = new JLabel(new ImageIcon(resizeImage("corkBoard.png",(int) sizeSource.getHeight(),(int) sizeSource.getWidth())));
        corkpic.setAlignmentX(0.5f);
        corkpic.setAlignmentY(0.5f);
        motherPnl.add(corkpic);
        */

        add(p); // add panel to frame
        pack(); //set the compnents to fit in the frame
//        setUndecorated(true);
//        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.closeAddEventWindow();
            }
        });
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }


    /*
    public static BufferedImage resizeImage(String address, int width, int height) {
        try{
             BufferedImage rawHolder = ImageIO.read(new File(address));
             Image raw = rawHolder.getScaledInstance(width, height, Image.SCALE_SMOOTH);
             BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
             Graphics2D g2d = resized.createGraphics();
             g2d.drawImage(raw, 0, 0, null);
             g2d.controller.closeViewDateWindow();
             return resized;
        }
        catch(IOException e){
             System.out.println("File not found.");
             return null;
        }
    }
    */

    public String getEventName() {
        return eventName.getText();
    }

    public Color getEventColor() {
        return chooseClr.getSelectedColor();
    }

    public int getEventInterval() {
        return interval.getSelectedIndex();
    }

    public Calendar getEventDate() {
        int m = mon.getSelectedIndex();
        int y = Integer.parseInt((String)yr.getSelectedItem());
        int d = day.getSelectedIndex();
        Calendar c = Calendar.getInstance();
        c.set(y, m - 1, d, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        if (c.get(Calendar.MONTH) == m - 1 && c.get(Calendar.YEAR) == y && c.get(Calendar.DAY_OF_MONTH) == d)
            return c;
        else
            return null;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!eventName.getText().trim().equals("")){

                if (yr.getSelectedIndex() != 0) {
                    if (mon.getSelectedIndex() != 0){
                        if (day.getSelectedIndex() != 0){
                            save.setEnabled(true);
                        }
                        else
                            save.setEnabled(false);
                    }
                    else
                        save.setEnabled(false);
                }
                else
                    save.setEnabled(false);

        }
        else
            save.setEnabled(false);



        if(e.getSource() == cancel)
            controller.closeAddEventWindow();

        if(e.getSource() == save){
            if (getEventName().equals("")) {
                controller.closeAddEventWindow();
                return;
            }
            Calendar d = getEventDate();
            if (d != null)
                if (((String)type.getSelectedItem()).equals("Holiday")) {
                    controller.addHoliday(getEventDate(), getEventName(), getEventColor());
                } else {
                    controller.addEvent(getEventDate(), getEventName(), getEventColor(), getEventInterval());
                    System.out.println(getEventInterval());
                }
            else
                JOptionPane.showMessageDialog(null, "Invalid date.");
            controller.closeAddEventWindow();
        }
    }

}
