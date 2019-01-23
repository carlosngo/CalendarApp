package View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class DateRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        CustomPanel panel = (CustomPanel) table.getModel().getValueAt(row, column);
        return panel;
//        if (column == 0 || column == 6)
//            setBackground(new Color(220,220,255));
//        else
//            setBackground(Color.WHITE);
//        setBorder(null);
//        setForeground(Color.black);
//        return this;
    }

}

