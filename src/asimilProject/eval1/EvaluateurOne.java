package asimilProject.eval1;

import jade.core.Agent;

public class EvaluateurOne extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println("Eval1-agent "+getAID().getName()+" beginning.");
		doDelete();
	}
	
	protected void takeDown() {
        System.out.println("Eval1-agent "+getAID().getName()+" terminating.");
    }
}
