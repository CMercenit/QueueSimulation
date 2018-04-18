package queues;

/**
 * What do with myServiceTime
 * 
 * @author Charles Mercenit
 *
 */
public class Customer
{
	private int myServiceTime;
	private long myEntryTime;
	private long myWaitTime;
	
	public Customer()
	{
		myEntryTime = System.currentTimeMillis();
	}
	
	public long getWaitTime()
	{
		myWaitTime = (System.currentTimeMillis() - myEntryTime);
		
		return myWaitTime;
	}
	
	public long getEntryTime()
	{
		return myEntryTime;
	}
	
	public int getServiceTime()
	{
		return myServiceTime;
	}
}
