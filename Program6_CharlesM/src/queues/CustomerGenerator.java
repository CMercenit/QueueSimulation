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
	private Thread myThread;
	
	public abstract int generateTimeBetweenCustomers();
	
	public CustomerGenerator(int maxTimeBetweenCustomers,
							 int maxNumCustomers,
							 ServiceQueueManager serviceQueueManager)
	{
		myMaxTimeBetweenCustomers = maxTimeBetweenCustomers;
		myMaxNumCustomers = maxNumCustomers;
		myServiceQueueManager = serviceQueueManager;
		
	}
	
	public Customer generateCustomer()
	{
		return new Customer();
	}
	
	public void run()
	{
		
	}
	
	public void start()
	{
		
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
}
