package beatalbumshop.utils;

/**
 * The Chart class provides static methods to create charts for displaying data
 * in a graphical format. It supports two types of charts: column charts and
 * bar charts.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ChartHelper {

    /**
     * Creates a column chart with the specified column heights and displays it
     * in the specified panel.
     *
     * @param columnHeights an array of double values representing the heights
     *                      of the columns in the chart; each value should be in
     *                      the range [0, 10]
     * @param chartPanel    the JPanel where the chart will be displayed
     */
    public static void createChart(double[] columnHeights, JPanel chartPanel) {
        int colWidth = 50;
        int numCols = columnHeights.length;
        int fullHeight = chartPanel.getPreferredSize().height;
        Color colBackground;
        
        chartPanel.setLayout(new GridLayout(1, numCols));
        chartPanel.setPreferredSize(new Dimension(numCols * colWidth, fullHeight));

        //label
        JLabel[] columnLabels = new JLabel[numCols];
        for (int i = 0; i < numCols; i++) {
            JLabel lbl = new JLabel();
            
            //col size
            double percentHeight = columnHeights[i] * fullHeight / 10;
            lbl.setPreferredSize(new Dimension(colWidth, ((int)percentHeight)));
            
            //col background
            if(percentHeight >= fullHeight * 80 / 100) {
                colBackground = new Color(17, 164, 40);
            }
            else if(percentHeight >= fullHeight * 50 / 100) {
                colBackground = new Color(233, 84, 32);
            }
            else {
                colBackground = new Color(208, 23, 45);
            }
            lbl.setOpaque(true);
            lbl.setBackground(colBackground);
            lbl.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Col", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Open Sans", 0, 16), Color.BLACK));
            
            //add to panel
            JPanel pnl = new JPanel(new BorderLayout());
            pnl.add(lbl, BorderLayout.SOUTH);
            
            //add to chart
            chartPanel.add(pnl);
        }
    }
    
    
    
    /**
     * Creates a bar chart with the specified column values and names and
     * displays it in the specified panel.
     *
     * @param lColValue   an ArrayList of double values representing the values
     *                    of the columns in the chart; each value should be in
     *                    the range [0, 10]
     * @param colNames    an array of Strings representing the names of the
     *                    columns in the chart; the length of this array should
     *                    match the size of lColValue
     * @param chartPanel  the JPanel where the chart will be displayed
     */
    public static void createChart(ArrayList<Double> lColValue, ArrayList<String> colNames, JPanel chartPanel) {
        int colWidth = 70;
        int numCols = lColValue.size();
        int fullHeight = chartPanel.getPreferredSize().height;
        double max = Collections.max(lColValue);
        Double per = 1.0;
        if(max > fullHeight) {
            per = fullHeight / max;
        }
        
        Color colBackground;
        chartPanel.setLayout(new GridLayout(1, numCols, 10, 5));
        chartPanel.setPreferredSize(new Dimension(numCols * colWidth, fullHeight));

        //label
        JLabel[] columnLabels = new JLabel[numCols];
        for (Double i : lColValue) {
            JLabel lbl = new JLabel();

            //col size
            double percentHeight = i * per;
            lbl.setPreferredSize(new Dimension(colWidth, ((int)percentHeight)));
            System.out.println(percentHeight);
            //col background
            colBackground = new Color(208, 23, 45);

            lbl.setOpaque(true);
            lbl.setBackground(colBackground);
            lbl.setBorder(new CompoundBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "$" + String.format("%.2f", i), TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Open Sans", 0, 16), Color.WHITE), 
                                            new TitledBorder(new EmptyBorder(0, 0, 0, 0), colNames.get(lColValue.indexOf(i)), TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Open Sans", 0, 16), Color.WHITE)));
            
            //add to panel
            JPanel pnl = new JPanel(new BorderLayout());
            pnl.add(lbl, BorderLayout.SOUTH);
            
            //add to chart
            chartPanel.add(pnl);
        }
    }
    
}
