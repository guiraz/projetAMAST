package asimilProject.pedagogique;

import asimilProject.eval2.EvaluateurTwoBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Pedagogique extends Agent 
{
	protected void setup() {
		System.out.println("Pedagogique-agent "+getAID().getName()+" beginning.");
		
		//envoi un message a l'interface pour lui signifier que l'agent est bien réveillé
		addBehaviour(new asimilProject.utils.OneMessageBehaviour(this, new String[] {"intfce"}, ACLMessage.CFP, ""));
		
		//traitement des erreurs
		addBehaviour(new PedagogiqueBehaviour(this));
	}
	
	protected void takeDown() {
        System.out.println("Pedagogique-agent "+getAID().getName()+" terminating.");
    }

}
