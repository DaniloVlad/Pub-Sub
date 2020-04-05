package states.subscriber;

import events.AbstractEvent;
import subscribers.AbstractSubscriber;

public class RunningState implements IState {

	@Override
	public void handleEvent(AbstractEvent event, String channelName, AbstractSubscriber sub) {
		System.out.println("Subscriber :: " + sub + " is currently running. Adding Event to Queue");
		sub.addEvent(event);
	}

}
