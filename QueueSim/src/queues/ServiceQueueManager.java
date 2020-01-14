package queues;

import java.awt.Dimension;

/**
 * ServiceQueueManager acts as the model for MVC. It collects data
 * for all of the ServiceQueues and the customers, as well as start,
 * pause, and resume each thread for each ServiceQueue and for the
 * CustomerGenerator.
 * 
 * @author Charles Mercenit
 */

public class ServiceQueueManager
{
	private int myNumServiceQueues;
	private int myTotalWaitTime;
	private int myTotalServiceTime;
	private int myTotalIdleTime;
	private int myTotalCustomerServiceTime;
	private int myTotalCustomerWaitTime;
	private long myPresentTime;
	private long myStartTime;
	private float myAverageWaitTime;
	private float myAverageServiceTime;
	private float myAverageIdleTime;
	private float myAverageCustomerServiceTime;
	private float myAverageCustomerWaitTime;
	private ServiceQueue[] myServiceQueues;
	private ServiceQueue myShortestQueue;
	private UniformCustomerGenerator myCustomerGenerator;
	private UniformCashier[] myCashiers;
	private boolean mySuspended;
	
	public ServiceQueueManager(int numQueues, int maxTimeBetweenCustomers, int maxServiceTime, int maxNumCustomers)
	{
		myStartTime = System.currentTimeMillis();
		myNumServiceQueues = numQueues;
		myServiceQueues = new ServiceQueue[myNumServiceQueues];
		myCashiers = new UniformCashier[numQueues];
		
		myCustomerGenerator = new UniformCustomerGenerator(maxTimeBetweenCustomers, maxNumCustomers, this);
		myCustomerGenerator.start();
		
		for(int i = 0; i < numQueues; i++)
		{
			myServiceQueues[i] = new ServiceQueue();
			myCashiers[i] = new UniformCashier(maxServiceTime, myServiceQueues[i]);
			myCashiers[i].start();
		}
	}
	
	/**
	 * Suspends the thread in CustomerGenerator and each
	 * Cashier thread.
	 */
	
