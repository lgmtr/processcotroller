package processcontrol.core.interpreter;

import org.springframework.stereotype.Service;

@Service
public class TaskInterpreter {
	
	public void taskCommand(String command){
		System.out.println("Command: " + command + " / Thread: " + Thread.currentThread().getName());
	}

}
