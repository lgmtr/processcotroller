package processcontrol.core.interpreter;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import processcontrol.core.akka.interpreter.MainAkkaInterpreter;
import processcontrol.core.logging.LoggingBean;
import processcontrol.core.logging.LoggingBean.Log;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;

@Service
public class TaskInterpreter {
	
	@Resource
	private LoggingBean loggingBean;
	
	private final ActorSystem system = ActorSystem.create("CreationSystem", ConfigFactory.load("remotecreation"));
	
	private final ActorRef commandPrinter = system.actorOf(Props.create(MainAkkaInterpreter.class), "MainAkkaInterpreter"); 
	
	public void taskCommand(String command, String time){
		if(command != "DONE"){
			Log log = loggingBean.new Log(time, command, Thread.currentThread().getName());
			loggingBean.addLogList(log);
			commandPrinter.tell("Time: " + log.getLogTime() + " / Command: " + log.getCommandText() + " / Thread: " + log.getThread(), null);
		}else{
			commandPrinter.tell(command, null);
		}
	}

}
