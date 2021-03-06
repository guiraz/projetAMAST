package asimilProject.traceur;

import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class Traceur extends Agent {

	private static final long serialVersionUID = 1L;
	
	//gui that draws the plot
	private PlotFrame _pf;
	
	//informations and coordonates of the action
	private List<Double> _x;
	private List<Double> _y;
	private List<String> _actionMsg;
	private List<Integer> _actionGravity;
	private List<Integer> _actionIds;
	
	public List<Double> getX() {
		return _x;
	}
	
	public List<Double> getY() {
		return _y;
	}
	
	public List<Integer> getActionIds() {
		return _actionIds;
	}
	
	public List<Integer> getActionGravities() {
		return _actionGravity;
	}
	
	public List<String> getActionMsgs() {
		return _actionMsg;
	}

	protected void setup() {
		System.out.println("Traceur-agent "+getAID().getName()+" beginning.");
		
		//send a wake up message to interface agent
		addBehaviour(new asimilProject.utils.OneMessageBehaviour(this, new String[] {"intfce"}, ACLMessage.CFP, ""));
		
		//init arrays with a fake action
		//to make the plot start at (0,0)
		_x = new ArrayList<Double>();
		_x.add(0.);
		_y = new ArrayList<Double>();
		_y.add(0.);
		_actionMsg = new ArrayList<String>();
		_actionMsg.add(null);
		_actionGravity = new ArrayList<Integer>();
		_actionGravity.add(-1);
		_actionIds = new ArrayList<Integer>();
		_actionIds.add(0);
		
		//launch the gui
		_pf = new PlotFrame(this);
		
		//launch the message receival behavior
		addBehaviour(new ReceiveMessageTraceurBehaviour(this));
	}
	
	protected void takeDown() {
        System.out.println("Traceur-agent "+getAID().getName()+" terminating.");
        _pf.close();
    }

	//call the method of the gui which redraws the plot
	public void updatePlot() {
		_pf.update();
	}
	
	//convert List of doubles to arrays of doubles
	public double[] listDoubleToArrayDouble(List<Double> list) {
		double[] array = new double[list.size()];
		
		for(int i=0; i<list.size(); i++)
			array[i] = list.get(i);
		
		return array;
	}
}
