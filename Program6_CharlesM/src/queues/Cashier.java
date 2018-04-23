package queues;

public abstract class Cashier implements Runnable
{
	private int myMaxServiceTime;
	private int myNumServed;
	private long myServiceTime;
	private ServiceQueue myServiceQueue;
	private Customer myCustomer;
	private boolean mySuspended;
	private Thread myThread;
	
	public abstract int generateServiceTime();
	
	public Cashier(int maxServiceTime, ServiceQueue serviceQueue)
	{
		myMaxServiceTime = maxServiceTime;
		myServiceQueue = serviceQueue;
		mySuspended = false;
		myThread = new Thread(this);
	}
	
	public int serveCustomer()
	{
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
	}

	public void run()
	{
		try
		{
			synchronized(this)
			{
				System.out.println("Cashier running.");
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
			System.out.println("Cashier started");
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
				System.out.println("Thread in Cashier going.");
				serveCustomer();
				Thread.sleep(myServiceTime);
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
		mySuspended = true;
	}
	
	private void waitWhileSuspended() throws InterruptedException
	{
		while(mySuspended)
		{
			this.wait();
		}
	}
}
