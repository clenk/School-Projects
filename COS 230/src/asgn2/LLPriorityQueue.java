package asgn2;

//Christopher Lenk for COS 230
//An implementation of a priority queue using a linked list

public class LLPriorityQueue<E> implements PriQue<E> {

	//Embedded class for the nodes of the linked list
	private class Node {
		private E data;
		private Node next;
		private int priority;
		
		//Constructor
		Node(E data, Node next, int priority) {
			this.data = data;
			this.next = next;
			this.priority = priority;
		}
	}
	
	//Pointers for the linked list
	private Node head;
	private Node tail;
	
	//Constructor
	public LLPriorityQueue() {
		head = null;
		tail = null;
	}
	
	//Add to the queue at the given priority
	@Override
	public void insert(int pri, E data) {
		
		if (head == null) { //If it's the first thing to be put in the queue...
			head = new Node(data, null, pri);
			tail = head;
		} else { //Otherwise...
			for (Node n = head; n != null; n = n.next) { //Look through the list
				
				if (n.next == null) { //Gotten to the end of the list
					if (n.priority < pri) {
						n.next = new Node(data, null, pri);
						tail = n.next;
						return;
					} else {
						n = new Node(data, n, pri);
						setHead(n);
						return;
					}
				} else if (n.priority < pri && n.next.priority >= pri) {
					n.next = new Node(data, n.next, pri);
					return;
				} else if (n.priority >= pri) {
					n = new Node(data, n, pri);
					setHead(n);
					return;
				}
				
			}
		}
	}
	
	//Use to make sure the head is getting set after inserting new nodes, if necessary
	private void setHead(Node nd) {
		if (head == nd.next) {
			head = nd;
		}
	}

	//Pop out whatever's at the head of the queue
	@Override
	public E remove() {
		//Hold onto the data so it can still be returned after deletion from the list
		E tmp = head.data;
		
		if (head.next != null) {
			head = head.next;
		} else {
			head = null;
			tail = null;
		}		
		
		return tmp;
	}

	//Check whether the queue is empty or not
	@Override
	public boolean isEmpty() {
		if (head == tail) {
			return true;
		} else {
			return false;
		}
	}
	
	//Prints out the contents of the list, useful when testing
	//Pass in null to print the whole list
	public void print() {
		if (isEmpty()) {
			System.out.println("List is empty.");
		} else {
			Node n = head;
			while (n != null) {
				System.out.println(n.data);
				n = n.next;
			}
		}
	}
	
	//An earlier version of the print method using recursion
	public void print2(Node n) {
		if (isEmpty()) {
			System.out.println("List is empty.");
		} else if (n != null) {
			System.out.println(n.data);
			if (n.next != null) {
				print2(n.next);
			}
		} else if (head != null) {
			print2(head);
		}
	}

}
