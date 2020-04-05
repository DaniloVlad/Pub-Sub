package states.subscriber;

import events.AbstractEvent;
import subscribers.AbstractSubscriber;

public class ErrorState implements IState {

	@Override
	public void handleEvent(AbstractEvent event, String channelName, AbstractSubscriber sub) {
		System.out.println("Subscriber :: " + sub + " had an error occur. Please Reset, Event is being ignored.");
	}

}
