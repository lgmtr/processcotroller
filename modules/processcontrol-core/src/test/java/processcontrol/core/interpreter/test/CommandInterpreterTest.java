package processcontrol.core.interpreter.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import processcontrol.core.interpreter.CommandInterpreter;
import processcontrol.core.interpreter.ProcessVariable;

public class CommandInterpreterTest {

	private final String[] commands = { "SET:WINDOW:2:20:FAST", "SET:WINDOW:ALL:20:FAST", "SET:HEATER:1:5", "SET:CURTAIN:2:OPEN",
			"SET:SHUTTER:DINING_KITCHEN:HALF", "SET:LIGHT:2:100", "SET:LIGHT:DINING_KITCHEN:BLUE:50", "GET:WINDOW:STATUS" };
	
	private Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();

	@Test
	public void commandInterpreterTest() {
		for (int i = 0; i < commands.length; i++) {
			CommandRunner cr = new CommandRunner(commands[i]);
			cr.start();
			try {
				cr.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class CommandRunner extends Thread {

		private String returnValue;

		private String command;

		public CommandRunner(String command) {
			this.command = command;
		}

		@Override
		public void run() {
			this.setReturnValue(CommandInterpreter.parseCommand(command, processVariables));
		}

		public String getReturnValue() {
			return returnValue;
		}

		public void setReturnValue(String returnValue) {
			this.returnValue = returnValue;
		}
		
	}

}
