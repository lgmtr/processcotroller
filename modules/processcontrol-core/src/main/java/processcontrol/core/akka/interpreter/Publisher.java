package processcontrol.core.akka.interpreter;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Publisher extends UntypedActor {
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	private ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();

	private String channel;

	public Publisher(String channel) {
		this.channel = channel;
	}

	@Override
	public void onReceive(Object msg) {
		log.info("Publish Channel: {}, got: {}", channel, msg);
		if (msg instanceof String) {
			String in = (String) msg;
			String out = in.toUpperCase();
			mediator.tell(new DistributedPubSubMediator.Publish(channel, out), getSelf());
		} else {
			unhandled(msg);
		}
	}
}
