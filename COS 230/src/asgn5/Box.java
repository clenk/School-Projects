package asgn5;

//Christopher Lenk for COS 230
//Represents a box to pack books in

public class Box {
	private int size; //Total number of pounds that can fit in the box
	private int spaceLeft; //Number of ounces that can still be put in the box
	private Book[] books;
	private int last; //index of last book in the array
	
	//Constructor
	public Box(int size) {
		this.size = size;
		spaceLeft = size*16; //Multiply by 16 because there are 16 ounces in a pound
		books = new Book[size*16];
		last = -1;
	}

	//Getters
	public int getSpaceLeft() {
		return spaceLeft;
	}
	public int getSize() {
		return size;
	}
	
	//Sticks a book in the box; returns true if completed successfully
	public void add(Book bk) {
		last++;
		books[last] = bk;
		spaceLeft -= bk.getWeight();
	}
	
	//Prints a list of the books in this box,
	//followed by the total weight of the books and the amount of weight left over
	public void print() {
		for (int i = 0; books[i] != null && i < books.length; i++) {
			System.out.print(books[i].getRef()+" ");
			System.out.print(books[i].getTitle()+" ");
			System.out.print(books[i].getWeight()+"\n");
		}
		System.out.println("--------------------");
		System.out.println("Box Total: " + getWeight());
		System.out.println("Unused Weight: " + spaceLeft);
	}
	
	//Returns the total weight of all the books in this box
	public int getWeight() {
		int total = 0;
		for (int i = 0; books[i] != null && i < books.length; i++) {
			total += books[i].getWeight();
		}
		return total;
	}
}