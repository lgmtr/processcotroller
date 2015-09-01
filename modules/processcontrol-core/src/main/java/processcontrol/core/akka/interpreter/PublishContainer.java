package processcontrol.core.akka.interpreter;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.Props;

public class PublishContainer {

	private static PublishContainer instance = null;

	private Map<String, ActorRef> publisher;

	public PublishContainer() {
		publisher = new HashMap<>();
	}

	public ActorRef getPublisher(String channel) {
		ActorRef publishActor = null;
		if (publisher.containsKey(channel)) {
			publishActor = publisher.get(channel);
		} else {
			publishActor = ActorSystemContainer.getInstance().getSystem().actorOf(Props.create(Publisher.class, channel), channel);
			publisher.put(channel, publishActor);
		}
		return publishActor;
	}

	public static synchronized PublishContainer getInstance() {
		if (instance == null) {
			instance = new PublishContainer();
		}
		return instance;
	}
}
