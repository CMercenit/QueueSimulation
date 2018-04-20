package queues;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

import javax.swing.*;

/**
 * 
 * 
 * TODO:
 * Should I change the package for the regular stuff? For the view/controller? change view and controller package
 * Is it okay to completely copy+paste ButtonListener class? Because I did. yes
 * Where do I add the images for my customers to the customers that I create?
 * 
 * try adding slider that increases or descreases simulation time
 * 	
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
	private final Image REGULAR_PERSON = Toolkit.getDefaultToolkit().getImage("images/RegularMan(Transparent).png"); //55 x 155
	private final ImageIcon SCALED_REGULAR_PERSON = new ImageIcon(REGULAR_PERSON.getScaledInstance(50, 100, Image.SCALE_SMOOTH)); //23 x 66
	private final Image BOW_TIE_PERSON = Toolkit.getDefaultToolkit().getImage("images/BowTie(Transparent).png"); //50 x 157
	private final ImageIcon SCALED_BOW_TIE_PERSON = new ImageIcon(BOW_TIE_PERSON.getScaledInstance(40, 95, Image.SCALE_SMOOTH)); //21 x 67
	private final Image PONY_TAIL_PERSON = Toolkit.getDefaultToolkit().getImage("images/PonyTail(Transparent).png"); //105 x 160
	private final ImageIcon SCALED_PONY_TAIL_PERSON = new ImageIcon(PONY_TAIL_PERSON.getScaledInstance(65, 108, Image.SCALE_SMOOTH)); //45 x 69
	private final Image TOP_HAT_PERSON = Toolkit.getDefaultToolkit().getImage("images/TopHat(Transparent).png"); //55 x 185
	private final ImageIcon SCALED_TOP_HAT_PERSON = new ImageIcon(TOP_HAT_PERSON.getScaledInstance(53, 128, Image.SCALE_SMOOTH)); //23 x 79
	
	private final int MAX_PEOPLE_IN_LINE = 11;
	private final int MAX_NUM_CASHIERS = 5;
	private final int OPEN_CASHIER_WIDTH = 160;
	private final int OPEN_CASHIER_HEIGHT = 155;
	private final int CLOSED_CASHIER_WIDTH = 145;
	private final int CLOSED_CASHIER_HEIGHT = 70;
	
	private final Font myFont = new Font("Font", Font.BOLD, 15);
	private final Font myParamFont = new Font("Param Font", Font.BOLD, 20);
	private final Font myStatsFont = new Font("Stats Font", Font.BOLD, 15);
	
	private JFrame myFrame = new JFrame("Queues");
	private SimulationController myController;
	private JButton myStartPause;
	private ButtonListener myStartPauseListener;
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
						myGenerationTime,
						myNumCustomers,
						myServiceTime;
	private JTextField[] myNumServed;
	private JTextField[] myOverflow;
	private JComboBox<Integer> myNumCashiers;
	private JLabel[][] myCustomers;
	private JLabel[] myCashiers = new JLabel[MAX_NUM_CASHIERS];
	private JLabel  myRegularPerson,
					myBowTiePerson,
					myPonyTailPerson,
					myTopHatPerson,
					myCashier,
					myCashierClosed;
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

		
/*
		myRegularPerson = new JLabel(new ImageIcon(REGULAR_PERSON));
		myRegularPerson.setSize(55, 155);
		myRegularPerson.setLocation(55, 650);
		myRegularPerson.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.MAGENTA));
		mySimulationPanel.add(myRegularPerson);
		
		myPonyTailPerson = new JLabel(new ImageIcon(PONY_TAIL_PERSON));
		myPonyTailPerson.setSize(105, 160);
		myPonyTailPerson.setLocation(120, 650);
		myPonyTailPerson.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.MAGENTA));
		mySimulationPanel.add(myPonyTailPerson);	

		myTopHatPerson = new JLabel(new ImageIcon(TOP_HAT_PERSON));
		myTopHatPerson.setSize(55, 185);
		myTopHatPerson.setLocation(235, 650);
		myTopHatPerson.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.MAGENTA));
		mySimulationPanel.add(myTopHatPerson);	
		
		myBowTiePerson = new JLabel(new ImageIcon(BOW_TIE_PERSON));
		myBowTiePerson.setSize(50, 157);
		myBowTiePerson.setLocation(300, 650);
		myBowTiePerson.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.MAGENTA));
		mySimulationPanel.add(myBowTiePerson);	
		
		myCashier = new JLabel(new ImageIcon(CASHIER_OPEN));
		myCashier.setSize(160, 155);
		myCashier.setLocation(371, 650);
		myCashier.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.MAGENTA));
		mySimulationPanel.add(myCashier);	
		
		myCashierClosed = new JLabel(new ImageIcon(CASHIER_CLOSED));
		myCashierClosed.setSize(145, 70);
		myCashierClosed.setLocation(371, 570);
		myCashierClosed.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.MAGENTA));
		mySimulationPanel.add(myCashierClosed);		

//SCALED IMAGE		
		JLabel test = new JLabel(SCALED_CASHIER_CLOSED);
		test.setSize(62, 30);
		test.setLocation(371, 530);
		test.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.MAGENTA));
		mySimulationPanel.add(test);
//SCALED IMAGE
		
*/
		for(int i = 0; i < MAX_NUM_CASHIERS; i++)
		{
			myCashiers[i] = new JLabel(SCALED_CASHIER_CLOSED);
			myCashiers[i].setSize(124, 60);
			myCashiers[i].setLocation(-5 + (140*i), 775);
			myCashiers[i].setVisible(true);
			mySimulationPanel.add(myCashiers[i]);
		}
		
