package viewcontroller;

import queues.CustomerGenerator;
import queues.ServiceQueue;
import queues.ServiceQueueManager;
import queues.SimulationModel;
import queues.UniformCashier;
import queues.UniformCustomerGenerator;

/**
 * TODO:
 * Learn how to use threading
 * 
 * 
 * @author Charles
 *
 */

public class SimulationController implements Runnable
{
	private SimulationModel myModel;
	private SimulationView myView;
	private CustomerGenerator myCustomerGenerator;
	private UniformCashier myUniformCashier;
	private ServiceQueueManager myServiceQueueManager;
	private ServiceQueue myServiceQueue;
	private boolean mySuspended;
	private Thread myThread;
	
	
	public SimulationController()
	{
		myModel = new SimulationModel();
		myView = new SimulationView(this);
		myServiceQueueManager = new ServiceQueueManager();
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
		int numQueues = myView.getComboBoxNumber();
		
		myCustomerGenerator = new UniformCustomerGenerator(myView.getGenerationTime(),
														   myView.getNumCustomers(),
														   myServiceQueueManager);
		myUniformCashier = new UniformCashier(myView.getServiceTime(), myServiceQueue);
		
		this.startCustomerGenerator();
		
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
				System.out.println("working");
				
				Thread.sleep(1000);
				for(int i = 0; i < numQueues; i++)
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
	
	private void startCustomerGenerator()
	{
		myCustomerGenerator.start();
	}
	
	private void waitWhileSuspended() throws InterruptedException
	{
		while(mySuspended)
		{
			this.wait();
		}
	}
	
	private void displayCustomers(int queue)
	{
		int numInQueue = myModel.getNumCustomers(queue);
		myView.setCustomersInLine(queue, numInQueue);
	}
	
	public void startPause()
	{
		this.start();
		myView.changeStartPause();
		myView.changeCashiers(myView.getComboBoxNumber());
		myView.disable();
		
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
		myCustomerGenerator.setSuspended(false);
		
		this.notify();
		myCustomerGenerator.notify();
	}
	
	public void suspend()
	{
		mySuspended = true;
		myCustomerGenerator.setSuspended(true);
	}
}
