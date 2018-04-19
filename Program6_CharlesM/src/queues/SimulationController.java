package queues;

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
	private ServiceQueueManager myServiceQueueManager;
	private boolean mySuspended;
	private Thread myThread;
	
	
	public SimulationController()
	{
		myModel = new SimulationModel();
		myView = new SimulationView(this);
		myServiceQueueManager = new ServiceQueueManager();
		myThread = new Thread(this);
		mySuspended = false;
		
		this.start();
	}
	
	public static void main(String args[])
	{
		new SimulationController();
	}
	
	public void run()
	{
		
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
	
	public void startPause()
	{
		myView.changeStartPause();
		myView.changeCashiers(myView.getComboBoxNumber());
		
//I don't know if this is correct
		myCustomerGenerator = 
				new UniformCustomerGenerator(myView.getGenerationTime(),
											 myView.getNumCustomers(),
											 myServiceQueueManager);
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
		this.notify();
	}
	
	public void suspend()
	{
		mySuspended = true;
	}
}
