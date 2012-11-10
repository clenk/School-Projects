package lab4;

//Modified by Christopher Lenk for COS 230

public class BSTree {
    static int startValues[] = {50, 20, 45, 55, 75, 12, 97, 17, 83, 76, 34, 66, 27, 53, 48};
    private class Node {
    	int key;
    	Node left;
    	Node right;
    	
    	//Node Constructor
    	Node(int key){
    		this.key = key;
    		left = right = null;
    	}
    }
    Node root;
    
    //BST Constructor
	public BSTree(){
		for(int i = 0; i < startValues.length; i++){
			insert(startValues[i]);
		}
	}
	
	//Adds nodes to the tree
	public void insert(int key){
		// assumes no duplicates
		if(root == null){
			root = new Node(key);
		} else {
			Node c = root;
			while(true){
				
				if(key < c.key){
					// go left
					if(c.left == null) {
						c.left = new Node(key);
						break;
					} else {
						c = c.left;
					}
				} else {
					// go right
					if(c.right == null){
						c.right = new Node(key);
						break;
					} else {
						c = c.right;
					}
				}
			}
		}
	}
	
	//Pre-Order Traversal, starting at node passed in
	public void preorder(Node n, String spacing) {
		if (n != null) {
			System.out.println(spacing+n.key);
			preorder(n.left, spacing+"...");
			preorder(n.right, spacing+"...");
		}
	}
	
	//Pre-Order Traversal, starting at node passed in
	public void inorder(Node n, String spacing) {
		if (n != null) {
			inorder(n.left, spacing+"...");
			System.out.println(spacing+n.key);
			inorder(n.right, spacing+"...");
		}
	}
	
	//Pre-Order Traversal, starting at node passed in
	public void postorder(Node n, String spacing) {
		if (n != null) {
			postorder(n.left, spacing+"...");
			postorder(n.right, spacing+"...");
			System.out.println(spacing+n.key);
		}
	}
	
	//Goes through all three traversal methods, starting at root each time
	public void kicker() {
		System.out.println("Pre-order traversal: ");
		preorder(root, "");
		System.out.println("\nIn-order traversal: ");
		inorder(root, "");
		System.out.println("\nPost-order traversal: ");
		postorder(root, "");
	}
	
	//MAIN
	public static void main(String[] args) {
		BSTree bst = new BSTree();
		
		bst.kicker();
	}

}