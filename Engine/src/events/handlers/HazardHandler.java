package events.handlers;

import squares.Square;
import squares.components.Component;
import squares.components.Movable;
import squares.types.PlayerCharacter;
import events.EventManager;
import events.types.*;

public class HazardHandler extends EventHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HazardHandler(int i) {
		super(i);
	}

	@Override
	public void onEvent(Event e) {
		HazardEvent event = (HazardEvent) e;
		Square s1 = event.getS1();
		switch (event.getHazardType()) {
		case 0://Death, the only hazard so far
				if (s1.isInstanceOf(Component.PLAYER_ID)) {
					playerDeath((PlayerCharacter) s1);
				}
			break;

		default:
			break;
		}
		if( s1.isInstanceOf(Component.PLAYER_ID)){
			
		}
	}

	@Override
	public int instanceOf() {
		return eventType;
	}
	/**
	 * disables the player character so that it is no longer enabled
	 * for events and regular updates
	 * Also cancels out any motion
	 * @param p
	 */
	private void playerDeath(PlayerCharacter p){
		EventManager eventManager = EventManager.getEventManager();
		p.setEnabled(false);
		Movable pmotion = (Movable) p.getComponent(Component.MOVABLE_ID);
		pmotion.stopMotion();
		SpawnEvent respawn = new SpawnEvent(EventManager.SPAWN_ID, p, SpawnEvent.DEATH_SPAWN);
		eventManager.chainEvent(respawn);
	}

}
