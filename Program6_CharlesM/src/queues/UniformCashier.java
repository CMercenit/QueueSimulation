package queues;

import java.util.Random;

public class UniformCashier extends Cashier
{
	private Random myRandom;
	
	public UniformCashier(int maxServiceTime, ServiceQueue serviceQueue)
	{
		super(maxServiceTime, serviceQueue);
	}

	public int generateServiceTime()
	{
		myRandom = new Random();
		return myRandom.nextInt(getMaxServiceTime());
	}
}
