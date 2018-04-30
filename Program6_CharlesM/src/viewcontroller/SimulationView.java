package viewcontroller;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;

import queues.ButtonListener;
import queues.Customer;
import queues.ServiceQueue;

/**
 * 
 * 
 * TODO:
 * Should I make individual methods for each thing in the view?
 * (BONUS)
 * Scale mom better(too small), also doesn't display manager properly
 * 
 * email teacher tomorrow, say I think I broke bitbucket, want to meet up on Monday so he can look everything over and make sure it's all good,
 * 	ask for help fixing manager, ask for difference between idle time and wait time, ask if things in the view should be in different methods
 * 
 * 
 * 
 * @author Charles Mercenit
 *
 */

public class SimulationView
{
	private final String myBackgroundImage = "images/background.jpg";
	
	private final Image CASHIER_CLOSED = Toolkit.getDefaultToolkit().getImage("images/EmptyDesk(Transparent).png"); //145 x 70
	private final ImageIcon SCALED_CASHIER_CLOSED = new ImageIcon(CASHIER_CLOSED.getScaledInstance(124, 60, Image.SCALE_SMOOTH));
	private final Image CASHIER_OPEN = Toolkit.getDefaultToolkit().getImage("images/Cashier(Transparent).png"); //160 x 155
	private final ImageIcon SCALED_CASHIER_OPEN = new ImageIcon(CASHIER_OPEN.getScaledInstance(127, 123, Image.SCALE_SMOOTH)); //137 x 133
	private final Image CASHIER_MANAGER = Toolkit.getDefaultToolkit().getImage("images/CashierManager(Transparent).png");
	private final ImageIcon SCALED_CASHIER_MANAGER = new ImageIcon(CASHIER_MANAGER.getScaledInstance(125, 125, Image.SCALE_SMOOTH));
	
	private final int MAX_PEOPLE_IN_LINE = 11;
	private final int MAX_NUM_CASHIERS = 5;
	
	private final Font myFont = new Font("Font", Font.BOLD, 15);
	private final Font myParamFont = new Font("Param Font", Font.BOLD, 20);
	private final Font myStatsFont = new Font("Stats Font", Font.BOLD, 15);
	
	private JFrame myFrame = new JFrame("Queues");
	private SimulationController myController;
	private JButton myStartPause;
	private ButtonListener myStartPauseListener;
	private ButtonListener[] myCashierStatsListeners;
	private Container myContentPane;
	private JPanel mySimulationPanel;
	private JTextArea myStats1;
	private JTextArea myStats2;
	private JTextField  myHeading1,
						myHeading2,
						myHeading3,
						myHeading4, 
						myHeading5,
						myHeading6,
						myHeading7,
						myGenerationTime,
						myNumCustomers,
						myServiceTime;
	private JTextField[] myNumServed;
	private JTextField[] myOverflow;
	private JComboBox<Integer> myNumCashiers;
	private JLabel[][] myCustomers = new JLabel[MAX_NUM_CASHIERS][MAX_PEOPLE_IN_LINE];
	private JLabel[] myCashiers = new JLabel[MAX_NUM_CASHIERS];
	private JLabel myBackground;
	private JSlider mySlider;	
	private JPanel myStatsPanel;
	
