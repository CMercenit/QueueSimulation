package queues;

import java.awt.Dimension;

/**
 * CustomerGenerator class runs a thread that generates a customer
 * and immediately enqueues it into the shortest queue.
 *  
 * @author Charles Mercenit
 */

public abstract class CustomerGenerator implements Runnable
{
	private int myMaxTimeBetweenCustomers;
	private int myOriginalMaxTimeBetweenCustomers;
	private int myMaxNumCustomers;
	private int customers;
	private ServiceQueueManager myServiceQueueManager;
	private Customer myCustomer;
	private boolean mySuspended;
	private Thread myThread;
	
	public abstract int generateTimeBetweenCustomers();
	
	public CustomerGenerator(int maxTimeBetweenCustomers,
							 int maxNumCustomers,
							 ServiceQueueManager serviceQueueManager)
	{
		myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
		myMaxNumCustomers = maxNumCustomers;
		myServiceQueueManager = serviceQueueManager;
		myOriginalMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
		customers = 0;
		mySuspended = false;
		myThread = new Thread(this);
	}
	
	/**
	 * Generates a customer and enqueues it into
	 * the shortest queue, determined by the
	 * ServiceQueueManager class.
	 * 
	 * @return: enqueued customer
	 */
	
	public Customer generateCustomer()
	{
		myCustomer = new Customer();
		myServiceQueueManager.enqueue(myCustomer);
		
		return myCustomer;
	}
	
	/**
	 * Runs the thread.
	 */
	
	public void run()
	{
		try
		{
			synchronized(this)
			{
				this.generateCustomers();
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
		mySuspended = true;
	}
	
	/**
	 * Waits while the thread is suspended.
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
	 * Generates a customer, then sleeps a random
	 * number between 0 and the maximum generation
	 * time specified by the user.
	 * 
	 * @throws InterruptedException
	 */
	
	private void generateCustomers() throws InterruptedException
	{
		while(customers < myMaxNumCustomers)
		{
			this.waitWhileSuspended();
			
			try
			{
				generateCustomer();
				customers++;
				Thread.sleep(generateTimeBetweenCustomers());
			}
			catch(InterruptedException e)
			{
				String error;
				error = e.toString();
				System.out.println(error);
			}
		}
	}
	
	/**
	 * Returns the maximum number of customers
	 * specified by the user.
	 * 
	 * @return: max num customers
	 */
	
	public int getMaxNumCustomers()
	{
		return myMaxNumCustomers;
	}
	
	/**
	 * Returns the ServiceQueueManager.
	 * 
	 * @return: service queue manager
	 */
	
	public ServiceQueueManager getServiceQueueManager()
	{
		return myServiceQueueManager;
	}
	
	/**
	 * Returns the maximum amount of time between
	 * customer generation specified by the user.
	 * 
	 * @return: max time between customers
	 */
	
	public int getMaxTimeBetweenCustomers()
	{
		return myMaxTimeBetweenCustomers;
	}
	
	/**
	 * Sets mySuspended to the passed in boolean; used
	 * to suspend or resume the thread based on the
	 * thread in SimulationController.
	 * 
	 * @param boolean to set mySuspended to
	 */
	
	public void setSuspended(boolean b)
	{
		mySuspended = b;
	}
	
	/**
	 * Returns the most recently generated customer.
	 * 
	 * @return: customer
	 */
	
	public Customer getCustomer()
	{
		return myCustomer;
	}
	
	/**
	 * Returns the size of the most recently generated customer.
	 * 
	 * @return: customer size
	 */
	
	public Dimension getCustomerSize()
	{
		return myCustomer.getSize();
	}
	
	/**
	 * Changes max generation time based on the
	 * number passed in. Used to speed up or
	 * slow down the simulation according to
	 * the slider.
	 * 
	 * @param num to divide generation time by
	 */
	
	public void setGenerationTime(float num)
	{
		myMaxTimeBetweenCustomers = myOriginalMaxTimeBetweenCustomers;
		myMaxTimeBetweenCustomers = (int)(myMaxTimeBetweenCustomers / num);
	}
}