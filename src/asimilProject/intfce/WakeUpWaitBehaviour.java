package asimilProject.intfce;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class WakeUpWaitBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	
	private boolean _eval1WakeUp = false;
	private boolean _eval2WakeUp = false;
	private boolean _pedagogiqueWakeUp = false;
	private boolean _traceurWakeUp = false;
	
	private Interface _papa;
	
	public WakeUpWaitBehaviour(Interface papa) {
		_papa = papa;
	}

	@Override
	public void action() {
		ACLMessage msg = _papa.receive();
		
		if(msg != null) {
			if(msg.getSender().getLocalName().equals(_papa.getEval1Name())) {
				_eval1WakeUp = true;
			}
			
			if(msg.getSender().getLocalName().equals(_papa.getEval2Name())) {
				_eval2WakeUp = true;
			}
			
			if(msg.getSender().getLocalName().equals(_papa.getPedagogiqueName())) {
				_pedagogiqueWakeUp = true;
			}
			
			if(msg.getSender().getLocalName().equals(_papa.getTraceurName())) {
				_traceurWakeUp = true;
			}
		}
		else {
			block();
		}
	}

	@Override
	public boolean done() {
		if( _eval1WakeUp && _eval2WakeUp && _pedagogiqueWakeUp && _traceurWakeUp) {
			_papa.startSimulation();
			return true;
		}
		else
			return false;
	}

}
