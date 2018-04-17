package queues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class SimulationView
{
	private final String myBackgroundImage = "images/background.jpg";
	
	private JFrame myFrame = new JFrame("Queues");
	private Font myFont = new Font("Font", Font.BOLD, 15);
	private SimulationController myController;
	private JButton myStartPause;
	private Container myContentPane;
	private JPanel mySimulationPanel;
	private JTextArea myStats1;
	private JTextArea myStats2;
	private JTextArea myEditable1;
	private JTextArea myEditable2;
	private JTextArea myEditable3;
	private JTextArea myEditable4;
	private JTextField myHeading1;
	private JTextField myHeading2;
	private JTextField myHeading3;
	private JTextField myHeading4;
	private JTextField myHeading5;
	private JTextField myHeading6;
	private JPanel myStatsPanel;
	
	public SimulationView(SimulationController controller)
	{
		myController = controller;
		
		myFrame.setSize(1000, 1000);
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
		
		myHeading3 = new JTextField("Customer Generation Time: ");
		myHeading3.setLayout(null);
		myHeading3.setSize(150, 25);
		myHeading3.setBackground(new Color(225, 225, 225));
		myHeading3.setBorder(BorderFactory.createEmptyBorder());
		myHeading3.setEditable(false);
		myHeading3.setLocation(8, 675);
		myHeading3.setFont(myFont);
		myStatsPanel.add(myHeading3);
		
		myHeading4 = new JTextField("# of Customers: ");
		myHeading4.setLayout(null);
		myHeading4.setSize(150, 25);
		myHeading4.setBackground(new Color(225, 225, 225));
		myHeading4.setBorder(BorderFactory.createEmptyBorder());
		myHeading4.setEditable(false);
		myHeading4.setLocation(8, 700);
		myHeading4.setFont(myFont);
		myStatsPanel.add(myHeading4);
		
		myHeading5 = new JTextField("# of Cashiers: ");
		myHeading5.setLayout(null);
		myHeading5.setSize(150, 25);
		myHeading5.setBackground(new Color(225, 225, 225));
		myHeading5.setBorder(BorderFactory.createEmptyBorder());
		myHeading5.setEditable(false);
		myHeading5.setLocation(8, 725);
		myHeading5.setFont(myFont);
		myStatsPanel.add(myHeading5);
		
		myHeading6 = new JTextField("Maximum Service Time: ");
		myHeading6.setLayout(null);
		myHeading6.setSize(150, 25);
		myHeading6.setBackground(new Color(225, 225, 225));
		myHeading6.setBorder(BorderFactory.createEmptyBorder());
		myHeading6.setEditable(false);
		myHeading6.setLocation(8, 750);
		myHeading6.setFont(myFont);
		myStatsPanel.add(myHeading6);
		
		
		mySimulationPanel.add(myStatsPanel);
		
		myContentPane.add(mySimulationPanel, BorderLayout.CENTER);
		
		
		
		
		
		myStartPause = new JButton("Start");
		
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
}