//		myCustomers = new JLabel[MAX_NUM_CASHIERS][MAX_PEOPLE_IN_LINE];
//		for(int i = 0; i < MAX_NUM_CASHIERS; i++)
//		{
//			for(int j = 0; j < MAX_PEOPLE_IN_LINE; j++)
//			{
//				myCustomers[i][j] = new JLabel();
//				myCustomers[i][j].setSize();
//				myCustomers[i][j].setLocation(150 + (*i), 500 - (50*j));
//				myCustomers[i][j].setVisible(true);
//				mySimulationPanel.add(myCustomers[i][j]);
//			}
//		}
		

//TESTING PEOPLE IMAGES
		JLabel test = new JLabel(SCALED_REGULAR_PERSON);
		test.setSize(50, 100);
		test.setLocation(40, 600);
		test.setVisible(true);
		mySimulationPanel.add(test);
		
		JLabel test2 = new JLabel(SCALED_BOW_TIE_PERSON);
		test2.setSize(50, 100);
		test2.setLocation(95, 600);
		test2.setVisible(true);
		mySimulationPanel.add(test2);
		
		JLabel test3 = new JLabel(SCALED_TOP_HAT_PERSON);
		test3.setSize(50, 125);
		test3.setLocation(150, 580);
		test3.setVisible(true);
		mySimulationPanel.add(test3);
		
		JLabel test4 = new JLabel(SCALED_PONY_TAIL_PERSON);
		test4.setSize(75, 100);
		test4.setLocation(195, 598);
		test4.setVisible(true);
		mySimulationPanel.add(test4);
		
		JLabel[] test5 = new JLabel[MAX_PEOPLE_IN_LINE];
		boolean b = true;
		for(int i = 0; i < MAX_PEOPLE_IN_LINE; i++)
		{
			test5[i] = new JLabel(SCALED_REGULAR_PERSON);
			test5[i].setSize(50, 100);
			test5[i].setVisible(true);
			if(b)
			{
				test5[i].setLocation(614, 627 - (60*i));
				b = !b;
			}
			else
			{
				test5[i].setLocation(596, 627 - (60*i));
				b = !b;
			}
			mySimulationPanel.add(test5[i]);
		}
		
		JLabel[] test6 = new JLabel[MAX_PEOPLE_IN_LINE];
		boolean e = true;
		ImageIcon[] people = new ImageIcon[4];
		people[0] = SCALED_REGULAR_PERSON;
		people[1] = SCALED_BOW_TIE_PERSON;
		people[2] = SCALED_PONY_TAIL_PERSON;
		people[3] = SCALED_TOP_HAT_PERSON;
		for(int i = 0; i < MAX_PEOPLE_IN_LINE; i++)
		{
			int num = (int)(Math.random()*4);
			test6[i] = new JLabel(people[num]);
			if(test6[i].getIcon().equals(SCALED_REGULAR_PERSON))
			{
				test6[i].setSize(50, 100);
			}
			else if(test6[i].getIcon().equals(SCALED_TOP_HAT_PERSON))
			{
				test6[i].setSize(50, 125);
			}
			else if(test6[i].getIcon().equals(SCALED_PONY_TAIL_PERSON))
			{
				test6[i].setSize(75, 100);
			}
			else
			{
				test6[i].setSize(50, 100);
			}
			test6[i].setVisible(true);
			if(e)
			{
				if(test6[i].getIcon().equals(SCALED_PONY_TAIL_PERSON))
				{
					test6[i].setLocation(472, 627 - (60*i));
				}
				else
				{
					test6[i].setLocation(474, 627 - (60*i)); //18 apart
				}
				e = !e;
			}
			else
			{
				if(test6[i].getIcon().equals(SCALED_PONY_TAIL_PERSON))
				{
					test6[i].setLocation(452, 627 - (60*i));
				}
				else
				{
					test6[i].setLocation(456, 627 - (60*i));
				}
				e = !e;
			}
			mySimulationPanel.add(test6[i]);
		}
