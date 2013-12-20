package asimilProject.intfce;

import asimilProject.utils.BDTrainee;
import jade.core.behaviours.Behaviour;

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
		String mess = new String();
		mess += BDTrainee.getIdTrainneAction(_iterator).toString() + "~";
		mess += BDTrainee.getIdPedagogy(_iterator).toString() + "~";
		mess += BDTrainee.getIdAction(_iterator).toString() + "~";
		mess += BDTrainee.getTimeElapsed(_iterator).toString() + "~";
		mess += BDTrainee.getErrorMessage(_iterator) + "~";
		mess += BDTrainee.getGravity(_iterator).toString();
		
		_papa.waitAndSend(mess, BDTrainee.getTimeElapsed(_iterator) - _timer);
		_timer = BDTrainee.getTimeElapsed(_iterator);
		System.out.println(_iterator);
		_iterator++;
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
