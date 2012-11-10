package asgn3;

public class Node<E> {
	private E data; //Value of the node
	private int count; //
	private Node<E> next; //Pointer to next node
	
	//Getters
	public E getData() {
		return data;
	}
	public int getCount() {
		return count;
	}
	public Node<E> getNext() {
		return next;
	}

	//Setters
	public void setData(E data) {
		this.data = data;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setNext(Node<E> next) {
		this.next = next;
	}

	//Constructor
	Node(E data, Node<E> next) {
		this.data = data;
		this.next = next;
		this.count = 1;
	}
}