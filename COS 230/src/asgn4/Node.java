package asgn4;

//Christopher Lenk for COS 230
//A Node class for BSTs, containing words and definitions as in a dictionary

public class Node {
	private String word;
	private String def; //Definition
	
	private Node right; //Right child
	private Node left; //Left child
	private Node moreDef; //Additional definition node
	private Node parent;
	
	//Constructors
	public Node(String word, String def) {
		this.word = word;
		this.def = def;
	}
	public Node(String word, String def, Node parent) {
		this.word = word;
		this.def = def;
		this.parent = parent;
	}

	//Getters
	public String getWord() {
		return word;
	}
	public String getDef() {
		return def;
	}
	public Node getRight() {
		return right;
	}
	public Node getLeft() {
		return left;
	}
	public Node getMoreDef() {
		return moreDef;
	}
	public Node getParent() {
		return parent;
	}
	
	//Setters
	public void setWord(String word) {
		this.word = word;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public void setMoreDef(Node moreDef) {
		this.moreDef = moreDef;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}

	//Adds another definition of the same word
	public void addDef(String def) {
		this.moreDef = new Node(word, def);
	}
}