//TESTING PEOPLE IMAGES		
		
		
		myStatsPanel = new JPanel();
		myStatsPanel.setLayout(null);
		myStatsPanel.setSize(290, 1000);
		myStatsPanel.setLocation(700, 0);
		myStatsPanel.setBackground(new Color(225, 225, 225));
		
		myStartPause = new JButton("Start");
		myStartPause.setLayout(null);
		myStartPause.setSize(150, 25);
		myStartPause.setLocation(70, 825);
		myStatsPanel.add(myStartPause);

		myStats1 = new JTextArea();
		myStats1.setLayout(null);
		myStats1.setSize(275, 275);
		myStats1.setLineWrap(true);
		myStats1.setBorder(BorderFactory.createLoweredBevelBorder());
		myStats1.setBackground(Color.WHITE);
		myStats1.setLocation(8, 50);
		myStats1.setEditable(false);
		myStatsPanel.add(myStats1);
		
		myStats2 = new JTextArea();
		myStats2.setLayout(null);
		myStats2.setSize(275, 275);
		myStats2.setLineWrap(true);
		myStats2.setBorder(BorderFactory.createLoweredBevelBorder());
		myStats2.setBackground(Color.WHITE);
		myStats2.setLocation(8, 375);
		myStats2.setEditable(false);
		myStatsPanel.add(myStats2);
		
		myHeading1 = new JTextField("Overall Stats: ");
		myHeading1.setLayout(null);
		myHeading1.setSize(150, 25);
		myHeading1.setBackground(new Color(225, 225, 225));
		myHeading1.setBorder(BorderFactory.createEmptyBorder());
		myHeading1.setEditable(false);
		myHeading1.setLocation(8, 25);
		myHeading1.setFont(myFont);
		myStatsPanel.add(myHeading1);
		
		myHeading2 = new JTextField("Cashier Stats: ");
		myHeading2.setLayout(null);
		myHeading2.setSize(150, 25);
		myHeading2.setBackground(new Color(225, 225, 225));
		myHeading2.setBorder(BorderFactory.createEmptyBorder());
		myHeading2.setEditable(false);
		myHeading2.setLocation(8, 350);
		myHeading2.setFont(myFont);
		myStatsPanel.add(myHeading2);
		
		myHeading3 = new JTextField("Generation Time: ");
		myHeading3.setLayout(null);
		myHeading3.setSize(200, 25);
		myHeading3.setBackground(new Color(225, 225, 225));
		myHeading3.setBorder(BorderFactory.createEmptyBorder());
		myHeading3.setEditable(false);
		myHeading3.setLocation(8, 675);
		myHeading3.setFont(myParamFont);
		myStatsPanel.add(myHeading3);
		
		myGenerationTime = new JTextField();
		myGenerationTime.setLayout(null);
		myGenerationTime.setSize(75, 25);
		myGenerationTime.setBackground(Color.WHITE);
		myGenerationTime.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myGenerationTime.setEditable(true);
		myGenerationTime.setLocation(210, 675);
		myStatsPanel.add(myGenerationTime);
		
		myHeading4 = new JTextField("# of Customers: ");
		myHeading4.setLayout(null);
		myHeading4.setSize(150, 25);
		myHeading4.setBackground(new Color(225, 225, 225));
		myHeading4.setBorder(BorderFactory.createEmptyBorder());
		myHeading4.setEditable(false);
		myHeading4.setLocation(8, 710);
		myHeading4.setFont(myParamFont);
		myStatsPanel.add(myHeading4);
		
		myNumCustomers = new JTextField();
		myNumCustomers.setLayout(null);
		myNumCustomers.setSize(75, 25);
		myNumCustomers.setBackground(Color.WHITE);
		myNumCustomers.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myNumCustomers.setEditable(true);
		myNumCustomers.setLocation(210, 710);
		myStatsPanel.add(myNumCustomers);
		
		myHeading5 = new JTextField("# of Cashiers: ");
		myHeading5.setLayout(null);
		myHeading5.setSize(150, 25);
		myHeading5.setBackground(new Color(225, 225, 225));
		myHeading5.setBorder(BorderFactory.createEmptyBorder());
		myHeading5.setEditable(false);
		myHeading5.setLocation(8, 745);
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
		myNumCashiers.setLocation(210, 745);
		myStatsPanel.add(myNumCashiers);		
		
		myHeading6 = new JTextField("Service Time: ");
		myHeading6.setLayout(null);
		myHeading6.setSize(175, 25);
		myHeading6.setBackground(new Color(225, 225, 225));
		myHeading6.setBorder(BorderFactory.createEmptyBorder());
		myHeading6.setEditable(false);
		myHeading6.setLocation(8, 780);
		myHeading6.setFont(myParamFont);
		myStatsPanel.add(myHeading6);
		
		myServiceTime = new JTextField();
		myServiceTime.setLayout(null);
		myServiceTime.setSize(75, 25);
		myServiceTime.setBackground(Color.WHITE);
		myServiceTime.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myServiceTime.setEditable(true);
		myServiceTime.setLocation(210, 780);
		myStatsPanel.add(myServiceTime);
		
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
		
