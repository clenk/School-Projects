package lab5;

//Christopher Lenk for COS 230
//An array implementation of a binary search tree

public class BSTreeArray {
	
	//Starting values
	static int[] values = { 50, 25, 75, 10, 15, 5, 53, 29, 79, 78, 111, 33};
	//Array to hold the tree
	private Integer[] tree;
	
	//Constructor
	public BSTreeArray() {
		tree = new Integer[100];
		for(int i = 0; i < values.length; i++){
			insert(values[i]);
		}
	}

	//Returns index of left child of the given index
	public int left(int index) {
		return 2 * index + 1;
	}
	
	//Returns index of right child of the given index
	public int right(int index) {
		return 2 * index + 2;
	}
	
	//Returns index of parent of the given index
	public int parent(int index) {
		return (index -1) / 2;
	}
	
	//Adds the value of the key to the tree
	//Assumes no duplicate values exist
	public void insert(int key) {
		//Handle case of the first value
		if (tree[0] == null) {
			tree[0] = key;
		//Handle the other cases
		} else {
			int cur = 0;
			while (true) {
				if (tree[cur] > key) { //Go left
					if (tree[left(cur)] == null) {
						tree[left(cur)] = key;
						break;
					} else {
						cur = left(cur);
					}
				} else { //Go right
					if (tree[right(cur)] == null) {
						tree[right(cur)] = key;
						break;
					} else {
						cur = right(cur);
					}
				}
			}
		}
	}
	
	//Prints the tree using pre-order traversal
	public void preorder(int index, String dots) {
		if (tree[index] != null) {
			System.out.println(	dots+tree[index]);
			preorder(left(index), dots+"...");
			preorder(right(index), dots+"...");
		}
	}
  
	//MAIN
	public static void main(String[] args) {
		BSTreeArray bst = new BSTreeArray();
		
		//Print out the contents of the tree using a preorder traversal
		bst.preorder(0, "");
	}

}