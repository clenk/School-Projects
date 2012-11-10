package asgn1;

//Christopher Lenk for COS 230

public class LinkedList {

	private class Node {
		private long begin;
		private long end;
		private Node next;
		
		//Constructor
		public Node(long begin, long end, Node next) {
			super();
			this.begin = begin;
			this.end = end;
			this.next = next;
		}
	}
	
	private Node head; //Pointer to first node in the list
	
	//Constructor
	public LinkedList() {
		head = null;
	}
	
	//Adds a node to the end of the list
	public void add(long begin, long end) {
		//Handle case of empty list
		if (head == null) {
			head = new Node(begin, end, null);
			return;
		}
		//Check if first item is also last item
		if (head.next == null) {
			head.next = new Node(begin, end, null);
			return;
		}
		//Get to the end of the list
		Node cur = head;
		while (cur.next != null) {
			cur = cur.next;
		}
		
		//Create the new node
		cur.next = new Node(begin, end, null);
	}

	//Prints the contents of the linked list
	public void print() {
		for (Node cur = head; cur != null; cur = cur.next) {
			//Check if current node is a range or a single number
			if (cur.begin == cur.end) {
				System.out.print(cur.begin);
			} else {
				System.out.print(cur.begin + "-" + cur.end);
			}
			
			//Check if the node is the last in the list or not
			if (cur.next != null) {
				System.out.print(", ");
			}
		}
		System.out.println("");
	}
	
	//Sorts the linked list using a Bubble Sort
	public void sort() {
		long tempBegin;
		long tempEnd;
		
		//Go through the list multiple times to make sure it's sorted
		boolean changed = true;
		while (changed) {
			changed = false;
			
			for (Node cur = head; cur.next != null; cur = cur.next) {
				if (cur.begin > cur.next.begin) {					
					tempBegin = cur.begin;
					tempEnd = cur.end;
					cur.begin = cur.next.begin;
					cur.end = cur.next.end;
					cur.next.begin = tempBegin;
					cur.next.end = tempEnd;
					
					changed = true;
				}
			}
		}
	}
	
	//Collapses nodes if they include the same range of numbers
	//Only use after list has been sorted
	public void collapse() {
		boolean changed = true;
		
		//Go through the list multiple times to make sure it's correct
		while (changed) {
			changed = false;
			
			for (Node p = head; p != null && p.next != null; p = p.next) {				
				if (p.end +1 >= p.next.begin) {
					if (p.end <= p.next.end) {
						p.end = p.next.end; //Copy the value over
					}
					p.next = p.next.next; //Delete the node
					changed = true;
				}
			}
		}
	}
}
