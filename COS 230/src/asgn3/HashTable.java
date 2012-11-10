package asgn3;

public class HashTable<E> implements HashInterface<E> {

	private LinkedList<E> array[];	

	//Getters and Setters
	public LinkedList<E>[] getArray() {
		return array;
	}
	public void setArray(LinkedList<E>[] array) {
		this.array = array;
	}
	
	//Constructor
	@SuppressWarnings("unchecked")
	public HashTable(int size) {
		array = new LinkedList[size];
		for (int i = 0; i < array.length ; i++) {
			array[i] = new LinkedList<E>();
		}
	}
	
	//Generates a hash for the given string
	public int hash(E data) {
		String str = (String)data;
		int hashVal = 1;
		
		for (int i = 0; i < str.length(); i++) {
			int letter = str.charAt(i);
			hashVal = (hashVal * 27 + letter) % array.length;
		}
		
		return hashVal;
	}

	//Stick a piece of data into the hash table
	@Override
	public void insert(E data) {
		int h = hash(data); //Get the hash for this data

		array[h].add(data);
	}
	
	//Prints out the contents of the hash table
	public void print() {
		for (int i = 0; i < array.length ; i++) {
			array[i].print();
		}
		System.out.println();
	}
	
	//Gives the total number of entries in the hash table
	public int totEntries() {
		int total = 0;
		for (int i = 0; i < array.length ; i++) {
			total += array[i].length();
		}
		return total;
	}
	
	//Gives the total number of collisions in the hash table
	public int totCols() {
		int total = 0;
		for (int i = 0; i < array.length ; i++) {
			total += array[i].getCols();
		}
		return total;
	}
	
	//Gives the average length of all the linked lists in the hash table
	public int aveLength() {
		int totLength = 0;
		int count = 0;
		
		for (int i = 0; i < array.length ; i++) {
			int cur = array[i].length();
			totLength += cur;
			if (cur != 0) { //Don't count empty lists in the count
				count++;
			}
		}
		return totLength / count;
	}
	
	//Gives the maximum length of all the linked lists in the hash table
	public int maxLength() {
		int max = 0;

		for (int i = 0; i < array.length ; i++) {
			int cur = array[i].length();
			if (cur > max) {
				max = cur;
			}
		}
		return max;
	}
}
