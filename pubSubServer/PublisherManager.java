package pubSubServer;

import java.util.List;
import java.util.Map;

import publishers.AbstractPublisher;

public class PublisherManager {
	private ChannelPoolManager cpm;
	private static PublisherManager instance = null;
	
	private PublisherManager() {
		cpm = ChannelPoolManager.getInstance();
	}
	
	public static PublisherManager getInstance() {
		if(instance == null) instance = new PublisherManager();
		return instance;
	}

	public void removePublisher(AbstractPublisher pub) {
		cpm.removePublisher(pub);
	}
	
	public void addPublisher(AbstractPublisher pub) {
		cpm.addPublisher(pub);
	}
	
	public Map<String, AbstractPublisher> getPubList() {
		return cpm.listPublishers();
	}
	
	public AbstractPublisher getPubFromName(String name) {
		return cpm.getPublisherByName(name);
	}
	public List<AbstractChannel> getPublisherChannels(int pubID) {
		return cpm.getPublisherChannels(pubID);
	}
	
	public void addPublisherChannel(int pubID, String channelName) {
		cpm.addPublisherChannel(pubID, channelName);
	}
	
	public void addPublisherChannels(int pubID, String[] channelNames) {
		cpm.addPublisherChannels(pubID, channelNames);
	}
	
	public void adminCreateChannel(String channelName) {
		AbstractChannel ch = cpm.findChannel(channelName);
		if(ch == null) cpm.addChannel(channelName);
		else return;
	}
}