	public SimulationView(SimulationController controller)
	{
		myController = controller;
		
		myFrame.setSize(1000, 900);
		myFrame.setLocation(500, 100);
		myFrame.setLayout(null);
		myFrame.setResizable(false);
		
		myContentPane = myFrame.getContentPane();
		myContentPane.setLayout(new BorderLayout());
		
		mySimulationPanel = new JPanel();
		mySimulationPanel.setBorder(BorderFactory.createEtchedBorder());
		mySimulationPanel.setLayout(null);
		
		for(int i = 0; i < MAX_NUM_CASHIERS; i++)
		{
			myCashiers[i] = new JLabel(SCALED_CASHIER_CLOSED);
			myCashiers[i].setSize(124, 60);
			myCashiers[i].setLocation(-5 + (140*i), 775);
			myCashiers[i].setVisible(true);
			mySimulationPanel.add(myCashiers[i]);
		}
		
		for(int i = 0; i < MAX_NUM_CASHIERS; i++)
		{
			for(int j = 0; j < MAX_PEOPLE_IN_LINE; j++)
			{
				myCustomers[i][j] = new JLabel();
			//	myCustomers[i][j].setSize();
			//	myCustomers[i][j].setLocation(150 + (*i), 500 - (50*j));
			//	mySimulationPanel.add(myCustomers[i][j]);
			}
		}
		
		
		
		
		
		
		myStatsPanel = new JPanel();
		myStatsPanel.setLayout(null);
		myStatsPanel.setSize(290, 1000);
		myStatsPanel.setLocation(700, 0);
		myStatsPanel.setBackground(new Color(225, 225, 225));
		
		myStartPause = new JButton("Start");
		myStartPause.setLayout(null);
		myStartPause.setSize(150, 25);
//		myStartPause.setLocation(70, 825);
		myStartPause.setLocation(70, 835);
		myStatsPanel.add(myStartPause);

		myStats1 = new JTextArea();
		myStats1.setLayout(null);
		myStats1.setSize(275, 275);
		myStats1.setLineWrap(true);
		myStats1.setBorder(BorderFactory.createLoweredBevelBorder());
		myStats1.setBackground(Color.WHITE);
//		myStats1.setLocation(8, 50);
		myStats1.setLocation(8, 95);
		myStats1.setEditable(false);
		myStats1.setFont(new Font("Font 1", Font.BOLD, 19));
		myStatsPanel.add(myStats1);
		
		myStats2 = new JTextArea();
		myStats2.setLayout(null);
		myStats2.setSize(275, 275);
		myStats2.setLineWrap(true);
		myStats2.setBorder(BorderFactory.createLoweredBevelBorder());
		myStats2.setBackground(Color.WHITE);
//		myStats2.setLocation(8, 375);
		myStats2.setLocation(8, 400);
		myStats2.setEditable(false);
		myStats2.setFont(new Font("Font 1", Font.BOLD, 19));
		myStatsPanel.add(myStats2);
		
		myHeading1 = new JTextField("Customer Stats: ");
		myHeading1.setLayout(null);
		myHeading1.setSize(115, 25);
		myHeading1.setBackground(new Color(225, 225, 225));
		myHeading1.setBorder(BorderFactory.createEmptyBorder());
		myHeading1.setEditable(false);
//		myHeading1.setLocation(8, 25);
		myHeading1.setLocation(8, 70);
		myHeading1.setFont(myFont);
		myStatsPanel.add(myHeading1);
		
		myHeading2 = new JTextField("Cashier Stats: ");
		myHeading2.setLayout(null);
		myHeading2.setSize(150, 25);
		myHeading2.setBackground(new Color(225, 225, 225));
		myHeading2.setBorder(BorderFactory.createEmptyBorder());
		myHeading2.setEditable(false);
//		myHeading2.setLocation(8, 350);
		myHeading2.setLocation(8, 375);
		myHeading2.setFont(myFont);
		myStatsPanel.add(myHeading2);
		
		myHeading3 = new JTextField("Generation Time: ");
		myHeading3.setLayout(null);
		myHeading3.setSize(200, 25);
		myHeading3.setBackground(new Color(225, 225, 225));
		myHeading3.setBorder(BorderFactory.createEmptyBorder());
		myHeading3.setEditable(false);
//		myHeading3.setLocation(8, 675);
		myHeading3.setLocation(8, 685);
		myHeading3.setFont(myParamFont);
		myStatsPanel.add(myHeading3);
		
		myGenerationTime = new JTextField();
		myGenerationTime.setLayout(null);
		myGenerationTime.setSize(75, 25);
		myGenerationTime.setBackground(Color.WHITE);
		myGenerationTime.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myGenerationTime.setEditable(true);
//		myGenerationTime.setLocation(210, 675);
		myGenerationTime.setLocation(210, 685);
		myStatsPanel.add(myGenerationTime);
		
		myHeading4 = new JTextField("# of Customers: ");
		myHeading4.setLayout(null);
		myHeading4.setSize(150, 25);
		myHeading4.setBackground(new Color(225, 225, 225));
		myHeading4.setBorder(BorderFactory.createEmptyBorder());
		myHeading4.setEditable(false);
//		myHeading4.setLocation(8, 710);
		myHeading4.setLocation(8, 720);
		myHeading4.setFont(myParamFont);
		myStatsPanel.add(myHeading4);
		
		myNumCustomers = new JTextField();
		myNumCustomers.setLayout(null);
		myNumCustomers.setSize(75, 25);
		myNumCustomers.setBackground(Color.WHITE);
		myNumCustomers.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myNumCustomers.setEditable(true);
//		myNumCustomers.setLocation(210, 710);
		myNumCustomers.setLocation(210, 720);
		myStatsPanel.add(myNumCustomers);
		
		myHeading5 = new JTextField("# of Cashiers: ");
		myHeading5.setLayout(null);
		myHeading5.setSize(150, 25);
		myHeading5.setBackground(new Color(225, 225, 225));
		myHeading5.setBorder(BorderFactory.createEmptyBorder());
		myHeading5.setEditable(false);
//		myHeading5.setLocation(8, 745);
		myHeading5.setLocation(8, 755);
		myHeading5.setFont(myParamFont);
		myStatsPanel.add(myHeading5);
			
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
//		myNumCashiers.setLocation(210, 745);
		myNumCashiers.setLocation(210, 755);
		myStatsPanel.add(myNumCashiers);		
		
		myHeading6 = new JTextField("Service Time: ");
		myHeading6.setLayout(null);
		myHeading6.setSize(175, 25);
		myHeading6.setBackground(new Color(225, 225, 225));
		myHeading6.setBorder(BorderFactory.createEmptyBorder());
		myHeading6.setEditable(false);
//		myHeading6.setLocation(8, 780);
		myHeading6.setLocation(8, 790);
		myHeading6.setFont(myParamFont);
		myStatsPanel.add(myHeading6);
		
		myServiceTime = new JTextField();
		myServiceTime.setLayout(null);
		myServiceTime.setSize(75, 25);
		myServiceTime.setBackground(Color.WHITE);
		myServiceTime.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myServiceTime.setEditable(true);
//		myServiceTime.setLocation(210, 780);
		myServiceTime.setLocation(210, 790);
		myStatsPanel.add(myServiceTime);
		
		mySlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
		mySlider.setSize(290, 45);
		Hashtable<Integer, JLabel> sliderTable = new Hashtable<Integer, JLabel>();
		sliderTable.put(new Integer(0), new JLabel("0"));
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
		
		myHeading7 = new JTextField("Speed:");
		myHeading7.setLayout(null);
		myHeading7.setSize(115, 25);
		myHeading7.setBackground(new Color(225, 225, 225));
		myHeading7.setBorder(BorderFactory.createEmptyBorder());
		myHeading7.setEditable(false);
		myHeading7.setLocation(8, 0);
		myHeading7.setFont(myFont);
		myStatsPanel.add(myHeading7);		
		
		
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
		
		mySimulationPanel.add(myStatsPanel);		
		myContentPane.add(mySimulationPanel, BorderLayout.CENTER);		
		
		this.associateListeners(myController);
		
		myBackground = new JLabel(new ImageIcon(myBackgroundImage));
		myBackground.setSize(700, 1000);
		myBackground.setLocation(0, 0);
		mySimulationPanel.add(myBackground);
		
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
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
	
	public void setOverallStatsText(String text)
	{
		myStats1.setText(text);
	}
	
	public void setCashierStatsText(String text)
	{
		myStats2.setText(text);
	}
	
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
	
	public int getComboBoxNumber()
	{
		return myNumCashiers.getSelectedIndex() + 1;
	}
	
	public int getGenerationTime()
	{
		String text = myGenerationTime.getText();		
		return Integer.parseInt(text);
	}
	
	public int getNumCustomers()
	{
		String text = myNumCustomers.getText();
		return Integer.parseInt(text);
	}
	
	public int getServiceTime()
	{
		String text = myServiceTime.getText();
		return Integer.parseInt(text);
	}
	
	public void setOverflowText(int text, int queue)
	{
//		for(int i = 0; i < getComboBoxNumber(); i++)
//		{
//			myOverflow[i].setText("+" + text);
//		}
		myOverflow[queue].setText("+" + text);
	}
	
	public void setNumServedText(int text, int queue)
	{
		myNumServed[queue].setText("" + text);
	}
	
	public void setCustomersInLine(int queue, int num, ServiceQueue customers)
	{//queue = line that i'm updating, num = amount of customers in that line, MAX_PEOPLE_IN_LINE = 11
		

//Doesn't place the customers properly using setCustomerLocation method
//Need to call this method after generating customer instead of every 100 ms

//		System.out.println("queue: " + queue);
//		System.out.println("num: " + num);
		
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
			//This is why the last image keeps changing with the Customer customer = customers.get(i) loop
			position = MAX_PEOPLE_IN_LINE - 1;
		}
		
		
//		This is for the vector of images
//		for(ImageIcon i: images)
//		{
//			
//		}
				
		
		
 //		Reason why previous images aren't deleted is position keeps going up, have to go through all of myCustomers[][] and replace all images
 
 
// 		for(int i = 0; i < myCustomers[queue].length; i++)
// 		{
// 			
// 		}
		
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
		
		
//		boolean b = true;
//		if(queue == 0)
//		{
//			if(b)
//			{
//				myCustomers[queue][num].setLocation(54, (627 - (60*num)));
//				b = !b;
//			}
//			else
//			{
//				myCustomers[queue][num].setLocation(36, (627 - (60*num)));
//				b = !b;
//			}
//		}
//		else if(queue == 1)
//		{
//			if(b)
//			{
//				myCustomers[queue][num].setLocation(194, (627 - (60*num)));
//				b = !b;
//			}
//			else
//			{
//				myCustomers[queue][num].setLocation(176, (627 - (60*num)));
//			}
//		}
//		else if(queue == 2)
//		{
//			if(b)
//			{
//				myCustomers[queue][num].setLocation(334, (627 - (60*num)));
//				b = !b;
//			}
//			else
//			{
//				myCustomers[queue][num].setLocation(316, (627 - (60*num)));
//			}
//		}
//		else if(queue == 3)
//		{
//			if(b)
//			{
//				myCustomers[queue][num].setLocation(474, (627 - (60*num)));
//				b = !b;
//			}
//			else
//			{
//				myCustomers[queue][num].setLocation(456, (627 - (60*num)));
//			}
//		}
//		else if(queue == 4)
//		{
//			if(b)
//			{
//				myCustomers[queue][num].setLocation(614, (627 - (60*num)));
//				b = !b;
//			}
//			else
//			{
//				myCustomers[queue][num].setLocation(596, (627 - (60*num)));
//			}
//		}
	}
	
	public void disable()
	{
		myGenerationTime.setEnabled(false);
		myServiceTime.setEnabled(false);
		myNumCustomers.setEnabled(false);
		myNumCashiers.setEnabled(false);
	}
	
	public void enable()
	{
		myGenerationTime.setEnabled(true);
		myServiceTime.setEnabled(true);
		myNumCustomers.setEnabled(true);
		myNumCashiers.setEnabled(true);
	}
	
	public JLabel[] getCashiers()
	{
		return myCashiers;
	}
	
	public int getSliderValue()
	{
		return mySlider.getValue();
	}
}
