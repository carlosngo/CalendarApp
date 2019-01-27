package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//public class CustomPanel extends JPanel implements ActionListener {
public class CustomPanel extends JPanel {
    private JLabel header;
    JLabel[] rowLabels;
    private int maxRows; // sets the limit of displayed events
    int rowIndex = 0;

    public CustomPanel(String title, int maxRows) {
        this.maxRows = maxRows;
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        header = new JLabel(title);
        header.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        add(header);

        rowLabels = new JLabel[maxRows];
        for (int i = 0; i < maxRows; i++) {
            rowLabels[i] = new JLabel("    ");
            rowLabels[i].setVisible(false);
//            rowLabels[i].setOpaque(false);
            rowLabels[i].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
//            add(Box.createRigidArea(new Dimension(0, 5)));
            add(rowLabels[i]);
        }

    }

    public String getHeader() {
        return header.getText();
    }

    public void addRow(String content, Color c) {
        if (rowIndex >= maxRows)  {
//            System.out.println("Max events reached.");
            return;
        };
        rowLabels[rowIndex].setVisible(true);
        rowLabels[rowIndex].setText(content);
        rowLabels[rowIndex].setForeground(c);
//        double y = (299 * c.getRed() + 587 * c.getGreen() + 114 * c.getBlue()) / 1000;
//        if (y >= 128) rowLabels[rowIndex].setForeground(Color.black);
//        else rowLabels[rowIndex].setForeground(Color.white);
//        rowLabels[rowIndex].setOpaque(true);
        rowIndex++;
    }
}
