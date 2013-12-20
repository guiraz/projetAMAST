package asimilProject.eval2;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class EvaluateurTwo extends Agent 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println("Eval2-agent "+getAID().getName()+" beginning.");
		
		//envoi un message a l'interface pour lui signifier que l'agent est bien réveillé
		addBehaviour(new asimilProject.utils.OneMessageBehaviour(this, new String[] {"intfce"}, ACLMessage.CFP, ""));
		
		//Traitement des messages recus
		addBehaviour(new EvaluateurTwoBehaviour());		
	}
	
	protected void takeDown() {
        System.out.println("Eval2-agent "+getAID().getName()+" terminating.");
    }

}
