package queues;

public class CustomerGenerator extends UniformCustomerGenerator implements Runnable
{
	public CustomerGenerator(int maxTimeBetweenCustomers, ServiceQueueManager serviceQueueManager)
	{
		super(maxTimeBetweenCustomers, serviceQueueManager);
	}

	public int generateTimeBetweenCustomers()
	{
		return 0;
	}
	
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
