package View;

import Controller.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddEventView extends JFrame implements ActionListener {

    private JButton cancel, save, importFile;
    private JTextField eventName;
    private JComboBox mon, day, yr, color;
    private String sMon, sDay, sYr, sEventName;
    private AddEventController controller;


    public AddEventView(){
        init();
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
        p.setBackground(new Color(11,102,35));

        JLabel title = new JLabel("EVENT");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Abril Fatface", Font.PLAIN, 36));
        title.setForeground(new Color(255, 255, 255));
        p.add(title);

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.setOpaque(false);
        JLabel eventNameLabel = new JLabel("EVENT NAME:");
        eventNameLabel.setForeground(new Color(255, 255, 255));
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
        JLabel colorLabel = new JLabel("COLOR:");
        colorLabel.setForeground(new Color(255, 255, 255));
        p2.add(colorLabel);

        String[] choices = {"Select a color", "Red", "Orange", "Yellow", "Green", "Blue", "Violet"};
        color = new JComboBox(choices);
        color.setFont(new Font("Abril Fatface", Font.PLAIN, 18));
        //color.setOpaque(true);
        //color.setBackground(new Color(15, 25, 0));
        //color.setForeground(new Color(255, 255, 255));
        color.addActionListener(this);
        p2.add(color);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        p3.setOpaque(false);
        JLabel date = new JLabel("Date (MM/DD/YY):");
        date.setForeground(new Color(255, 255, 255));
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
        save.addActionListener(this);
        p4.add(save);

        importFile = new JButton("Import File");
        //importFile.setFont(new Font("Abril Fatface", Font.PLAIN, 20));
        importFile.setAlignmentX(Component.CENTER_ALIGNMENT);
        importFile.addActionListener(this);
        p.add(importFile);

        p.add(p1);
        p.add(p2);
        p.add(p3);
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
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public static void main(String args[]){
        AddEventView a = new AddEventView();
    }

    /*
    public static BufferedImage resizeImage(String address, int width, int height) {
        try{
             BufferedImage rawHolder = ImageIO.read(new File(address));
             Image raw = rawHolder.getScaledInstance(width, height, Image.SCALE_SMOOTH);
             BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
             Graphics2D g2d = resized.createGraphics();
             g2d.drawImage(raw, 0, 0, null);
             g2d.dispose();
             return resized;
        }
        catch(IOException e){
             System.out.println("File not found.");
             return null;
        }
    }
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!eventName.getText().trim().equals("")){
            if (color.getSelectedIndex() != 0){
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
        }
        else
            save.setEnabled(false);

        if(e.getSource() == cancel)
            dispose();

        if(e.getSource() == save){
            sYr = (String) yr.getSelectedItem();
            sMon = (String) mon.getSelectedItem();
            sDay = (String) day.getSelectedItem();
            sEventName = eventName.getText();
        }
    }

}
