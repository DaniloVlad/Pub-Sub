package orchestration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.AbstractChannel;
import pubSubServer.ChannelDiscovery;
import pubSubServer.PublisherManager;
import pubSubServer.SubscriptionManager;
import publishers.AbstractPublisher;
import publishers.PublisherFactory;
import publishers.PublisherType;
import states.subscriber.StateName;
import strategies.publisher.StrategyName;
import subscribers.AbstractSubscriber;
import subscribers.SubscriberFactory;
import subscribers.SubscriberType;

public class Orchestration {
	public static void handleSubscribeAction(AbstractSubscriber sub, String channelName) {
		System.out.println("Subscriber :: " + sub + " subscribing to " + channelName);
		sub.subscribe(channelName);
	}
	
	public static void handleUnSubscribeAction(AbstractSubscriber sub, String channelName) {
		sub.unsubscribe(channelName);
	}
	
	public static void handleBlockEvent(AbstractSubscriber sub, String channelName) {
		SubscriptionManager.getInstance().block(sub, channelName);
	}
	
	public static void handleUnBlockEvent(AbstractSubscriber sub, String channelName) {
		SubscriptionManager.getInstance().unBlock(sub, channelName);
	}
	
	public static void handlePublisherAction(String[] words) {
		String name = words[1].replace(" ", "");
		AbstractPublisher pub = PublisherManager.getInstance().getPubFromName(name);
		System.out.println("Publisher " + pub + " publishing event!");
		if(words.length > 2) {
			EventType eType = EventType.valueOf(words[2]);
			EventMessage payload = new EventMessage(words[3], words[4]);
			AbstractEvent event = EventFactory.createEvent(eType, pub.hashCode(), payload);
			pub.publish(event);
		}
		else
			pub.publish();
	}
	
	public AbstractPublisher createPublisher(String line) {
		AbstractPublisher newPub;
		String[] words = line.split("\t");
		PublisherType type = PublisherType.values()[Integer.parseInt(words[0].replace(" ", ""))]; 
		String name = words[1].replace(" ", "");
		StrategyName strat;
		if(words.length < 3) {
			strat = StrategyName.Admin;
			newPub = PublisherFactory.createPublisher(type, strat, name);
			return newPub;
		}
		else {
			if(words[2].contains(",")) {
				strat = StrategyName.MultiChannel;
				newPub = PublisherFactory.createPublisher(type, strat, name);
				String[] channelList = words[2].split(",");
				for(String channel: channelList) {
					newPub.addChannel(channel);
				}
				return newPub;
			}
			else {
				strat = StrategyName.SingleChannel;
				newPub = PublisherFactory.createPublisher(type, strat, name);
				newPub.addChannel(words[2]);
				return newPub;
			}
		}
		
		
	}

	public static void main(String[] args) {

		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		Orchestration testHarness = new Orchestration();
		try {
			listOfPublishers = testHarness.createPublishers();
			listOfSubscribers = testHarness.createSubscribers();
					
			List<AbstractChannel> channels = ChannelDiscovery.getInstance().listChannels();
			
			System.out.println("Done Creating: ("+listOfSubscribers.size()+") Subscribers | ("+ listOfPublishers.size()+") Publishers | (" + channels.size()+") Channels");
			try {
			BufferedReader initialChannels = new BufferedReader(new FileReader(new File("Channels.chl")));
			List<String> channelList = new ArrayList<String>();
			String line = "";
			while((line = initialChannels.readLine()) != null )
				channelList.add(line);
			int subscriberIndex = 0;
			for(AbstractSubscriber subscriber : listOfSubscribers) {
				subscriber.subscribe(channelList.get(subscriberIndex%channelList.size()));
				subscriberIndex++;
			}
			initialChannels.close();
			}catch(IOException ioe) {
				System.out.println("Loading Channels from file failed proceeding with random selection");
				for(AbstractSubscriber subscriber : listOfSubscribers) {
					int index = (int) Math.round((Math.random()*10))/3;
					SubscriptionManager.getInstance().subscribe(channels.get(index).getChannelTopic(), subscriber);
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			System.out.println("Will now terminate");
			return;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Could not load, terminating");
			return;
		}
		System.out.println("Starting to run main scenario...");
		//CHANNELS DONE ADD READING SCENARIO HERE
		BufferedReader scenario;
		try {
			scenario = new BufferedReader(new FileReader("mainTest"));
			String line;
			while((line = scenario.readLine()) != null) {
				String[] words = line.split("\t");
				String channelName;
				switch(words[0]) {
					case "PUB":
						handlePublisherAction(words);
						break;
					case "SUB":
						channelName = words[2].replace(" ", "");
						handleSubscribeAction(listOfSubscribers.get(Integer.parseInt(words[1])) , channelName);
						break;
					case "UNSUB":
						channelName = words[2].replace(" ", "");
						handleUnSubscribeAction(listOfSubscribers.get(Integer.parseInt(words[1])), channelName);
						break;
					case "BLOCK":
						channelName = words[2].replace(" ", "");
						handleBlockEvent(listOfSubscribers.get(Integer.parseInt(words[1])), channelName);
						break;
					case "UNBLOCK":
						channelName = words[2].replace(" ", "");
						handleUnBlockEvent(listOfSubscribers.get(Integer.parseInt(words[1])), channelName);
						break;
					default:
						System.out.println("Invalid Command Found: " + words[0]);
						break;
				}
			}
			scenario.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done executing...");
		
	}

	
	private List<AbstractPublisher> createPublishers() throws IOException{
		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		AbstractPublisher newPub;
		BufferedReader StrategyBufferedReader = new BufferedReader(new FileReader(new File("Strategies.str")));
		while(StrategyBufferedReader.ready()) {
			String PublisherConfigLine = StrategyBufferedReader.readLine();
			newPub = createPublisher(PublisherConfigLine);
			listOfPublishers.add(newPub);
		}
		StrategyBufferedReader.close();
		return listOfPublishers;
	}
	
	private List<AbstractSubscriber> createSubscribers() throws IOException{
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		AbstractSubscriber newSub;
		BufferedReader StateBufferedReader = new BufferedReader(new FileReader(new File("States.sts")));
		while(StateBufferedReader.ready()) {
			String StateConfigLine = StateBufferedReader.readLine();
			String[] StateConfigArray = StateConfigLine.split("\t");
			SubscriberType type = SubscriberType.values()[Integer.parseInt(StateConfigArray[0])];
			StateName state = StateName.values()[Integer.parseInt(StateConfigArray[1])];
			newSub = SubscriberFactory.createSubscriber(type, state);
			listOfSubscribers.add(newSub);
		}
		StateBufferedReader.close();
		return listOfSubscribers;
	}
	
	
	
}
