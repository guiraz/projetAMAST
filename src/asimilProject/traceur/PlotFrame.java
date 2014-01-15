package asimilProject.traceur;

import java.awt.event.*;

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
 
		// put the PlotPanel in a JFrame like a JPanel
		_frame = new JFrame("Resultats");
		_frame.setSize(700, 700);
		_frame.setContentPane(_plot);
		
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                _papa.takeDown();
            }
        });
		
		update();
	}

	synchronized public void update() {
		try{
			_plot.removeAllPlots();
		}
		catch(Exception e){}
		
		double[] x = _papa.listDoubleToArrayDouble(_papa.getX());
		double[] y = _papa.listDoubleToArrayDouble(_papa.getY());
		
		String name = "Résultats";
		_plot.addStaircasePlot(name, x, y);
		_frame.setVisible(true);
	}

	public void close() {
		_frame.dispose();
	}
}