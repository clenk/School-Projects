package asgn4;

//Christopher Lenk for COS 230
//A Binary Search Tree Implementation for the dictionary

public class BSTree {
	private Node root;
	private boolean delSwitch; //For alternating deletes to keep tree relatively balanced
	
	//Getter	
	public Node getRoot() {
		return root;
	}

	//Constructor
	public BSTree() {
		root = null;
		delSwitch = true;
	}
	
	//Adds the given word and its definition to the tree
	public void insert(String word, String def) {
		//Case of empty tree
		if (root == null) {
			root = new Node(word, def);
		//Case of NOT empty tree
		} else {
			//Go through the tree and find where to put the word
			Node cur = root;
			while (true) {
				int alphBetOrder = word.compareToIgnoreCase(cur.getWord());
				
				//If word comes before current node, go left
				if (alphBetOrder < 0) {
					if (cur.getLeft() == null) {
						//Stick the word in
						cur.setLeft(new Node(word, def, cur));
						break;
					} else {
						//Keep looking
						cur = cur.getLeft();
					}
				//If word is same as current node 
				} else if (alphBetOrder == 0) {
					//Make sure this definition isn't already in the dictionary
					if (cur.getDef().equals(def)) {
						return;
					}
					//Add the definition at the end of existing definitions
					while (cur.getMoreDef() != null) {
						//Make sure this definition isn't already in the dictionary
						if (cur.getDef().equals(def)) {
							return;
						}
						cur = cur.getMoreDef();
					}
					//Make sure this definition isn't already in the dictionary
					if (cur.getDef().equals(def)) {
						return;
					}
					cur.addDef(def);
					break;
				//If word comes after current node, go right
				} else {
					if (cur.getRight() == null) {
						//Stick the word in
						cur.setRight(new Node(word, def, cur));
						break;
					} else {
						//Keep looking
						cur = cur.getRight();
					}
				}
			}
		}
	}
	
	//Prints out the tree using in-order traversal
	public void print() {
		inOrder(root);
	}
	
	//Prints out the tree from one word to the other using in-order traversal
	public void print(String begin, String end) {		
		inOrder(root, begin, end);
	}
	
	//Prints all the definitions linked off of a node
	public void printAllDefs(Node n) {
		while (n.getMoreDef() != null) {
			System.out.println(n.getWord() + ": " + n.getDef());
			n = n.getMoreDef();
		}
		System.out.println(n.getWord() + ": " + n.getDef());
	}
	
	//In-Order Traversal, starting at given node
	//Prints the word and definition
	public void inOrder(Node nd) {
		if (nd != null) {
			inOrder(nd.getLeft());
			printAllDefs(nd);
			inOrder(nd.getRight());
		}
	}
	
	//In-Order Traversal, from given node to node with given word
	public void inOrder(Node nd, String begin, String end) {		
		if (nd != null) {
			int compare1 = begin.compareToIgnoreCase(nd.getWord());
			int compare2 = end.compareToIgnoreCase(nd.getWord());
			
			inOrder(nd.getLeft(), begin, end);
			if (compare1 <= 0 && compare2 >= 0) {
				System.out.println(nd.getWord() + ": " + nd.getDef());
			}
			inOrder(nd.getRight(), begin, end);
		}
	}

	//Pre-Order Traversal, starting at node passed in,
	//Prints the word along with spacing
	public void preorder(Node n, String spacing) {
		if (n != null) {
			System.out.println(spacing+n.getWord());
			preorder(n.getLeft(), spacing+"...");
			preorder(n.getRight(), spacing+"...");
		}
	}
	
	//Returns the node with the given word
	public Node search(String word) {
		Node c = root; //Start at the root
		
		while (c != null) {
			int alphBetOrder = word.compareToIgnoreCase(c.getWord());
			
			if (alphBetOrder == 0) { //Node with beginning word found
				return c;
			} else if (alphBetOrder < 0) { //Go left
				c = c.getLeft();
			} else { //Go right
				c = c.getRight();
			}
		}
		
		return null; //Word not found
	}
	
	//Removes the node with the given word from the tree
	public void delete(String word) {
		Node n = search(word); //Find the node
		Node successor;
		
		//Make sure the word was found
		if (n == null) {
			System.out.println("Word not found!");
			return;
		}
		
		if (n.getLeft() != null && n.getRight() != null) { //Two children
			//Get smallest node that's bigger than the node to be deleted
			if (delSwitch) {
				successor = n.getRight();
				while (successor.getLeft() != null) {
					successor = successor.getLeft();
				}
				delSwitch = !delSwitch;
			} else {
				successor = n.getLeft();
				while (successor.getRight() != null) {
					successor = successor.getRight();
				}
				delSwitch = !delSwitch;
			}
			n.setWord(successor.getWord());
			n.setDef(successor.getDef());
			n.setMoreDef(successor.getMoreDef());
			replace(successor, successor.getRight());
		} else if (n.getLeft() != null) { //Only a left child
			replace(n, n.getLeft());
		} else if (n.getRight() != null) { //Only a right child
			replace(n, n.getRight());
		} else { //No children
			replace(n, null);
		}
	}
	
	//Makes the parent of the first node passed in
	//point instead to the second node passed in
	public void replace(Node older, Node newer) {
		if (older.getParent() != null) {
			if (older == older.getParent().getLeft()) {
				older.getParent().setLeft(newer);
			} else {
				older.getParent().setRight(newer);
			}
		}
		if (newer != null) {
			newer.setParent(older.getParent());
		}
	}
}
