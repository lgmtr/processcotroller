package processcontrol.core.interpreter.test;

import junit.framework.Assert;

import org.testng.annotations.Test;

import processcontrol.core.interpreter.CommandInterpreter;

public class CommandInterpreterTest {

	@Test
	public void commandInterpreterTest(){
		Assert.assertEquals("ControlWindow(2, 20, FAST);", CommandInterpreter.parseCommand("SET:WINDOW:2:20:FAST"));
		Assert.assertEquals("ControlWindowGroup(ALL, 20, FAST);", CommandInterpreter.parseCommand("SET:WINDOW:ALL:20:FAST"));
		Assert.assertEquals("ControlHeater(1, 5);", CommandInterpreter.parseCommand("SET:HEATER:1:5"));
		Assert.assertEquals("ControlCurtain(2, OPEN);", CommandInterpreter.parseCommand("SET:CURTAIN:2:OPEN"));
		Assert.assertEquals("ControlShutter(DINING_KITCHEN, HALF);", CommandInterpreter.parseCommand("SET:SHUTTER:DINING_KITCHEN:HALF"));
		Assert.assertEquals("ControlLight(2, 100);", CommandInterpreter.parseCommand("SET:LIGHT:2:100"));
		Assert.assertEquals("ControlLightRGBGroup(DINING_KITCHEN, BLUE, 50);", CommandInterpreter.parseCommand("SET:LIGHT:DINING_KITCHEN:BLUE:50"));
	}
	
}
