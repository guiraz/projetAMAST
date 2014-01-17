package asimilProject.traceur;

import java.awt.Color;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import org.math.plot.*;

public class PlotFrame {
	
	//parent agent
	private Traceur _papa;
	//the frame window
	private JFrame _frame;
	//the plot
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
                _papa.doDelete();
            }
        });
		
		update();
	}

	synchronized public void update() {
		//we remove the plot
		try{
			_plot.removeAllPlots();
		}
		catch(Exception e){}
		
		//redraw it
		double[] x = _papa.listDoubleToArrayDouble(_papa.getX());
		double[] y = _papa.listDoubleToArrayDouble(_papa.getY());
		
		String name = "Résultats";
		_plot.addStaircasePlot(name, x, y);
		_plot.getAxis(0).setLabelText("Temps");
		_plot.getAxis(1).setLabelText("Erreur");
		
		//get the informations for the labels
		List<String> messages = _papa.getActionMsgs();
		List<Integer> gravities = _papa.getActionGravities();
		
		//for each error action
		//add a label with the color depending on the gravity
		for(int i=0; i<messages.size(); i++) {
			if(messages.get(i) != null){
				if(gravities.get(i) <= 1)
					_plot.addLabel(messages.get(i), Color.GRAY, x[i], y[i]);
				else if(gravities.get(i) <= 6)
					_plot.addLabel(messages.get(i), Color.YELLOW, x[i], y[i]);
				else if(gravities.get(i) <= 10)
					_plot.addLabel(messages.get(i), Color.RED, x[i], y[i]);
			}
		}
		
		//draw
		_frame.setVisible(true);
	}

	//close
	public void close() {
		_frame.dispose();
	}
}