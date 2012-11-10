package asgn4;

//Christopher Lenk for COS 230
//A Splay Tree Implementation for a dictionary

public class SplayTree {
	private Node root;
	private boolean delSwitch; //For alternating deletes
	
	//Getter	
	public Node getRoot() {
		return root;
	}

	//Constructor
	public SplayTree() {
		root = null;
		delSwitch = true;
	}

	//Adds the given word and its definition to the tree
	public void insert(String word, String def) {
		def = def.trim();
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
						splay(cur.getLeft());
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
						splay(cur.getRight());
						break;
					} else {
						//Keep looking
						cur = cur.getRight();
					}
				}
			}
		}
	}
	
	//Moves the given node to the root position
	//Based on code from Wikipedia
	public void splay(Node x) {
		while (x.getParent() != null) { //Don't do anything if it's the root
			
			//Find the root
			Node rt = x;
			while (rt.getParent() != null) {
				rt = rt.getParent();
			}
			
			if (x.getParent() == rt) {
				if (x == rt.getLeft()) { //Zig
					rotRight(rt);
				} else if (x == rt.getRight()) { //Zig
					rotLeft(rt);
				}
			} else {
				Node p = x.getParent(); //Parent node
				Node g = p.getParent(); //Grandparent node
				
				if (x == p.getRight() && p == g.getRight()) { //Zig-zig
					rotLeft(g);
					rotLeft(p);
				} else if (x == p.getLeft() && p == g.getLeft()) { //Zig-zig
					rotRight(g);
					rotRight(p);
				} else if (x == p.getRight() && p == g.getLeft()) { //Zig-zag
					rotLeft(p);
					rotRight(g);
				} else if (x == p.getLeft() && p == g.getRight()) { //Zig-zag
					rotRight(p);
					rotLeft(g);
				}
			}
		}
	}
	
	//Rotates the given node right
	//Based on code from Wikipedia
	public void rotRight (Node p) {
		Node x = p.getLeft();
		p.setLeft(x.getRight());
		if (x.getRight() != null) {
			x.getRight().setParent(p);
		}
		x.setRight(p);
		if (p.getParent() != null) {
			if (p == p.getParent().getRight()) {
				p.getParent().setRight(x);
			} else {
				p.getParent().setLeft(x);
			}
		}
		x.setParent(p.getParent());
		p.setParent(x);
		//Update the root pointer if necessary
		if (p == root) {
			root = x;
		}
	}
	
	//Rotates the given node left
	//Based on code from Wikipedia
	public void rotLeft (Node p) {
		Node x = p.getRight();
		p.setRight(x.getLeft());
		if (x.getLeft() != null) {
			x.getLeft().setParent(p);
		}
		x.setLeft(p);
		if (p.getParent() != null) {
			if (p == p.getParent().getLeft()) {
				p.getParent().setLeft(x);
			} else {
				p.getParent().setRight(x);
			}
		}
		x.setParent(p.getParent());
		p.setParent(x);
		//Update the root pointer if necessary
		if (p == root) {
			root = x;
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
				splay(c);
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
		//Find the node (splays it at the same time)
		Node n = search(word); 
		
		//Make sure the word was found
		if (n == null) {
			System.out.println("Word not found!");
			return;
		}
		
		Node leftSubtree = root.getLeft();
		Node rightSubtree = root.getRight();
		Node successor; //Node to replace the one deleted
		
		if (leftSubtree != null && rightSubtree == null) { //Root has only left subtree
			successor = root.getLeft();
			while (successor.getRight() != null) {
				successor = successor.getRight();
			}
			
			root.getLeft().setParent(null);
			splay(successor);
			root = successor;
		} else if (rightSubtree != null && leftSubtree == null) { //Root has only right subtree
			successor = root.getRight();
			while (successor.getLeft() != null) {
				successor = successor.getLeft();
			}
			
			root.getRight().setParent(null);
			splay(successor);
			root = successor;
		} else { //Root has two subtrees
			if (delSwitch) {
				//Find the leftmost element of the right sub tree
				successor = root.getRight();
				while (successor.getLeft() != null) {
					successor = successor.getLeft();
				}
				
				rightSubtree.setParent(null);
				splay(successor);
				successor.setLeft(leftSubtree);
				leftSubtree.setParent(successor);
				root = successor;
				delSwitch = !delSwitch;
			} else {
				//Find the rightmost element of the left sub tree
				successor = root.getLeft();
				while (successor.getRight() != null) {
					successor = successor.getRight();
				}
				
				leftSubtree.setParent(null);
				splay(successor);
				successor.setRight(rightSubtree);
				rightSubtree.setParent(successor);
				root = successor;
				delSwitch = !delSwitch;
			}
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
