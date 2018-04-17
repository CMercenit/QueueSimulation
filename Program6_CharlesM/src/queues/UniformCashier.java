package queues;

import java.util.Random;

public abstract class UniformCashier
{
	private Random myRandom;
	
	public UniformCashier(int maxServiceTime, int serviceQueue)
	{
		
	}
	
	public abstract int generateServiceTime();
}
