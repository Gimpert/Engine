package events.types;

import squares.Square;

/**
 * An event that is raised after one square has been updated and is overlapping
 * another square
 *
 */
public class CollisionEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The square object that triggered the collision */
	private Square s1;
	private Square s2;

	/**
	 * Constructor for a Collision Event
	 * @param i COLLISION_ID
	 * @param s1
	 * @param s2 
	 */
	public CollisionEvent(int i, Square s1, Square s2) {
		super(i);
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public int instanceOf() {
		return eventType;
	}

	public Square getS1() {
		return s1;
	}


	public Square getS2() {
		return s2;
	}


}
