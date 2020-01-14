package viewcontroller;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.Hashtable;
import javax.swing.*;

import qsim.ResourceLoader;
import queues.ButtonListener;
import queues.Customer;
import queues.ServiceQueue;

/**
 * View of MVC, displays all images for the simulation.
 * 
 * @author Charles Mercenit
 */

public class SimulationView
{
	private final String myBackgroundImage = "background.jpg";
	private final ImageIcon SCALED_CASHIER_CLOSED = new ImageIcon(ResourceLoader
			.loadImage("EmptyDesk(Transparent).png")
			.getScaledInstance(124, 60, Image.SCALE_SMOOTH));
	private final ImageIcon SCALED_CASHIER_OPEN = new ImageIcon(ResourceLoader
			.loadImage("Cashier(Transparent).png")
			.getScaledInstance(127, 123, Image.SCALE_SMOOTH));
	private final ImageIcon SCALED_CASHIER_MANAGER = new ImageIcon(ResourceLoader
			.loadImage("CashierManager(Transparent).png")
			.getScaledInstance(125, 125, Image.SCALE_SMOOTH));
	
	private final int MAX_PEOPLE_IN_LINE = 11;
	private final int MAX_NUM_CASHIERS = 5;
	private final int DEFAULT_CUSTOMER_GENERATION_TIME = 20;
	private final int DEFAULT_NUM_CUSTOMERS = 500;
	private final int DEFAULT_SERVICE_TIME = 600;
	
	private final Font myFont = new Font("Font", Font.BOLD, 15);
	private final Font myParamFont = new Font("Param Font", Font.BOLD, 20);
	private final Font myStatsFont = new Font("Stats Font", Font.BOLD, 15);
	
	private JFrame myFrame = new JFrame("Queues");
	private SimulationController myController;
	private JButton myStartPause;
	private ButtonListener myStartPauseListener;
	private ButtonListener[] myCashierStatsListeners;
	private Container myContentPane;
	private JPanel mySimulationPanel,
				   myStatsPanel;
	private JLabel myBackground;
	private JLabel[] myCashiers = new JLabel[MAX_NUM_CASHIERS];
	private JLabel[][] myCustomers = new JLabel[MAX_NUM_CASHIERS][MAX_PEOPLE_IN_LINE];
	private JTextArea myStats1,
					  myStats2;
	private JTextField myHeading1,
					   myHeading2,
					   myHeading3,
					   myHeading4, 
					   myHeading5,
					   myHeading6,
					   myHeading7,
					   myGenerationTime,
					   myNumCustomers,
					   myServiceTime;
	private JComboBox<Integer> myNumCashiers;
	private JTextField[] myNumServed,
						 myOverflow;
	private JSlider mySlider;
	
