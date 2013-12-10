package asimilProject;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class InterfaceReveilBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
		cfp.addReceiver(new AID("eval1", AID.ISLOCALNAME));
		cfp.addReceiver(new AID("eval2", AID.ISLOCALNAME));
		cfp.addReceiver(new AID("pedagogique", AID.ISLOCALNAME));
		cfp.addReceiver(new AID("trace", AID.ISLOCALNAME));
		cfp.setContent("reveil");
		myAgent.send(cfp);
	}

}
