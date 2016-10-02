package etc;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BasicHost implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int SERVER_PORT = 28123;
	
	private static int clientPort;
	

	private static final int CLIENT_NUMBER = 3;
	
	private static int clientCount = 0;
	private static String mode = null;
	//private static int port = 0;
	private static ClientSocket[] clientList = new ClientSocket[CLIENT_NUMBER];
	private static int id;
	private static BasicHost inst;

	public static void main(String[] args) {
		inst = new BasicHost();
		// TODO Auto-generated method stub
		if( (args.length == 1)){
			mode = args[0];
		}else{
			System.err.println("Usage: BasicHost <server>");
			System.err.println("or   : BasicHost <client>");
			System.exit(0);
		}
		
		if(mode.equals("server")){
			server();
		}else if(mode.equals("client")){
			client();
		}else{
			System.err.println("Usage: BasicHost <server>");
			System.err.println("or   : BasicHost <client>");
			System.exit(0);
		}
	}

	private static void server(){
		ServerSocket serverSock = null;
		Socket clientSock = null;
		ObjectInputStream serverIn = null;
		ObjectOutputStream serverOut = null;
		ClientPacket cp = null;
		ClientSocket cs = null;
		while( clientCount < CLIENT_NUMBER){
			try {
				serverSock = new ServerSocket(SERVER_PORT, 0, InetAddress.getLocalHost());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			try {//listen for connection attempt
				clientSock = serverSock.accept();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			
			try{//found one, generate output
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
				cp = (ClientPacket) serverIn.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			
//			try {
//				inst.wait(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.exit(0);
//			}
			//reopen the server socket to accept private connection;
			try {
				serverSock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
			try {
				serverSock = new ServerSocket(Integer.parseInt(cp.getMessage()), 0 , InetAddress.getLocalHost());
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
			try {
				clientSock = serverSock.accept();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			ObjectOutputStream csOut = null;
			try {
				csOut = new ObjectOutputStream(clientSock.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			ObjectInputStream csIn = null;
			try {
				csIn = new ObjectInputStream(clientSock.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			//generate socket object
			cs = new ClientSocket(clientSock, csIn, csOut );
			System.out.println("Client " + clientCount + " connected on port number " + Integer.parseInt(cp.getMessage()));

			clientList[clientCount] = cs;
			clientCount++;
			//close for next loop
			try {
				serverSock.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}

			
			
			
			
			
		}
	}
	
	private static void client(){
		Scanner input = new Scanner(System.in);
		System.out.print("Enter port number to open connection on:");
		
		
		clientPort = input.nextInt();
		input.close();
		
		Socket ss = null;
		ObjectInputStream clientIn = null;
		ObjectOutputStream clientOut = null;
		
		
		
		
		ClientPacket cp = null;
		try {
			ss = new Socket(InetAddress.getLocalHost(), SERVER_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//attempt to open outputstream
		try {
			clientOut = new ObjectOutputStream(ss.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//attempt to open input stream
		try {
			clientIn = new ObjectInputStream(ss.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		cp = inst.new ClientPacket(0, -1, "" + clientPort);
		
		//try tosend connection request
		try {
			clientOut.writeObject(cp);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		//wait for server to begin listening on specified port
//		try {
//			inst.wait(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.exit(0);
//		}
		
		try {
			ss = new Socket(InetAddress.getLocalHost(), clientPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	
		try {
			clientOut = new ObjectOutputStream(ss.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		try {
			clientIn = new ObjectInputStream(ss.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		
		

	}
	
	public class ClientPacket implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = -8615114030592427618L;
		private int code;
		private int id;
		private String message;
		
		public ClientPacket(int c,int i, String m){
			this.code = c;
			this.id = i;
			this.message = m;
		}

		public int getCode() {
			return code;
		}

		public int getId() {
			return id;
		}

		public String getMessage() {
			return message;
		}


	}
	
	public static class ClientSocket implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2938367845740442195L;
		private static Socket clientSock;
		private static ObjectInputStream in;
		private static ObjectOutputStream out;
		
		public ClientSocket(Socket cs, ObjectInputStream in, ObjectOutputStream out){
			this.setClientSock(cs);
			this.setIn(in);
			this.setOut(out);
		}

		public Socket getClientSock() {
			return clientSock;
		}

		public void setClientSock(Socket clientSock) {
			ClientSocket.clientSock = clientSock;
		}

		public InputStream getIn() {
			return in;
		}

		public void setIn(ObjectInputStream in) {
			ClientSocket.in = in;
		}

		public OutputStream getOut() {
			return out;
		}

		public void setOut(ObjectOutputStream out) {
			ClientSocket.out = out;
		}
	}

}
