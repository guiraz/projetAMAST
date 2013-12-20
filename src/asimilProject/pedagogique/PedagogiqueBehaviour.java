package asimilProject.pedagogique;

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
	
    public void action()
    {
    	//on recupere les messages de type CFP
    	MessageTemplate m1 = MessageTemplate.MatchPerformative(ACLMessage.CFP);
    	MessageTemplate m2 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(m1);
		ACLMessage msg2 = myAgent.receive(m2);
		String mess;
		
		if (msg != null)
		{
			mess = msg.getContent();
			_nbError++;
			
			if(mess.equals("g"))
			{
				_nbErrorG++;
			}
			
			if((_nbError == 10) || (_nbErrorG == 5))
			{
				//arret !!
				//a voir comment faire
				//+ possibilite de désactiver pour poursuivre la procedure
			}
			else if ((_nbErrorG == 3) || (mess.equals("pb2")))
			{
				System.out.println("msg3");
			}
			else if ((mess.equals("m")) || (mess.equals("g")))
			{
				System.out.println("msg1");
			}
			else if (mess.equals("pb1"))
			{
				System.out.println("msg2");
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
}
