package etc;

import java.io.Serializable;

//TODO: don't bother with this class for now, use for session thread for reconnections later
public class RunSquares  implements Serializable, Runnable{
	//The default server port number
	public static final int SERVER_PORT = 28123;
	//The type of connection to establish: Server, Client, or Server Listener (created by server thread)
	private static String mode = null;
	//flag used to indicate whether the thread is busy


	
	
	
	/*do not need until re-connect loop is added
	private static RunSquares sessthread; // reference to the session's worker thread;
	*/


	@Override
	public void run() {
		// TODO Auto-generated method stub
		//1. 1st thread, determine mode then start appropriate network thread ( server or client)
			//2. generate session thread then wait for it to terminate
			//3. exit, for now
		
	}
	
	
}
