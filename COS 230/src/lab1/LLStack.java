package lab1;

import java.util.EmptyStackException;

//Christopher Lenk for COS 230
//Unbounded implementation of a stack using a linked list to hold elements

public class LLStack<E> implements Stack<E> {

	private class Node {
		private E data;
		private Node next;
		
		//Constructor
		public Node(E e, Node next) {
			super();
			this.data = e;
			this.next = next;
		}
	}
	
	private Node head; //Points to the top entry in the stack
	
	//Constructor
	public LLStack() {
		head = null;
	}

	//Puts something on the stack
	@Override
	public void push(E e) {
		head = new Node(e, head);
	}

	//Takes off the item on the top of the stack
	@Override
	public E pop() {
		if (isEmpty()) { //Make sure the stack isn't empty
			throw new EmptyStackException();
		} else {
			Node temp = head;
			head = head.next;
			return temp.data;
		}
	}

	//Checks whether the stack is empty
	@Override
	public boolean isEmpty() {
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}

}
