package viewcontroller;

import java.awt.Dimension;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import queues.Customer;
import queues.ServiceQueueManager;

/**
 * 
 * @author Charles
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
				
				myServiceQueueManager = new ServiceQueueManager(myNumCashiers,
						myGenerationTime,
						myServiceTime,
						myNumCustomers);
				
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
		
		String text = "Total Served: " + myServiceQueueManager.totalServedSoFar() + "\n";
		text += "Total Time Passed: " + determineTime() + "\n";
		text += "Total Service Time: " + "\n" + myServiceQueueManager.totalCustomerServiceTime() + " ms" +  "\n";
		text += "Avg Service Time: " + "\n" + myServiceQueueManager.averageCustomerServiceTime() + " ms" + "\n";
		text += "Total Wait Time: " + "\n" + myServiceQueueManager.totalCustomerWaitTime() + " ms" + "\n";
		text += "Avg Wait Time: " + "\n" + myServiceQueueManager.averageCustomerWaitTime() + " ms" + "\n";
		myView.setOverallStatsText(text);
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
		
		String text = "Name: " + myServiceQueueManager.getName(num) + "\n";
		text += "Total Served: " + myServiceQueueManager.totalServedSoFar(num) + "\n";
		text += "Total ServiceTime: " + "\n" + myServiceQueueManager.getServiceQueue(num).getTotalServiceTime() + " ms" + "\n";
		text += "Avg Service Time: " + "\n" + myServiceQueueManager.getServiceQueue(num).averageServiceTime() + " ms" + "\n";
		text += "Total Idle Time: " + "\n" + myServiceQueueManager.getServiceQueue(num).getTotalIdleTime() + " ms" + "\n";
		text += "Avg Idle Time: " + "\n" + myServiceQueueManager.getServiceQueue(num).averageIdleTime() + " ms" + "\n";
		myView.setCashierStatsText(text);
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
