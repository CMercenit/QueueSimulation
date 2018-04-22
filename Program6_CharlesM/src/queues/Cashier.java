package queues;

public abstract class Cashier implements Runnable
{
	private int myMaxServiceTime;
	private int myNumServed;
	private ServiceQueue myServiceQueue;
	private Customer myCustomer = new Customer();
	private boolean mySuspended;
	private Thread myThread;
	
	public abstract int generateServiceTime();
	
	public Cashier(int maxServiceTime, ServiceQueue serviceQueue)
	{
		myMaxServiceTime = maxServiceTime;
		myServiceQueue = serviceQueue;
	}
	
	public int serveCustomer()
	{
		long waitTime = myCustomer.getEntryTime() - System.currentTimeMillis();
		myCustomer.setWaitTime(waitTime);
		
		long serviceTime = (long)generateServiceTime();
		myCustomer.setServiceTime(serviceTime);
		
		myNumServed++;
//		myServiceQueue.dequeue(myCustomer);
		
		try
		{
			Thread.sleep(serviceTime);
		}
		catch(InterruptedException e)
		{
			String error;
			error = e.toString();
			System.out.println(error);
		}
		return myNumServed;
	}

	public void run()
	{
		System.out.println("cashiering");
	}
	
	public void start()
	{
		try
		{
			myThread.start();
		}
		catch(IllegalThreadStateException e)
		{
			String error;
			error = e.toString();
			System.out.println(error);
		}
	}
	
	public ServiceQueue getServiceQueue()
	{
		return myServiceQueue;
	}
	
	public int getMaxServiceTime()
	{
		return myMaxServiceTime;
	}
	
	public void setSuspended(boolean b)
	{
		mySuspended = b;
	}
	
	
}
