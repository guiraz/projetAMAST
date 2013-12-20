package asimilProject.traceur;

import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class Traceur extends Agent {

	private static final long serialVersionUID = 1L;
	private PlotFrame _pf;
	
	private double[] _x;
	private double[] _y;
	
	public double[] getX() {
		return _x;
	}
	
	public double[] getY() {
		return _y;
	}

	protected void setup() {
		System.out.println("Traceur-agent "+getAID().getName()+" beginning.");
		
		//send message to say agent is awake
		addBehaviour(new asimilProject.utils.OneMessageBehaviour(this, new String[] {"intfce"}, ACLMessage.CFP, ""));
		
		_x = new double[1];
		_x[0]=0;
		_y = new double[1];
		_y[0]=0;
		
		_pf = new PlotFrame(this);
	}
	
	protected void takeDown() {
        System.out.println("Traceur-agent "+getAID().getName()+" terminating.");
    }

	public void updatePlot() {
		_pf.update();
	}
}