	public void suspend()
	{
		myCustomerGenerator.setSuspended(mySuspended);
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myCashiers[i].setSuspended(mySuspended);
		}
	}
	
	/**
	 * Resumes the thread in CustomerGenerator and each
	 * Cashier thread.
	 */
	
	public synchronized void resume()
	{
		myCustomerGenerator.setSuspended(mySuspended);
		myCustomerGenerator.resume();
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myCashiers[i].setSuspended(mySuspended);
			myCashiers[i].resume();
		}
	}
	
	/**
	 * Compares the size of each queue and returns the
	 * shortest one.
	 * 
	 * @return: the shortest queue
	 */
	
	public ServiceQueue determineShortestQueue()
	{
		myShortestQueue = myServiceQueues[0];
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			if(myShortestQueue.size() > myServiceQueues[i].size())
			{
				myShortestQueue = myServiceQueues[i];
			}
		}
		return myShortestQueue;
	}
	
	/**
	 * Returns the total amount of customers served in
	 * all queues.
	 * 
	 * @return: total served
	 */
	
	public int totalServedSoFar()
	{
		int totalServed = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			totalServed += myServiceQueues[i].getNumCustomersServedSoFar();
		}
		
		return totalServed;
	}
	
	/**
	 * Returns the total amount of customers served in
	 * a specific queue.
	 * 
	 * @param queue to get total served
	 * @return: total served in a specific queue
	 */
	
	public int totalServedSoFar(int queue)
	{
		int totalServed = 0;
		
		totalServed += myServiceQueues[queue].getNumCustomersServedSoFar();
		
		return totalServed;
	}
	
	/**
	 * Calculates the total wait time for each
	 * service queue.
	 * 
	 * @return: total wait time
	 */
	
	public int totalWaitTime()
	{
		myTotalWaitTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalWaitTime += myServiceQueues[i].getTotalWaitTime();
		}
		return myTotalWaitTime;
	}
	
	/**
	 * Calculates the total service time for each
	 * service queue.
	 * 
	 * @return: total service time
	 */
	
	public int totalServiceTime()
	{
		myTotalServiceTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalServiceTime += myServiceQueues[i].getTotalServiceTime();
		}
		return myTotalServiceTime;
	}
	
	/**
	 * Calculates the total idle time for each
	 * service queue.
	 * 
	 * @return: total idle time
	 */
	
	public int totalIdleTime()
	{
		myTotalIdleTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalIdleTime += myServiceQueues[i].getTotalIdleTime();
		}
		return myTotalIdleTime;
	}
	
	/**
	 * Calculates the total service time for
	 * the customers.
	 * 
	 * @return: total customer service time
	 */
	
	public int totalCustomerServiceTime()
	{
		myTotalCustomerServiceTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalCustomerServiceTime += myServiceQueues[i].totalCustomerServiceTime();
		}
		
		return myTotalCustomerServiceTime;
	}
	
	/**
	 * Calculates the total wait time for
	 * the customers.
	 * 
	 * @return: total customer wait time
	 */
	
	public int totalCustomerWaitTime()
	{
		myTotalCustomerWaitTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalCustomerWaitTime += myServiceQueues[i].totalCustomerWaitTime();
		}
		
		return myTotalCustomerWaitTime;
	}
	
	/**
	 * Calculates the average wait time across
	 * each service queue.
	 * 
	 * @return: average wait time
	 */
	
	public float averageWaitTime()
	{
		myAverageWaitTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageWaitTime += myServiceQueues[i].averageWaitTime();
		}
		return myAverageWaitTime / myNumServiceQueues;
	}
	
	/**
	 * Calculates the average service time across
	 * each service queue.
	 * 
	 * @return: average service time
	 */
	
	public float averageServiceTime()
	{
		myAverageServiceTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageServiceTime += myServiceQueues[i].averageServiceTime();
		}
		return myAverageServiceTime / myNumServiceQueues;
	}
	
	/**
	 * Calculates the average idle time across
	 * each service queue.
	 * 
	 * @return: average idle time
	 */
	
	public float averageIdleTime()
	{
		myAverageIdleTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageIdleTime += myServiceQueues[i].averageIdleTime();
		}
		return myAverageIdleTime / myNumServiceQueues;
	}
	
	/**
	 * Calculates the average service time for
	 * the customers. If 0 customers have been
	 * served, returns 0.
	 * 
	 * @return: average customer service time
	 */
	
	public float averageCustomerServiceTime()
	{
		myAverageCustomerServiceTime = 0;
		
		if(totalServedSoFar() > 0)
		{
			myAverageCustomerServiceTime += (totalCustomerServiceTime() / totalServedSoFar());
		}		
		
		return myAverageCustomerServiceTime;
	}
	
	/**
	 * Calculates the average wait time for
	 * the customers. If 0 customers have been
	 * served, returns 0.
	 * 
	 * @return: average customer wait time
	 */
	
	public float averageCustomerWaitTime()
	{
		myAverageCustomerWaitTime = 0;
		
		if(totalServedSoFar() > 0)
		{
			myAverageCustomerWaitTime += (myTotalCustomerWaitTime / totalServedSoFar());
		}
		
		return myAverageCustomerWaitTime;
	}
	
	/**
	 * Calculates the time passed since the simulation
	 * started, in seconds.
	 * 
	 * @return: time passed
	 */
	
	public double timePassed()
	{
		myPresentTime = System.currentTimeMillis();
		return (myPresentTime - myStartTime) / 1000.0;
	}
	
	/**
	 * Sets mySuspended to the passed in boolean; used
	 * to used to suspend or resume the threads for each
	 * cashier and the customer generator based on
	 * the thread in SimulationController.
	 * 
	 * @param boolean to set mySuspended to
	 */
	
	public void setSuspended(boolean b)
	{
		mySuspended = b;
	}
	
	/**
	 * Gets a specific service queue.
	 * 
	 * @param num of service queue to get
	 * @return: service queue
	 */
	
	public ServiceQueue getServiceQueue(int num)
	{
		return myServiceQueues[num];
	}
	
	/**
	 * Returns the number of service queues (cashiers)
	 * are being used.
	 * 
	 * @return: num service queues
	 */
	
	public int getNumServiceQueues()
	{
		return myNumServiceQueues;
	}
	
	/**
	 * Returns the size of the most recent customer.
	 * 
	 * @return: customer size
	 */
	
	public Dimension getCustomerSize()
	{
		return myCustomerGenerator.getCustomerSize();
	}
	
	/**
	 * Returns the most recent customer.
	 * 
	 * @return: customer
	 */
	
	public Customer getCustomer()
	{
		return myCustomerGenerator.getCustomer();
	}
	
	/**
	 * Enqueues the passed in customer in the shortest queue.
	 * 
	 * @param customer to enqueue
	 */
	
	public void enqueue(Customer customer)
	{
		ServiceQueue queue = determineShortestQueue();
		queue.enqueue(customer);
	}
	
	/**
	 * Returns the number of customers in a specific
	 * queue.
	 * 
	 * @param num of queue
	 * @return: num of customers in queue
	 */
	
	public int getNumInQueue(int num)
	{
		ServiceQueue queue = myServiceQueues[num];
		return queue.size();
	}
	
	/**
	 * Returns the name of a specific queue.
	 * 
	 * @param queue to get name of
	 * @return: name of queue
	 */
	
	public String getName(int queue)
	{
		return myServiceQueues[queue].getName();
	}
	
	/**
	 * Sets the generation time based on the passed
	 * in float. Used to speed up or slow down the
	 * simulation based on the slider.
	 * 
	 * @param num to change generation time by
	 */
	
	public void setGenerationTime(float num)
	{
		myCustomerGenerator.setGenerationTime(num);		
	}
	
	/**
	 * Sets the service time for each cashier(queue)
	 * based on the passed in float. Used to speed
	 * up or slow down the simulation based on the slider.
	 * 
	 * @param num to change service time by
	 */
	
	public void setServiceTime(float num)
	{
		for(int i = 0; i < myCashiers.length; i++)
		{
			myCashiers[i].setServiceTime(num);
		}
	}
	
	/**
	 * Returns a specific cashier.
	 * 
	 * @param num of cashier to get
	 * @return: cashier
	 */
	
	public Cashier getCashier(int num)
	{
		return myCashiers[num];
	}
}