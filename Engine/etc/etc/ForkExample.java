package etc;


//Demonstrating multithreading and thread synchronization in Java
public class ForkExample implements Runnable {
	
	public static int threadCount = 0;
	public static ClientThread[] clientList= null;
	int i; // the ID of the thread, so we can control behavior
boolean busy; // the flag, Thread 1 will wait until Thread 0 is no longer busy before continuing
	ForkExample other; // reference to the other thread we will synchronize on. This is needed so we can control behavior.

	
	// create the runnable object
	public ForkExample(int i, ForkExample other) {
		this.i = i; // set the thread ID (0 or 1)
		if(i==0) { busy = true; } // set the busy flag so Thread 1 waits for Thread 0
		else { this.other = other; }
	}

	// synchronized method to test if server is busy communicating with a client
	//TODO
	public synchronized boolean isBusy() { return busy; } // What happens if this isn't synchronized? 

	// run method needed by runnable interface
	public void run() {
		if(i==0) { // 1st thread, sleep for a while, then notify threads waiting
			try {
				//loop until all threads have exited
				for(int j = 0; j < threadCount; j++){
					Thread.sleep(4000); // What happens if you put this sleep inside the synchronized block?
					synchronized(this) {
						busy = false; // must synchronize while editing the flag
						notify(); // notify() will only notify threads waiting on *this* object;
					}
				}

			}
			catch(InterruptedException tie) { tie.printStackTrace(); }
		}
	}

	public static void main(String[] args) {
		int total = 1;
		if( (args.length == 1)){
			try{
				total = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				System.err.println("Usage: ForkExample");
				System.err.println("or   : ForkExample <# of Clients>");
				System.exit(0);
			}
		}
		threadCount = total;
		clientList = new ClientThread[threadCount];
		ForkExample t1 = new ForkExample(0, null);
		(new Thread(t1)).start();
		for(int j = 0; j < threadCount; j++){
			clientList[j] = t1.new ClientThread(j + 1, t1);
			(new Thread(clientList[j])).start();
		}
	}
	
	
	public class ClientThread extends ForkExample implements Runnable{

		public ClientThread(int i, ForkExample other) {
			super(i, other);
			// TODO Auto-generated constructor stub
		}
		
		public void run(){
			while(other.isBusy()) { // check if other thread is still working
				System.out.println("Waiting!");
				// must sychnronize to wait on other object
				try { synchronized(other) {
						other.wait(); 
					} 
				} // note we have synchronized on the object we are going to wait on
				catch(InterruptedException tie) { tie.printStackTrace(); }
			}
			System.out.println("" + this.i);
		}
		
	}
	


}