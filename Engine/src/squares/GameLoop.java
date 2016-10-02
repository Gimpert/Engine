package squares;

import java.util.Scanner;

import networkthreads.ClientSession;
import networkthreads.GameSession;
import networkthreads.ServerSession;
import events.*;
import events.handlers.*;
import processing.core.*;
import squares.components.Component;
import squares.components.Physical;
import squares.components.Visible;
public class GameLoop extends PApplet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Global constant for the number of frames calculated per second*/
	public static final int FRAME_RATE = 8;
	
	private GameBoard gameBoard;
	

	
	//The constructs containing all the logic for communicating in a game session
	//Can be a Server Session or a GameSession
	private GameSession session;

	
	
	
	//TODO add handlers
	/**
	 * check whether the loop should be server mode or client mode
	 * @param args
	 */
	public static void main(String args[]) {
		
		PApplet.main(new String[] { "--present", "squares.GameLoop" });
	}
	


	public void setup() {
		Scanner input = new Scanner(System.in);
		String mode = "";
		System.out.println("Create server or client session?");
		mode = input.nextLine().toLowerCase();
		if(mode.equals("server")){
			serverSetUp();
		}else if(mode.equals("client")){
			
			clientSetUp();
		}else{
			System.err.println("Usage:<server>");
			System.err.println("or   :<client>");
			System.exit(0);
		}
		size(GameBoard.QUADRANTS_WIDTH * Square.BASIC_SQUARE, 
				GameBoard.QUADRANTS_HEIGHT * Square.BASIC_SQUARE);
		frameRate(FRAME_RATE);
		input.close();
	
	}
	
	private void clientSetUp() {
		int port = 0;
		String color = "";	
		String name = "Bob_Default";
		//get user options
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter desired port number: ");
		port = input.nextInt();

		System.out.print("Enter player name: ");
		name = "" + input.next();

		System.out.print("Enter Desired Color(red, blue, green, yellow ): ");
		color = "" + input.next();
		input.close();

		

		session = new ClientSession("client", this, name, color, port);
		gameBoard = ((ClientSession) session).connect();
		//generate session
		//connect to game server
		
	}

	private void serverSetUp() {
		Thread thread;
		//TODO:finish implementing event manager within gameboard
		GameBoard gameBoard = GameBoard.getGameBoard(this);
		EventManager eventManager = EventManager.getEventManager();

		
		//Register Event Handlers
		CollisionHandler collisionHandler = new CollisionHandler(EventManager.COLLISION_ID);
		eventManager.registerHandler(collisionHandler);
		
		HazardHandler hazardHandler = new HazardHandler(EventManager.HAZARD_ID);
		eventManager.registerHandler(hazardHandler);
		
		SpawnHandler spawnHandler = new SpawnHandler(EventManager.SPAWN_ID);
		eventManager.registerHandler(spawnHandler);
		
		//generate multithreaded server
		session = new ServerSession("server",this);
		thread = new Thread(session);
		thread.start();
		gameBoard.testSetUp();
		//begin loop
		
	}

	public void draw() {

		String m = session.getMode();

		if(m.equals("server")){
			EventManager eventManager = EventManager.getEventManager();


			eventManager.handleQueue();

			gameBoard = GameBoard.getGameBoard();
			gameBoard.updateSquares();
			((ServerSession) session).readClientPackets();
			((ServerSession) session).updateClients();

			background(255);
			//set the coordinate plane to have x start from the bottom left of the screen
			translate(0, GameBoard.QUADRANTS_HEIGHT * Square.BASIC_SQUARE);
			scale(1.0f, -1.0f);
			drawObjects();//TODO: server may not need to draw objects






		}else if(m.equals("client")){

			if(keyPressed){
				if(key == 'a' || key == 'd' || key== 'r'
						|| key == 't'|| key == '1' || key == '2'|| key == '3'){
					String inString = "" + key;
					gameBoard = ((ClientSession) session).sendInput(inString);
				}
			}else if( keyPressed && key == ' '){
				String inString = "" + "jump";
				gameBoard = ((ClientSession) session).sendInput(inString);
			}else{
				gameBoard = ((ClientSession) session).sendUpdateRequest();
			}

			//set the coordinate plane to have x start from the bottom left of the screen
			translate(0, GameBoard.QUADRANTS_HEIGHT * Square.BASIC_SQUARE);
			scale(1.0f, -1.0f);
			if (gameBoard != null){
				drawObjects();
			}

		}

	}



	
	
	/**
	 * iterates through the list of objects on the current game board, and draws them.
	 */
	public void drawObjects(){
		int[] backColor;
		int[] borderColor;
		int x;
		int y;
		int w;
		int h;
		Visible drawable;
		Physical dimensions;
		for(Square square : gameBoard.getObjList()){
			if (square.isEnabled() && square.isInstanceOf(Component.VISIBLE_ID) && square.isInstanceOf(Component.PHYSICAL_ID)){
				drawable = (Visible) square.getComponent(Component.VISIBLE_ID);
				dimensions = (Physical) square.getComponent(Component.PHYSICAL_ID);
				backColor = drawable.getBackground();
				borderColor = drawable.getBorder();
				x = square.getCurX();
				y = square.getCurY();
				w = dimensions.getWid();
				h = dimensions.getHgt();

				fill(backColor[0], backColor[1], backColor[2]);
				stroke(borderColor[0], borderColor[1], borderColor[2]);
				rect(x, y, w, h);
			}
		}

		
	}
}
