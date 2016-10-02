/**
 * 
 */
package etc;

import java.io.Serializable;

/**
 * @author Owner
 *
 */
public class ClientThread implements Serializable, Runnable{

	@Override
	public void run() {
		//TODO: is this really needed? if clients are just going to be a client mode game loop
		//		that is networked with a server mode game loop, it would make more sense to
		//		use a client socket object that communicates with the multi-threaded server
		//		maybe use this later to implement client threads so that the server asynchronously
		//		processes client packets
	}

}
