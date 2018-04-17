package queues;

import java.util.Random;

public abstract class UniformCustomerGenerator
{
	private Random myRandom;
	
	public UniformCustomerGenerator(int maxTimeBetweenCustomers, ServiceQueueManager serviceQueueManager)
	{
		
	}
	
	public abstract int generateTimeBetweenCustomers();
}
