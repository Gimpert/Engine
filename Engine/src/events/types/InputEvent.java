package events.types;

import squares.types.PlayerCharacter;


public class InputEvent extends Event {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlayerCharacter player;
	private String action;


	public InputEvent(int i, PlayerCharacter p, String a) {
		super(i);
		this.player = p;
		this.action = a;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int instanceOf() {
		return eventType;

	}

	public PlayerCharacter getPlayer() {
		return player;
	}

	public String getAction() {
		return action;
	}

}
