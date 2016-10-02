/**
 * 
 */
package networkthreads;

import java.io.IOException;
import java.io.Serializable;

import processing.core.PApplet;
import squares.GameBoard;
import squares.components.Component;
import squares.types.PlayerCharacter;
import events.EventManager;
import events.handlers.ClientHandler;
import events.handlers.InputHandler;
import events.types.ClientEvent;
import events.types.Event;
import events.types.InputEvent;
import events.types.SpawnEvent;

/**
 * @author Owner
 *
 */
public class ServerSession  extends GameSession implements Serializable, Runnable{
	public ServerSession(String m,PApplet p) {
		super(m,p);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//The default server port number
	public static final int SERVER_PORT = 28123;
	//the maximum number of players
	protected static final int MAX_CLIENTS = 4;

	//The number of clients currently connected
	protected int clientCount = 0;
	
	public ClientSocket[] clientList = null;
	
	private boolean busy = true;
	
	// synchronized method to test if server loop is running
	public synchronized boolean isBusy() { return busy; }
	public synchronized void setBusy(Boolean b){ this.busy = b;}
	
	//thread to listen for new connections
	private ServerListenerThread listener= null;
	private Thread thread = null;
	//response to update requests
	private ServerPacket response = null;
	
	
	//int i; // the ID of the thread
	private ClientPacket msg;
	

	@Override
	public void run() {
		clientList = new ClientSocket[MAX_CLIENTS];
		busy = true;
		GameBoard gameBoard = GameBoard.getGameBoard();
		EventManager eventManager = EventManager.getEventManager();
		response = new ServerPacket(0, gameBoard);
		//Create server listener thread for incoming connections
		/** Register the client and input handlers with the Event Manager*/
		InputHandler inputHandler = new InputHandler(EventManager.INPUT_ID);
		eventManager.registerHandler(inputHandler);
		ClientHandler clientHandler = new ClientHandler(EventManager.CLIENT_ID, this);
		eventManager.registerHandler(clientHandler);
		listener = new ServerListenerThread(this);
		thread = new Thread(listener);
		thread.start();
		System.out.println("listener thread initialized");
	}
	

	
	public void updateClients(){
		response.setGameboard(GameBoard.getGameBoard());
		for(int i = 0; i < clientCount; i++){
			try {
				clientList[i].getOut().reset();
				clientList[i].getOut().writeObject(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void readClientPackets(){//add a disconnect function
		InputEvent input = null;
		EventManager eventManager = EventManager.getEventManager();
		for(int i = 0; i < clientCount; i++){
			try {
				msg = (ClientPacket) clientList[i].getIn().readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(! msg.getInfo().equals("ACK")){
				input = new InputEvent(EventManager.INPUT_ID, clientList[i].getPlayer() , msg.getInfo() );
				eventManager.raiseEvent(input);
			}
			
		}
		
	}

	public synchronized void addClient(Event event){
		setBusy(false);
		ClientEvent cEvent = (ClientEvent) event;
		ClientSocket newClient = cEvent.getClient();

		EventManager eventManager = EventManager.getEventManager();

		//Add the new client to the server's list
		newClient.setClientNumber(clientCount);
		clientList[clientCount] = newClient;
		//Send first game board
		updateClient(clientCount);
		
		//Add the new player to the game board
		PlayerCharacter newPlayer = newClient.getPlayer();
		GameBoard.getGameBoard().addSquare(newPlayer);

		//raise initial event to spawn player
		SpawnEvent sEvent = new SpawnEvent(Component.SPAWN_ID, newPlayer,
				SpawnEvent.INITIAL_SPAWN);
		eventManager.raiseEvent(sEvent);
		clientCount++;
		//Let the listener know it can resume
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBusy(true);
		
		thread = new Thread(listener);
		thread.start();
		

	}
	
	public void updateClient(int i){

		response.setGameboard(GameBoard.getGameBoard());
			try {
				clientList[i].getOut().reset();
				clientList[i].getOut().writeObject(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				
	}
	@Override
	public void takeStep() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ServerSession setup() {
		return null;
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
