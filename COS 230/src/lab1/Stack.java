package lab1;

//Christopher Lenk for COS 230
//Interface for Stacks

public interface Stack<E> {
	
	void push (E e); //Puts something on the stack
	
	E pop(); //Takes off the item on the top of the stack
	
	boolean isEmpty(); //Checks whether the stack is empty
}
