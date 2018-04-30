package viewcontroller;

import java.awt.Dimension;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import queues.Customer;
import queues.ServiceQueueManager;

/**
 * Cashier should say "is there anybody in my queue (can do with exceptions or with checking the size)" if there is nobody in the queue, cashier sleeps
 * 	for something like 5 ms and then try again, that 5 ms is added to idle time.
 * 
 * Every update in updateView should go to each queue,
 * get the icon for each customer in in the entire queue starting at 0 and going through the full thing
 * make sure that the view gets access to the full queue to do this, or in the 
 * 
 * 
 * @author Charles
 *
 */

public class SimulationController implements Runnable
{
	private int myNumCashiers;
	private int myGenerationTime;
	private int myServiceTime;
	private int myNumCustomers;
	private long myPrePause;
	private double myPostPause;
	private SimulationView myView;
	private ServiceQueueManager myServiceQueueManager;
	private boolean mySuspended;
	private boolean myStarted;
	private Thread myThread;
	
	
	public SimulationController()
	{
		myView = new SimulationView(this);
		myThread = new Thread(this);
		mySuspended = false;
		myStarted = false;
	}
	
	public static void main(String args[])
	{
		new SimulationController();
	}
	
	public void run()
	{
		try
		{
			synchronized(this)
			{
				myGenerationTime = myView.getGenerationTime();
				myNumCustomers = myView.getNumCustomers();
				myNumCashiers = myView.getComboBoxNumber();
				myServiceTime = myView.getServiceTime();				
//				myServiceQueueManager = new ServiceQueueManager(myView.getComboBoxNumber(), 
//						myView.getGenerationTime(),
//						myView.getServiceTime(),
//						myView.getNumCustomers());
				myServiceQueueManager = new ServiceQueueManager(myNumCashiers,
						myGenerationTime,
						myServiceTime,
						myNumCustomers);
//				
				this.updateView();
			}
		}
		catch(InterruptedException e)
		{
			String error;
			error = e.toString();
			System.out.println(error);
		}
		
	}
	
	public void start()
	{
		try
		{
			myThread.start();
		}
		catch(IllegalThreadStateException e)
		{
			String error;
			error = e.toString();
			System.out.println(error);
		}
	}
	
	private void updateView() throws InterruptedException
	{	
//while(myNumCustomers < myView.getNumCustomers())
//if(myNumCustomers == myView.getNumCustomers())
//	{
//		myView.enable();	
//	}
//		while(true)
//		{
		do
		{
			this.waitWhileSuspended();
			
			try
			{
				if(myView.getSliderValue() == 0)
				{
					System.out.println("Waiting for Infinity...");
				}
				else if(myView.getSliderValue() != 1.0 && myView.getSliderValue() != 0)
				{
					myServiceQueueManager.setGenerationTime((float)(myView.getSliderValue() / 100.0));
					myServiceQueueManager.setServiceTime((float)(myView.getSliderValue() / 100.0));
				}
				
				Thread.sleep(50);
				
				for(int i = 0; i < myNumCashiers; i++)
				{
					this.displayCustomers(i);
				}
				
//After pause, for each queue create a vector, go through the queue, get the icon for 0, for 1, fgor 2, until the size of the vector, send a vector of icons
//to the view and it sets the icon for all of the images. do this all at once instead of getting customer icons one by one.
				
			}
			catch(InterruptedException e)
			{
				String error;
				error = e.toString();
				System.out.println(error);
			}
		}
		while(myServiceQueueManager.totalServedSoFar() != myNumCustomers);
		
		for(int i = 0; i < myNumCashiers; i++)
		{
			myServiceQueueManager.getCashier(i).setContinue(false);
		}
//		}
	}
	
	private void waitWhileSuspended() throws InterruptedException
	{
		while(mySuspended)
		{
			myPrePause = System.currentTimeMillis();
			this.wait();
			myPostPause = myPostPause +  (System.currentTimeMillis() - myPrePause);
		}
	}
	
