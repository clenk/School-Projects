package asgn6;

//Christopher Lenk for COS 230
//Tracks the parent vertex and the distance from starting vertex

public class ParentDistance {
	
	public int parent; //Vertex's current parent
	public int dist; //Distance from start to this vertex
	
	//Constructor
	public ParentDistance(int parent, int dist) {
		super();
		this.parent = parent;
		this.dist = dist;
	}
}
