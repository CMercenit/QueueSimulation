package viewcontroller;

import java.awt.Dimension;

import queues.Customer;
import queues.CustomerGenerator;
import queues.ServiceQueue;
import queues.ServiceQueueManager;
import queues.SimulationModel;
import queues.UniformCashier;
import queues.UniformCustomerGenerator;

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
	private SimulationView myView;
	private ServiceQueueManager myServiceQueueManager;
	private ServiceQueue myServiceQueue;
	private int myQueue;
	private int myCounter;
	private boolean mySuspended;
	private boolean myStarted;
	private Thread myThread;
	
	
	public SimulationController()
	{
		myView = new SimulationView(this);
		myServiceQueue = new ServiceQueue();
		myThread = new Thread(this);
		mySuspended = false;
		myStarted = false;
		myCounter = 0;
		myQueue = 0;
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
				myServiceQueueManager = new ServiceQueueManager(myView.getComboBoxNumber(), 
						myView.getGenerationTime(),
						myView.getServiceTime(),
						myView.getNumCustomers());
				
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
			System.out.println(error + " in SimulationController.");
		}
	}
	
	private void updateView() throws InterruptedException
	{	
//while(myNumCustomers < myView.getNumCustomers())
//if(myNumCustomers == myView.getNumCustomers())
//	{
//		myView.enable();	
//	}
		while(true)
		{
			this.waitWhileSuspended();
			
			try
			{
				
		//		System.out.println("Thread in Controller going");
//Change this
				Thread.sleep(100);
//Change this
				for(int i = 0; i < myView.getComboBoxNumber(); i++)
				{
					this.displayCustomers(i);
				}
				
//After pause, for each queue create a vector, go through the queue, get the icon for 0, for 1, fgor 2, until the size of the vector, send a vector of icons
//to the view and it sets the icon for all of the images. do this all at once instead of getting customer icons one by one.
				
//				if(myQueue == 0)
//				{
//					if(myQueue < myView.getComboBoxNumber())
//					{
//	//					this.displayCustomers(myQueue);
//					}
//					myQueue = 1;
//				}
//				else if(myQueue == 1)
//				{
//					if(myQueue < myView.getComboBoxNumber())
//					{
//	//					this.displayCustomers(myQueue);
//					}
//					myQueue = 2;
//				}
//				else if(myQueue == 2)
//				{
//					if(myQueue < myView.getComboBoxNumber())
//					{
//	//					this.displayCustomers(myQueue);
//					}
//					myQueue = 3;
//				}
//				else if(myQueue == 3)
//				{
//					if(myQueue < myView.getComboBoxNumber())
//					{
//	//					this.displayCustomers(myQueue);
//					}
//					myQueue = 4;
//				}
//				else if(myQueue == 4)
//				{
//					if(myQueue < myView.getComboBoxNumber())
//					{
//	//					this.displayCustomers(myQueue);
//					}
//					myQueue = 0;
//				}
				
				
			}
			catch(InterruptedException e)
			{
				String error;
				error = e.toString();
				System.out.println(error);
			}
		}
	}
	
	private void waitWhileSuspended() throws InterruptedException
	{
		while(mySuspended)
		{
			System.out.println("Thread in Controller paused.");
			this.wait();
		}
	}
	
	private void displayCustomers(int queue)
	{
		int numInQueue = myServiceQueueManager.getNumInQueue(queue);
		myView.setCustomersInLine(queue, numInQueue, myServiceQueueManager.getServiceQueue(queue));
		
		myView.setNumServedText(myServiceQueueManager.totalServedSoFar(queue), queue);
	}
	
	public void startPause()
	{
		System.out.println("Start button pushed");
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
		
//		if(mySuspended)
//		{
//			this.resume();
//		}
//		else
//		{
//			this.suspend();
//		}
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
		else
		{
			String text = "Average Wait Time: " + myServiceQueueManager.getServiceQueue(num).averageWaitTime() + "\n";
			text = text + "Average Idle Time: " + myServiceQueueManager.getServiceQueue(num).averageIdleTime() + "\n";
			text = text + "Average Service Time: " + myServiceQueueManager.getServiceQueue(num).averageServiceTime() + "\n";
			myView.setCashierStatsText(text);
		}		
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
}
