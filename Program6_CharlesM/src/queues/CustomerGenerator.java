package queues;

import java.awt.Dimension;

/**
 *  
 * @author Charles Mercenit
 *
 */
public abstract class CustomerGenerator implements Runnable
{
	private int myMaxTimeBetweenCustomers;
	private ServiceQueueManager myServiceQueueManager;
	private int myMaxNumCustomers;
	private int customers;
	private Customer myCustomer;
	private boolean mySuspended;
	private boolean myGenerated;
	private Thread myThread;
	
	public abstract int generateTimeBetweenCustomers();
	
	public CustomerGenerator(int maxTimeBetweenCustomers,
							 int maxNumCustomers,
							 ServiceQueueManager serviceQueueManager)
	{
		myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
		myMaxNumCustomers = maxNumCustomers;
		myServiceQueueManager = serviceQueueManager;
		customers = 0;
		mySuspended = false;
		myGenerated = false;
		myThread = new Thread(this);
	}
	
	public Customer generateCustomer()
	{
		System.out.println("customer generated");
		myCustomer = new Customer();
		myServiceQueueManager.enqueue(myCustomer);
		myGenerated = true;
		return myCustomer;
	}
	
	public void run()
	{
		//put in a loop that continues until max num customers is reached
		//serviceQueueManager could be model instead of simulationModel
		//each cashier needs suspend and resume like in controller, same with customer generator
		//when suspend method happens (button is clicked), go to each class that needs to be suspended and get them to call their suspend method
		//Cashier[] cashiers = myModel.getCashiers();
		//for each cashier:
		//	cashier.suspend()
		//CustomerGenerator gen = myModel.getCustomerGenerator();
		//gen.suspend();
		//all this happens before mySuspended = true;
		//in resume, do the opposite.
		
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
		System.out.println("Thread in CustomerGenerator paused.");
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
		//Once while loop is done, boolean isDone sent to Controller to tell it to enable the text fields
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
	
	public boolean isGenerated()
	{
		return myGenerated;
	}
}
