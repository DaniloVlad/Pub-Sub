package subscribers;

import java.util.LinkedList;
import java.util.Queue;
import events.AbstractEvent;
import pubSubServer.SubscriptionManager;
import states.subscriber.StateFactory;
import states.subscriber.StateName;


/**
 * @author kkontog, ktsiouni, mgrigori
 * an example concrete subscriber
 */
class ConcreteSubscriber extends AbstractSubscriber {

	protected Queue<AbstractEvent> completed;
	protected Queue<AbstractEvent> notCompleted;
	
	protected ConcreteSubscriber(StateName stateName) {
		state = StateFactory.createState(stateName);
		completed = new LinkedList<AbstractEvent>();
		notCompleted = new LinkedList<AbstractEvent>();
		System.out.println("Subscriber :: " + this + " has been created!");
	}
	
	/* (non-Javadoc)
	 * @see subscribers.ISubscriber#setState(states.subscriber.StateName)
	 */
	public void setState(StateName stateName) {
		state = StateFactory.createState(stateName);
		System.out.println("Subscriber :: " + this + " changing state to :: " + stateName);
	}
		
	public void addCompletedEvent(AbstractEvent event) {
		completed.add(event);
	}
	
	public void addEvent(AbstractEvent event) {
		notCompleted.add(event);
	}
	/* (non-Javadoc)
	 * @see subscribers.ISubscriber#alert(events.AbstractEvent, java.lang.String)
	 */
	@Override
	public void alert(AbstractEvent event, String channelName) {
		System.out.println("ConcreteSubscriber :: " + this + " recieved event ::" + event + ":: published on channel " + channelName);
		state.handleEvent(event, channelName, this);
	}

	/* (non-Javadoc)
	 * @see subscribers.ISubscriber#subscribe(java.lang.String)
	 */
	@Override
	public void subscribe(String channelName) {
		SubscriptionManager.getInstance().subscribe(channelName, this);		
	}

	/* (non-Javadoc)
	 * @see subscribers.ISubscriber#unsubscribe(java.lang.String)
	 */
	@Override
	public void unsubscribe(String channelName) {
		SubscriptionManager.getInstance().unSubscribe(channelName, this);
		
	}
	
	
	
}
