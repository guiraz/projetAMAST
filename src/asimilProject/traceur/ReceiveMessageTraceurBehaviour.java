package asimilProject.traceur;

import asimilProject.utils.KillBehaviour;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveMessageTraceurBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	
	//parent agent
	private Traceur _papa;
	//variable setting the end of the behaviour
	private boolean _stop = false;
	
	public ReceiveMessageTraceurBehaviour(Traceur papa) {
		_papa = papa;
	}

	@Override
	public void action() {
		//receiving messages
		ACLMessage msg = _papa.receive();
		
		if(msg != null) {
			//if INFORM message from interface
			//stop behaviour and kill parent agent
			if(msg.getSender().getLocalName().equals("intfce")) {
				if(msg.getPerformative() == ACLMessage.INFORM)
					_stop = true;
				
				//if CFP message from interface
				if(msg.getPerformative() == ACLMessage.CFP) {
					//parse it
					String[] currentStep = msg.getContent().split("~");
					//if mistake calculate the coordonates (x and y) of the next point
					//get informations from the message
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
					//if not mistake calculate the coordonates (only x) of the next point
					//get informations from the message
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
					//update the plot
					_papa.updatePlot();
				}
				
			}
			
			//if CFP message from pedagogique
			//fill the arrays with the informations
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
		//if no message block and wait
		else {
			block();
		}
	}

	//stop behavior, kill parent agent
	@Override
	public boolean done() {
		if(_stop)
			_papa.addBehaviour(new KillBehaviour(_papa));
		return _stop;
	}

}