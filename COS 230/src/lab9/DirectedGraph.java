package lab9;

//Modified by Christopher Lenk for COS 230
//An implementation of a directed graph using an adjacency matrix and warshall's algorithm

public class DirectedGraph {
	private static final int limit = 50;
	private boolean[][] adjMatrix;
	private String[] keys;
	private int numVerts;

	//Constructor
    public DirectedGraph() {
    	adjMatrix = new boolean[limit][limit];
    	keys = new String[limit];
    	numVerts = 0;
    }

    //Adds a new edge to the graph and adds the vertices if needed
    void addRelation(String from, String to) {
    	int fromKey = getIndex(from);
    	int toKey = getIndex(to);
    	
    	if (fromKey == -1 && toKey == -1) { //neither is in the graph already
    		//Add the first edge to the graph
    		keys[numVerts] = from;
    		numVerts++;
    		//Add the second edge to the graph
    		keys[numVerts] = to;
    		numVerts++;
    		//Add the connection
    		adjMatrix[numVerts-2][numVerts-1] = true;
    	} else if (fromKey != -1 && toKey == -1) { //only first one in in graph already
    		//Add the second edge to the graph
    		keys[numVerts] = to;
    		numVerts++;
    		//Add the connection
    		adjMatrix[fromKey][numVerts-1] = true;
    	} else if (fromKey == -1 && toKey != -1) { //only second one in in graph already
    		//Add the first edge to the graph
    		keys[numVerts] = from;
    		numVerts++;
    		//Add the connection
    		adjMatrix[numVerts-1][toKey] = true;
    	} else { //both area already in the graph
    		//Add the connection
    		adjMatrix[fromKey][toKey] = true;
    	}
    }
    
    //Prints out the matrix 
    void printAdjMatrix() {
    	for (int i = 0; keys[i] != null && i < keys.length; i++) {
    		System.out.println("Relations from " + keys[i] + ":");
    		
    		for (int j = 0;  j < adjMatrix[i].length; j++) {
    			if (adjMatrix[i][j]) {
    				System.out.println("\t"+keys[j]);
    			}
    		}
    		System.out.println();
    	}
    }
    
    //Finds all the vertices can be reached eventually from each vertex and adds that edge to the graph  
    void calcWarshall() {
    	for (int i = 0; i < adjMatrix.length; i++) { //rows
    		for (int j = 0; j < adjMatrix[i].length; j++) { //columns
    			if (adjMatrix[i][j]) { //Find an existing connection
    				for (int k = 0; k < adjMatrix.length; k++) { //rows
    					if (adjMatrix[k][i]) { //Find connections to the vertex found above
    						adjMatrix[k][j] = true; //Set the connection
    					}
    				}
    			}
    		}
    	}
    }
    
    //Returns the index of the slot in the array corresponding to the string passed in
    int getIndex(String str) {
    	int key = -1;
    	
    	for (int i = 0; i < keys.length; i++) {
    		if (keys[i] != null && keys[i].equals(str)) {
    			key = i;
    		}
    	}
    	
    	return key;
    }
    
    //MAIN
    public static void main(String args[]) {
        DirectedGraph g = new DirectedGraph();
        g.addRelation("Atlanta",  "Chattanooga");
        g.addRelation("Chattanooga",  "Nashville");
        g.addRelation("Chattanooga",  "Knoxville");
        g.addRelation("Atlanta",  "Birmingham");
        g.addRelation("Atlanta",  "Columbia");
        g.addRelation("Columbia", "Charleston");
        g.addRelation("Columbia",  "Greenville");
        g.addRelation("Greenville",  "Atlanta");
        g.addRelation("Charleston",  "Savanna");
        g.addRelation("Savanna",  "Atlanta");
        g.addRelation("Savanna", "Jacksonville");
        g.addRelation("Jacksonville", "Atlanta");
        g.addRelation("Knoxville", "Greenville");/**/
        
        g.printAdjMatrix();
        System.out.println("----------------------------------\n");
        g.calcWarshall();
        g.printAdjMatrix();
     }

}