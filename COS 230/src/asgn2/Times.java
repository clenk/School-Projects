package asgn2;

//Christopher Lenk for COS 230
//Holds, for a single simulation, the total wait time for all customers
//and the wait time for the customer who had to wait the longest. 

public class Times {

	int total;
	int worst;
	
	//Getters and Setters
	public int getTotal() {
		return total;
	}
	public int getWorst() {
		return worst;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setWorst(int worst) {
		this.worst = worst;
	}

	//Constructor
	public Times(int worst, int total) {
		super();
		this.total = total;
		this.worst = worst;
	}
	
	//So we can print this
	public String toString() {
		return new String("Worst Time: "+worst+" - Wait Total: "+total);
	}
}
