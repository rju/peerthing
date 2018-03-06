package de.peerthing.visualization.view.charts;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.data.category.CategoryDataset;

/**
 * A boxplot renderer. The only difference compared to the standard
 * renderer is that boxplots have a maximum width of 20 pixels.
 *
 * @author Michael Gottschalk
 *
 */
public class MyBoxAndWhiskerRenderer extends BoxAndWhiskerRenderer {
    private static final long serialVersionUID = -1346078771807654510L;

    @Override
    public void drawItem(Graphics2D arg0, CategoryItemRendererState state, Rectangle2D arg2, CategoryPlot arg3, CategoryAxis arg4, ValueAxis arg5, CategoryDataset arg6, int arg7, int arg8) {
        if (state.getBarWidth() > 20) {
            state.setBarWidth(20);
        }

        super.drawItem(arg0, state, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }


}
