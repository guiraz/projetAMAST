package asimilProject.traceur;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import org.math.plot.*;

public class PlotFrame {
	
	private Traceur _papa;
	private JFrame _frame;
	private Plot2DPanel _plot;
	
	public PlotFrame(Traceur papa) {
		_papa = papa;
		
		// create your PlotPanel (you can use it as a JPanel)
		_plot = new Plot2DPanel();
		
		// define the legend position
		_plot.addLegend("SOUTH");
 
		// put the PlotPanel in a JFrame like a JPanel
		_frame = new JFrame("Resultats");
		_frame.setSize(600, 600);
		_frame.setContentPane(_plot);
		
		_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                _papa.takeDown();
            }
        });
		
		update();
	}

	public void update() {
		_plot.removeAllPlots();
		double[] x =  _papa.getX();
		double[] y =  _papa.getY();
		String name = "Résultats";
		_plot.addStaircasePlot(name, x, y);
		_plot.getPlot(0).setColor(Color.black);
		_frame.setVisible(true);
	}
}
