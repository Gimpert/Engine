package events.handlers;

import squares.components.Component;
import squares.components.Movable;
import squares.components.Player;
import squares.types.PlayerCharacter;
import events.types.Event;
import events.types.InputEvent;

public class InputHandler extends EventHandler {
	public static final int LEFT_KEY =			0x0001;
	public static final int RIGHT_KEY =			0x0002;
	public static final int JUMP_KEY =			0x0004;
	public static final int REPLAY_RECORD_KEY =	0x0008;
	public static final int REPLAY_START_KEY =	0x0010;
	public static final int HALF_SPEED_KEY =	0x0020;
	public static final int NORMAL_SPEED_KEY =	0x0040;
	public static final int DOUBLE_SPEED_KEY =	0x0080;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputHandler(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEvent(Event e) {
		PlayerCharacter player = ((InputEvent) e).getPlayer();
		String action = ((InputEvent) e).getAction();
		//TODO: Add input reading logic
		


	}
	/**
	 * adjusts the inertia of the player block in question to go left
	 * @param pb
	 */
	public void moveLeft(PlayerCharacter player){
		if(player.isEnabled()){
			Player pComp = (Player) player.getComponent(Component.PLAYER_ID);
			Movable mComp = (Movable) player.getComponent(Component.MOVABLE_ID);
			//check if the the player has vertical inertia 
			// and if the player is not already running at top speed
			if(mComp.getVelY() == 0 && mComp.getVelX() > (- mComp.getMaxVel()) ){
				//if these conditions are met, the player's inertia is adjusted
				mComp.setInertiaX(-1);
			}
		}
	}
	
	/**
	 * adjusts the inertia of the player block in question to go right
	 * @param pb
	 */
	public void moveRight(PlayerCharacter player){
		if(player.isEnabled()){
			Player pComp = (Player) player.getComponent(Component.PLAYER_ID);
			Movable mComp = (Movable) player.getComponent(Component.MOVABLE_ID);
			//check if the the player is not airbourne 
			// and if the player is not already running at top speed
			if( mComp.getInertiaY() == 0 && mComp.getVelX() <  mComp.getMaxVel()){
				//if these conditions are met, the player's inertia is adjusted
				mComp.setInertiaX(1);
			}
		}
	}

	public void jump(PlayerCharacter player){
		if(player.isEnabled()){
			Player pComp = (Player) player.getComponent(Component.PLAYER_ID);
			Movable mComp = (Movable) player.getComponent(Component.MOVABLE_ID);
			if( pComp.isJumpReleased() && mComp.getInertiaY() == 0){
				mComp.setInertiaY(24);
			}
		}

	}
	
	public void replayRecord(){
		
	}
	
	public void replayStart(){
		
	}
	
	public void replaySpeed(){
		
	}

}
