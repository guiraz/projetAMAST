package asimilProject.utils;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class OneMessageBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	private Agent _papa;
	private String[] _receiver;
	private int _prot;
	private String _message;
	
	public OneMessageBehaviour(Agent papa, String[] receiver, int prot, String message) {
		_papa = papa;
		_receiver = receiver;
		_prot = prot;
		_message = message;
	}

	@Override
	public void action() {
		ACLMessage msg = new ACLMessage(_prot);
		for(int i=0; i<_receiver.length; i++)
			msg.addReceiver(new AID(_receiver[i], AID.ISLOCALNAME));
		msg.setContent(_message);
		_papa.send(msg);
	}

}
