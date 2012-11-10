package asgn3;

public interface HashInterface<E> {
	int hash(E data); //Generates a hash key
	void insert(E data); //Add something to the hash table
}
