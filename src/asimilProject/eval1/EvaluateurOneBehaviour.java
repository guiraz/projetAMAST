package asimilProject.eval1;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EvaluateurOneBehaviour extends Behaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _id_traineeaction;
	private int _id_pedagogy;
	private int _id_action;
	private int _timeelapsed_traineeaction;
	private String _errormessage_traineeaction;
	private int _gravity_traineeaction;
	private boolean _end = false;
	private int _nbError = 0;
	private boolean _twoError = false;
	private String _name_pedagogy = "pedagogique";
	
    public void action()
    {
    	//on recupere les messages de type CFP
    	MessageTemplate m1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    	MessageTemplate m2 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(m1);
		ACLMessage msg2 = myAgent.receive(m2);
		
		if (msg != null)
		{
			//on a bien un message on le traite
			parseString(msg.getContent());
			
			//on teste si le message est un message d'erreur
			if(errorMessage())
			{
				_nbError++;
				
				if (_nbError == 2)
				{
					_twoError = true;	
				}
				else if (_nbError == 3)
				{
					//on signale le probleme
					sendMessage("pb2");
					
					//on remet les compteurs à zero
					_twoError = false;
					_nbError = 0;
				}
			}
			else
			{
				_nbError = 0;
				
				if (_twoError == true)
				{
					//on signale le probleme
					sendMessage("pb1");
					
					//on remet le compteur à zero
					_twoError = false;
				}
			}
			
		}
		else if (msg2 != null)
		{
			//fin de la simulation
			_end = true;
		}
		else
		{
			// on se bloque tant que l'on a pas de message
			block();
		}
    }
    	
    public boolean done()
    {
    	return _end;
    }
    
    protected void sendMessage(String content)
    {
    	ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		msg.addReceiver(new AID(_name_pedagogy, AID.ISLOCALNAME));
		msg.setContent(content);
		myAgent.send(msg);
    }
    
    protected boolean errorMessage()
	{

		return _errormessage_traineeaction.contains("(action)");

	}
    
    protected void parseString(String text)
	{
		String str[]= text.split("~");
		
		_id_traineeaction = Integer.parseInt(str[0]);
		_id_pedagogy = Integer.parseInt(str[1]);
		_id_action = Integer.parseInt(str[2]);
		_timeelapsed_traineeaction = Integer.parseInt(str[3]);
		_errormessage_traineeaction = str[4];
		_gravity_traineeaction = Integer.parseInt(str[5]);		
	}
}

