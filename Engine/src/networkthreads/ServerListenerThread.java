/**
 * 
 */
package networkthreads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import events.EventManager;
import events.types.ClientEvent;
import squares.GameBoard;
import squares.types.PlayerCharacter;

/**
 * @author Owner
 *
 */
public class ServerListenerThread implements Serializable, Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//reference to the server main thread for synchronization
	private ServerSession server;
	//server socket, streams, and response for communicating with incoming clients
	private ServerSocket serverSock = null;
	private ObjectInputStream serverIn = null;
	private ObjectOutputStream serverOut = null;
	private ServerPacket response = null;
	//socket and client packet for communicating with incoming clients
	private Socket clientSock = null;
	private ClientPacket msg;

	//new client objects
	private ObjectOutputStream clientOut = null;
	private ObjectInputStream clientIn = null;
	private ClientSocket newClient = null;
	private PlayerCharacter newPlayer = null;
	

	
	

	
	
	public ServerListenerThread(ServerSession server) {
		this.server = server;
	}

	@Override
	public void run() {
		if( server.clientCount < GameBoard.MAX_PLAYERS){//start listening
			System.out.println("Searching for clients");
			addClient();
			System.out.println("Client Found");
			
		}
		
	}
	/**
	 * establishes a private connection to the game server on the port
	 * specified. Through this process, the client opens a socket to
	 * the server's listening thread. Here, the thread then establishes a
	 * unique communication channel for each client before raising an event
	 * to tell the main server that a connection is pending. During the 
	 * Initial test communications the client establishes it's chosen
	 * name, and color, while the server sends the client an initial
	 * representation of the gameboard.
	 * This is raised in the form of a client event by the listener thread.
	 * 
	 * help reduce special cases.
	 */
	public void addClient(){

		EventManager eventManager = EventManager.getEventManager();
		//set up server Socket
		try {
			serverSock = new ServerSocket(ServerSession.SERVER_PORT, 0, InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("Listening for client");

		try {//listen for connection attempt
			clientSock = serverSock.accept();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Found Client");
		
		try{//found one, generate outputstream
			serverOut = new ObjectOutputStream(clientSock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		

		
		try {//generate inputstream
			serverIn = new ObjectInputStream(clientSock.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		//read connection message
		try {
			msg = (ClientPacket) serverIn.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Connection from client: " + msg.getName());
		System.out.println("Opening Private Socket on Port: " + msg.getInfo());
		
		//reopen the server socket to accept private connection;
		try {
			serverSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		//use the provided port
		try {
			serverSock = new ServerSocket(Integer.parseInt(msg.getInfo()), 0 , InetAddress.getLocalHost());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		try {//listen for the client to reconnect to the server
			clientSock = serverSock.accept();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		

		try {//generate personal streams
			clientOut = new ObjectOutputStream(clientSock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		try {
			clientIn = new ObjectInputStream(clientSock.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		//read connection message
		try {
			msg = (ClientPacket) clientIn.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		

		newPlayer = new PlayerCharacter(msg.getName(), server.clientCount, msg.getInfo());
		newClient = new ClientSocket(clientSock, clientIn, clientOut, newPlayer);


		System.out.println("Private Connection from client: " + msg.getName());

		//create client event for the new connection
		ClientEvent cEvent = new ClientEvent(EventManager.CLIENT_ID, newClient,
				msg.getCode(), msg.getInfo());

		//raise client connection event
		eventManager.raiseEvent(cEvent);
		
		//close for next loop
		try {
			serverSock.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	

}
