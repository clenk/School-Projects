package lab3;

//Christopher Lenk for COS 230
//Implements a queue of Java ints as a linked list

public class Queue {

	private class Node {
		private int data;
		private Node next;
		
		//Constructor
		public Node(int data, Node next) {
			super();
			this.data = data;
			this.next = next;
		}
	}
	
	private Node head; //Points to first node in the queue
	private Node tail; //Points to the last node in the queue
	
	//Constructor
	public Queue() {
		head = null;
		tail = null;
	}
	
	//Adds the given number to the end of the queue
	public void add(int num) {		
		//Case: Empty list
		if (head == null) {
			head = new Node(num, tail);
			tail = head;
			return;
		}
		//Case: First item is also last item
		if (head.next == null) {
			head.next = new Node(num, null);
			tail = head.next;
			return;
		}
		//Normal Case
		tail.next = new Node(num, null);
		tail = tail.next;
	}
	
	//Takes out the number at the head of the queue and returns it
	public int remove() throws QueueEmptyException {
		if (isEmpty()) {
			throw new QueueEmptyException();
		}
		
		int tmp = head.data;
		
		if (head.next != null) {
			head = head.next;
		} else {
			head = null;
			tail = null;
		}		
		
		return tmp;
	}
	
	//Checks whether the queue is empty or not
	public boolean isEmpty() {
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}

}
