package asgn6;

//Christopher Lenk for COS 230
//Implementation of a weighted, directional graph

class Graph {
	private int maxsize; //Highest number of vertices allowed
	private final int INF = 1000000; //Infinity
	private Vertex vertList[]; //List of vertices in the graph
	private int adjMatrix[][]; //Adjacency matrix
	private int numVerts; //Current number of vertices
	private int numTreeVerts; //Number of vertices in the tree
	private ParentDistance shortPath[]; //Array of shortest paths
	private int curVert; //Current vertex
	private int startDist; //Distance from starting vertex to current vertex
	private String endPoint; //Current ending vertex

	// Constructor
	public Graph(int size) {
		maxsize = size;
		vertList = new Vertex[maxsize];
		adjMatrix = new int[maxsize][maxsize];
		numVerts = 0;
		numTreeVerts = 0;
		
		//Set whole adjacency matrix to zero (infinity)
		for (int j = 0; j < maxsize; j++)
			for (int k = 0; k < maxsize; k++)
				adjMatrix[j][k] = INF;
		shortPath = new ParentDistance[maxsize];
	}
	
	//EndPoint setter
	public void setEndPoint(String endpt) {
		endPoint = endpt;
	}
	
	//Adds a vertex with the given name to the graph
	public void addVertex(String label) {
		vertList[numVerts++] = new Vertex(label);
	}

	//Adds an edge to the graph between the vertices with indices of the
	//first and second number passed in, with the weight of the third
	public void addEdge(int start, int end, int weight) {
		adjMatrix[start][end] = weight;
	}

	//Finds all the shortest paths in the graph (Dijkstra's algorithm)
	public void path(String start) {
		//Start the tree with the vertex of the given label
		swapStart(start);
		int startTree = 0;
		vertList[startTree].inTree = true;
		numTreeVerts = 1;

		//Copy distances from the adjacency matrix to the array of shortest paths
		for (int j = 0; j < numVerts; j++) {
			int tempDist = adjMatrix[startTree][j];
			shortPath[j] = new ParentDistance(startTree, tempDist);
		}

		while (numTreeVerts < numVerts) { //Go through until all the vertices are in the tree
			int indexMin = getMin();
			int minDist = shortPath[indexMin].dist;

			if (minDist == INF) { //If all infinite or in tree 
				//System.out.println("There are unreachable vertices");
				break; //Array of shortest paths is complete
			} else {
				//Reset current vertex to the closest vertex
				curVert = indexMin;
				startDist = shortPath[indexMin].dist;
			}
			
			//Add current vertex to the tree
			vertList[curVert].inTree = true;
			numTreeVerts++;
			adjustShortPath(); //Adjust the shortest paths
		}

		printShorts(); //Show the shortest paths

		//Clear the tree
		numTreeVerts = 0;
		for (int j = 0; j < numVerts; j++)
			vertList[j].inTree = false;
	}

	//Returns the path with the minimum distance
	public int getMin()	{
		int minDist = INF;
		int indexMin = 0;
		
		for (int j = 1; j < numVerts; j++) {
			//Check if the vertex is in the tree and shorter than the current minimum
			if (!vertList[j].inTree && shortPath[j].dist < minDist) {
				minDist = shortPath[j].dist;
				indexMin = j;
			}
		}
		
		return indexMin;
	}

	//Updates the array of shortest paths
	public void adjustShortPath() {
		int column = 1; //Skip the first (starting) vertex
		while (column < numVerts) { //Go through all columns
			//Skip the vertex if it's already in the tree
			if (vertList[column].inTree) {
				column++;
				continue;
			}

			//Calculate the distance for current shortPath entry
			int currentToFringe = adjMatrix[curVert][column]; //Get edge from current vertex to column
			int startToFringe = startDist + currentToFringe; //Add the distance from the start
			int sPathDist = shortPath[column].dist; //Get distance from the current shortPath entry

			//Compare distance from start with current shortPath entry
			if (startToFringe < sPathDist) {
				//If the path is shorter, update the array of shortest paths
				shortPath[column].parent = curVert;
				shortPath[column].dist = startToFringe;
			}
			column++;
		}
	}

	//Displays the shortest paths
	public void printShorts() {
		System.out.println("\nShortest Paths:");
		
		for (int j = 0; j < numVerts; j++) {
			System.out.print(vertList[j].label + "=");
			if (shortPath[j].dist == INF) {
				System.out.print("inf"); 
			} else {
				System.out.print(shortPath[j].dist); 
			}
			String parent = vertList[shortPath[j].parent].label;
			System.out.print("(" + parent + ") "); 
		}
		System.out.println("");
	}
	
	//Displays the graph
	public void printGraph() {
		for (int k = 0; k < vertList.length; k++) {
			if (vertList[k] == null) {
				System.out.print("null ");
			} else {
				System.out.print(vertList[k].label+" ");
			}
		}
		System.out.println();
		
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				System.out.print(adjMatrix[i][j]+"\t");
			}
			System.out.println();
		}
	}

	//Finds the index in the matrix of the given label
	public int find(String label) {
		for (int i = 0; i< vertList.length; i++) {
			if (vertList[i] != null && vertList[i].label.equals(label)) {
				return i;
			}
		}
		return -1;
	}
	
	//Picks a new starting vertex
	//(Swaps the vertex with the given label and the first vertex in the adjacency matrix)
	public void swapStart(String label) {
		int replcmnt = find(label);
		
		//Make sure it's a vertex in the graph
		if (replcmnt < 0) {
			System.out.println("Error: This starting point is not in the graph!");
			return;
		}
		
		//First switch rows (from)
		//Store the old first vertex
		int temp1[] = new int[maxsize];
		for (int i = 0; i < adjMatrix.length; i++) {
			temp1[i] = adjMatrix[0][i];
		}
		//Move the new starting vertex to the first place in the matrix
		for (int j = 0; j < adjMatrix.length; j++) {
			adjMatrix[0][j] = adjMatrix[replcmnt][j]; 
		}
		//Put the old first vertex where the new one used to be
		for (int k = 0; k < adjMatrix.length; k++) {
			adjMatrix[replcmnt][k] = temp1[k];
		}
		
		//Switch columns (to)
		int temp2[] = new int[maxsize];
		for (int i = 0; i < adjMatrix.length; i++) {
			temp2[i] = adjMatrix[i][0];
		}
		//Move the new starting vertex to the first place in the matrix
		for (int j = 0; j < adjMatrix.length; j++) {
			adjMatrix[j][0] = adjMatrix[j][replcmnt]; 
		}
		//Put the old first vertex where the new one used to be
		for (int k = 0; k < adjMatrix.length; k++) {
			adjMatrix[k][replcmnt] = temp2[k];
		}
		
		//Swap the labels, too
		Vertex tempVert = vertList[0];
		vertList[0] = vertList[replcmnt];
		vertList[replcmnt] = tempVert;
		
	}

	//Prints out the path from the current starting vertex to the vertex with the given label
	//Returns true if a path was found to that vertex
	public boolean pathTo(String label) {
		int i = find(label);
		//Make sure the ending point is in the graph
		if (i < 0) {
			System.out.println("Error: This ending point is not in the graph!");
			return false;
		}
		boolean bool = false;
		
		if (shortPath[i].dist != INF) {
			bool = pathTo(vertList[shortPath[i].parent].label);
			System.out.print(vertList[shortPath[i].parent].label + " to ");
			
			if (vertList[i].label.equals(endPoint)) {
				System.out.print(vertList[i].label);
				bool = true;
			}
		}
		return bool;
	}
}