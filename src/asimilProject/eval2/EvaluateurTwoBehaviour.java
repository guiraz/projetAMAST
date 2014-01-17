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
	
	//parent agent
	private EvaluateurTwo _papa;
	
	//action's id
	private int _id_traineeaction;
	//action's message
	private String _errormessage_traineeaction;
	//action's gravity
	private int _gravity_traineeaction;
	//variable setting the end of the behaviour
	private boolean _end = false;
	//localname of the pedagogique agent
	private String _name_pedagogy = "pedagogique";
	
	public EvaluateurTwoBehaviour(EvaluateurTwo papa) {
		_papa = papa;
	}
	
    public void action()
    {
    	//receiving messages
    	MessageTemplate m1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    	MessageTemplate m2 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(m1);
		ACLMessage msg2 = myAgent.receive(m2);
		
		//if we receive a CFP message
		if (msg != null)
		{
			///we parse it
			parseString(msg.getContent());
			
			//if the action is an error
			if(errorMessage())
			{
				//depending on the gravity, we send "b", "m" or "g" to pedagogique agent with the action's id
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
		//if we receive a INFORM message
		else if (msg2 != null)
		{
			//end of the simulation
			_end = true;
		}
		//if no message
		else
		{
			//we block and wait for a message
			block();
		}
    }
    
  //send a message to pedagogique agent
    protected void sendMessage(String content)
    {
    	String[] receiver = {_name_pedagogy};
    	_papa.addBehaviour(new OneMessageBehaviour(_papa, receiver, ACLMessage.CFP, content));
    }
    
    //parsing the message and getting the usefull informations
	protected void parseString(String text)
	{
		String str[]= text.split("~");
		
		_id_traineeaction = Integer.parseInt(str[0]);
		_errormessage_traineeaction = str[4];
		_gravity_traineeaction = Integer.parseInt(str[5]);		
	}
	
	//verifying if the action is an error or not
	protected boolean errorMessage()
	{
		return !_errormessage_traineeaction.contains("(action)");
	}
	
	//end of behaviour un killing the parent agent
    public boolean done()
    {
    	if(_end)
    		_papa.addBehaviour(new KillBehaviour(_papa));
    	return _end;
    }
}
