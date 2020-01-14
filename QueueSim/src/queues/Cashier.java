package queues;

/**
 * Cashier class runs a thread that serves a customer
 * (dequeues a customer), adds to the wait time of that
 * customer, generates a service time and adds that
 * to the service time for both the customer and the queue,
 * then sleeps the service time. If there are no customers in
 * line, sleeps 5 milliseconds and adds that to the idle time.
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
	
	/**
	 * Dequeues a customer, adds to the wait time for
	 * that customer, generates a service time,
	 * adds that service time to both the customer and
	 * the ServiceQueue, then increments the number of
	 * served customers.
	 * 
	 * @return: number of served customers
	 */
	
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
	
	/**
	 * Runs the thread that serves (dequeues) customers.
	 */

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
	
	/**
	 * Starts the thread.
	 */
	
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
	
	/**
	 * Returns the cashier's service queue.
	 * 
	 * @return: service queue
	 */
	
	public ServiceQueue getServiceQueue()
	{
		return myServiceQueue;
	}
	
	/**
	 * Returns the maximum service time
	 * specified by the user.
	 * 
	 * @return: max service time
	 */
	
	public int getMaxServiceTime()
	{
		return myMaxServiceTime;
	}
	
	/**
	 * Sets mySuspended to the passed in boolean;
	 * used to suspend or resume the thread based on
	 * the thread in SimulationController.
	 * 
	 * @param boolean to set mySuspended to
	 */
	
	public void setSuspended(boolean b)
	{
		mySuspended = b;
	}
	
	/**
	 * Serves the customers. If there are no customers in line,
	 * sleeps 5 milliseconds and adds that to the idle time.
	 * 
	 * @throws InterruptedException
	 */
	
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
	
	/**
	 * Resumes the thread.
	 */
	
	public synchronized void resume()
	{
		mySuspended = false;
		this.notify();
	}
	
	/**
	 * Suspends the thread.
	 */
	
	public void suspend()
	{
		System.out.println("Thread in Cashier paused.");
		mySuspended = true;
	}
	
	/**
	 * If the thread is suspended, wait until it's not.
	 * 
	 * @throws InterruptedException
	 */
	
	private void waitWhileSuspended() throws InterruptedException
	{
		while(mySuspended)
		{
			this.wait();
		}
	}
	
	/**
	 * Changes max service time based on the number
	 * passed in. Used to speed up or slow down the
	 * simulation according to the slider.
	 * 
	 * @param num to divide service time by
	 */
	
	public void setServiceTime(float num)
	{
		myMaxServiceTime = myOriginalMaxServiceTime;
		myMaxServiceTime = (int)(myMaxServiceTime / num);
	}
	
	/**
	 * Sets myContinue to the passed in boolean; used to
	 * tell the thread when to stop looping (once the
	 * simulation is done).
	 * 
	 * @param boolean to set myContinue to
	 */
	
	public void setContinue(boolean b)
	{
		myContinue = b;
	}
}