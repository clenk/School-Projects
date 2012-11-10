package lab3;

//Christopher Lenk for COS 230
//Implements a method for sorting queues of ints using a radix sort 

public class Radix {

	//Extract a digit from a number
	//"digit" parameter is 1 for ones place, 10 for tens place, etc.
	public Queue sortByDigit(Queue q, Queue a[], int digit) {
		digit = digit*10;
		
		Queue outq = new Queue();
		
		try {
			//Add the numbers to buckets based on the rightmost digit
			while (!q.isEmpty()) {
				int num = q.remove();
				a[(num%digit)/(digit/10)].add(num);
			}

			//Put the results of the sort into a new queue
			for (int i = 0; i < a.length; i++) {
				while (!a[i].isEmpty()) {
					outq.add(a[i].remove());
				}
			}
		} catch (QueueEmptyException e) {
			e.printStackTrace();
		}
		
		return outq;
	}

	//Sorting method
	public Queue sort(Queue inq) {

		//Create "buckets" to put stuff in while sorting
		Queue arr[] = new Queue[10];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Queue();
		}

		//Sort the numbers by digit
		Queue q1 = sortByDigit(inq, arr, 1);
		Queue q2 = sortByDigit(q1, arr, 10);
		Queue q3 = sortByDigit(q2, arr, 100);

		return q3;
	}
}
