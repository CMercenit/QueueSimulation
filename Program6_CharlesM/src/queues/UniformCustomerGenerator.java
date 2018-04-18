package queues;

import java.util.Random;

public class UniformCustomerGenerator extends CustomerGenerator
{
	private Random myRandom = new Random();
	
	public UniformCustomerGenerator(int maxTimeBetweenCustomers, int maxNumCustomers, ServiceQueueManager serviceQueueManager)
	{
		super(maxTimeBetweenCustomers, maxNumCustomers, serviceQueueManager);
	}

	public int generateTimeBetweenCustomers()
	{
		return 0;
	}
	
	public Random getRandom()
	{
//		myRandom.ints(0, generateTimeBetweenCustomers());
		return myRandom;
	}
}
