package events;

public class EventTypeC extends AbstractEvent {
	
	protected EventTypeC(long eventID, int eventPublisherId, EventMessage payload) {
		super(eventID, eventPublisherId, payload);
	}

}
