/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Controller.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author User
 */
public class ViewDateWindow extends JFrame {

    private Date date;

    // SWING COMPONENTS
    private CalendarController controller;
    private JLabel dateLabel;
    private CustomPanel cp;
    private JScrollPane sp;
    private JButton addEvent;
    private JLabel eventArea;
    //ArrayList<String> test_events;
    //String test_date;

    public ViewDateWindow(CalendarController controller, Date date, ArrayList<String> eventNames, ArrayList<Color> eventColors, Point pt) {
        this.controller = controller;
        this.date = date;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
//        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(Box.createRigidArea(new Dimension(0,10)));
        p.setOpaque(true);
//        p.setBackground(new Color(11,102,35));

        DateFormat df = new SimpleDateFormat("EEEE, MM/dd/yyyy");
        String reportDate = df.format(date);
        dateLabel = new JLabel(reportDate);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));

        p.add(dateLabel);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(Box.createRigidArea(new Dimension(0, 5)));

        eventArea = new JLabel();
        eventArea.setFont(new Font("Arial", Font.PLAIN, 16));
//        cp = new CustomPanel("", 1000);
        StringBuilder sb = new StringBuilder();

        if (eventNames.isEmpty()) {
//            cp.addRow("There are no events at this date.", cp.getBackground());
            sb.append("    There are no events at this date.");
        } else {
            sb.append("<html>");
            for (int i = 0; i < eventNames.size(); i++) {
//                cp.addRow(eventNames.get(i), eventColors.get(i));
                Color c = eventColors.get(i);
                String rgbCode = "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")";
                String name = "&nbsp;&nbsp;&nbsp;" + eventNames.get(i);
                sb.append("<font color='" + rgbCode + "'>" + name + "</font><br/>");
            }
            sb.append("</html>");
        }
        eventArea.setText(sb.toString());

        sp = new JScrollPane(eventArea);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setMaximumSize(new Dimension(800, 75));
        p.add(sp);
//        p.add(eventArea);
        p.add(Box.createRigidArea(new Dimension(0,10)));

        addEvent = new JButton("+ New Event");
        addEvent.setFont(new Font("Arial", Font.BOLD, 14));
//        addEvent.setBackground(Color.BLUE);
//        addEvent.setForeground(Color.WHITE);
        addEvent.addActionListener(new addEvent_Action());
        p.add(addEvent);

        p.add(Box.createRigidArea(new Dimension(0,10)));
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
//        p.setMaximumSize(new Dimension(400, 100));
//        p.setPreferredSize(new Dimension(200, 150));
        setContentPane(p);

        setUndecorated(true);
        setVisible(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.closeViewDateWindow();
            }
        });
        setLocation(pt);
        pack();
    }

    class addEvent_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.openAddEventWindow(date);
            controller.closeViewDateWindow();
//            dispose();
        }
    }
}
