package events;

import publishers.AbstractPublisher;


/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public class EventFactory {

	

	/**
	 * This is an implementation of the Factory Method design pattern
	 * Creates an instance of any of the subclasses derived from the top level class AbstractEvent
	 * 
	 * @param eventType a member of the {@link EventType} enumeration
	 * @param eventPublisherId a number generated by invoking the  {@link AbstractPublisher#hashCode()} on the {@link AbstractPublisher} instance issuing the event
	 * @param payload an object of type {@link EventMessage}
	 * @return
	 */
	public static AbstractEvent createEvent(EventType eventType, int eventPublisherId, EventMessage payload) {
		AbstractEvent ret;
		//added logic for generating new events and return the object
		switch(eventType) {
			case TypeA:
				ret = new EventTypeA(EventIDMaker.getNewEventID(), eventPublisherId, payload);
				return ret;
			case TypeB:
				ret = new EventTypeB(EventIDMaker.getNewEventID(), eventPublisherId, payload);
				return ret;
			case TypeC:
				ret = new EventTypeC(EventIDMaker.getNewEventID(), eventPublisherId, payload);
				return ret;
			default:
				System.out.println("EventType either unspecified or DNE!");
				return null;
		}
		
	}
	
}
