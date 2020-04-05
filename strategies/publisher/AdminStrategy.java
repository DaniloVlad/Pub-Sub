package strategies.publisher;

import java.util.ArrayList;
import java.util.List;
import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.AbstractChannel;
import pubSubServer.ChannelDiscovery;
import pubSubServer.ChannelEventDispatcher;

public class AdminStrategy implements IStrategy {
	
	//ADMIN STRATEGY: publishes default admin event to all channels
	
	@Override
	public void doPublish(int publisherId) {
		
		EventMessage eMsg = new events.EventMessage("TypeC", "Made using AdminStrategy.");
		AbstractEvent aEvent = EventFactory.createEvent(EventType.TypeC, publisherId, eMsg);
		
		System.out.println("AdminStrategy doPublish(event, publisherId):: " + aEvent+", " + publisherId);

		ChannelEventDispatcher query = pubSubServer.ChannelEventDispatcher.getInstance();
		List<AbstractChannel> channelList = pubSubServer.ChannelDiscovery.getInstance().listChannels();
		List<String> serverList = new ArrayList<String>();
		
		for(AbstractChannel ch: channelList)
			serverList.add(ch.getChannelTopic());
			
		query.postEvent(aEvent, serverList);
	}

	@Override
	public void doPublish(AbstractEvent event, int publisherId) {
		System.out.println("AdminStrategy doPublish(event, publisherId):: " + event+", " + publisherId);
		ChannelEventDispatcher query = pubSubServer.ChannelEventDispatcher.getInstance();
		ChannelDiscovery channelList = pubSubServer.ChannelDiscovery.getInstance();
		List<AbstractChannel> lst = channelList.listChannels();
		List<String> serverList = new ArrayList<String>();
		
		
		for(AbstractChannel ch: lst) 
				serverList.add(ch.getChannelTopic());
		
		query.postEvent(event, serverList);

	}
	
	public void addChannel(String channelName, int publisherId) {
		pubSubServer.PublisherManager.getInstance().adminCreateChannel(channelName);
	}

}
