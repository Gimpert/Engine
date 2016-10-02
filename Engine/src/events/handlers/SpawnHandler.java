package events.handlers;

import squares.GameBoard;
import squares.Square;
import squares.components.Component;
import squares.components.Player;
import squares.components.Spawn;
import squares.types.PlayerCharacter;
import squares.types.SpawnBlock;
import events.types.Event;
import events.types.SpawnEvent;

public class SpawnHandler extends EventHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SpawnBlock[] spawners = new SpawnBlock[GameBoard.MAX_PLAYERS];

	public SpawnHandler(int i) {
		super(i);
		for(int j = 0; j < GameBoard.MAX_PLAYERS;j++){
			spawners[j] = new SpawnBlock(j, (32 + (16 * j) ) * Square.BASIC_SQUARE,
					52 * Square.BASIC_SQUARE);
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEvent(Event e) {
		SpawnEvent sEvent = (SpawnEvent) e;
		PlayerCharacter playerObject = sEvent.getPlayer();
		Player player = (Player) playerObject.getComponent(Component.PLAYER_ID);
		int playerNumber = player.getPlayerNumber();
		//So far, no difference
		//will be used if differences between spawn types are used.
		SpawnBlock spawner;

		spawner = spawners[playerNumber]; //retrieve the spawner the player
											// will be moved to
		playerObject.setCurX(spawner.getCurX());
		playerObject.setCurY(spawner.getCurY());
		Spawn spawn = (Spawn) spawner.getComponent(Component.SPAWN_ID); //retrieve the
																		//spawn component
		spawn.setSpawnQueue(playerObject);
			switch (sEvent.getType()) {
			case 0:
				spawn.setSpawnDelay(2);
				
				break;
				
			case 1:
				spawn.setSpawnDelay(5);
				
				
				break;
			default:
				break;
			}
			spawn.update(spawner); //Send an update to the spawn component.
									//Enables the parent block for updates and physical interactions
	}

	@Override
	public int instanceOf() {
		return eventType;
	}

}
