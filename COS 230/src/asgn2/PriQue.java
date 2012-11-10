package asgn2;

/*
 * Author John M. Hunt
 * PriQue is the interface for a priority queue implementation
 * New entries are inserted in order of the pri parameter of insert
 * Java generics are used to support different types of data elements
 */
public interface PriQue<E> {
	void insert(int pri, E data);
	E remove(); // return null when empty
	boolean isEmpty();
}