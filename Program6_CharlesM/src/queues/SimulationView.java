package queues;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * 
 * TODO:
 * Fix the JComboBox
 * 
 * 
 * 
 * @author Charles Mercenit
 *
 */

public class SimulationView
{
	private final String myBackgroundImage = "images/background.jpg";
	private final int MAX_PEOPLE_IN_LINE = 30;
	
	private JFrame myFrame = new JFrame("Queues");
	private Font myFont = new Font("Font", Font.BOLD, 15);
	private Font myParamFont = new Font("Param Font", Font.BOLD, 20);
	private SimulationController myController;
	private JButton myStartPause;
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
	private JComboBox<Integer> myNumCashiers;
	private JLabel[][] myCustomer;
	private JLabel[] myCashier;
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
		
		JLabel myBackground = new JLabel(new ImageIcon(myBackgroundImage));
		myBackground.setSize(700, 1000);
		myBackground.setLocation(0, 0);
		mySimulationPanel.add(myBackground);
		
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
	//	myStats1.setBackground(new Color(184, 184, 184));
		myStats1.setBackground(Color.WHITE);
		myStats1.setLocation(8, 50);
		myStats1.setEditable(false);
		myStatsPanel.add(myStats1);
		
		myStats2 = new JTextArea();
		myStats2.setLayout(null);
		myStats2.setSize(275, 275);
		myStats2.setLineWrap(true);
		myStats2.setBorder(BorderFactory.createLoweredBevelBorder());
	//	myStats2.setBackground(new Color(184, 184, 184));
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
		
//FIX THIS		
		Integer[] ints = new Integer[5];
		ints[0] = 1;
		ints[1] = 2;
		ints[2] = 3;
		ints[3] = 4;
		ints[4] = 5;
		myNumCashiers = new JComboBox<Integer>(ints);
		myNumCashiers.setLayout(null);
		myNumCashiers.setSize(75, 25);
		myNumCashiers.setBackground(Color.WHITE);
		myNumCashiers.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		myNumCashiers.setEditable(true);
		myNumCashiers.setLocation(210, 745);
		myStatsPanel.add(myNumCashiers);
//FIX THIS		
		
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
		

/*
		myCashier = new JLabel[5];
		for(int i = 0; i < myCashier.length; i++)
		{
//			myCashier[i] = new JLabel(new ImageIcon(CASHIER_CLOSED));
//			myCashier[i].setSize(CASHIER_WIDTH, CASHIER_HEIGHT);
//			myCashier[i].setLocation(100 + (CUSTOMER_WIDTH*i), 750);
			myCashier[i].setVisible(true);
			mySimulationPanel.add(myCashier[i]);
		}
		
		myCustomer = new JLabel[5][MAX_PEOPLE_IN_LINE];
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < MAX_PEOPLE_IN_LINE; j++)
			{
				myCustomer[i][j] = new JLabel();
//				myCustomer[i][j].setSize(CUSTOMER_WIDTH, CUSTOMER_HEIGHT);
//				myCustomer[i][j].setLocation(150 + (CUSTOMER_WIDTH*i), 500 - (50*j));
				myCustomer[i][j].setVisible(true);
				mySimulationPanel.add(myCustomer[i][j]);
			}
		}
*/

		
		mySimulationPanel.add(myStatsPanel);
		
		myContentPane.add(mySimulationPanel, BorderLayout.CENTER);
		
		
		
		
		
		
		
		this.associateListeners(myController);
		
		
		
		
		
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void associateListeners(SimulationController controller)
	{
		
	}
	
	public void setOverallStatsText(String text)
	{
		
	}
	
	public void setCashierStatsText(String text)
	{
		
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
	
}
