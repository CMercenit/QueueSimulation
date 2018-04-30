package viewcontroller;

import java.awt.Dimension;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import queues.Customer;
import queues.ServiceQueueManager;

/**
 * The controller in MVC. Controls the main thread
 * that runs the whole simulation, as well as the
 * individual threads for customer generation and
 * cashiers.
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
	
	/**
	 * Runs the thread.
	 * Creates a new ServiceQueueManager that runs
	 * all of the other threads required for
	 * the simulation.
	 */
	
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
	
	/**
	 * Starts the thread.
	 */
	
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
	
	/**
	 * Updates the view, keeps track of where the slider
	 * is at, and then stops once the number of customers
	 * served is equal to the max number of customers
	 * specified by the user.
	 * 
	 * @throws InterruptedException
	 */
	
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
	
	/**
	 * Waits while the thread is suspended. Keeps track of
	 * how much time passed while waiting.
	 * 
	 * @throws InterruptedException
	 */
	
	private void waitWhileSuspended() throws InterruptedException
	{
		while(mySuspended)
		{
			myPrePause = System.currentTimeMillis();
			this.wait();
			myPostPause = myPostPause +  (System.currentTimeMillis() - myPrePause);
		}
	}
	
	/**
	 * Displays the customers in the passed in queue and
	 * sets the text in the customer text box to reflect
	 * customer statistics.
	 * 
	 * @param queue to update view of
	 */
	
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
		myView.setCustomerStatsText(text);
	}
	
	/**
	 * Starts and pauses the simulation when the
	 * start button is pressed.
	 */
	
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
	
	/**
	 * Resumes every thread.
	 */
	
	public synchronized void resume()
	{
		mySuspended = false;
		myServiceQueueManager.setSuspended(false);
		
		this.notify();
		myServiceQueueManager.resume();
	}
	
	/**
	 * Suspends every thread.
	 */
	
	public void suspend()
	{
		mySuspended = true;
		myServiceQueueManager.setSuspended(true);
		myServiceQueueManager.suspend();
	}
	
	/**
	 * Changes the cashier text box to reflect the  statistics of
	 * the cashier at the passed in number using reflection. If
	 * the number is greater than the number of service queues
	 * in the simulation, sets the text to show that the cashier
	 * is inactive.
	 * 
	 * @param num of cashier to set text for
	 */
	
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
	
	/**
	 * Returns the size of the most recent customer.
	 * 
	 * @return: customer size
	 */
	
	public Dimension getCustomerSize()
	{
		return myServiceQueueManager.getCustomerSize();
	}
	
	/**
	 * Returns the most recent customer.
	 * 
	 * @return: customer
	 */
	
	public Customer getCustomer()
	{
		return myServiceQueueManager.getCustomer();
	}
	
	/**
	 * Returns the size of the queue at the
	 * passed in number.
	 * 
	 * @param queue to get the size of
	 * @return: size of queue
	 */
	
	public int getQueueSize(int queue)
	{
		return myServiceQueueManager.getNumInQueue(queue);
	}
	
	/**
	 * Calculates what time to show in the customer
	 * statistics panel. Subtracts the time passed
	 * during pauses from the time total time passed.
	 * Uses DecimalFormat to force the number to be
	 * only 3 decimal places long.
	 * 
	 * @return: time passed
	 */
	
	public String determineTime()
	{
		DecimalFormat format = new DecimalFormat("#.###");
		format.setRoundingMode(RoundingMode.HALF_UP);
		String time = format.format(myServiceQueueManager.timePassed() - (myPostPause / 1000.0));
		time += "s";
		
		return time;
	}
}