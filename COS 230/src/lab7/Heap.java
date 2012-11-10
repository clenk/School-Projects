package lab7;

/*
 * Author: John Hunt
 * This class is a binary heap implemented as a tree
 * it provides a method to build the tree, print the 
 * tree and to remove the last child from the tree.
 * The primary operations of remove and insert are left
 * as exercises
 */

// Modified by Christopher Lenk for COS 230

public class Heap {

	class Node {
		int num;
		Node left;
		Node right;
		
		Node(int num, Node left, Node right){
			this.num = num;
			this.left = left;
			this.right = right;
		}
		
		public String toString(){
			return num + "";
		}
	}
	
	Node root;
	int nodeCnt;
	
	Heap(){
		root = null;
		nodeCnt = 0;
	}
	
	/*
	 * Builds a complete binary tree from the array
	 * passed in.  This method does not attempt to
	 * order the nodes on value.  For the resulting tree
	 * to have the quality of heapness the array must 
	 * be in a correct order.
	 */
	void build(int nums[]){
		Node nodes[] = new Node[nums.length];
		for(int i = 0; i < nums.length; i++){
			nodes[i] = new Node(nums[i], null, null);
		}
		root = nodes[0];
		for(int i=0; i < nums.length/2; i++){
			nodes[i].left = nodes[2*i+1];
			nodes[i].right = nodes[2*i+2];
		}
		nodeCnt = nums.length;
	}
	
	void print(Node c, String indent){
		if(c != null) {
			System.out.println(indent + c.num);
			print(c.left, indent + "    ");
			print(c.right, indent + "    ");
		}
	}
	
	/* 
	 * Performs a pre-order traversal of the tree
	 * printing the data value
	 */
	void printHeap() {
		print(root, "");
	}
	
	/*
	 * Returns the number of nodes in the tree
	 */
	int size() {
		return nodeCnt;
	}
	
	/*
	 * Removes the last child in the tree 
	 * returning the last child's value
	 * The nodeCnt is decremented
	 */
	int removeLastChild() {
		int cnt = nodeCnt;
		String path = "";
		while(cnt >= 1) {
			path = (cnt %2) + path;
			cnt = cnt / 2;
		}

		int value = -1;
		Node c = root;
		for(int i = 1; i < path.length()-1; i++){
			if(path.charAt(i)== '0') {
				c = c.left;
			} else {
				c = c.right;
			}
		}
		if(path.length() == 1) {
			value = root.num;
			root = null;
		} else if(path.charAt(path.length()-1)== '0') {
			value = c.left.num;
			c.left = null;
		} else {
			value = c.right.num;
			c.right = null;
		}
		nodeCnt--;
		return value;
	}
	
	/*
	 * Removes the root node and rearranges the heap to still have
	 * heapness and completeness
	 */
	int remove() {
		// Save the value from the root
		int value = root.num;
		
		// Find the last child, save its value, and remove it from the tree
		int last = removeLastChild();
		
		// Bubble the value from the last child to the right place in the tree
		// ie. "trickle-down"
		if (root != null) {
			root.num = last;
			Node lrgChild;
			Node c = root;
			while (c.left != null && c.right != null) {
				// Pick the largest child
				if (c.left.num < c.right.num) {
					lrgChild = c.right;
				} else {
					lrgChild = c.left;
				}
				
				// Check if the root is now the largest child
				if (last >= lrgChild.num) {
					break;
				}
				
				// Move the larger child up
				c.num = lrgChild.num;
				// Go down a row in the heap
				c = lrgChild;
			}
			c.num = last;
		}
		
		// Return the value saved from the root
		return value;
	}
	
	public static void main(String[] args) {
		Heap h = new Heap();
		int numbers[] = {50, 30, 20, 25, 15, 7, 18, 22, 13, 14, 9, 2,3, 1,17};
		h.build(numbers);
		h.printHeap();

		// put your code to test remove here
		while(h.size() > 0) {
		    System.out.println(h.remove());
		}
	}

}