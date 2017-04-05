package processcontrol.core.akka.cluster.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import processcontrol.core.akka.interpreter.ActorSystemContainer;
import processcontrol.core.akka.interpreter.PublishContainer;
import processcontrol.core.akka.interpreter.Subscriber;
import processcontrol.core.interpreter.ProcessVariable;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class PublishSubscribeTest {
	
	private final static String CHANNEL = "processengine";

	@Test
	public void pubSubTest() {
		Map<String, ProcessVariable> processVariables = new HashMap<String, ProcessVariable>();
		final ActorSystem system = ActorSystemContainer.getInstance().getSystem();
		
		system.actorOf(Props.create(Subscriber.class, CHANNEL, processVariables), "subscriber1");
		// another node
		system.actorOf(Props.create(Subscriber.class, CHANNEL, processVariables), "subscriber2");
		system.actorOf(Props.create(Subscriber.class, CHANNEL, processVariables), "subscriber3");

		ActorRef publisher = PublishContainer.getInstance().getPublisher(CHANNEL);
		// after a while the subscriptions are replicated
		for (int i = 0; i < 3; i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			publisher.tell("hello", null);
		}
	}
}
