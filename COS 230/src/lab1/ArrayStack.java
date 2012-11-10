package lab1;

import java.util.EmptyStackException;

//Christopher Lenk for COS 230
//Bounded implementation of a stack using an array to hold elements

public class ArrayStack<E> implements Stack<E> {
	
	private E[] arr; //Array holding the data in the stack
	private int top; //Points to the index in the array of the top of the stack
	
	//Constructor
	@SuppressWarnings("unchecked")
	public ArrayStack(int size) {
		arr = (E[])new Object[size];
		top = 0;
	}

	//Puts something on the stack
	@Override
	public void push(E e) {
		if (top >= arr.length) { //Make sure the stack isn't full
			throw new StackFullException();
		} else {
			arr[top] = e;
			top++;
		}
	}

	//Takes off the item on the top of the stack
	@Override
	public E pop() {
		if (isEmpty()) { //Make sure the stack isn't empty
			throw new EmptyStackException();
		} else {
			top--;
			return arr[top];
		}
	}

	//Checks whether the stack is empty
	@Override
	public boolean isEmpty() {
		if (top == 0) {
			return true;
		} else {
			return false;
		}
	}

}
