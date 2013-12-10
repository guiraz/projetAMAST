package asimilProject;

import jade.core.Agent;

public class EvaluateurOne extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {
		
	}
	
	protected void takeDown() {
        System.out.println("Buyer-agent "+getAID().getName()+" terminating.");
    }
}
