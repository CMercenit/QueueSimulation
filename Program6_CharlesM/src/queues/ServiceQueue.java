package queues;

public class ServiceQueue extends Queue
{
	private int myNumCustomersServedSoFar;
	private int myNumCustomersInLine;
	private int myTotalWaitTime;
	private int myTotalServiceTime;
	private int myTotalIdleTime;
	private int myTotalTime;
	private int myTotalCustomerServiceTime;
	private int myTotalCustomerWaitTime;
	private float myAverageCustomerServiceTime;
	private float myAverageCustomerWaitTime;
	private Name myName;
	
	public ServiceQueue()
	{
		super();
		myNumCustomersServedSoFar = 0;
		myNumCustomersInLine = 0;
		myName = new Name();
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
	
	public void addToServiceTime(long service)
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
	
	public float averageWaitTime()
	{
		if(myNumCustomersServedSoFar == 0)
		{
			return myTotalWaitTime;
		}
		else
		{
			return myTotalWaitTime / myNumCustomersServedSoFar;
		}
	}
	
	public float averageServiceTime()
	{
		if(myNumCustomersServedSoFar == 0)
		{
			return myTotalServiceTime;
		}
		else
		{
			return myTotalServiceTime / myNumCustomersServedSoFar;
		}
	}
	
	public float averageIdleTime()
	{
		if(myNumCustomersServedSoFar == 0)
		{
			return myTotalIdleTime;
		}
		else
		{
			return myTotalIdleTime / myNumCustomersServedSoFar;
		}
	}
	
	public void addToCustomerServiceTime(long num)
	{
		myTotalCustomerServiceTime += num;
	}
	
	public void addToCustomerWaitTime(long num)
	{
		myTotalCustomerWaitTime += num;
	}
	
	public int totalCustomerServiceTime()
	{
		return myTotalCustomerServiceTime;
	}
	
	public int totalCustomerWaitTime()
	{
		return myTotalCustomerWaitTime;
	}
	
	public float averageCustomerServiceTime()
	{
		myAverageCustomerServiceTime = 0;
		
		if(myNumCustomersServedSoFar == 0)
		{
			return myTotalCustomerServiceTime;
		}
		else
		{
			myAverageCustomerServiceTime =
					myAverageCustomerServiceTime / myNumCustomersServedSoFar;
			
			return myAverageCustomerServiceTime;
		}
	}
	
	public float averageCustomerWaitTime()
	{
		myAverageCustomerWaitTime = 0;
		
		if(myNumCustomersServedSoFar == 0)
		{
			return myTotalCustomerWaitTime;
		}
		else
		{
			myAverageCustomerWaitTime =
					myAverageCustomerWaitTime / myNumCustomersServedSoFar;
			
			return myAverageCustomerWaitTime;
		}
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
	
	public String getName()
	{
		return myName.toString();
	}
}