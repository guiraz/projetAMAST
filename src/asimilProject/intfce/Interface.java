package asimilProject.intfce;

import javax.swing.JOptionPane;

import asimilProject.utils.*;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.AgentContainer;

public class Interface extends Agent {

	private String numExos;
	
	private final String _eval1Name = "eval1";
	private final String _eval2Name = "eval2";
	private final String _pedagogiqueName = "pedagogique";
	private final String _traceurName = "traceur";
	
	private static final long serialVersionUID = 1L;

	protected void setup() {
		System.out.println("Interface-agent "+getAID().getName()+" beginning.");
		//Create DB object
		BDTrainee.create();
		
		//Ask user exercice number
		Object[] exercices = new Object[16];
		for(int i=0; i<exercices.length;i++)
			exercices[i] = new Integer(80+i);
		numExos = JOptionPane.showInputDialog(null, "Choisir un exercices : ", "Exercice selection", JOptionPane.QUESTION_MESSAGE, null, exercices, exercices[0]).toString();

		//Import exercice data
		BDTrainee.importDB(numExos);
		
		//Launch other agents
		String[] receiver = new String[] {"eval1","eval2","pedagogique","traceur"};
		addBehaviour(new OneMessageBehaviour(this, receiver, ACLMessage.CFP, "launch"));
		
		AgentContainer c = getContainerController();
        try {
            AgentController eval1 = c.createNewAgent( _eval1Name, "asimilProject.eval1.EvaluateurOne", null );
            eval1.start();
        }
        catch (Exception e){}
		
		JOptionPane.showMessageDialog(null, "Commencer la simulation?", "Question", JOptionPane.QUESTION_MESSAGE);
		
	}
	
	protected void takeDown() {
        System.out.println("Interface-agent "+getAID().getName()+" terminating.");
    }

}
