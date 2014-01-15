package asimilProject.intfce;

import javax.swing.JOptionPane;

import asimilProject.utils.BDTrainee;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SimulationBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;
	private Interface _papa;
	private Integer _iterator = 0;
	private Integer _timer = 0;
	
	public SimulationBehaviour(Interface papa) {
		_papa = papa;
	}

	@Override
	public void action() {
		ACLMessage msg = _papa.receive();
		if(msg != null && msg.getPerformative() == ACLMessage.FAILURE){
			int n = JOptionPane.showConfirmDialog(null, "Vous avez échoué la simulation, continuer quand même?", "Trop d'erreurs", JOptionPane.YES_NO_OPTION);
			if(n == 1){
				_iterator = BDTrainee.getCount();
			}
		}else{
			String mess = new String();
			mess += BDTrainee.getIdTrainneAction(_iterator).toString() + "~";
			mess += BDTrainee.getIdPedagogy(_iterator).toString() + "~";
			mess += BDTrainee.getIdAction(_iterator).toString() + "~";
			mess += BDTrainee.getTimeElapsed(_iterator).toString() + "~";
			mess += BDTrainee.getErrorMessage(_iterator) + "~";
			mess += BDTrainee.getGravity(_iterator).toString();
			
			_papa.waitAndSend(mess, BDTrainee.getTimeElapsed(_iterator) - _timer);
			_timer = BDTrainee.getTimeElapsed(_iterator);
			_iterator++;
		}
	}

	@Override
	public boolean done() {
		if(_iterator == BDTrainee.getCount()) {
			_papa.finish();
			return true;
		}
		else
			return false;
	}

}
