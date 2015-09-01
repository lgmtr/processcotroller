package processcontrol.core.interpreter;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import processcontrol.core.akka.interpreter.ActorSystemContainer;
import processcontrol.core.akka.interpreter.PublishContainer;
import processcontrol.core.akka.interpreter.Subscriber;
import akka.actor.Props;

public class CommandInterpreter {

	private final static int TIMEOUT = 5000;

	public static String parseCommand(String command, Map<String, ProcessVariable> processVariables) {
		String[] commandArray = command.split(":");
		String returnValue = "";
		switch (commandArray[0].toUpperCase()) {
		case "SET":
			switch (commandArray[1].toUpperCase()) {
			case "WINDOW":
				windowCommand(commandArray);
				break;
			case "HEATER":
				heaterCommand(commandArray);
				break;
			case "SHUTTER":
				shutterCommand(commandArray);
				break;
			case "CURTAIN":
				curtainCommand(commandArray);
				break;
			case "LIGHT":
				lightCommand(commandArray);
				break;
			default:
				returnValue = "No Such Command found!";
				break;
			}
			break;
		case "GET":
			switch (commandArray[1].toUpperCase()) {
			case "WINDOW":
				returnValue = getWindowCommand(commandArray, processVariables);
				break;
			default:
				returnValue = "No Such Command found!";
				break;
			}
			break;
		default:
			returnValue = "No Such Command found!";
			break;
		}
		return returnValue;
	}

	private static String getWindowCommand(String[] commandArray, Map<String, ProcessVariable> processVariables) {
		ActorSystemContainer.getInstance().getSystem().actorOf(Props.create(Subscriber.class, commandArray[1].toUpperCase(), processVariables), commandArray[1].toUpperCase() + "_Subscriber");
		int timeOutCounter = 0;
		do{
			PublishContainer.getInstance().getPublisher(commandArray[1].toUpperCase()).tell(commandArray[0] + ":" + commandArray[1] + ":" +commandArray[2], null);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			if(processVariables.containsKey(commandArray[1].toUpperCase())){
				return (String) processVariables.get(commandArray[1].toUpperCase()).getValue();
			}
			timeOutCounter+=50;
		}while(timeOutCounter < TIMEOUT);
		return "";
	}

	private static void lightCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		String[] dummy2 = idOrGroupCheck(command[3]);
		String returnString = "";
		if ("Group".equals(dummy2[0])){
			returnString =  "ControlLightRGB" + dummy[0] + "(" + dummy[1] + ", " + dummy2[1] + ", " + command[4] + ");";
		}else{
			returnString =  "ControlLight" + dummy[0] + "(" + dummy[1] + ", " + dummy2[1] + ");";
		}
		PublishContainer.getInstance().getPublisher(command[1].toUpperCase()).tell(returnString, null);
	}

	private static void curtainCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		final String returnString =  "ControlCurtain(" + dummy[1] + ", " + command[3].toUpperCase() + ");";
		PublishContainer.getInstance().getPublisher(command[1].toUpperCase()).tell(returnString, null);
	}

	private static void shutterCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		final String returnString =  "ControlShutter(" + dummy[1] + ", " + command[3].toUpperCase() + ");";
		PublishContainer.getInstance().getPublisher(command[1].toUpperCase()).tell(returnString, null);
	}

	private static void heaterCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		final String returnString =  "ControlHeater" + dummy[0] + "(" + dummy[1] + ", " + command[3] + ");";
		PublishContainer.getInstance().getPublisher(command[1].toUpperCase()).tell(returnString, null);
	}

	private static void windowCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		final String returnString = "ControlWindow" + dummy[0] + "(" + dummy[1] + ", " + command[3] + ", " + command[4].toUpperCase() + ");";
		PublishContainer.getInstance().getPublisher(command[1].toUpperCase()).tell(returnString, null);
	}

	private static String[] idOrGroupCheck(String idOrGroup) {
		String[] idOrGroupArray = new String[2];
		if (idOrGroup != null) {
			if (StringUtils.isNumeric(idOrGroup)) {
				idOrGroupArray[0] = "";
				idOrGroupArray[1] = idOrGroup;
			} else {
				idOrGroupArray[0] = "Group";
				idOrGroupArray[1] = idOrGroup.toUpperCase();
			}
		} else {
			throw new NullPointerException();
		}
		return idOrGroupArray;
	}

}
