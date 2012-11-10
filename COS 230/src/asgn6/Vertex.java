package asgn6;

//Christopher Lenk for COS 230
//Vertex class for the graph; each vertex represents a city.

public class Vertex {

	public String label; //Name of the city
	public boolean inTree; //True if the vertex is part of the tree
	
	//Constructor
	public Vertex(String label) {
		super();
		this.label = label;
		this.inTree = false;
	} 
}
