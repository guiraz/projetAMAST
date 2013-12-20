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
	
	public String getEval1Name() {
		return _eval1Name;
	}
	
	public String getEval2Name() {
		return _eval2Name;
	}
	
	public String getPedagogiqueName() {
		return _pedagogiqueName;
	}
	
	public String getTraceurName() {
		return _traceurName;
	}

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
		
		AgentContainer c = getContainerController();
        try {
            AgentController eval1 = c.createNewAgent( _eval1Name, "asimilProject.eval1.EvaluateurOne", null );
            eval1.start();
        }
        catch (Exception e){}
        try {
            AgentController eval2 = c.createNewAgent( _eval2Name, "asimilProject.eval2.EvaluateurTwo", null );
            eval2.start();
        }
        catch (Exception e){}
        try {
            AgentController pedagogique = c.createNewAgent( _pedagogiqueName, "asimilProject.pedagogique.Pedagogique", null );
            pedagogique.start();
        }
        catch (Exception e){}
        try {
            AgentController traceur = c.createNewAgent( _traceurName, "asimilProject.traceur.Traceur", null );
            traceur.start();
        }
        catch (Exception e){}

    	addBehaviour(new WakeUpWaitBehaviour(this));
		
	}
	
	protected void takeDown() {
        System.out.println("Interface-agent "+getAID().getName()+" terminating.");
    }
	
	public void startSimulation() {
		JOptionPane.showMessageDialog(null, "Commencer la simulation?", "Question", JOptionPane.QUESTION_MESSAGE);
		addBehaviour(new SimulationBehaviour(this));
	}

	public void finish() {
		String[] receiver = new String[] {"eval1","eval2","pedagogique","traceur"};
		addBehaviour(new OneMessageBehaviour(this, receiver, ACLMessage.INFORM, ""));
		JOptionPane.showMessageDialog(null, "Simulation terminer, cliquez pour quitter!", "Simulation terminée", JOptionPane.QUESTION_MESSAGE);
		this.takeDown();
	}

	public void waitAndSend(String mess, int timer) {
		doWait(timer);
		String[] receiver = new String[] {"eval1","eval2", "traceur"};
		addBehaviour(new OneMessageBehaviour(this, receiver, ACLMessage.CFP, mess));
	}

}
