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
	
	private Pedagogique _papa;
	
	private List<Integer> _actionIds;
	private List<String> _actionMsg;
	private List<String> _actionGravity;
	private List<Boolean> _msgReceived;
	private List<Boolean> _gravityReceived;

	private boolean _alreadyStop = false;
	private boolean _end = false;
	private int _nbError = 0;
	private int _nbErrorG = 0;
	private String _name_traceur = "traceur";
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
    	//on recupere les messages de type CFP
    	MessageTemplate m1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    	MessageTemplate m2 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(m1);
		ACLMessage msg2 = myAgent.receive(m2);
		
		if (msg != null)
		{
			Integer index = parseString(msg.getContent());
			_nbError++;
			
			if(_gravityReceived.get(index) && _msgReceived.get(index)) {
				String tempMsg = _actionMsg.get(index);
				String tempGravity = _actionGravity.get(index);
				
				if(tempGravity.equals("g"))
					_nbErrorG++;
				
				if(tempMsg.equals("pb2") || _nbErrorG >= 3){
					sendMessage(_actionIds.get(index) + "~" + "msg3");
				}
				else if(tempMsg.equals("pb1")) {
					sendMessage(_actionIds.get(index) + "~" + "msg2");
				}
				else if(tempGravity.equals("g") || tempGravity.equals("m")) {
					sendMessage(_actionIds.get(index) + "~" + "msg1");
				}
				
				if((_nbError >= 10 || _nbErrorG >= 5) && !_alreadyStop) {
					_alreadyStop = true;
					stopSimulation();
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
	
    protected void sendMessage(String content)
    {
    	String[] receiver = {_name_traceur};
    	_papa.addBehaviour(new OneMessageBehaviour(_papa, receiver, ACLMessage.CFP, content));
    }
    
    private void stopSimulation() {
    	String[] receiver = {_name_interface};
    	_papa.addBehaviour(new OneMessageBehaviour(_papa, receiver, ACLMessage.FAILURE, ""));
    }
	
    public boolean done()
    {
    	if(_end)
    		_papa.addBehaviour(new KillBehaviour(_papa));
    	return _end;
    }
}
