package queues;

import java.util.LinkedList;

/**
 * Uses java.util.LinkedList to implement a Queue
 * wrapper class that shows only what is necessary
 * for the Queue to function.
 * 
 * @author Charles Mercenit
 */

public class Queue
{
	private LinkedList<Customer> myLinkedList;
	
	public Queue()
	{
		myLinkedList = new LinkedList<Customer>();
	}
	
	/**
	 * Adds the passed in customer first in the LinkedList.
	 * 
	 * @param customer
	 */
	
	public void enqueue(Customer customer)
	{
		myLinkedList.addFirst(customer);
	}
	
	/**
	 * Removes the last Customer of the LinkedList and
	 * returns it.
	 * 
	 * @return: removed customer
	 */
	
	public Customer dequeue()
	{
		return myLinkedList.removeLast();
	}
	
	/**
	 * Checks if the LinkedList is empty.
	 * 
	 * @return: true or false if empty or not
	 */
	
	public boolean isEmpty()
	{
		return myLinkedList.isEmpty();
	}
	
	/**
	 * Looks at the customer that will be removed next
	 * without removing them.
	 * 
	 * @return: the last customer in the LinkedList
	 */
	
	public Customer peek()
	{
		return myLinkedList.getLast();
	}
	
	/**
	 * Gets the size of the LinkedList.
	 * 
	 * @return: int size
	 */
	
	public int size()
	{
		return myLinkedList.size();
	}
	
	/**
	 * Gets a customer at the index that is passed
	 * in.
	 * 
	 * @param num to get
	 * @return: customer at num
	 */
	
	public Customer get(int num)
	{
		return myLinkedList.get(num);
	}
}