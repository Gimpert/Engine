package networkthreads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import events.handlers.InputHandler;
import processing.core.PApplet;
import squares.GameBoard;


public class ClientSession extends GameSession implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name = "";
	private String color = "";
	private int port;
	private PApplet parent = null;
	
	private boolean connected = false;
	private ObjectInputStream clientIn = null;
	private ObjectOutputStream clientOut = null;
	
	private Socket server = null;
	
	private ClientPacket msg = null;
	private String info;
	private ServerPacket response = null;
	private GameBoard board = null;

	/**
	 * Constructor for default client session
	 */
	public ClientSession(String m, PApplet p){
		super(m, p);
	}

	/**
	 * Constructor for a Client side game session
	 * initializes for immediate .connect()
	 * @param name chosen player name
	 * @param color 
	 * @param port
	 */
	public ClientSession(String m,PApplet p,String name, String color, int port) {
		super(m,p);
		this.setName(name);
		this.setColor(color);
		this.setPort(port);
	}
	/**
	 * No input, sends an acknowledgement request to the server to check in
	 * and avoid disconnection
	 * @return 
	 * 
	 */
	public GameBoard sendUpdateRequest(){
		msg.setCode(ClientPacket.UPDATE_ID);
		msg.setInfo("ACK");
		//Try to send request to server
		try {
			clientOut.reset();
			clientOut.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		//success, wait for server to send state update packet
		//try to receive game board
		
		try {
			response = (ServerPacket) clientIn.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		this.board = response.getGameboard();
		return this.board;
	}
	/**
	 * Client reads input
	 * Send an input message to the server to request
	 * an event
	 * @param i
	 */
	public GameBoard sendInput(String i){
		msg.setCode(ClientPacket.UPDATE_ID);
		msg.setInfo(i);
		//Try to send input request to server
		try {
			clientOut.reset();
			clientOut.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		

		//success, wait for server to send state update packet
		//try to receive game board
		
		try {
			response = (ServerPacket) clientIn.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		this.board = response.getGameboard();
		return this.board;
	}
	
	/**
	 * TODO: add connected logic and reconnect
	 * Establishes a connection with the game server
	 * @return 
	 */
	public GameBoard connect(){
		//generate connection packet
		info ="" + port;
		msg = new ClientPacket(ClientPacket.CONNECT_ID, name, info);
		//attempt to establish connection with the given name and port number
		System.out.println("Attempting to establish connection with game server on port: " + ServerSession.SERVER_PORT);
		try {
			server = new Socket(InetAddress.getLocalHost(), ServerSession.SERVER_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//try to open output stream
		try {
			clientOut = new ObjectOutputStream(server.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//try to open input stream
		try {
			clientIn = new ObjectInputStream(server.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//try to send connection request
		try {
			clientOut.reset();
			clientOut.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//connection request sent
		
		System.out.println("Initial serverconnection established, attempting to reopen"
				+ " communication on port: " + port);
		
		//try to re-establish server connection on private port
		try {
			server = new Socket(InetAddress.getLocalHost(), port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//success, open streams
		try {
			clientOut = new ObjectOutputStream(server.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		try {
			clientIn = new ObjectInputStream(server.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("Communication established. Sending player info.");
		
		//success, send chosen color to server
		info = color;
		//change packet message
		msg.setInfo(info);
		
		//try to send color to server
		try {
			clientOut.reset();
			clientOut.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//success, connection established
		//try to receive game board
		
		try {
			response = (ServerPacket) clientIn.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		//success, retrieve game board update and return it
		this.board = response.getGameboard();
		System.out.println("Server communication setup complete.");
		return this.board;
		
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	public PApplet getParent() {
		return parent;
	}
	public void setParent(PApplet parent) {
		this.parent = parent;
	}
	@Override
	public GameSession setup() {
		int port = 0;
		String color = "";	
		String name = "Bob_Default";
		//get user options
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter desired port number: ");
		port = input.nextInt();
		setPort(port);

		System.out.print("Enter player name: ");
		name = "" + input.next();
		setName(name);
		
		System.out.print("Enter Desired Color(red, blue, green, yellow ): ");
		color = "" + input.next();
		input.close();
		setColor(color);
		//TODO Add error checking
		
		return this;
		
	}

	@Override
	public void takeStep() {
		// TODO Auto-generated method stub
		
	}

}
