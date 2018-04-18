package queues;

public class SimulationController implements Runnable
{
	private SimulationModel myModel;
	private SimulationView myView;
	private boolean mySuspended;
	private Thread myThread;
	
	
	public SimulationController()
	{
		myModel = new SimulationModel();
		myView = new SimulationView(this);
		myThread = new Thread(this);
		mySuspended = false;
		
//		this.start();
//		this.startPause();
	}
	
	public static void main(String args[])
	{
		new SimulationController();
	}
	
	public void run()
	{
		
	}
	
	public void startPause()
	{
		myView.changeStartPause();
		myView.changeCashiers(myView.getComboBoxNumber());
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
