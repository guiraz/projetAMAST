package asimilProject.utils;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class KillBehaviour extends OneShotBehaviour {
	
	private Agent _papa;

	private static final long serialVersionUID = 1L;
	
	public KillBehaviour(Agent papa) {
		_papa = papa;
	}

	@Override
	public void action() {
		_papa.doDelete();
	}

}
