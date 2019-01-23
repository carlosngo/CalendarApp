package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//public class CustomPanel extends JPanel implements ActionListener {
public class CustomPanel extends JPanel {
    private JLabel header;
    JLabel[] rowLabels;
    final static public int MAX_ROWS = 3; // sets the limit of displayed events
    int rowIndex = 0;

    public CustomPanel(String title) {
        setBackground(Color.WHITE);
//        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        header = new JLabel(title);
        header.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        add(header);

        rowLabels = new JLabel[MAX_ROWS];
        for (int i = 0; i < MAX_ROWS; i++) {
            rowLabels[i] = new JLabel("    ");
//            rowLabels[i].setVisible(false);
            rowLabels[i].setOpaque(false);
            rowLabels[i].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(rowLabels[i]);
        }

//        JButton b = new JButton("Add Label");
//        b.addActionListener(this);
//        add(b);
    }

    public void addRow(String content, Color c) {
        if (rowIndex >= MAX_ROWS)  {
//            System.out.println("Max events reached.");
            return;
        }
        rowLabels[rowIndex].setText(content);
        rowLabels[rowIndex].setBackground(c);
        rowLabels[rowIndex].setOpaque(true);
//        rowLabels[rowIndex].setVisible(true);
        rowIndex++;
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() instanceof JButton) {
//            this.addRow("I have been added!", Color.BLUE);
//            System.out.println("Button clicked.");
//        }
//    }

//    public static void main(String[] args) {
//        JFrame f = new JFrame("Testing CustomPanel");
//        CustomPanel dp = new CustomPanel("Day 2");
//        f.setContentPane(dp);
//        f.setVisible(true);
//        f.pack();
//        f.setSize(new Dimension(500, 500));
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
}
