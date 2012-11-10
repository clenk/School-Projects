package lab8;

//Modified by Christopher Lenk for COS 230
//An implementation of an adjacency list

public class AdjListLab {
	public final int LIMIT = 50; //Maximum upper limit on the number of vertices
	private Entry arr[]; //Array to be used for the list
	private int nextEmpty; //Holds index of next empty slot in the array
	
	//Class for the nodes in the array itself
	private class Entry {
		private String word;
		private Node next;
		
		//Constructors (Entry)
		public Entry(String wrd) {
			word = wrd;
		}
		public Entry(String wrd, Node n) {
			word = wrd;
			next = n;
		}
		
		//Adds a node onto the end of the list coming off this entry in the array
		public void setlast(int value) {
			Node c = next;
			while (c.next != null) {
				c = c.next;
			}
			c.next = new Node(value);
		}
	}
	
	//Class for the nodes in the list coming off the array
	private class Node {
		private int data;
		private Node next;
		
		//Constructor (Node)
		public Node(int value) {
			data = value;
		}
	}

	//Constructor (Adjacency List)
    public AdjListLab() {
    	arr = new Entry[LIMIT];
    	nextEmpty = 0;
    }

    //Adds an edge between the vertices of the words passed in,
    //Creates vertices if they aren't in the list already
    void addRelation(String from, String to) {
    	int first = find(from); //index in array of "from"
    	int second = find(to); //index in array of "to"
    	
    	if (first == -1 && second == -1) { //Neither is in list already   , new Node(nextEmpty+1)
    		arr[nextEmpty] = new Entry(from);
    		arr[nextEmpty+1] = new Entry(to);
    		arr[nextEmpty].next = new Node(nextEmpty+1);
    		arr[nextEmpty+1].next = new Node(nextEmpty);
    		nextEmpty += 2;
    	} else if (first != -1 && second == -1) { //First is in list, second isn't
    		arr[nextEmpty] = new Entry(to, new Node(first));
    		arr[first].setlast(nextEmpty);
    		nextEmpty += 1;
    	} else if (first == -1 && second != -1) { //First isn't in list, second is
    		arr[nextEmpty] = new Entry(from, new Node(second));
    		arr[second].setlast(nextEmpty);
    		nextEmpty += 1;
    	} else { //Both are already in the list
    		arr[first].setlast(second);
    		arr[second].setlast(first);
    	}
    }

    //Prints out all vertices, each followed by a list of the vertices it is connected to
    void printAdjList() {
    	//Go through the array
    	for (int i = 0; i < arr.length; i++) {
    		if (arr[i] != null) {
        		System.out.println("Connections from " + arr[i].word + ":");
        		//Go through the list off of this entry in the array and print out the connections
        		Node cur;
        		if (arr[i].next != null) {
        			cur = arr[i].next;
        			System.out.println("\t" + arr[cur.data].word);
            		while (cur.next != null) {
            			cur = cur.next;
            			System.out.println("\t" + arr[cur.data].word);
            		}
        		}
    		}
    	}
    }
    
    //Returns the index in the array containing the word passed in 
    public int find(String val) {
    	for (int i = 0; i < arr.length; i++) {
    		if (arr[i] != null) {
	    		if (arr[i].word.equals(val)) {
	    			return i;
	    		}
    		}
    	}
    	//If program reaches here, word not found in the array
    	return -1;
    }
    
   //MAIN
    public static void main(String args[]) {
        AdjListLab g = new AdjListLab ();
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
        g.addRelation("Greenville", "Knoxville");

        g.printAdjList();
     }

}

     
