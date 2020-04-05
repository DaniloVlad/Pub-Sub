package states.subscriber;

import events.AbstractEvent;
import subscribers.AbstractSubscriber;

public class OfflineState implements IState {

	@Override
	public void handleEvent(AbstractEvent event, String channelName, AbstractSubscriber sub) {
		System.out.println("Subscriber :: " + sub + " is currently offline. Queueing Event...");
		sub.addEvent(event);
	}

}
