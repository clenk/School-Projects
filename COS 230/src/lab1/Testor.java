package lab1;

/*
 * Testor
 * Author: John M. Hunt
 * Tests the stack interface and implementations for the 
 * ADT stack lab
 */
import java.util.EmptyStackException;

public class Testor {

	public static void main(String[] args) {
		// create and test an ArrayStack
		Stack<Integer> s = new ArrayStack<Integer>(4);
		for (int i = 0; i < 4; i++) {
			s.push(i);
		}
		try {
			s.push(99);
		} catch (StackFullException e) {
			System.out.println("Stack full");
		}
		for (int i = 0; i < 4; i++) {
			int n = s.pop();
			System.out.println(n);
		}
		try {
			s.pop();
		} catch (EmptyStackException e) {
			System.out.println("Stack empty");
		}
		
		// create and test a linked list stack
		Stack<Integer> s1 = new LLStack<Integer>();
		for (int i = 0; i < 4; i++) {
			s1.push(i);
		}
		for (int i = 0; i < 4; i++) {
			int n = s1.pop();
			System.out.println(n);
		}
		try {
			s.pop();
		} catch (EmptyStackException e) {
			System.out.println("Stack empty");
		}
	}

}