/*
		myCustomers = new JLabel[MAX_NUM_TELLERS][MAX_PEOPLE_IN_LINE];
		for(int i = 0; i < MAX_NUM_TELLERS; i++)
		{
			for(int j = 0; j < MAX_PEOPLE_IN_LINE; j++)
			{
				myCustomers[i][j] = new JLabel();
				myCustomers[i][j].setSize(CUSTOMER_WIDTH, CUSTOMER_HEIGHT);
				myCustomers[i][j].setLocation(150 + (CUSTOMER_WIDTH*i), 500 - (50*j));
				myCustomers[i][j].setVisible(true);
				mySimulationPanel.add(myCustomers[i][j]);
			}
		}
*/

		
		mySimulationPanel.add(myStatsPanel);
		
		myContentPane.add(mySimulationPanel, BorderLayout.CENTER);
		
		
		
		
		
		
		
		this.associateListeners(myController);
		
		
		

		
		JLabel myBackground = new JLabel(new ImageIcon(myBackgroundImage));
		myBackground.setSize(700, 1000);
		myBackground.setLocation(0, 0);
		mySimulationPanel.add(myBackground);
		
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void associateListeners(SimulationController controller)
	{
		Class<? extends SimulationController> controllerClass;
		Method startPauseMethod;
		
		controllerClass = myController.getClass();
		
		startPauseMethod = null;
		
		try
		{
			startPauseMethod = controllerClass.getMethod("startPause", (Class<?>[])null);
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
		return myNumCashiers.getSelectedIndex();
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
	
	public void setOverflowText(int text)
	{
		for(int i = 0; i < (getComboBoxNumber() + 1); i++)
		{
			myOverflow[i].setText("+" + text);
		}
	}
	
	public void setNumServedText(int text)
	{
		for(int i = 0; i < (getComboBoxNumber() + 1); i++)
		{
			myOverflow[i].setText("" + text);
		}
	}
	
	public void setCustomersInLine(int queue, int num)
	{
		
	}
	
	public void disable()
	{
		myGenerationTime.setEnabled(false);
		myServiceTime.setEnabled(false);
		myNumCustomers.setEnabled(false);
		myNumCashiers.setEnabled(false);
	}
}
