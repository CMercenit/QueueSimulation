package queues;


/**
 * TODO:
 * Do UniformCustomerGenerator, CustomerGenerator, and Customer first
 * Do UniformCashier and Cashier second (UniformCashier and UniformCustomerGenerator are almost identical, all they do is generate random numbers)
 * Do ServiceQueue third (just a lot of adding numbers)
 * 		Assume you have one queue (no ServiceQueueManager), have the CustomerGenerator add it to the queue and Cashier remove it from queue.
 * 		(change ServiceQueueManager in CustomerGenerator constructor to just queue to quickly test this functionality, not permanent)
 * 		(change ServiceQueue in Cashier constructor to just queue to quickly test this functionality, not permanent)
 * Do ServiceQueueManager fourth (gets a generated customer, determines which line to place it in)
 * 
 * 
 * 
 * When a customer is generated, it has to go into a queue. When a cashier wants a customer, it has to be taken out of the queue.
 * 
 * 
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
		myThread = new Thread(this);
	}
	
	public Customer generateCustomer()
	{
		System.out.println("customer generated");
		myCustomer = new Customer();
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
	//			System.out.println(myCustomer.toString());
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
}
