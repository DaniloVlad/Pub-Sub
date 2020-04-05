package strategies.publisher;

/**
 * @author kkontog, ktsiouni, mgrigori
 * creates new {@link IStrategy } objects
 * contributes to the Strategy design pattern
 * implements the FactoryMethod design pattern   
 */
public class StrategyFactory {

	
	/**
	 * creates a new {@link IStrategy} using an entry from the {@link StrategyName} enumeration
	 * @param strategyName a value from the {@link StrategyName} enumeration specifying the strategy to be created 
	 * @return the newly created {@link IStrategy} instance 
	 */
	public static IStrategy createStrategy(StrategyName strategyName) {
		IStrategy strategy;
		switch(strategyName) {
			case SingleChannel:
				strategy = new SingleChannelStrategy();
				return strategy;
			case MultiChannel:
				strategy = new MultiChannelStrategy();
				return strategy;
			default:
				strategy = new AdminStrategy();
				return strategy;
		}
	}
	
	
}
