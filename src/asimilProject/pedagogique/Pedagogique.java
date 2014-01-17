package asimilProject.pedagogique;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class Pedagogique extends Agent 
{
	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println("Pedagogique-agent "+getAID().getName()+" beginning.");
		
		//send a wake up message to interface agent
		addBehaviour(new asimilProject.utils.OneMessageBehaviour(this, new String[] {"intfce"}, ACLMessage.CFP, ""));
		
		//mistakes processing behaviour launched
		addBehaviour(new PedagogiqueBehaviour(this));
	}
	
	protected void takeDown() {
        System.out.println("Pedagogique-agent "+getAID().getName()+" terminating.");
    }

}
