package queues;

import java.awt.Dimension;

/**
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
	
	public Customer generateCustomer()
	{
		myCustomer = new Customer();
		myServiceQueueManager.enqueue(myCustomer);
		
		return myCustomer;
	}
	
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
	
	public int getMaxNumCustomers()
	{
		return myMaxNumCustomers;
	}
	
	public ServiceQueueManager getServiceQueueManager()
	{
		return myServiceQueueManager;
	}
	
	public int getMaxTimeBetweenCustomers()
	{
		return myMaxTimeBetweenCustomers;
	}
	
	public void setSuspended(boolean b)
	{
		mySuspended = b;
	}
	
	public Customer getCustomer()
	{
		return myCustomer;
	}
	
	public Dimension getCustomerSize()
	{
		return myCustomer.getSize();
	}
	
	public void setGenerationTime(float num)
	{
		myMaxTimeBetweenCustomers = myOriginalMaxTimeBetweenCustomers;
		myMaxTimeBetweenCustomers = (int)(myMaxTimeBetweenCustomers / num);
	}
}
