package queues;

/**
 * Keeps track of statistics for a certain queue, as well
 * as serve customers, insert customers, and assign a
 * cashier a name from the Name class.
 * 
 * @author Charles Mercenit
 */

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
	
	/**
	 * Adds to the elapsed time of the simulation.
	 * 
	 * @param elapsed time to add
	 */
	
	public void addToElapsedTime(int elapsed)
	{
		myTotalTime += elapsed;
	}
	
	/**
	 * Adds to the idle time of the service queue.
	 * 
	 * @param idle time to add
	 */
	
	public void addToIdleTime(int idle)
	{
		myTotalIdleTime += idle;
	}
	
	/**
	 * Adds to the wait time of the service queue.
	 * 
	 * @param wait time to add
	 */
	
	public void addToWaitTime(int wait)
	{
		myTotalWaitTime += wait;
	}
	
	/**
	 * Adds to the service time of the service queue.
	 * 
	 * @param service time to add
	 */
	
	public void addToServiceTime(long service)
	{
		myTotalServiceTime += service;
	}
	
	/**
	 * Adds to the service time of the customers.
	 * 
	 * @param service time to add
	 */
	
	public void addToCustomerServiceTime(long service)
	{
		myTotalCustomerServiceTime += service;
	}
	
	/**
	 * Adds to the wait time of the customers.
	 * 
	 * @param wait time to add
	 */
	
	public void addToCustomerWaitTime(long wait)
	{
		myTotalCustomerWaitTime += wait;
	}
	
	/**
	 * Inserts a customer into the queue using
	 * the Queue class.
	 * 
	 * @param customer to insert
	 */
	
	public void insertCustomer(Customer customer)
	{
		enqueue(customer);
	}
	
	/**
	 * Dequeues the last customer in line and
	 * increments the number of customers served.
	 * 
	 * @return: customer that has been served
	 */
	
	public Customer serveCustomer()
	{
		Customer served = dequeue();
		myNumCustomersServedSoFar++;
		return served;
	}
	
	/**
	 * Calculates the average wait time for the service
	 * queue. If 0 customers have been served, returns the
	 * total wait time.
	 * 
	 * @return: average wait time for the service queue
	 */
	
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
	
	/**
	 * Calculates the average service time for the service
	 * queue. If 0 customers have been served, returns the
	 * total service time.
	 * 
	 * @return: average service time for the service queue
	 */
	
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
	
	/**
	 * Calculates the average idle time for the service
	 * queue. If 0 customers have been served, returns the
	 * total idle time.
	 * 
	 * @return: average idle time for the service queue
	 */
	
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
	
	/**
	 * Calculates the average service time for the customers.
	 * If 0 customers have been served, returns the total
	 * service time.
	 * 
	 * @return: average service time for the customers
	 */
	
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
	
	/**
	 * Calculates the average wait time for the customers.
	 * If 0 customers have been served, returns the total
	 * wait time.
	 * 
	 * @return: average wait time for the customers
	 */
	
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
	
	/**
	 * Returns the number of customers served so far.
	 * 
	 * @return: num customers served
	 */
	
	public int getNumCustomersServedSoFar()
	{
		return myNumCustomersServedSoFar;
	}
	
	/**
	 * Returns the number of customers in line
	 * (size of the queue).
	 * 
	 * @return: size of the queue
	 */
	
	public int getNumCustomersInLine()
	{
		myNumCustomersInLine = size();
		return myNumCustomersInLine;
	}
	
	/**
	 * Returns the total wait time for the
	 * service queue.
	 * 
	 * @return: total wait time
	 */
	
	public int getTotalWaitTime()
	{
		return myTotalWaitTime;
	}
	
	/**
	 * Returns the total idle time for the
	 * service queue.
	 * 
	 * @return: total idle time
	 */
	
	public int getTotalIdleTime()
	{
		return myTotalIdleTime;
	}
	
	/**
	 * Returns the total service time for the
	 * service queue.
	 * 
	 * @return: total service time
	 */
	
	public int getTotalServiceTime()
	{
		return myTotalServiceTime;
	}
	
	/**
	 * Returns the total service time for the
	 * customers.
	 * 
	 * @return: total customer service time
	 */
	
	public int totalCustomerServiceTime()
	{
		return myTotalCustomerServiceTime;
	}
	
	/**
	 * Returns the total wait time for the
	 * customers.
	 * 
	 * @return: total customer wait time
	 */
	
	public int totalCustomerWaitTime()
	{
		return myTotalCustomerWaitTime;
	}
	
	/**
	 * Returns the total time of the simulation.
	 * 
	 * @return: total time
	 */
	
	public int getTotalTime()
	{
		return myTotalTime;
	}
	
	/**
	 * Returns the customer that will be dequeued next.
	 * 
	 * @return: last customer in line
	 */
	
	public Customer getCustomer()
	{
		return peek();
	}
	
	/**
	 * Returns the name of the cashier in this service queue
	 * as a string instead of a place in memory.
	 * 
	 * @return: cashier's name
	 */
	
	public String getName()
	{
		return myName.toString();
	}
}