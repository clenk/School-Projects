package asgn3;

//Christopher Lenk for COS 230
//A Linked List implementation specifically for use in a Hash Table using open chaining

public class LinkedList<E> {
	
	private Node<E> head; //Pointer to first node in the list
	private int colisions; //Number of collisions encountered when adding stuff to this list
	
	//Getters and Setters
	public Node<E> getHead() {
		return head;
	}
	public void setHead(Node<E> head) {
		this.head = head;
	}
	public int getCols() {
		return colisions;
	}

	//Constructor
	public LinkedList() {
		head = null;
		colisions = 0;
	}
	
	//Prints the contents of the linked list
	public void print() {
		//if (!isEmpty()) System.out.print("\n");
		for (Node<E> cur = head; cur != null; cur = cur.getNext()) {
			System.out.println("\""+cur.getData()+"\" occurs " + cur.getCount() + " times.");
		}
	}
	
	//Adds a node to the end of the list
	public void add(E data) {
		//Handle case of empty list
		if (head == null) {
			head = new Node<E>(data, null);
			return;
		//Otherwise, handle the collision
		} else {
			//Handle case of it already being in the list
			for (Node<E> cur = head; cur != null; cur = cur.getNext()) {
				if (cur.getData().equals(data)) {
					cur.setCount(cur.getCount()+1);
					return;
				}
			}
			//Otherwise add it to the list:
			colisions++;
			//Handle case of only one node in the list
			if (head.getNext() == null) {
				head.setNext(new Node<E>(data, null));
				return;
			}
			//Get to the end of the list
			Node<E> cur = head;
			while (cur.getNext() != null) {
				cur = cur.getNext();
			}
			
			//Create the new node
			cur.setNext(new Node<E>(data, null));
		}
	}
	
	//Determine if the list is empty or not
	public boolean isEmpty() {
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}
	
	//Find the number of nodes in the list
	public int length() {
		int cnt = 0;
		for (Node<E> cur = head; cur != null; cur = cur.getNext()) {
			cnt++;
		}
		return cnt;
	}
}