	public SimulationView(SimulationController controller)
	{
		myController = controller;
		
		//Creates the frame
		myFrame.setSize(1000, 900);
		myFrame.setLocation(500, 100);
		myFrame.setLayout(null);
		myFrame.setResizable(false);
		
		//Creates the content pane where everything is placed
		myContentPane = myFrame.getContentPane();
		myContentPane.setLayout(new BorderLayout());
		
		//Creates the panel that houses everything
		mySimulationPanel = new JPanel();
		mySimulationPanel.setBorder(BorderFactory.createEtchedBorder());
		mySimulationPanel.setLayout(null);
		
		//Creates and displays cashier images, starting with closed cashier
		for(int i = 0; i < MAX_NUM_CASHIERS; i++)
		{
			myCashiers[i] = new JLabel(SCALED_CASHIER_CLOSED);
			myCashiers[i].setSize(124, 60);
			myCashiers[i].setLocation(-5 + (140*i), 775);
			myCashiers[i].setVisible(true);
			mySimulationPanel.add(myCashiers[i]);
		}
		
		//Creates the customers
		for(int i = 0; i < MAX_NUM_CASHIERS; i++)
		{
			for(int j = 0; j < MAX_PEOPLE_IN_LINE; j++)
			{
				myCustomers[i][j] = new JLabel();
			}
		}
		
		//Creates the panel where statistics are kept
		myStatsPanel = new JPanel();
		myStatsPanel.setLayout(null);
		myStatsPanel.setSize(290, 1000);
		myStatsPanel.setLocation(700, 0);
		myStatsPanel.setBackground(new Color(225, 225, 225));
		
		//Creates the Start/Pause button
		myStartPause = new JButton("Start");
		myStartPause.setLayout(null);
		myStartPause.setSize(150, 25);
		myStartPause.setLocation(70, 835);
		myStatsPanel.add(myStartPause);

		//Creates statistics area 1
		myStats1 = new JTextArea();
		myStats1.setLayout(null);
		myStats1.setSize(275, 275);
		myStats1.setLineWrap(true);
		myStats1.setBorder(BorderFactory.createLoweredBevelBorder());
		myStats1.setBackground(Color.WHITE);
		myStats1.setLocation(8, 95);
		myStats1.setEditable(false);
		myStats1.setFont(new Font("Font 1", Font.BOLD, 19));
		myStatsPanel.add(myStats1);
		
		//Creates statistics area 2
		myStats2 = new JTextArea();
		myStats2.setLayout(null);
		myStats2.setSize(275, 275);
		myStats2.setLineWrap(true);
		myStats2.setBorder(BorderFactory.createLoweredBevelBorder());
		myStats2.setBackground(Color.WHITE);
		myStats2.setLocation(8, 400);
		myStats2.setEditable(false);
		myStats2.setFont(new Font("Font 1", Font.BOLD, 19));
		myStatsPanel.add(myStats2);
		
		//Creates the heading for statistics area 1
		myHeading1 = new JTextField("Customer Stats: ");
		myHeading1.setLayout(null);
		myHeading1.setSize(115, 25);
		myHeading1.setBackground(new Color(225, 225, 225));
		myHeading1.setBorder(BorderFactory.createEmptyBorder());
		myHeading1.setEditable(false);
		myHeading1.setLocation(8, 70);
		myHeading1.setFont(myFont);
		myStatsPanel.add(myHeading1);
		
		//Creates the heading for statistics area 2
		myHeading2 = new JTextField("Cashier Stats: ");
		myHeading2.setLayout(null);
		myHeading2.setSize(150, 25);
		myHeading2.setBackground(new Color(225, 225, 225));
		myHeading2.setBorder(BorderFactory.createEmptyBorder());
		myHeading2.setEditable(false);
		myHeading2.setLocation(8, 375);
		myHeading2.setFont(myFont);
		myStatsPanel.add(myHeading2);
		
		//Creates the heading for generation time
		myHeading3 = new JTextField("Generation Time: ");
		myHeading3.setLayout(null);
		myHeading3.setSize(200, 25);
		myHeading3.setBackground(new Color(225, 225, 225));
		myHeading3.setBorder(BorderFactory.createEmptyBorder());
		myHeading3.setEditable(false);
		myHeading3.setLocation(8, 685);
		myHeading3.setFont(myParamFont);
		myStatsPanel.add(myHeading3);
		
		//Creates the input field for generation time
		myGenerationTime = new JTextField();
		myGenerationTime.setLayout(null);
		myGenerationTime.setSize(75, 25);
		myGenerationTime.setBackground(Color.WHITE);
		myGenerationTime.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myGenerationTime.setEditable(true);
		myGenerationTime.setLocation(210, 685);
		myStatsPanel.add(myGenerationTime);
		
		//Creates the heading for number of customers
		myHeading4 = new JTextField("# of Customers: ");
		myHeading4.setLayout(null);
		myHeading4.setSize(150, 25);
		myHeading4.setBackground(new Color(225, 225, 225));
		myHeading4.setBorder(BorderFactory.createEmptyBorder());
		myHeading4.setEditable(false);
		myHeading4.setLocation(8, 720);
		myHeading4.setFont(myParamFont);
		myStatsPanel.add(myHeading4);
		
		//Creates the input field for number of customers
		myNumCustomers = new JTextField();
		myNumCustomers.setLayout(null);
		myNumCustomers.setSize(75, 25);
		myNumCustomers.setBackground(Color.WHITE);
		myNumCustomers.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myNumCustomers.setEditable(true);
		myNumCustomers.setLocation(210, 720);
		myStatsPanel.add(myNumCustomers);
		
		//Creates the heading for number of cashiers
		myHeading5 = new JTextField("# of Cashiers: ");
		myHeading5.setLayout(null);
		myHeading5.setSize(150, 25);
		myHeading5.setBackground(new Color(225, 225, 225));
		myHeading5.setBorder(BorderFactory.createEmptyBorder());
		myHeading5.setEditable(false);
		myHeading5.setLocation(8, 755);
		myHeading5.setFont(myParamFont);
		myStatsPanel.add(myHeading5);
		
		//Creates the drop down menu for number of cashiers
		Integer[] ints = new Integer[5];
		ints[0] = 1;
		ints[1] = 2;
		ints[2] = 3;
		ints[3] = 4;
		ints[4] = 5;
		myNumCashiers = new JComboBox<Integer>(ints);
		myNumCashiers.setSize(75, 25);
		myNumCashiers.setBackground(Color.WHITE);
		myNumCashiers.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myNumCashiers.setEditable(false);
		myNumCashiers.setLocation(210, 755);
		myStatsPanel.add(myNumCashiers);		
		
		//Creates the heading for service time
		myHeading6 = new JTextField("Service Time: ");
		myHeading6.setLayout(null);
		myHeading6.setSize(175, 25);
		myHeading6.setBackground(new Color(225, 225, 225));
		myHeading6.setBorder(BorderFactory.createEmptyBorder());
		myHeading6.setEditable(false);
		myHeading6.setLocation(8, 790);
		myHeading6.setFont(myParamFont);
		myStatsPanel.add(myHeading6);
		
		//Creates the input field for service time
		myServiceTime = new JTextField();
		myServiceTime.setLayout(null);
		myServiceTime.setSize(75, 25);
		myServiceTime.setBackground(Color.WHITE);
		myServiceTime.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myServiceTime.setEditable(true);
		myServiceTime.setLocation(210, 790);
		myStatsPanel.add(myServiceTime);
		
		//Creates the heading for the slider
		myHeading7 = new JTextField("Speed:");
		myHeading7.setLayout(null);
		myHeading7.setSize(115, 25);
		myHeading7.setBackground(new Color(225, 225, 225));
		myHeading7.setBorder(BorderFactory.createEmptyBorder());
		myHeading7.setEditable(false);
		myHeading7.setLocation(8, 0);
		myHeading7.setFont(myFont);
		myStatsPanel.add(myHeading7);	
		
		//Creates the slider
		mySlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
		mySlider.setSize(290, 45);
		Hashtable<Integer, JLabel> sliderTable = new Hashtable<Integer, JLabel>();
		sliderTable.put(new Integer(0), new JLabel("0.01"));
		sliderTable.put(new Integer(25), new JLabel("0.25"));
		sliderTable.put(new Integer(50), new JLabel("0.5"));
		sliderTable.put(new Integer(75), new JLabel("0.75"));
		sliderTable.put(new Integer(100), new JLabel("1"));
		sliderTable.put(new Integer(125), new JLabel("1.25"));
		sliderTable.put(new Integer(150), new JLabel("1.5"));
		sliderTable.put(new Integer(175), new JLabel("1.75"));
		sliderTable.put(new Integer(200), new JLabel("2"));
		mySlider.setLabelTable(sliderTable);
		mySlider.setSnapToTicks(true);
		mySlider.setMinorTickSpacing(25);
		mySlider.setLocation(0, 25);
		mySlider.setPaintTicks(true);
		mySlider.setPaintLabels(true);
		myStatsPanel.add(mySlider);	
		
		//Creates the text box under each cashier that tracks the number of customers served
		myNumServed = new JTextField[MAX_NUM_CASHIERS];
		for(int i = 0; i < MAX_NUM_CASHIERS; i++)
		{
			myNumServed[i] = new JTextField("0");
			myNumServed[i].setSize(50, 25);
			myNumServed[i].setLocation(30 + (140*i), 835);
			myNumServed[i].setVisible(true);
			myNumServed[i].setEditable(false);
			myNumServed[i].setBackground(Color.WHITE);
			myNumServed[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			myNumServed[i].setFont(myStatsFont);
			mySimulationPanel.add(myNumServed[i]);
		}
		
		//Creates the text box at the top of each line that shows customer overflow
		myOverflow = new JTextField[MAX_NUM_CASHIERS];
		for(int i = 0; i < MAX_NUM_CASHIERS; i++)
		{
			myOverflow[i] = new JTextField("+0");
			myOverflow[i].setSize(50, 25);
			myOverflow[i].setLocation(45 + (140*i), 5);
			myOverflow[i].setVisible(true);
			myOverflow[i].setEditable(false);
			myOverflow[i].setBackground(Color.WHITE);
			myOverflow[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			myOverflow[i].setFont(myStatsFont);
			mySimulationPanel.add(myOverflow[i]);
		}
		
		//Adds everything to the content pane
		mySimulationPanel.add(myStatsPanel);		
		myContentPane.add(mySimulationPanel, BorderLayout.CENTER);		
		
		//Associates button listeners
		this.associateListeners(myController);
		
		//Creates the background
		myBackground = new JLabel(new ImageIcon(ResourceLoader.loadImage(myBackgroundImage)));
		myBackground.setSize(700, 1000);
		myBackground.setLocation(0, 0);
		mySimulationPanel.add(myBackground);
		
		//Displays the frame
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Associates the Start/Pause button and each cashier
	 * with the appropriate method in SimulationController.
	 * 
	 * @param controller that includes the methods
	 */
	
	private void associateListeners(SimulationController controller)
	{
		Class<? extends SimulationController> controllerClass;
		Method startPauseMethod, cashierStatsMethod;
		Class<?>[] classArgs;
		Integer[] args;
		
		controllerClass = myController.getClass();
		
		startPauseMethod = null;
		cashierStatsMethod = null;
		
		classArgs = new Class[1];
		
		try
		{
			classArgs[0] = Class.forName("java.lang.Integer");
		}
		catch(ClassNotFoundException e)
		{
			String error;
			error = e.toString();
			System.out.println(error);
		}
		
		try
		{
			startPauseMethod = controllerClass.getMethod("startPause", (Class<?>[])null);
			cashierStatsMethod = controllerClass.getMethod("setCashierStatsText", classArgs);
		}
		catch(SecurityException e)
		{
			String error;
			error = e.toString();
			System.out.println(error);
		}
		catch(NoSuchMethodException e)
		{
			String error;
			error = e.toString();
			System.out.println(error);
		}
		
		myStartPauseListener = new ButtonListener(myController, startPauseMethod, null);
		myStartPause.addMouseListener(myStartPauseListener);
		
		myCashierStatsListeners = new ButtonListener[5];
		for(int i = 0; i < myCashierStatsListeners.length; i++)
		{
			args = new Integer[1];
			args[0] = new Integer(i);
			myCashierStatsListeners[i] = new ButtonListener(myController, cashierStatsMethod, args);
			myCashiers[i].addMouseListener(myCashierStatsListeners[i]);
		}		
	}
	
	/**
	 * Changes the customer statistics panel text.
	 * 
	 * @param text to change to
	 */
	
	public void setCustomerStatsText(String text)
	{
		myStats1.setText(text);
	}
	
	/**
	 * Changes the cashier statistics panel text.
	 * 
	 * @param text to change to
	 */
	
	public void setCashierStatsText(String text)
	{
		myStats2.setText(text);
	}
	
	/**
	 * Changes Start button to Pause button and vice versa.
	 */
	
	public void changeStartPause()
	{
		if(myStartPause.getText().equals("Start"))
		{
			myStartPause.setText("Pause");
		}
		else
		{
			myStartPause.setText("Start");
		}
	}
	
	/**
	 * Changes Cashier images from closed to open based
	 * on the number of cashiers that are specified by
	 * the user.
	 * 
	 * @param num of cashiers to change
	 */
	
	public void changeCashiers(int num)
	{		
		num = num + 1;
		for(int i = 0; i < num; i++)
		{
			if(myCashiers[i].getIcon().equals(SCALED_CASHIER_CLOSED))
			{
				myCashiers[i].setIcon(SCALED_CASHIER_OPEN);
				myCashiers[i].setSize(137, 133);
				myCashiers[i].setLocation(-5 + (140*i), 706);
				myCashiers[i].repaint();
				myNumServed[i].setLocation(45 + (140*i), 835);
				myNumServed[i].repaint();
			}
			else
			{
				myCashiers[4 - i].setIcon(SCALED_CASHIER_CLOSED);
				myCashiers[4 - i].setSize(124, 60);
				myCashiers[4 - i].setLocation(-5 + (140*(4 - i)), 775);
				myCashiers[4 - i].repaint();
				myNumServed[4 - i].setLocation(30 + (140*(4 - i)), 835);
				myNumServed[4 - i].repaint();
			}
		}
	}
	
	/**
	 * Returns the number of cashiers specified by
	 * the user.
	 * 
	 * @return: num of cashiers
	 */
	
	public int getComboBoxNumber()
	{
		return myNumCashiers.getSelectedIndex() + 1;
	}
	
	/**
	 * Returns the maximum generation time specified
	 * by the user. If input is less than 0 or not a number,
	 * returns default generation time (20).
	 * 
	 * @return: generation time
	 */
	
	public int getGenerationTime()
	{
		try
		{
			String text = myGenerationTime.getText();	
			if(Integer.parseInt(text) < 0)
			{
				return DEFAULT_CUSTOMER_GENERATION_TIME;
			}
			else
			{
				return Integer.parseInt(text);
			}
		}
		catch(NumberFormatException e)
		{
			return DEFAULT_CUSTOMER_GENERATION_TIME;	
		}
	}
	
	/**
	 * Returns the number of customers specified by the
	 * user. If input is less than 0 or not a number, 
	 * returns default number of customers (500).
	 * 
	 * @return: num customers
	 */
	
	public int getNumCustomers()
	{
		try
		{
			String text = myNumCustomers.getText();
			if(Integer.parseInt(text) < 0)
			{
				return DEFAULT_NUM_CUSTOMERS;
			}
			else
			{
				return Integer.parseInt(text);
			}
		}
		catch(NumberFormatException e)
		{
			return DEFAULT_NUM_CUSTOMERS;
		}
		
	}
	
	/**
	 * Returns the maximum service time specified by
	 * the user. If input is less than 0 or not a
	 * number, returns default service time (600).
	 * 
	 * @return: service time
	 */
	
	public int getServiceTime()
	{
		try
		{
			String text = myServiceTime.getText();
			if(Integer.parseInt(text) < 0)
			{
				return DEFAULT_SERVICE_TIME;
			}
			else
			{
				return Integer.parseInt(text);
		
			}
		}
		catch(NumberFormatException e)
		{
			return DEFAULT_SERVICE_TIME;
		}
	}
	
	/**
	 * Changes the text in the specified customer
	 * overflow text box to the passed in text.
	 * 
	 * @param text to change to
	 * @param queue to change
	 */
	
	public void setOverflowText(int text, int queue)
	{
		myOverflow[queue].setText("+" + text);
	}
	
	/**
	 * Changes the text in the specified cashier
	 * text box to the passed in text.
	 * 
	 * @param text to change to
	 * @param queue to change
	 */
	
	public void setNumServedText(int text, int queue)
	{
		myNumServed[queue].setText("" + text);
	}
	
	/**
	 * Determines how many customers to display, goes through
	 * the entire passed in ServiceQueue and displays each
	 * customer, then sets the overflow text if there are
	 * more customers in the queue than the max allowed in line.
	 * If the customer at the front of the line is a mom,
	 * change the image of the cashier.
	 * 
	 * @param queue that customers are in
	 * @param num of customers in line
	 * @param customers in line
	 */
	
	public void setCustomersInLine(int queue, int num, ServiceQueue customers)
	{
		int position;
		if(num == 0)
		{
			position = num;
		}
		else if((num - MAX_PEOPLE_IN_LINE) < 0 || (num - MAX_PEOPLE_IN_LINE) == 0)
		{
			position = num - 1;
		}
		else
		{
			position = MAX_PEOPLE_IN_LINE - 1;
		}
		
		for(int i = (MAX_PEOPLE_IN_LINE - 1); i > position; i--)
		{
			myCustomers[queue][i].setIcon(null);
			if(position == 0)
			{
				myCustomers[queue][0].setIcon(null);
			}
		} 

		for(int i = 1; i < (position + 1); i++)
		{
			try
			{
				Customer customer = customers.get(customers.size() - i);
			
				if(customers.get(customers.size() - 1).getIsMom() && customers.size() > 0)
				{
					myCashiers[queue].setIcon(SCALED_CASHIER_MANAGER);
				}
				else
				{
					myCashiers[queue].setIcon(SCALED_CASHIER_OPEN);
				}
				myCustomers[queue][i].setIcon(customer.getIcon());
				myCustomers[queue][i].setSize(customer.getSize());
			}
			catch(IndexOutOfBoundsException e)
			{
				
			}
			catch(NullPointerException e)
			{
				
			}
			
			setCustomerLocation(queue, i);
			myCustomers[queue][i].setVisible(true);
			myBackground.add(myCustomers[queue][i]);
			myBackground.repaint();
		}
		
		int customersLeft = num - MAX_PEOPLE_IN_LINE;
		int counter = 0;
		while(customersLeft > -1)
		{
			setOverflowText(counter, queue);
			counter++;
			customersLeft--;
		}
	}
	
	/**
	 * Sets the location of the customer based on
	 * what queue they're in.
	 * 
	 * @param queue that customers are in
	 * @param num in array of customers
	 */
	
	public void setCustomerLocation(int queue, int num)
	{
		
		switch(queue)
		{
			case 0:
				myCustomers[queue][num].setLocation(54, (627 - (60*num)));
				break;
			case 1:
				myCustomers[queue][num].setLocation(194, (627 - (60*num)));
				break;
			case 2:
				myCustomers[queue][num].setLocation(334, (627 - (60*num)));
				break;
			case 3:
				myCustomers[queue][num].setLocation(474, (627 - (60*num)));
				break;
			case 4:
				myCustomers[queue][num].setLocation(614, (627 - (60*num)));
				break;
		}
	}
	
	/**
	 * Returns the cashier images.
	 * 
	 * @return: cashiers
	 */
	
	public JLabel[] getCashiers()
	{
		return myCashiers;
	}
	
	/**
	 * Returns the value that the slider is at.
	 * 
	 * @return: slider value
	 */
	
	public int getSliderValue()
	{
		return mySlider.getValue();
	}
}