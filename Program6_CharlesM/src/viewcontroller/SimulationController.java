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
 * 
 * @author Charles
 *
 */

public class SimulationController implements Runnable
{
	private SimulationView myView;
	private ServiceQueueManager myServiceQueueManager;
	private ServiceQueue myServiceQueue;
	private boolean mySuspended;
	private Thread myThread;
	
	
	public SimulationController()
	{
		myView = new SimulationView(this);
		myServiceQueue = new ServiceQueue();
		myThread = new Thread(this);
		mySuspended = true;
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
		myServiceQueueManager = new ServiceQueueManager(myView.getComboBoxNumber(), 
														myView.getGenerationTime(),
														myView.getServiceTime(),
														myView.getNumCustomers());
		
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
				
				System.out.println("Thread in Controller going");
//Change this
				Thread.sleep(100);
				for(int i = 0; i < myView.getComboBoxNumber(); i++)
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
		myView.setCustomersInLine(queue, numInQueue);
	}
	
	public void startPause()
	{
		System.out.println("Start button pushed");
		this.start();
		myView.changeStartPause();
		myView.changeCashiers(myView.getComboBoxNumber() - 1);
//		myView.disable(); (disables text input fields so the simulation can't be broken)
		
//Once simulation is done, myView.enable();
		
		if(mySuspended)
		{
			this.resume();
		}
		else
		{
			this.suspend();
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
}
