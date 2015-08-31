package processcontrol.core.interpreter.test;

import junit.framework.Assert;

import org.testng.annotations.Test;

import processcontrol.core.interpreter.CommandInterpreter;

public class CommandInterpreterTest {

	private final String[] expectedValue = { "ControlWindow(2, 20, FAST);", "ControlWindowGroup(ALL, 20, FAST);", "ControlHeater(1, 5);",
			"ControlCurtain(2, OPEN);", "ControlShutter(DINING_KITCHEN, HALF);", "ControlLight(2, 100);", "ControlLightRGBGroup(DINING_KITCHEN, BLUE, 50);",
			"Get Value Command" };

	private final String[] commands = { "SET:WINDOW:2:20:FAST", "SET:WINDOW:ALL:20:FAST", "SET:HEATER:1:5", "SET:CURTAIN:2:OPEN",
			"SET:SHUTTER:DINING_KITCHEN:HALF", "SET:LIGHT:2:100", "SET:LIGHT:DINING_KITCHEN:BLUE:50", "GET:WINDOW:STATE" };

	@Test
	public void commandInterpreterTest() {
		Assert.assertEquals(expectedValue.length, commands.length);
		for (int i = 0; i < expectedValue.length; i++) {
			CommandRunner cr = new CommandRunner(commands[i]);
			cr.setThread(cr);
			cr.start();
			try {
				cr.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(cr.getReturnValue());
			Assert.assertEquals(expectedValue[i], cr.getReturnValue());
		}
	}

	public class CommandRunner extends Thread {
		private Thread thread;

		private String returnValue;

		private String command;

		public CommandRunner(String command) {
			this.command = command;
		}

		@Override
		public void run() {
			this.setReturnValue(CommandInterpreter.parseCommand(command, thread));
		}

		public String getReturnValue() {
			return returnValue;
		}

		public void setReturnValue(String returnValue) {
			this.returnValue = returnValue;
		}
		
		public void setThread(Thread thread){
			this.thread = thread;
		}
	}

}
