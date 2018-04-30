package queues;

import java.util.Random;

/**
 * Generates a random number between 0 and the
 * maximum service time specified by the user.
 * 
 * @author Charles Mercenit
 */

public class UniformCashier extends Cashier
{
	private Random myRandom;
	
	public UniformCashier(int maxServiceTime, ServiceQueue serviceQueue)
	{
		super(maxServiceTime, serviceQueue);
	}

	/**
	 * Uses java.util.Random to generate a random number.
	 * Gets the upper bound for the random number from the
	 * class that this extends, Cashier.
	 * 
	 * @return: random int between 0 and max service time
	 */
	
	public int generateServiceTime()
	{
		myRandom = new Random();
		return myRandom.nextInt(getMaxServiceTime());
	}
}