package queues;

import java.awt.Dimension;

public class ServiceQueueManager
{
	public final int MAX_NUM_QUEUES = 5;
	
	private int myNumServiceQueues;
	private int myTotalWaitTime;
	private int myTotalServiceTime;
	private int myTotalIdleTime;
	private int myPresentTime;
	private long myStartTime;
	private float myAverageWaitTime;
	private float myAverageServiceTime;
	private float myAverageIdleTime;
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
		System.out.println("myCustomerGenerator started.");
		myCustomerGenerator.start();
		
		for(int i = 0; i < numQueues; i++)
		{
			myServiceQueues[i] = new ServiceQueue();
			myCashiers[i] = new UniformCashier(maxServiceTime, myServiceQueues[i]);
//			System.out.println("myCashiers[" + i + "] started.");
//			myCashiers[i].start();
		}
	}
	
	public void suspend()
	{
		myCustomerGenerator.setSuspended(mySuspended);
		
//		for(int i = 0; i < myNumServiceQueues; i++)
//		{
//			myCashiers[i].setSuspended(mySuspended);
//		}
	}
	
	public synchronized void resume()
	{
		System.out.println("myCustomerGenerator resumed.");
		myCustomerGenerator.setSuspended(mySuspended);
		myCustomerGenerator.resume();
		
//		for(int i = 0; i < myNumServiceQueues; i++)
//		{
//			System.out.println("myCashiers[" + i + "] resumed.");
//			myCashiers[i].setSuspended(mySuspended);
//			myCashiers[i].resume();
//		}
	}
	
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
	
	public int totalServedSoFar()
	{
		int totalServed = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			totalServed = totalServed + myServiceQueues[i].getNumCustomersServedSoFar();
		}
		
		return totalServed;
	}
	
	public int totalWaitTime()
	{
		myTotalWaitTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalWaitTime = myTotalWaitTime + myServiceQueues[i].getTotalWaitTime();
		}
		return myTotalWaitTime;
	}
	
	public int totalServiceTime()
	{
		myTotalServiceTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalServiceTime = myTotalServiceTime + myServiceQueues[i].getTotalServiceTime();
		}
		return myTotalServiceTime;
	}
	
	public int totalIdleTime()
	{
		myTotalIdleTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalIdleTime = myTotalIdleTime + myServiceQueues[i].getTotalIdleTime();
		}
		return myTotalIdleTime;
	}
	
	public float averageWaitTime()
	{
		myAverageWaitTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageWaitTime = myAverageWaitTime + myServiceQueues[i].averageWaitTime();
		}
		return myAverageWaitTime / myNumServiceQueues;
	}
	
	public float averageServiceTime()
	{
		myAverageServiceTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageServiceTime = myAverageServiceTime + myServiceQueues[i].averageServiceTime();
		}
		return myAverageServiceTime / myNumServiceQueues;
	}
	
	public float averageIdleTime()
	{
		myAverageIdleTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageIdleTime = myAverageIdleTime + myServiceQueues[i].averageIdleTime();
		}
		return myAverageIdleTime / myNumServiceQueues;
	}
	
	public long timePassed()
	{
		return myPresentTime - myStartTime;
	}
	
	public void setSuspended(boolean b)
	{
		mySuspended = b;
	}
	
	public ServiceQueue getServiceQueue(int num)
	{
		return myServiceQueues[num];
	}
	
	public int getNumServiceQueues()
	{
		return myNumServiceQueues;
	}
	
	public Dimension getCustomerSize()
	{
		return myCustomerGenerator.getCustomerSize();
	}
	
	public Customer getCustomer()
	{
		return myCustomerGenerator.getCustomer();
	}
	
	public void enqueue(Customer customer)
	{
		ServiceQueue queue = determineShortestQueue();
		queue.enqueue(customer);
	}
	
	public int getNumInQueue(int num)
	{
		ServiceQueue queue = myServiceQueues[num];
		return queue.size();
	}
}
