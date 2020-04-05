package publishers;

import events.AbstractEvent;
import pubSubServer.PublisherManager;
import strategies.publisher.IStrategy;
import strategies.publisher.StrategyFactory;


/**
 * @author kkontog, ktsiouni, mgrigori
 * 
 * the WeatherPublisher class is an example of a ConcretePublisher 
 * implementing the IPublisher interface. Of course the publish 
 * methods could have far more interesting logics
 */
public class ConcretePublisher extends AbstractPublisher {

	String name;
	/**
	 * @param concreteStrategy attaches a concreteStrategy generated from the {@link StrategyFactory#createStrategy(strategies.publisher.StrategyName)}
	 * method
	 */
	protected ConcretePublisher(String name, IStrategy concreteStrategy) {
		this.publishingStrategy = concreteStrategy;
		this.name = name;
		//register publisher with pubSubServer
		PublisherManager.getInstance().addPublisher(this);
		System.out.println("Publisher :: " + this + " has been created!");
	}

	/* (non-Javadoc)
	 * @see publishers.IPublisher#publish(events.AbstractEvent)
	 */
	@Override
	public void publish(AbstractEvent event) {
		System.out.println("Publisher :: " + name + " posting event :: "+event);
		publishingStrategy.doPublish(event, this.hashCode());
	}

	/* (non-Javadoc)
	 * @see publishers.IPublisher#publish()
	 */
	@Override
	public void publish() {
		System.out.println("Publisher :: " + name + " posting default event!");
		publishingStrategy.doPublish(this.hashCode());
	}
	
	@Override
	public void addChannel(String channelName) {
		publishingStrategy.addChannel(channelName, this.hashCode());
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
