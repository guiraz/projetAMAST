package asimilProject.pedagogique;

import java.util.ArrayList;
import java.util.List;

import asimilProject.utils.KillBehaviour;
import asimilProject.utils.OneMessageBehaviour;

import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PedagogiqueBehaviour extends Behaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//parent agent
	private Pedagogique _papa;
	
	//all received informations of the actions
	private List<Integer> _actionIds;
	private List<String> _actionMsg;
	private List<String> _actionGravity;
	//booleans setting if we received the messages from eval1 & eval2
	private List<Boolean> _msgReceived;
	private List<Boolean> _gravityReceived;

	//if the FAILURE message have already be sent or not
	private boolean _alreadyStop = false;
	//variable setting the end of the behaviour
	private boolean _end = false;
	//mistake counter
	private int _nbError = 0;
	//huge mistake counter
	private int _nbErrorG = 0;
	//name of the traceur agent
	private String _name_traceur = "traceur";
	//name of the interface agent
	private String _name_interface = "intfce";
	
	public PedagogiqueBehaviour(Pedagogique papa){
		_papa = papa;
		_actionIds = new ArrayList<Integer>();
		_actionMsg = new ArrayList<String>();
		_actionGravity = new ArrayList<String>();
		_msgReceived = new ArrayList<Boolean>();
		_gravityReceived = new ArrayList<Boolean>();
	}
	
    public void action()
    {
    	//receiving messages
    	MessageTemplate m1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    	MessageTemplate m2 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(m1);
		ACLMessage msg2 = myAgent.receive(m2);
		
		//if CFP message
		if (msg != null)
		{
			//parse it
			Integer index = parseString(msg.getContent());
			//imcrements the mistake counter
			_nbError++;
			
			//if we receive both the eval1 & eval2 messages
			if(_gravityReceived.get(index) && _msgReceived.get(index)) {
				
				//get the infos of this action
				String tempMsg = _actionMsg.get(index);
				String tempGravity = _actionGravity.get(index);
				
				//if it's a huge mistake, imcrements the counter
				if(tempGravity.equals("g"))
					_nbErrorG++;
				
				//depends on messages of eval1 & eval 2 and on the huge mistakes counter
				//send traceur agent "msg1", "msg2" or "msg3"
				if(tempMsg.equals("pb2") || _nbErrorG >= 3){
					sendMessage(_actionIds.get(index) + "~" + "msg3");
				}
				else if(tempMsg.equals("pb1")) {
					sendMessage(_actionIds.get(index) + "~" + "msg2");
				}
				else if(tempGravity.equals("g") || tempGravity.equals("m")) {
					sendMessage(_actionIds.get(index) + "~" + "msg1");
				}
				
				//if mistakes counter >= 10 or huge mistakes >= 5
				//send FAILURE message to interface agent
				if((_nbError >= 10 || _nbErrorG >= 5) && !_alreadyStop) {
					_alreadyStop = true;
					stopSimulation();
				}
			}
		}
		//if INFORM message
		else if (msg2 != null)
		{
			//end of simulation
			_end = true;
		}
		//if no message
		else
		{
			//block and wait
			block();
		}
    }
    
    //parse the message and fill the arrays with the informations
    //manage the fact that eval1's message can arrive before eval2's one
    //and vice versa
    protected Integer parseString(String text)
	{
		String str[]= text.split("~");
		
		Integer tempId = Integer.parseInt(str[0]);
		String tempMsg = str[1];
		
		Integer index = _actionIds.indexOf(tempId);
		
		if(tempMsg.equals("b") || tempMsg.equals("m") || tempMsg.equals("g")){
			if(index != -1) {
				_actionGravity.set(index, tempMsg);
				_gravityReceived.set(index, true);
			}else{
				_actionIds.add(tempId);
				_actionGravity.add(tempMsg);
				_actionMsg.add(null);
				_gravityReceived.add(true);
				_msgReceived.add(false);
			}
		}else {
			if(index != -1) {
				_actionMsg.set(index, tempMsg);
				_msgReceived.set(index, true);
			}else{
				_actionIds.add(tempId);
				_actionMsg.add(tempMsg);
				_actionGravity.add(null);
				_msgReceived.add(true);
				_gravityReceived.add(false);
			}
		}
		
		return _actionIds.indexOf(tempId);
	}
	
    //send a message to traceur agent
    protected void sendMessage(String content)
    {
    	String[] receiver = {_name_traceur};
    	_papa.addBehaviour(new OneMessageBehaviour(_papa, receiver, ACLMessage.CFP, content));
    }
    
    //send the FAILURE message to interface agent
    private void stopSimulation() {
    	String[] receiver = {_name_interface};
    	_papa.addBehaviour(new OneMessageBehaviour(_papa, receiver, ACLMessage.FAILURE, ""));
    }
	
    //stop the behaviour and kill parent agent
    public boolean done()
    {
    	if(_end)
    		_papa.addBehaviour(new KillBehaviour(_papa));
    	return _end;
    }
}
