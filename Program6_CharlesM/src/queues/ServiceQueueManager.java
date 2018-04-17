package queues;

public class ServiceQueueManager
{
	public final int MY_MAX_NUMBER_QUEUES = 0;
	
	private int myNumberOfServiceQueues;
	private int myTotalWaitTime;
	private int myTotalServiceTime;
	private int myTotalIdleTime;
	private int myPresentTime;
	private int myStartTime;
	private float myAverageWaitTime;
	private float myAverageServiceTime;
	private float myAverageIdleTime;
	private ServiceQueue[] myServiceQueues;
	
	public ServiceQueueManager()
	{
		
	}
	
	public int totalServedSoFar()
	{
		return 0;
	}
	
	public int totalWaitTime()
	{
		return 0;
	}
	
	public int totalServiceTime()
	{
		return 0;
	}
	
	public ServiceQueue determineShortestQueue()
	{
		return null;
	}
	
	public int averageWaitTime()
	{
		return 0;
	}
	
	public int averageServiceTime()
	{
		return 0;
	}
	
	public int averageIdleTime()
	{
		return 0;
	}
}
