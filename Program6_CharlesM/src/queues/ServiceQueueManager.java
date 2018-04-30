package queues;

import java.awt.Dimension;

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
	
	public void suspend()
	{
		myCustomerGenerator.setSuspended(mySuspended);
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myCashiers[i].setSuspended(mySuspended);
		}
	}
	
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
			totalServed += myServiceQueues[i].getNumCustomersServedSoFar();
		}
		
		return totalServed;
	}
	
	public int totalServedSoFar(int queue)
	{
		int totalServed = 0;
		
		totalServed += myServiceQueues[queue].getNumCustomersServedSoFar();
		
		return totalServed;
	}
	
	public int totalWaitTime()
	{
		myTotalWaitTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalWaitTime += myServiceQueues[i].getTotalWaitTime();
		}
		return myTotalWaitTime;
	}
	
	public int totalServiceTime()
	{
		myTotalServiceTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalServiceTime += myServiceQueues[i].getTotalServiceTime();
		}
		return myTotalServiceTime;
	}
	
	public int totalIdleTime()
	{
		myTotalIdleTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalIdleTime += myServiceQueues[i].getTotalIdleTime();
		}
		return myTotalIdleTime;
	}
	
	public float averageWaitTime()
	{
		myAverageWaitTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageWaitTime += myServiceQueues[i].averageWaitTime();
		}
		return myAverageWaitTime / myNumServiceQueues;
	}
	
	public float averageServiceTime()
	{
		myAverageServiceTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageServiceTime += myServiceQueues[i].averageServiceTime();
		}
		return myAverageServiceTime / myNumServiceQueues;
	}
	
	public float averageIdleTime()
	{
		myAverageIdleTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myAverageIdleTime += myServiceQueues[i].averageIdleTime();
		}
		return myAverageIdleTime / myNumServiceQueues;
	}
	
	public double timePassed()
	{
		myPresentTime = System.currentTimeMillis();
		return (myPresentTime - myStartTime) / 1000.0;
	}
	
	public int totalCustomerServiceTime()
	{
		myTotalCustomerServiceTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalCustomerServiceTime += myServiceQueues[i].totalCustomerServiceTime();
		}
		
		return myTotalCustomerServiceTime;
	}
	
	public int totalCustomerWaitTime()
	{
		myTotalCustomerWaitTime = 0;
		
		for(int i = 0; i < myNumServiceQueues; i++)
		{
			myTotalCustomerWaitTime += myServiceQueues[i].totalCustomerWaitTime();
		}
		
		return myTotalCustomerWaitTime;
	}
	
	public float averageCustomerServiceTime()
	{
		myAverageCustomerServiceTime = 0;
		
		if(totalServedSoFar() > 0)
		{
			myAverageCustomerServiceTime += (totalCustomerServiceTime() / totalServedSoFar());
		}		
		
		return myAverageCustomerServiceTime;
	}
	
	public float averageCustomerWaitTime()
	{
		myAverageCustomerWaitTime = 0;
		
		if(totalServedSoFar() > 0)
		{
			myAverageCustomerWaitTime += (myTotalCustomerWaitTime / totalServedSoFar());
		}
		
		return myAverageCustomerWaitTime;
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
	
	public String getName(int queue)
	{
		return myServiceQueues[queue].getName();
	}
	
	public void setGenerationTime(float num)
	{
		myCustomerGenerator.setGenerationTime(num);		
	}
	
	public void setServiceTime(float num)
	{
		for(int i = 0; i < myCashiers.length; i++)
		{
			myCashiers[i].setServiceTime(num);
		}
	}
	
	public Cashier getCashier(int num)
	{
		return myCashiers[num];
	}
	
	public void setStartTime(long num)
	{
		myStartTime = num;
	}
}