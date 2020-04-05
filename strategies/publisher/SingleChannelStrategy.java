package strategies.publisher;

import java.util.ArrayList;
import java.util.List;
import events.AbstractEvent;
import events.EventFactory;
import events.EventType;
import events.EventMessage;
import pubSubServer.AbstractChannel;
import pubSubServer.ChannelEventDispatcher;


public class SingleChannelStrategy implements IStrategy {
	
	@Override
	public void doPublish(int publisherId) {
		System.out.println("SingleChannelStrategy :: Publisher: " + publisherId + " sending default event!");
		EventMessage eMsg = new events.EventMessage("TypeA", "Made using SingleChannel Strategy.");
		AbstractEvent aEvent = EventFactory.createEvent(EventType.TypeA, publisherId, eMsg);
		
		ChannelEventDispatcher query = pubSubServer.ChannelEventDispatcher.getInstance();
		List<AbstractChannel> channelList = pubSubServer.PublisherManager.getInstance().getPublisherChannels(publisherId);
		List<String> channels = new ArrayList<String>();
		
		if(channelList.isEmpty()) {
			System.out.println("Publisher: " + publisherId + " is not assigned to any channels");
			return;
		}
		
		channels.add(channelList.get(0).getChannelTopic());
		query.postEvent(aEvent, channels);
		
	}

	//AStrategy
	@Override
	public void doPublish(AbstractEvent event, int publisherId) {
		System.out.println("SingleChannelStrategy :: Publisher: " + publisherId + " sending Event :: " + event);

		ChannelEventDispatcher query = pubSubServer.ChannelEventDispatcher.getInstance();
		List<AbstractChannel> channelList = pubSubServer.PublisherManager.getInstance().getPublisherChannels(publisherId);
		List<String> serverList = new ArrayList<String>();
		
		if(channelList.isEmpty()) {
			System.out.println("Publisher: " + publisherId + " is not assigned to any channels");
			return;
		}
	
		serverList.add(channelList.get(0).getChannelTopic());
		query.postEvent(event, serverList);
		
	}

	@Override
	public void addChannel(String channelName, int publisherId) {
		pubSubServer.PublisherManager.getInstance().addPublisherChannel(publisherId, channelName);
	}

}
