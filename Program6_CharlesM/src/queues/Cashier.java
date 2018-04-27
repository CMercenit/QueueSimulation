package queues;

/**
 * Customers have an idle time, wait time, and service time.
 * Cashiers have total served, average service time, and average idle time.
 * 
 * 
 * @author Charles Mercenit
 *
 */
public abstract class Cashier implements Runnable
{
	private int myMaxServiceTime;
	private int myNumServed;
	private long myServiceTime;
	private ServiceQueue myServiceQueue;
	private Customer myCustomer;
	private boolean mySuspended;
	private Thread myThread;
	
	private int myOriginalMaxServiceTime;
	
	public abstract int generateServiceTime();
	
	public Cashier(int maxServiceTime, ServiceQueue serviceQueue)
	{
		myMaxServiceTime = maxServiceTime;
		myServiceQueue = serviceQueue;
		myServiceTime = 0;
		mySuspended = false;
		myThread = new Thread(this);
		
		myOriginalMaxServiceTime = maxServiceTime;
	}
	
	public int serveCustomer()
	{
	/*
		System.out.println("Customer about to be served.");
		Customer served = myServiceQueue.serveCustomer();
		long waitTime = myCustomer.getEntryTime() - System.currentTimeMillis();
		myCustomer.setWaitTime(waitTime);
	
		myServiceTime = (long)generateServiceTime();
		myCustomer.setServiceTime(myServiceTime);
	
		myNumServed++;
		System.out.println("Customer served");
		myCustomer = myServiceQueue.getCustomer();
		System.out.println(served.toString());
	
		return myNumServed;
	*/
		
//		System.out.println("Customer served.");
		myCustomer = myServiceQueue.serveCustomer();
		myCustomer.addToWaitTime(System.currentTimeMillis() - myCustomer.getEntryTime());
		myServiceTime = (long)generateServiceTime();
		myCustomer.addToServiceTime(myServiceTime);
		myServiceQueue.addToServiceTime(myServiceTime);
		myNumServed++;
		
		return myNumServed;
	}

	public void run()
	{
		try
		{
			synchronized(this)
			{
//				System.out.println("Cashier running.");
				this.serveCustomers();
			}
		}
		catch(InterruptedException e)
		{
			String error;
			error = e.toString();
			System.out.println(error);
		}
	}
	
	public void start()
	{
		try
		{
//			System.out.println("Cashier started");
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
	
	public void serveCustomers() throws InterruptedException
	{
		while(true)
		{
			this.waitWhileSuspended();
			
			try
			{
				if(myServiceQueue.size() > 0)
				{
					serveCustomer();
					Thread.sleep(myServiceTime);
				}
				else
				{
					myServiceQueue.addToIdleTime(5);
					Thread.sleep(5);
				}				
			}
			catch(InterruptedException e)
			{
				String error;
				error = e.toString();
				System.out.println(error);
			}
		}		
	}
	
	public synchronized void resume()
	{
		mySuspended = false;
		this.notify();
	}
	
	public void suspend()
	{
		System.out.println("Thread in Cashier paused.");
		mySuspended = true;
	}
	
	private void waitWhileSuspended() throws InterruptedException
	{
		while(mySuspended)
		{
			this.wait();
		}
	}
	
	public void setServiceTime(float num)
	{
		myMaxServiceTime = myOriginalMaxServiceTime;
		myMaxServiceTime = (int)(myMaxServiceTime / num);
	}
}
