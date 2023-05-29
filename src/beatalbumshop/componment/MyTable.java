package beatalbumshop.componment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MyTable extends JTable {
    public MyTable() {
        Object[][] data = {{"Row 1, Column 1", "Row 1, Column 2"}, {"Row 2, Column 1", "Row 2, Column 2"}};
        Object[] columnNames = {"Column 1", "Column 2"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        setModel(model);
        
        // Style
        setRowHeight(30); //width height
        setForeground(Color.BLACK);
        setFont(new Font("Open Sans", 0, 16));
        
        getTableHeader().setPreferredSize(new Dimension(0,30)); //width height
        getTableHeader().setFont(new Font("Open Sans", 1, 16));
        getTableHeader().setBackground(Color.WHITE);
        ((DefaultTableCellRenderer)getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT); //Left-align Headings
        
        setSelectionForeground(Color.WHITE); //change text color of selected row
        setSelectionBackground(new Color(238, 84, 102)); //change background color of selected row
    }
}
