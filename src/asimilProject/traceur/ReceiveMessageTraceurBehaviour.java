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
						y = _papa.getY().get(_papa.getY().size()-1);
						xp = Double.parseDouble(currentStep[3]) / 1000;
						yp = y + Double.parseDouble(currentStep[5]);
						_papa.getX().add(xp);
						_papa.getY().add(yp);
						
						_papa.getActionIds().add(Integer.parseInt(currentStep[0]));
						_papa.getActionGravities().add(Integer.parseInt(currentStep[5]));
						_papa.getActionMsgs().add("");
					}
					else {
						Double y,xp;
						y = _papa.getY().get(_papa.getY().size()-1);
						xp = Double.parseDouble(currentStep[3]) / 1000;
						_papa.getX().add(xp);
						_papa.getY().add(y);
						
						_papa.getActionIds().add(Integer.parseInt(currentStep[0]));
						_papa.getActionGravities().add(-1);
						_papa.getActionMsgs().add(null);
					}
					_papa.updatePlot();
				}
				
			}
			
			if(msg.getSender().getLocalName().equals("pedagogique")) {
				
				if(msg.getPerformative() == ACLMessage.CFP){
					String[] content = msg.getContent().split("~");
					Integer id = Integer.parseInt(content[0]);
					String tempMsg = content[1];
					
					Integer index = _papa.getActionIds().indexOf(id);
					_papa.getActionMsgs().set(index, tempMsg);
				}
				
			}
			
		}
		else {
			block();
		}
	}

	@Override
	public boolean done() {
		if(_stop)
			_papa.doDelete();
		return _stop;
	}

}