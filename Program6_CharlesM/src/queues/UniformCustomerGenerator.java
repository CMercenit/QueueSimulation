package queues;

import java.util.Random;

/**
 * Generates a random number between 0 and the
 * maximum customer generation time specified by
 * the user.
 * 
 * @author Charles Mercenit
 */

public class UniformCustomerGenerator extends CustomerGenerator
{
	private Random myRandom;
	
	public UniformCustomerGenerator(int maxTimeBetweenCustomers,
									int maxNumCustomers, 
									ServiceQueueManager serviceQueueManager)
	{
		super(maxTimeBetweenCustomers, maxNumCustomers, serviceQueueManager);
	}
	
	/**
	 * Uses java.util.Random to generate a random number.
	 * Gets the upper bound for the random number from the
	 * class that this extends, CustomerGenerator.
	 * 
	 * @return: random int between 0 and max time between customers
	 */

	public int generateTimeBetweenCustomers()
	{
		myRandom = new Random();
		return myRandom.nextInt(getMaxTimeBetweenCustomers());
	}
}