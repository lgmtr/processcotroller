package processcontrol.core.interpreter;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import processcontrol.core.akka.interpreter.MainAkkaInterpreter;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;

@Service
public class TaskInterpreter implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final ActorSystem system = ActorSystem.create("CreationSystem", ConfigFactory.load("remotecreation"));
	
	private final ActorRef commandPrinter = system.actorOf(Props.create(MainAkkaInterpreter.class), "MainAkkaInterpreter"); 
	
	public void taskCommand(String command, String time){
		if(command != "DONE"){
			commandPrinter.tell("Time: " + time + " / Command: " + command + " / Thread: " + Thread.currentThread().getName(), null);
		}else{
			commandPrinter.tell(command, null);
		}
	}

}
