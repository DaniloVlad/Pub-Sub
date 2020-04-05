package publishers;

import strategies.publisher.StrategyFactory;
import strategies.publisher.StrategyName;


/**
 * @author kkontog, ktsiouni, mgrigori
 * creates new {@link AbstractPublisher} objects
 * contributes to the Strategy design pattern
 * implements the FactoryMethod design pattern   
 */
public class PublisherFactory {

	
	/**
	 * This is an implementation of the Factory Method design pattern
	 * Creates an instance of any of the classes implementing the top level Interface IPublisher
	 * 
	 * note we have multiple entries that return instances of the same ConcretePublisher class
	 * 
	 * @param publisherType an entry from the {@link PublisherType} enumeration
	 * @param strategyName an entry from the {@link StrategyName} enumeration
	 * @return an instance of the specified IPublisher implementation with the specified strategyName attached to it
	 */
	public static AbstractPublisher createPublisher(PublisherType publisherType, StrategyName strategyName, String name) {
		AbstractPublisher ip;
		//all of this was given in the base code
//		System.out.println("Making publisher of type:: "+publisherType+ " With strategy:: "+strategyName);
		switch (publisherType) {
			case alphaPub : 
				ip = new ConcretePublisher(name, StrategyFactory.createStrategy(strategyName));
				return ip;
			case betaPub : 
				ip = new ConcretePublisher(name, StrategyFactory.createStrategy(strategyName));
				return ip;
			case gammaPub : 
				ip = new ConcretePublisher(name, StrategyFactory.createStrategy(strategyName));
				return ip;
			case deltaPub : 
				ip = new ConcretePublisher(name, StrategyFactory.createStrategy(strategyName));
				return ip;
			default:
				ip = new ConcretePublisher(name, StrategyFactory.createStrategy(strategyName));
				return ip;
		}
	}
	
}
