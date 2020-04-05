package strategies.publisher;

import java.util.ArrayList;
import java.util.List;
import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.AbstractChannel;
import pubSubServer.ChannelEventDispatcher;

public class MultiChannelStrategy implements IStrategy {
	
	//multi channel is when posting an event to more than one channel but not all channels
	@Override
	public void doPublish(int publisherId) {
		System.out.println("MultiChannelStrategy :: Publisher: " + publisherId + " sending default event.");
		EventMessage eMsg = new events.EventMessage("TypeB", "Made using BStrategy.");
		AbstractEvent aEvent = EventFactory.createEvent(EventType.TypeB, publisherId, eMsg);
		
		ChannelEventDispatcher query = pubSubServer.ChannelEventDispatcher.getInstance();
		List<AbstractChannel> channelList = pubSubServer.PublisherManager.getInstance().getPublisherChannels(publisherId);
		
		if(channelList.isEmpty()) {
			System.out.println("MultiChannelStrategy :: Error: No channels assigned to publisher");
			return;
		}
		List<String> allChannels = new ArrayList<String>();
		for(AbstractChannel ch: channelList)
			allChannels.add(ch.getChannelTopic());
		
		query.postEvent(aEvent, allChannels);
	}

	@Override
	public void doPublish(AbstractEvent event, int publisherId) {
		System.out.println("MultiChannelStrategy :: Publisher : "+publisherId+" posting event: " + event);

		ChannelEventDispatcher query = pubSubServer.ChannelEventDispatcher.getInstance();
		List<AbstractChannel> lst = pubSubServer.PublisherManager.getInstance().getPublisherChannels(publisherId);
		List<String> serverList = new ArrayList<String>();
		
		if(lst.isEmpty()) {
			System.out.println("MultiChannelStrategy :: Error: No channels assigned to publisher");
			return;
		}
		
		for(AbstractChannel ch: lst) 
			serverList.add(ch.getChannelTopic());
		
		query.postEvent(event, serverList);

	}

	@Override
	public void addChannel(String channelName, int publisherId) {
		pubSubServer.PublisherManager.getInstance().addPublisherChannel(publisherId, channelName);
		
	}

}
