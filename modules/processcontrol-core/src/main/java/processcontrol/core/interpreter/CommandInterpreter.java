package processcontrol.core.interpreter;

import org.apache.commons.lang.StringUtils;

public class CommandInterpreter {

	public static String parseCommand(String command) {
		String[] commandArray = command.split(":");
		String returnValue = "";
		switch (commandArray[0].toUpperCase()) {
		case "SET":
			switch (commandArray[1].toUpperCase()) {
			case "WINDOW":
				returnValue = windowCommand(commandArray);
				break;
			case "HEATER":
				returnValue = heaterCommand(commandArray);
				break;
			case "SHUTTER":
				returnValue = shutterCommand(commandArray);
				break;
			case "CURTAIN":
				returnValue = curtainCommand(commandArray);
				break;
			case "LIGHT":
				returnValue = lightCommand(commandArray);
				break;
			default:
				returnValue = "No Such Command found!";
				break;
			}
			break;
		case "GET":
			returnValue = "No Such Command found!";
			break;
		default:
			returnValue = "No Such Command found!";
			break;
		}
		return returnValue;
	}

	private static String lightCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		String[] dummy2 = idOrGroupCheck(command[3]);
		if ("Group".equals(dummy2[0]))
			return "ControlLightRGB" + dummy[0] + "(" + dummy[1] + ", " + dummy2[1] + ", " + command[4] + ");";
		return "ControlLight" + dummy[0] + "(" + dummy[1] + ", " + dummy2[1] + ");";
	}

	private static String curtainCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		return "ControlCurtain(" + dummy[1] + ", " + command[3].toUpperCase() + ");";
	}

	private static String shutterCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		return "ControlShutter(" + dummy[1] + ", " + command[3].toUpperCase() + ");";
	}

	private static String heaterCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		return "ControlHeater" + dummy[0] + "(" + dummy[1] + ", " + command[3] + ");";
	}

	private static String windowCommand(String[] command) {
		String[] dummy = idOrGroupCheck(command[2]);
		return "ControlWindow" + dummy[0] + "(" + dummy[1] + ", " + command[3] + ", " + command[4].toUpperCase() + ");";
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
