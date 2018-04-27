package queues;

public class ServiceQueue extends Queue
{
	private int myNumCustomersServedSoFar;
	private int myNumCustomersInLine;
	private int myTotalWaitTime;
	private int myTotalServiceTime;
	private int myTotalIdleTime;
	private int myTotalTime;
	
	public ServiceQueue()
	{
		super();
		myNumCustomersServedSoFar = 0;
		myNumCustomersInLine = 0;
	}
	
	public void addToElapsedTime(int elapsed)
	{
		myTotalTime += elapsed;
	}
	
	public void addToIdleTime(int idle)
	{
		myTotalIdleTime += idle;
	}
	
	public void addToWaitTime(int wait)
	{
		myTotalWaitTime += wait;
	}
	
	public void addToServiceTime(int service)
	{
		myTotalServiceTime += service;
	}
	
	public void insertCustomer(Customer customer)
	{
		enqueue(customer);
	}
	
	public Customer serveCustomer()
	{
		Customer served = dequeue();
		myNumCustomersServedSoFar++;
		return served;
	}
	
	public int averageWaitTime()
	{
		return myTotalWaitTime / myNumCustomersServedSoFar;
	}
	
	public int averageServiceTime()
	{
		return myTotalServiceTime / myNumCustomersServedSoFar;
	}
	
	public int averageIdleTime()
	{
		return myTotalIdleTime / myNumCustomersServedSoFar;
	}
	
	public int getNumCustomersServedSoFar()
	{
		return myNumCustomersServedSoFar;
	}
	
	public int getNumCustomersInLine()
	{
		myNumCustomersInLine = size();
		return myNumCustomersInLine;
	}
	
	public int getTotalWaitTime()
	{
		return myTotalWaitTime;
	}
	
	public int getTotalIdleTime()
	{
		return myTotalIdleTime;
	}
	
	public int getTotalServiceTime()
	{
		return myTotalServiceTime;
	}
	
	public int getTotalTime()
	{
		return myTotalTime;
	}
	
	public Customer getCustomer()
	{
		return peek();
	}
}
