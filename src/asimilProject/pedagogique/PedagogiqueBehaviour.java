package asimilProject.pedagogique;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PedagogiqueBehaviour extends Behaviour
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private boolean _end = false;
	private int _nbError = 0;
	private int _nbErrorG = 0;
	private String _name_pedagogy = "pedagogie";
	private String _mess;
	private int _temps;
	
    public void action()
    {
    	//on recupere les messages de type CFP
    	MessageTemplate m1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    	MessageTemplate m2 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(m1);
		ACLMessage msg2 = myAgent.receive(m2);
		
		if (msg != null)
		{
			parseString(msg.getContent());
			_nbError++;
			
			if(_mess.equals("g"))
			{
				_nbErrorG++;
			}
			
			if((_nbError == 10) || (_nbErrorG == 5))
			{
				//arret !!
				//a voir comment faire
				//+ possibilite de désactiver pour poursuivre la procedure
				sendMessage("Arret !!!~" + _temps);
			}
			else if ((_nbErrorG == 3) || (_mess.equals("pb2")))
			{
				sendMessage("msg3~" + _temps);
			}
			else if ((_mess.equals("m")) || (_mess.equals("g")))
			{
				sendMessage("msg1~" + _temps);
			}
			else if (_mess.equals("pb1"))
			{
				sendMessage("msg2~" + _temps);
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
    
    protected void parseString(String text)
	{
		String str[]= text.split("~");
		
		_mess = str[0];
		_temps = Integer.parseInt(str[1]);	
	}
	
    protected void sendMessage(String content)
    {
    	ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		msg.addReceiver(new AID(_name_pedagogy, AID.ISLOCALNAME));
		msg.setContent(content);
		myAgent.send(msg);
    }
	
    public boolean done()
    {
    	return _end;
    }
}
