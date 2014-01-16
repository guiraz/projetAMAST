package asimilProject.eval1;

import asimilProject.utils.KillBehaviour;
import asimilProject.utils.OneMessageBehaviour;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EvaluateurOneBehaviour extends Behaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EvaluateurOne _papa;
	
	private int _id_traineeaction;
	private String _errormessage_traineeaction;
	private boolean _end = false;
	private int _nbError = 0;
	private String _name_pedagogy = "pedagogique";
	
	public EvaluateurOneBehaviour(EvaluateurOne papa) {
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
				//imcremente le nb d'erreurs
				_nbError++;
				//si deux erreurs d'affilées
				if (_nbError == 2)
				{
					sendMessage(_id_traineeaction + "~"  + "pb1");
				}
				//si trois erreurs d'affilées
				else if (_nbError >= 3)
				{
					sendMessage(_id_traineeaction + "~"  + "pb2");
				}
				else {
					sendMessage(_id_traineeaction + "~"  + " ");
				}
			}
			//si le message n'est pas une erreur
			else
			{
				_nbError = 0;
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
    	if(_end)
    		_papa.addBehaviour(new KillBehaviour(_papa));
    	return _end;
    }
    
    protected void sendMessage(String content)
    {
    	String[] receiver = {_name_pedagogy};
    	_papa.addBehaviour(new OneMessageBehaviour(_papa, receiver, ACLMessage.CFP, content));
    }
    
    protected boolean errorMessage()
	{

		return !_errormessage_traineeaction.contains("(action)");

	}
    
    protected void parseString(String text)
	{
		String str[]= text.split("~");
		
		_id_traineeaction = Integer.parseInt(str[0]);
		_errormessage_traineeaction = str[4];		
	}
}

