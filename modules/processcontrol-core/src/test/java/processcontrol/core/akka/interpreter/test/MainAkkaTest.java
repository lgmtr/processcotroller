package processcontrol.core.akka.interpreter.test;

import org.testng.annotations.Test;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import processcontrol.core.akka.interpreter.MainAkkaInterpreter;

public class MainAkkaTest {

	@Test
	public void akkaTest() {
		final ActorSystem system = ActorSystem.create("CreationSystem", ConfigFactory.load("remotecreation"));
		final ActorRef commandPrinter = system.actorOf(Props.create(MainAkkaInterpreter.class), "MainAkkaInterpreter");
		commandPrinter.tell("SEND", null);
	}

}
