package queues;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/**
 * 
	wait time is the time until a customer is served, set time being serviced. when the customer gets to the cashier, call that function that ends the wait
	time, as soon as the cashier calls serves customer, should call customer.setTimeServed (the difference between when they started and served is wait time
	dequeue customer in serve customer. serviceTime is how long a customer will take to be served, comes from UniformCashier.
 * 
 * 
 * serveCustomer:
 * dequeue, update customer's myWaitTime (myEntryTime - PresentTime), generate ServiceTime and give to customer, sleep ServiceTime, return service time
 * 
 * 
 * @author Charles Mercenit
 *
 */
public class Customer
{
	private final Image REGULAR_PERSON = Toolkit.getDefaultToolkit().getImage("images/RegularMan(Transparent).png");
	private final ImageIcon SCALED_REGULAR_PERSON = new ImageIcon(REGULAR_PERSON.getScaledInstance(53, 105, Image.SCALE_SMOOTH)); //50 x 100
	private final Image BOW_TIE_PERSON = Toolkit.getDefaultToolkit().getImage("images/BowTie(Transparent).png");
	private final ImageIcon SCALED_BOW_TIE_PERSON = new ImageIcon(BOW_TIE_PERSON.getScaledInstance(60, 115, Image.SCALE_SMOOTH)); //40 x 95
	private final Image PONY_TAIL_PERSON = Toolkit.getDefaultToolkit().getImage("images/PonyTail(Transparent).png");
	private final ImageIcon SCALED_PONY_TAIL_PERSON = new ImageIcon(PONY_TAIL_PERSON.getScaledInstance(62, 110, Image.SCALE_SMOOTH)); //65 x 108
	private final Image TOP_HAT_PERSON = Toolkit.getDefaultToolkit().getImage("images/TopHat(Transparent).png");
	private final ImageIcon SCALED_TOP_HAT_PERSON = new ImageIcon(TOP_HAT_PERSON.getScaledInstance(53, 118, Image.SCALE_SMOOTH)); //53, 128
	private final Image MANAGER_MOM = Toolkit.getDefaultToolkit().getImage("images/ManagerMom(Transparent).png");
	private final ImageIcon SCALED_MANAGER_MOM = new ImageIcon(MANAGER_MOM.getScaledInstance(60, 110, Image.SCALE_SMOOTH)); //
	private ImageIcon[] images = new ImageIcon[5];
	
	private long myServiceTime;
	private long myEntryTime;
	private long myWaitTime;
	private ImageIcon myImage;
	private Dimension mySize;
	private Point myLocation;
	private boolean myIsMom;
	
	public Customer()
	{
		myEntryTime = System.currentTimeMillis();
		myImage = setCustomerImage();
		mySize = getSize();
		myServiceTime = 0;
		myWaitTime = 0;
		
		if(myImage.equals(SCALED_MANAGER_MOM))
		{
			myIsMom = true;
		}
		else
		{
			myIsMom = false;
		}
	}
	
	public long getWaitTime()
	{
		return myWaitTime;
	}
	
	public long getEntryTime()
	{
		return myEntryTime;
	}
	
	public long getServiceTime()
	{
		return myServiceTime;
	}
	
	public void addToWaitTime(long num)
	{
		myWaitTime += num;
	}
	
	public void addToServiceTime(long num)
	{
		myServiceTime += num;
	}
	
	public String toString()
	{
		String customer = "Customer:" + "\n";
		customer += "Entry Time: " + myEntryTime + "\n";
		customer += "Wait Time: " + myWaitTime + "\n";
		customer += "Service Time: " + myServiceTime + "\n";
		customer += "Image: " + myImage.getIconHeight() + "\n";
		customer += "Is Mom: " + getIsMom();
		
		return customer;
	}
	
	public ImageIcon setCustomerImage()
	{
		images[0] = SCALED_REGULAR_PERSON;
		images[1] = SCALED_PONY_TAIL_PERSON;
		images[2] = SCALED_BOW_TIE_PERSON;
		images[3] = SCALED_TOP_HAT_PERSON;
		images[4] = SCALED_MANAGER_MOM;
		
		
		if((int)(Math.random()*15) == 10)
		{
//			setSize(images[4]);
			
//			return images[4];
			
			int random = (int)(Math.random()*5);
			setSize(images[random]);
			
			return images[random];
		}
		else
		{
			int random = (int)(Math.random()*4);
			setSize(images[random]);
			
			return images[random];
		}		
	}
	
	public Dimension getSize()
	{
		return mySize;
	}
	
	public void setSize(ImageIcon image)
	{
		if(image.equals(SCALED_PONY_TAIL_PERSON))
		{
			mySize = new Dimension(75, 100);
		}
		else if(image.equals(SCALED_BOW_TIE_PERSON) || image.equals(SCALED_REGULAR_PERSON))
		{
			mySize = new Dimension(50, 100);
		}
		else
		{
			mySize = new Dimension(50, 125);
		}
	}
	
	public Point getLocation()
	{
		return myLocation;
	}
	
	public ImageIcon getIcon()
	{
		return myImage;
	}
	
	public boolean getIsMom()
	{
		return myIsMom;
	}
}
