package asgn2;

import java.util.Random;

//Christopher Lenk for COS 230
//Models a single visit of a single person to the bank

public class Visit {
	int seq; //number of this visit in the day's sequence of visits
	int time; //time of the visit
	int visitLength; //length of time the visit lasted
	
	//Constructor
	Visit(Random r, int dayLength, int maxVisit, int seqNo) {
		time = r.nextInt(dayLength) + 1;
		visitLength = r.nextInt(maxVisit) + 1;
		seq = seqNo;
	}
	
	//Returns a string containing all the data about this particular visit
	public String toString() {
		return new String("Visit "+seq+" - Time: "+time+" "+" - Length of Visit: "+visitLength);
	}
}
