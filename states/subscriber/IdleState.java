package states.subscriber;

import events.AbstractEvent;
import subscribers.AbstractSubscriber;

public class IdleState implements IState {

	@Override
	public void handleEvent(AbstractEvent event, String channelName, AbstractSubscriber sub) {
		sub.setState(StateName.running);
		sub.addEvent(event);
		AbstractEvent todo;
		while((todo = sub.popNextEvent()) != null) {
			System.out.println("Subscriber :: " + sub + " handling event ::" + todo);
			sub.addCompletedEvent(todo);
		}
		sub.setState(StateName.idling);
	}
	
}
