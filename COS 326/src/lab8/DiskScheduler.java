package lab8;

//Taken from www.os-book.com

public interface DiskScheduler
{
	// service the requests
	// return the amount of head movement
	// for the particular algorithm
	public int serviceRequests();
}
