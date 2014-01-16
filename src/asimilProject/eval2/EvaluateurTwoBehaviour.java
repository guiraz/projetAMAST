package asimilProject.eval2;

import asimilProject.utils.KillBehaviour;
import asimilProject.utils.OneMessageBehaviour;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EvaluateurTwoBehaviour extends Behaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EvaluateurTwo _papa;
	
	private int _id_traineeaction;
	private String _errormessage_traineeaction;
	private int _gravity_traineeaction;
	private boolean _end = false;
	private String _name_pedagogy = "pedagogique";
	
	public EvaluateurTwoBehaviour(EvaluateurTwo papa) {
		_papa = papa;
	}
	
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
				//si c'est un message d'erreur on le traite
				if(_gravity_traineeaction <= 1)
				{
					sendMessage(_id_traineeaction + "~"  + "b");
				}
				else if (_gravity_traineeaction <= 6)
				{
					sendMessage(_id_traineeaction + "~"  + "m");
				}
				else
				{
					sendMessage(_id_traineeaction + "~" + "g");
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
    
    protected void sendMessage(String content)
    {
    	String[] receiver = {_name_pedagogy};
    	_papa.addBehaviour(new OneMessageBehaviour(_papa, receiver, ACLMessage.CFP, content));
    }
    
	protected void parseString(String text)
	{
		String str[]= text.split("~");
		
		_id_traineeaction = Integer.parseInt(str[0]);
		_errormessage_traineeaction = str[4];
		_gravity_traineeaction = Integer.parseInt(str[5]);		
	}
	
	protected boolean errorMessage()
	{
		return !_errormessage_traineeaction.contains("(action)");
	}
	
    public boolean done()
    {
    	if(_end)
    		_papa.addBehaviour(new KillBehaviour(_papa));
    	return _end;
    }
}
