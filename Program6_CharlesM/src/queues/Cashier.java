package queues;

public abstract class Cashier implements Runnable
{
	private int myMaxServiceTime;
	private int myNumServed;
	private ServiceQueue myServiceQueue;
	private Thread myThread;
	
	public abstract int generateServiceTime();
	
	public Cashier(int maxServiceTime, ServiceQueue serviceQueue)
	{
		myMaxServiceTime = maxServiceTime;
		myServiceQueue = serviceQueue;
	}
	
	public int serveCustomer()
	{
		myNumServed++;
		return myNumServed;
	}

	public void run()
	{
		
	}
	
	public void start()
	{
		
	}
	
	public ServiceQueue getServiceQueue()
	{
		return myServiceQueue;
	}
	
	public int getMaxServiceTime()
	{
		return myMaxServiceTime;
	}
	
	
}
