package queues;

public class Cashier extends UniformCashier implements Runnable
{
	public Cashier(int maxServiceTime, int serviceQueue)
	{
		super(maxServiceTime, serviceQueue);
	}

	public int generateServiceTime()
	{
		return 0;
	}
	
	public int serveCustomer()
	{
		return 0;
	}

	public void run()
	{
		
	}
	
	public void start()
	{
		
	}
}
