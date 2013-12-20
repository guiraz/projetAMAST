package asimilProject.traceur;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveMessageTraceurBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	
	private Traceur _papa;
	private boolean _stop = false;
	
	public ReceiveMessageTraceurBehaviour(Traceur papa) {
		_papa = papa;
	}

	@Override
	public void action() {
		ACLMessage msg = _papa.receive();
		if(msg != null) {
			
			if(msg.getSender().getLocalName().equals("intfce")) {
				if(msg.getPerformative() == ACLMessage.INFORM)
					_stop = true;
				
				if(msg.getPerformative() == ACLMessage.CFP) {
					String[] currentStep = msg.getContent().split("~");
					if(!currentStep[4].contains("(action)")) {
						Double y, xp,yp;
						y = _papa.getY()[_papa.getY().length-1];
						xp = Double.parseDouble(currentStep[3]);
						yp = y + Double.parseDouble(currentStep[5]);
						addPoint(xp,yp);
					}
					_papa.updatePlot();
				}
			}
			
		}
		else {
			block();
		}
	}

	private void addPoint(Double x, Double y) {
		double[] xList = new double[_papa.getX().length];
		double[] yList = new double[_papa.getY().length];
		
		for(int i=0; i<_papa.getX().length; i++) {
			xList[i] = _papa.getX()[i];
			yList[i] = _papa.getY()[i];
		}
		
		xList[xList.length-1] = x;
		yList[yList.length-1] = y;
	}

	@Override
	public boolean done() {
		if(_stop)
		{
			_papa.doDelete();
			return true;
		}
		else
			return false;
	}

}
