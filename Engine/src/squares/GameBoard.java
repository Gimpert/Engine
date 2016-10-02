package squares;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import squares.components.Component;
import squares.components.Player;
import squares.types.FloorBlock;
import squares.types.ImmovableBlock;
import squares.types.MovableBlock;
import squares.types.PlayerCharacter;

//TODO: make GameBoard Class Singleton
/**
 * 
 * @author Owner
 *
 */
public class GameBoard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Width for matrix storing objects on screen. currently set for 128 blocks*/
	public static final int QUADRANTS_WIDTH = 128;
	/** height for matrix storing objects on screen. currently set for 800 blocks*/
	public static final int QUADRANTS_HEIGHT = 80;
	
	private static GameBoard inst = null;
	
	
	
	/** matrix storing objects on screen. Represented as 10x10 blocks. Current resolution set at 1280x800
	 * unused currently
	 */
	//private static Square[][] board;
	//TODO: perhaps should be extracted into it's own class. could be used to generate complex game objects
	//		or can be used to generate a collision map so that movement can be calculated iteratively and
	//		collisions resolved as they happen.

	/** List of game objects*/
	private ArrayList<Square> objList = new ArrayList<Square>();
	
	/** List of players*/
	public static final int MAX_PLAYERS = 4;
	//private static ArrayList<PlayerCharacter> players = new ArrayList<PlayerCharacter>();
	//TODO: need?

	
	
	/** Reference to main game loop parent to allow processing inheritance*/
	public static transient PApplet parent;
	
	private GameBoard( PApplet p){
		GameBoard.parent = p;
		//GameBoard.board = new Square[QUADRANTS_WIDTH][QUADRANTS_HEIGHT];
		testSetUp();
	}
	
	public static GameBoard getGameBoard(PApplet p){
		if( inst == null){
			inst = new GameBoard(p);
		}
		return inst;
	}
	
	public static GameBoard getGameBoard(){
		return inst;
	}
	/**
	 * Adds a square to the object list. 
	 *
	 * @param s1 the square being added to the board
	 */
	public void addSquare(Square s1){
		
		objList.add(s1);
		
		Collections.sort(objList);
		
		
	}
	/**
	 * removes the square from its previous position, then adds it to its new location
	 * @param s1
//	 */
//	public void moveSquare(Square s1){
//		
//	}
	/**
	 * removes a square from the game board.
	 * used by the methods: moveSquare().
	 * @param s1
	 */
//	public void removeSquare(Square s1){
//		
//	}
	
	

	
//	public Square[][] getBoard(){
//		return GameBoard.board;
//	}
//
//	public Square getSquare(int x, int y) {
//		return board[x][y];
//	}

	/**
	 * returns the list of objects in the game
	 * @return
	 */
	public ArrayList<Square> getObjList() {
		return objList;
	}
	/**
	 * Public method to sort the object list 
	 */
	public void sortObjList(){
		Collections.sort(objList);
	}

	public void testSetUp(){
		//makes floor
		for(int i = 0; i < (QUADRANTS_WIDTH); i++){
			FloorBlock fb = new FloorBlock(parent, (i * Square.BASIC_SQUARE), 0, Square.BASIC_SQUARE, Square.BASIC_SQUARE);
			addSquare(fb);
		}
		//make player block
//		int[] pbackColor = {0, 0, 255};
//		int[] pborderColor = {0, 0, 0};

		//deprecated functionality. playerblocks are setup by
		// client events.
//		GameBoard.pb = new PlayerCharacter(parent, 0, 10, Square.BASIC_SQUARE, Square.BASIC_SQUARE, 1, pbackColor, pborderColor);
//		addSquare(pb);
		
		
		
		//make movable blocks
		int[] mbackColor = {0, 255, 0};
		int[] mborderColor = {0, 0, 0};
		MovableBlock mb = new MovableBlock(parent, 400, 10, Square.BASIC_SQUARE, Square.BASIC_SQUARE, 1, mbackColor, mborderColor);
		addSquare(mb);
		//make immovable block
		int[] ibackColor = {255, 0, 0};
		int[] iborderColor = {0, 0, 0};
		ImmovableBlock ib = new ImmovableBlock(parent, 600, 10, 2*Square.BASIC_SQUARE, Square.BASIC_SQUARE, 0, ibackColor, iborderColor);
		addSquare(ib);
	}
	
	public PlayerCharacter getPlayer(String n){
		PlayerCharacter pb = null;
		Player player;
		for( Square square : objList){
			player = null;
			if( square.isInstanceOf(Component.PLAYER_ID)){
				player = (Player) square.getComponent(Component.PLAYER_ID);
				if(player.getPlayerName().equals(n)){
					pb = (PlayerCharacter) square;
					break;
				}
			}
		}
		
		
		
		
		return pb;
		
	}
	

	
	/**
	 * Update each enabled Square for one step
	 */
	public void updateSquares(){
		for (Square square : objList) 
		{
			if(square.isEnabled()){
				square.updateComponents();
			}
		}
		sortObjList();
	}

	
	

	





}