	private void displayCustomers(int queue)
	{
		int numInQueue = myServiceQueueManager.getNumInQueue(queue);
		myView.setCustomersInLine(queue, numInQueue, myServiceQueueManager.getServiceQueue(queue));
		
		myView.setNumServedText(myServiceQueueManager.totalServedSoFar(queue), queue);		
		
		
//Have to stop idle time once program is done, also nothing in customer stats works
		
//		if(myServiceQueueManager.totalServedSoFar() != myView.getNumCustomers())
//		{
	//	System.out.println(myServiceQueueManager.timePassed() - myPostPause);
		String text = "Total Served: " + myServiceQueueManager.totalServedSoFar() + "\n";
		text += "Total Time Passed: " + determineTime() + "\n";
//		text += "Total Customer Service Time: " + myServiceQueueManager.totalServiceTime() + "\n";
//		text += "Avg Customer Service Time: " + myServiceQueueManager.averageServiceTime() + "\n";
//		text += "Total Customer Wait Time: " + myServiceQueueManager.totalWaitTime() + "\n";
//		text += "Avg Customer Wait Time: " + myServiceQueueManager.averageWaitTime() + "\n";
		text += "Total Service Time: " + "\n" + myServiceQueueManager.totalCustomerServiceTime() + " ms" +  "\n";
		text += "Avg Service Time: " + "\n" + myServiceQueueManager.averageCustomerServiceTime() + " ms" + "\n";
		text += "Total Wait Time: " + "\n" + myServiceQueueManager.totalCustomerWaitTime() + " ms" + "\n";
		text += "Avg Wait Time: " + "\n" + myServiceQueueManager.averageCustomerWaitTime() + " ms" + "\n";
//		text += "Total Idle Time: " + myServiceQueueManager.totalIdleTime() + "\n";
//		text += "Avg Idle Time: " + myServiceQueueManager.averageIdleTime() + "\n";
		myView.setOverallStatsText(text);
//		}
	}
	
	public void startPause()
	{
		if(myStarted)
		{
			myView.changeStartPause();
			if(mySuspended)
			{
				this.resume();
			}
			else
			{
				this.suspend();
			}
		}
		else
		{
			this.start();
			myView.changeStartPause();
			myView.changeCashiers(myView.getComboBoxNumber() - 1);
			myStarted = true;
//			myView.disable(); (disables text input fields so the simulation can't be broken)		
//			Once simulation is done, myView.enable();
		}
	}
	
	public synchronized void resume()
	{
		mySuspended = false;
		myServiceQueueManager.setSuspended(false);
		
		this.notify();
		myServiceQueueManager.resume();
	}
	
	public void suspend()
	{
		mySuspended = true;
		myServiceQueueManager.setSuspended(true);
		myServiceQueueManager.suspend();
	}
	
	public void setCashierStatsText(Integer num)
	{
		if((num + 1) > myServiceQueueManager.getNumServiceQueues())
		{
			myView.setCashierStatsText("This cashier is inactive.");
		}
//		else if(myNumCustomers > myServiceQueueManager.totalServedSoFar())
//		{
		//add milliseconds to String
			String text = "Name: " + myServiceQueueManager.getName(num) + "\n";
			text += "Total Served: " + myServiceQueueManager.totalServedSoFar(num) + "\n";
			text += "Total ServiceTime: " + "\n" + myServiceQueueManager.getServiceQueue(num).getTotalServiceTime() + " ms" + "\n";
			text += "Avg Service Time: " + "\n" + myServiceQueueManager.getServiceQueue(num).averageServiceTime() + " ms" + "\n";
			text += "Total Idle Time: " + "\n" + myServiceQueueManager.getServiceQueue(num).getTotalIdleTime() + " ms" + "\n";
			text += "Avg Idle Time: " + "\n" + myServiceQueueManager.getServiceQueue(num).averageIdleTime() + " ms" + "\n";
			myView.setCashierStatsText(text);
//		}
	}
	
	public Dimension getCustomerSize()
	{
		return myServiceQueueManager.getCustomerSize();
	}
	
	public Customer getCustomer()
	{
		return myServiceQueueManager.getCustomer();
	}
	
	public int getQueueSize(int queue)
	{
		return myServiceQueueManager.getNumInQueue(queue);
	}
	
	public String determineTime()
	{
		DecimalFormat format = new DecimalFormat("#.###");
		format.setRoundingMode(RoundingMode.HALF_UP);
		String time = format.format(myServiceQueueManager.timePassed() - (myPostPause / 1000.0));
		time += "s";
		
		return time;
	}
}
