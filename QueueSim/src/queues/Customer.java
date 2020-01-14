package queues;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

import qsim.ResourceLoader;

/**
 * Customer class creates a customer with an entry time and
 * a unique, random image (of the images provided). If the
 * image is MANAGER_MOM, also adds a true boolean that shows
 * that the customer is a mom.
 * 
 * @author Charles Mercenit
 */

public class Customer
{
	private final int MY_NUM_IMAGES = 5;
	
	private final ImageIcon SCALED_REGULAR_PERSON = new ImageIcon(ResourceLoader
			.loadImage("RegularMan(Transparent).png")
			.getScaledInstance(53, 105, Image.SCALE_SMOOTH));
	private final ImageIcon SCALED_BOW_TIE_PERSON = new ImageIcon(ResourceLoader
			.loadImage("BowTie(Transparent).png")
			.getScaledInstance(60, 115, Image.SCALE_SMOOTH));
	private final ImageIcon SCALED_PONY_TAIL_PERSON = new ImageIcon(ResourceLoader
			.loadImage("PonyTail(Transparent).png")
			.getScaledInstance(62, 110, Image.SCALE_SMOOTH));
	private final ImageIcon SCALED_TOP_HAT_PERSON = new ImageIcon(ResourceLoader
			.loadImage("TopHat(Transparent).png")
			.getScaledInstance(53, 118, Image.SCALE_SMOOTH));
	private final ImageIcon SCALED_MANAGER_MOM = new ImageIcon(ResourceLoader
			.loadImage("ManagerMom(Transparent).png")
			.getScaledInstance(60, 110, Image.SCALE_SMOOTH));
	
	private ImageIcon[] images = new ImageIcon[MY_NUM_IMAGES];
	
	private long myServiceTime;
	private long myEntryTime;
	private long myWaitTime;
	private ImageIcon myImage;
	private Dimension mySize;
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
	
	/**
	 * Returns the time a customer spent in line.
	 * 
	 * @return: wait time
	 */
	
	public long getWaitTime()
	{
		return myWaitTime;
	}
	
	/**
	 * Returns the time the customer was generated
	 * (entered the line).
	 * 
	 * @return: entry time
	 */
	
	public long getEntryTime()
	{
		return myEntryTime;
	}
	
	/**
	 * Returns the time a customer took to be
	 * serviced, generated and set in the Cashier
	 * class.
	 * 
	 * @return: service time
	 */
	
	public long getServiceTime()
	{
		return myServiceTime;
	}
	
	/**
	 * Adds the passed in number to the wait time.
	 * 
	 * @param num to add
	 */
	
	public void addToWaitTime(long num)
	{
		myWaitTime += num;
	}
	
	/**
	 * Adds the passed in number to the service time.
	 * 
	 * @param num to add
	 */
	
	public void addToServiceTime(long num)
	{
		myServiceTime += num;
	}
	
	/**
	 * This toString overrides Java's, provides a string of
	 * a customer's entry time, wait time, service time, and
	 * if the customer is a mom or not.
	 * 
	 * @return: String that details the customer
	 */
	
	public String toString()
	{
		String customer = "Customer:" + "\n";
		customer += "Entry Time: " + myEntryTime + "\n";
		customer += "Wait Time: " + myWaitTime + "\n";
		customer += "Service Time: " + myServiceTime + "\n";
		customer += "Is Mom: " + getIsMom();
		
		return customer;
	}
	
	/**
	 * Sets the image for the customer. Rare chance for
	 * the customer to be mom, has a 1 in 15 chance and
	 * then a 1 in 5 chance. Also sets the size of the
	 * image so the View can display it properly.
	 * 
	 * @return: customer ImageIcon
	 */
	
	public ImageIcon setCustomerImage()
	{
		images[0] = SCALED_REGULAR_PERSON;
		images[1] = SCALED_PONY_TAIL_PERSON;
		images[2] = SCALED_BOW_TIE_PERSON;
		images[3] = SCALED_TOP_HAT_PERSON;
		images[4] = SCALED_MANAGER_MOM;
		
		
		if((int)(Math.random()*8) == 3)
		{
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
	
	/**
	 * Returns the size of the customer.
	 * 
	 * @return: size
	 */
	
	public Dimension getSize()
	{
		return mySize;
	}
	
	/**
	 * Sets the size of the customer based on
	 * the passed in image.
	 * 
	 * @param image to set the size of
	 */
	
	public void setSize(ImageIcon image)
	{
		if(image.equals(SCALED_PONY_TAIL_PERSON))
		{
			mySize = new Dimension(62, 110);
		}
		else if(image.equals(SCALED_BOW_TIE_PERSON))
		{
			mySize = new Dimension(60, 115);
		}
		else if(image.equals(SCALED_REGULAR_PERSON))
		{
			mySize = new Dimension(53, 105);
		}
		else if(image.equals(SCALED_TOP_HAT_PERSON))
		{
			mySize = new Dimension(53, 118);
		}
		else
		{
			mySize = new Dimension(60, 110);
		}
	}
	
	/**
	 * Returns the customer's image.
	 * 
	 * @return: customer image
	 */
	
	public ImageIcon getIcon()
	{
		return myImage;
	}
	
	/**
	 * Returns boolean of whether or not
	 * the customer is a mom.
	 * 
	 * @return: is the customer a mom
	 */
	
	public boolean getIsMom()
	{
		return myIsMom;
	}
}