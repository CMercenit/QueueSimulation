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
	public CustomerGenerator(int maxTimeBetweenCustomers, int maxNumCustomers, ServiceQueueManager serviceQueueManager)
	{
		
	}

	public abstract int generateTimeBetweenCustomers();
	
	public Customer generateCustomer()
	{
		return null;
	}

	public void run()
	{
		
	}
	
	public void start()
	{
		
	}
}
