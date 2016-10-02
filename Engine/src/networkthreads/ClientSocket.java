package networkthreads;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import squares.types.PlayerCharacter;



public class ClientSocket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Socket clientSock;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int clientNumber = -1;
	
	private PlayerCharacter player = null;
	
	public ClientSocket(Socket cs, ObjectInputStream in, ObjectOutputStream out, PlayerCharacter p){
		this.setClientSock(cs);
		this.setIn(in);
		this.setOut(out);
		this.setPlayer(p);
	}

	public Socket getClientSock() {
		return clientSock;
	}

	public void setClientSock(Socket clientSock) {
		this.clientSock = clientSock;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	public PlayerCharacter getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}
}
