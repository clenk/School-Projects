package asgn5;

//By Christopher Lenk for COS 230
//Used as nodes in the heap

public class Book {
	private int ref;
	private String title;
	private int weight;
	//private Book next; //Only used in the splay tree, when the book is placed in a box
	
	//Constructor, Node
	Book(int ref, String title, int wt) {
		this.ref = ref;
		this.title = title;
		this.weight = wt;
	}
	
	//Getters
	public int getRef() {
		return ref;
	}
	public String getTitle() {
		return title;
	}
	public int getWeight() {
		return weight;
	}
}
