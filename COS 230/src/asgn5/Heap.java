package asgn5;

//By Christopher Lenk for COS 230,
//Based on code in Lafore

public class Heap {

	private Book[] arr; //Array to hold the heap
	private int max; //How many nodes the array can hold
	private int curSz; //How many nodes the array currently holds
	
	//Constructor
	public Heap(int size) {
		max = size;
		curSz = 0;
		arr = new Book[size];
	}
	
	//True if no nodes in heap, otherwise false
	public boolean isEmpty() {
		if (curSz == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//Adds a value to the heap (returns true if successful)
	public boolean insert(int ref, String title, int wt) {
		if (curSz == max) {
			return false;
		} else {
			Book n = new Book(ref, title, wt);
			arr[curSz] = n;
			trickleUp(curSz);
			curSz++;
			return true;
		}
	}

	//Moves the node at the given index up the heap
	//until the heap fulfills the condition of heapness
	private void trickleUp(int index) {
		int parent = (index - 1) / 2;
		Book bottom = arr[index];
		
		while (index > 0 && arr[parent].getWeight() < bottom.getWeight()) {
			arr[index] = arr[parent]; //Move the parent down
			index = parent; //Parent becomes the one we're looking at
			parent = (parent - 1) / 2; //Find the new parent
		}
		
		arr[index] = bottom; //Assign the node to its rightful place in the heap
	}
	
	//Takes off whatever's at the top of the heap
	public Book remove() {
		Book root = arr[0];
		curSz--;
		arr[0] = arr[curSz];
		trickleDown(0);
		return root;
	}

	//Brings the Node with the largest value to the top of the heap after a remove 
	private void trickleDown(int index) {
		int lrg; //Index of the larger child
		Book top = arr[index]; //Root
		
		while (index < (curSz / 2)) { //While the node has at least 1 child
			int left = 2 * index + 1; //Left child
			int right = left + 1; //Right child
			
			//Find the larger child
			if (arr[left].getWeight() < arr[right].getWeight() && right < curSz) {
				lrg = right;
			} else {
				lrg = left;
			}
			
			//Check if root is already the largest 
			if (top.getWeight() >= arr[lrg].getWeight()) {
				break;
			}
			
			arr[index] = arr[lrg]; //Move the larger child up
			index = lrg; //Go down a level in the heap
		}
		
		arr[index] = top; //Move the old root to its new place in the heap
	}
	
	//Prints out what the heap looks like (taken from the book)
	public void print() {
		int nBlanks = 32;
		int itemsPerRow = 1;
		int col = 0;
		int j = 0;
		String dots = "................................";
		System.out.println(dots+dots); //top line
		while (curSz > 0) {
			if (col == 0) {
				for (int k=0; k<nBlanks; k++) {
					System.out.print(" ");
				}
			}
			System.out.print(arr[j].getWeight());
			if (++j == curSz) { //done
				break;
			}
			if (++col==itemsPerRow) {
				nBlanks /= 2;
				itemsPerRow *=2;
				col = 0;
				System.out.println(); //new row
			} else {
				for (int k=0; k<nBlanks*2-2; k++) {
					System.out.print(" ");
				}
			}
		}
		System.out.println("\n"+dots+dots);
	}
}