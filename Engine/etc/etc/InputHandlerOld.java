package etc;

import processing.core.PApplet;
import squares.GameBoard;
import squares.types.PlayerCharacter;
import events.EventManager;
import events.handlers.EventHandler;
import events.types.*;
/**
 * A singleton class 
 * @author Corey Glasson
 *
 */
public class InputHandlerOld extends EventHandler{

	PApplet parent;
	private InputHandlerOld(int i, PApplet p){
		super(i);
		this.parent = p;
	}
	

	/**
	 * handles valid keyboard input events
	 * @param gameBoard the board that valid input operations are performed upon
	 */
	public void onEvent( Event e ){
		InputEvent p;

		if ( e.instanceOf() == EventManager.INPUT_ID){
			p = (InputEvent) e;
			

		}
	}




}
