package asimilProject;

import java.sql.*;

import jade.core.Agent;

public class Interface extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	protected void setup() {
//		String pilote = "com.mysql.jdbc.Driver";
		BDTrainee.create();
//		try{
//			Class.forName(pilote);
//			Connection connexion = DriverManager.getConnection("jdbc:mysql://scinfe069/dbasimil","asimiluser","asimil");
//			Statement instruction = connexion.createStatement();
//	 
//			for(Integer numExo=80; numExo<96; numExo++) {
//				BDTrainee.create();
//				ResultSet resultat = instruction.executeQuery("SELECT * FROM TRAINEEACTION WHERE ID_PEDAGOGY = " + numExo.toString());
//				while(resultat.next()){
//					
//					BDTrainee.setIdTrainneAction(resultat.getInt("ID_TRAINEEACTION"));
//					BDTrainee.setIdPedagogy(resultat.getInt("ID_PEDAGOGY"));
//					BDTrainee.setIdAction(resultat.getInt("ID_ACTION"));
//					BDTrainee.setTimeElapsed(resultat.getInt("TIMEELAPSED_TRAINEEACTION"));
//					BDTrainee.setErrorMessage(resultat.getString("ERRORMESSAGE_TRAINEEACTION"));
//					BDTrainee.setGravity(resultat.getInt("GRAVITY_TRAINEEACTION"));
//					
//				}
//				
//				BDTrainee.exportDB(numExo.toString());
//			}
//		}
//		catch (Exception e){
//			System.out.println("echec pilote : "+e);
//		}	
//		BDTrainee.importDB("82");
//		for(int i = 0; i<BDTrainee.getCount(); i++) {
//			System.out.println(BDTrainee.getIdTrainneAction(i) + "\n"+
//			BDTrainee.getIdPedagogy(i) + "\n"+
//			BDTrainee.getIdAction(i) + "\n"+
//			BDTrainee.getTimeElapsed(i) + "\n"+
//			BDTrainee.getErrorMessage(i) + "\n"+
//			BDTrainee.getGravity(i));
//			
//			System.out.println("");
//		}
		
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			String idExo = args[0].toString();
			BDTrainee.importDB(idExo);
        }
		else
		{
			System.out.println("No exercice id specified");
            doDelete();
		}
		
	}
	
	protected void takeDown() {
        System.out.println("Buyer-agent "+getAID().getName()+" terminating.");
    }

}
