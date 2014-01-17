package asimilProject.eval1;

import asimilProject.utils.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EvaluateurOneBehaviour extends Behaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//parent agent
	private EvaluateurOne _papa;
	
	//action's id
	private int _id_traineeaction;
	//action's message
	private String _errormessage_traineeaction;
	//variable setting the end of the behaviour
	private boolean _end = false;
	//error counter
	private int _nbError = 0;
	//localname of the pedagogique agent
	private String _name_pedagogy = "pedagogique";
	
	public EvaluateurOneBehaviour(EvaluateurOne papa) {
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
			//we parse it
			parseString(msg.getContent());
			
			//if the action is an error
			if(errorMessage())
			{
				//imcrements the error counter
				_nbError++;
				//if two mistakes in a row we send the action's id and "pb1"
				if (_nbError == 2)
				{
					sendMessage(_id_traineeaction + "~"  + "pb1");
				}
				//if three mistakes in a row we send the action's id and "pb2"
				else if (_nbError >= 3)
				{
					sendMessage(_id_traineeaction + "~"  + "pb2");
				}
				//else we send action's id and " "
				//because pedagogique waits for a message for every mistake
				else {
					sendMessage(_id_traineeaction + "~"  + " ");
				}
			}
			//if it's not
			else
			{
				_nbError = 0;
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
    
    //end of behaviour un killing the parent agent
    public boolean done()
    {
    	if(_end)
    		_papa.addBehaviour(new KillBehaviour(_papa));
    	return _end;
    }
    
    //send a message to pedagogique agent
    protected void sendMessage(String content)
    {
    	String[] receiver = {_name_pedagogy};
    	_papa.addBehaviour(new OneMessageBehaviour(_papa, receiver, ACLMessage.CFP, content));
    }
    
    //verifying if the action is an error or not
    protected boolean errorMessage()
	{
		return !_errormessage_traineeaction.contains("(action)");
	}
    
    //parsing the message and getting the usefull informations
    protected void parseString(String text)
	{
		String str[]= text.split("~");
		
		_id_traineeaction = Integer.parseInt(str[0]);
		_errormessage_traineeaction = str[4];		
	}
}

