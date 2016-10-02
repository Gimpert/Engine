package events.types;

import squares.types.PlayerCharacter;


public class SpawnEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int INITIAL_SPAWN = 0;
	public static final int DEATH_SPAWN = 1;
	
	private PlayerCharacter player;
	private int type;

	public SpawnEvent(int i, PlayerCharacter p, int type) {
		super(i);
		this.player = p;
		this.type = type;
	}

	@Override
	public int instanceOf() {
		return eventType;
		// TODO Auto-generated method stub

	}

	public int getType() {
		return type;
	}

	public PlayerCharacter getPlayer() {
		return player;
	}


}
