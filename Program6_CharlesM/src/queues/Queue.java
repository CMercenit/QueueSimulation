package queues;

import java.util.LinkedList;
import java.util.Vector;

public class Queue
{
	private LinkedList<Customer> myLinkedList;
	
	public Queue()
	{
		myLinkedList = new LinkedList<Customer>();
	}
	
	public void enqueue(Customer customer)
	{
		myLinkedList.addFirst(customer);
	}
	
	public Customer dequeue()
	{
		return myLinkedList.removeLast();
	}
	
	public boolean isEmpty()
	{
		return myLinkedList.isEmpty();
	}
	
	public Customer peek()
	{
		return myLinkedList.getLast();
	}
	
	public int size()
	{
		return myLinkedList.size();
	}
	
	public Customer get(int num)
	{
		return myLinkedList.get(num);
	}
	
//	public Vector<Customer> getCustomers()
//	{
//		Vector<Customer> customers = new Vector<Customer>();
//		for(Customer c : myLinkedList)
//		{
//			customers.add(c);
//		}
//		
//		return customers;
//	}
}
