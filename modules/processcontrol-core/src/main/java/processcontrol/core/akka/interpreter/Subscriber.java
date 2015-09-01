package processcontrol.core.akka.interpreter;

import java.util.Map;

import processcontrol.core.interpreter.ProcessVariable;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Subscriber extends UntypedActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	private Map<String, ProcessVariable> processVariables;
	
	private String channel;

	public Subscriber(String channel, Map<String, ProcessVariable> processVariables) {
		this.channel = channel;
		this.processVariables = processVariables;
		ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();
		// subscribe to the topic named "content"
		mediator.tell(new DistributedPubSubMediator.Subscribe(channel, getSelf()), getSelf());
	}
	
	@Override
	public void onReceive(Object msg) {
		if (msg instanceof String){
			ProcessVariable pv = new ProcessVariable();
			pv.setValue(msg);
			pv.setClassOfValue(String.class);
			processVariables.put(channel, pv);
			log.info("Got: {}", msg);
		}else if (msg instanceof DistributedPubSubMediator.SubscribeAck)
			log.info("subscribing");
		else
			unhandled(msg);
	}
}
