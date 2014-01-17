package asimilProject.eval1;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import asimilProject.utils.*;

public class EvaluateurOne extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void setup() {
		System.out.println("Eval1-agent "+getAID().getName()+" beginning.");
		
		//send a wake up message to interface agent
		addBehaviour(new OneMessageBehaviour(this, new String[] {"intfce"}, ACLMessage.CFP, ""));
				
		//message receival behaviour
		addBehaviour(new EvaluateurOneBehaviour(this));		
	}
	
	protected void takeDown() {
        System.out.println("Eval1-agent "+getAID().getName()+" terminating.");
    }
}
