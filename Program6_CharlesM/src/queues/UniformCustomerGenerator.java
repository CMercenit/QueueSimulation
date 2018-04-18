package queues;

import java.util.Random;

public class UniformCustomerGenerator extends CustomerGenerator
{
	private Random myRandom;
	
	public UniformCustomerGenerator(int maxTimeBetweenCustomers,
									int maxNumCustomers, 
									ServiceQueueManager serviceQueueManager)
	{
		super(maxTimeBetweenCustomers, maxNumCustomers, serviceQueueManager);
	}

	public int generateTimeBetweenCustomers()
	{
		myRandom = new Random();
		return myRandom.nextInt(getMaxTimeBetweenCustomers());
	}
}
