/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Controller.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author User
 */
public class ViewDateWindow extends JFrame {

    private Calendar date;

    // SWING COMPONENTS
    private CalendarController controller;
    private JLabel dateLabel;
    private JScrollPane sp;
    private JButton addEvent;
    private JLabel eventArea;
    private JPanel content;

    //ArrayList<String> test_events;
    //String test_date;

    public ViewDateWindow(CalendarController controller, Calendar date, ArrayList<String> eventNames, ArrayList<Color> eventColors, Point pt) {
        this.controller = controller;
        this.date = date;
        content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;

        DateFormat df = new SimpleDateFormat("EEEE, MM/dd/yyyy");
        String reportDate = df.format(date.getTime());
        dateLabel = new JLabel(reportDate);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));

        cons.insets = new Insets(10, 10, 2, 10);
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 3;
        content.add(dateLabel, cons);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        p.add(Box.createRigidArea(new Dimension(0, 5)));

//        eventArea = new JLabel();
//        eventArea.setFont(new Font("Arial", Font.PLAIN, 16));

        if (eventNames.isEmpty()) {
            cons.gridy = 1;
            cons.insets = new Insets(5, 10, 0, 10);
            cons.gridx = 0;
            cons.gridy = 1;
            cons.gridwidth = 3;
            JLabel lblName = new JLabel("There are no events at this date.");
            lblName.setFont(new Font("Arial", Font.PLAIN, 16));
            content.add(lblName, cons);
        } else {
            for (int i = 0; i < eventNames.size(); i++) {
                JLabel lblName = new JLabel();
                lblName.setFont(new Font("Arial", Font.PLAIN, 16));
                JButton deleteEvent = new JButton();
                deleteEvent.setOpaque(false);
                deleteEvent.setContentAreaFilled(false);
                deleteEvent.setBorderPainted(false);
                JButton editEvent = new JButton();
                editEvent.setOpaque(false);
                editEvent.setContentAreaFilled(false);
                editEvent.setBorderPainted(false);
                try {
                    URL resource = getClass().getClassLoader().getResource("delete.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    deleteEvent.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));

                    resource = getClass().getClassLoader().getResource("edit.png");
                    img = Paths.get(resource.toURI()).toFile();
                    editEvent.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                Color c = eventColors.get(i);
                String rgbCode = "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")";
//                String name = "&nbsp;&nbsp;&nbsp;" + eventNames.get(i);
                String name = eventNames.get(i);
                lblName.setText("<html><font color='" + rgbCode + "'>" + name + "</font></html>");
                deleteEvent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete this event?", "Delete Event", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            controller.removeEvent(date, name, c);
                            controller.closeViewDateWindow();
                        }
                    }
                });
                editEvent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.removeEvent(date, name, c);
                        controller.openAddEventWindow(date, name, c);
                        controller.closeViewDateWindow();
                    }
                });
                cons.insets = new Insets(5, 10, 0, 0);
                cons.gridx = 0;
                cons.gridy = i + 1;
                cons.gridwidth = 1;
//                cons.ipady = 10;
                content.add(lblName, cons);
                cons.insets = new Insets(5, 0, 0, 0);
                cons.gridx = 2;
                cons.gridwidth = 1;
                content.add(editEvent, cons);
                cons.insets = new Insets(5, 0, 0, 10);
                cons.gridx = 3;
                content.add(deleteEvent, cons);
            }

        }

//        sp = new JScrollPane(eventArea);
//        sp.setBorder(BorderFactory.createEmptyBorder());
//        sp.setMaximumSize(new Dimension(800, 75));
//        p.add(sp);
//        p.add(eventArea);
//        p.add(Box.createRigidArea(new Dimension(0,10)));
        addEvent = new JButton("+ New Event");
        addEvent.setFont(new Font("Arial", Font.BOLD, 14));
//        addEvent.setBackground(Color.BLUE);
//        addEvent.setForeground(Color.WHITE);
        addEvent.addActionListener(new addEvent_Action());

        cons.insets = new Insets(10, 10, 10, 10);
        cons.gridy++;
        cons.gridx = 0;
        cons.gridwidth = 2;
        content.add(addEvent, cons);

        content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
//        p.setMaximumSize(new Dimension(400, 100));
//        p.setPreferredSize(new Dimension(200, 150));
        setContentPane(content);

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
