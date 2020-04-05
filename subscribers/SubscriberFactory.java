package subscribers;

import states.subscriber.StateName;


/**
 * @author kkontog, ktsiouni, mgrigori
 *  
 */
/**
 * @author kkontog, ktsiouni, mgrigori
 * creates new {@link AbstractSubscriber} objects
 * contributes to the State design pattern
 * implements the FactoryMethod design pattern   
 */
public class SubscriberFactory {

	
	/**
	 * creates a new {@link AbstractSubscriber} using an entry from the {@link SubscriberType} enumeration
	 * @param subscriberType a value from the {@link SubscriberType} enumeration specifying the type of Subscriber to be created 
	 * @return the newly created {@link AbstractSubscriber} instance 
	 */
	public static AbstractSubscriber createSubscriber(SubscriberType subscriberType, StateName stateName) {
		//TO-DO: get rid of 3 concrete subs, move to a single model where the init state
		//is set through the constructor i.e case alpha: return new ConSub(stateName);
		//like how the publisher system works...
		switch (subscriberType) {
			case alpha: 
				return new ConcreteSubscriber(stateName);
			case beta:
				return new ConcreteSubscriber(stateName);
			case gamma:
				return new ConcreteSubscriber(stateName);
			default:
				return null;
		}
	}
	
}
