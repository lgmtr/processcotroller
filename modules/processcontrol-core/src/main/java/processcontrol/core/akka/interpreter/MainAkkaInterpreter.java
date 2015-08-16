package processcontrol.core.akka.interpreter;

import akka.actor.UntypedActor;

public class MainAkkaInterpreter extends UntypedActor {
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg == "DONE") {
			getContext().stop(getSelf());
		} else if(msg != null){
			//System.out.println(msg);
		} else
			unhandled(msg);
	}

}
