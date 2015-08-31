package processcontrol.core.interpreter.test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AkkaPubSubExample {

	public class Subscriber extends UntypedActor {
		LoggingAdapter log = Logging.getLogger(getContext().system(), this);

		public Subscriber() {
			ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();
			// subscribe to the topic named "content"
			mediator.tell(new DistributedPubSubMediator.Subscribe("content", getSelf()), getSelf());
		}

		public void onReceive(Object msg) {
			if (msg instanceof String)
				log.info("Got: {}", msg);
			else if (msg instanceof DistributedPubSubMediator.SubscribeAck)
				log.info("subscribing");
			else
				unhandled(msg);
		}
	}

	public class Publisher extends UntypedActor {

		// activate the extension
		ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();

		public void onReceive(Object msg) {
			if (msg instanceof String) {
				String in = (String) msg;
				String out = in.toUpperCase();
				mediator.tell(new DistributedPubSubMediator.Publish("content", out), getSelf());
			} else {
				unhandled(msg);
			}
		}
	}
	
	public static void main(String[] args) {
		new AkkaPubSubExample().startPubSub();
	}

	private void startPubSub() {
		final ActorSystem system = ActorSystem.create("pubSub");
		
		system.actorOf(Props.create(Subscriber.class), "subscriber1");
		//another node
		system.actorOf(Props.create(Subscriber.class), "subscriber2");
		system.actorOf(Props.create(Subscriber.class), "subscriber3");
		
		ActorRef publisher = system.actorOf(Props.create(Publisher.class), "publisher");
		// after a while the subscriptions are replicated
		publisher.tell("hello", null);
		
	}

}
