package queues;

import java.util.LinkedList;

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
}
