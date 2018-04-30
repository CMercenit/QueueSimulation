package queues;

/**
 *  
 * @author Charles Mercenit
 */
public abstract class Cashier implements Runnable
{
	private int myMaxServiceTime;
	private int myNumServed;
	private int myOriginalMaxServiceTime;
	private long myServiceTime;
	private ServiceQueue myServiceQueue;
	private Customer myCustomer;
	private boolean mySuspended;
	private boolean myContinue;
	private Thread myThread;
	
	public abstract int generateServiceTime();
	
	public Cashier(int maxServiceTime, ServiceQueue serviceQueue)
	{
		myMaxServiceTime = maxServiceTime;
		myOriginalMaxServiceTime = maxServiceTime;
		myServiceQueue = serviceQueue;
		myServiceTime = 0;
		mySuspended = false;
		myContinue = true;
		myThread = new Thread(this);
	}
	
	public int serveCustomer()
	{
		myCustomer = myServiceQueue.serveCustomer();
		myServiceQueue.addToCustomerWaitTime(System.currentTimeMillis() - myCustomer.getEntryTime());
		myServiceTime = (long)generateServiceTime();
		myServiceQueue.addToCustomerServiceTime(myServiceTime);
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
		do
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
		while(myContinue);	
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
	
	public void setContinue(boolean b)
	{
		myContinue = b;
	}
}