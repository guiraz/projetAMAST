package asimilProject.intfce;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import asimilProject.utils.*;

public class InterfaceIteratorBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	private Integer _iterator = 0;

	@Override
	public void action() {
		ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
		cfp.addReceiver(new AID("eval1", AID.ISLOCALNAME));
		cfp.addReceiver(new AID("eval2", AID.ISLOCALNAME));
		cfp.setContent(_iterator.toString());
		myAgent.send(cfp);
		_iterator++;
	}

	@Override
	public boolean done() {
		return _iterator == BDTrainee.getCount();
	}

}
