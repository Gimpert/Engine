package events.types;

import squares.Square;

/**
 * An event that is raised after one square has collided with another
 * square that possesses a hazardous effect
 * @author Owner
 *
 */
public class HazardEvent extends Event {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** list of global constants that maps an int ID to each type of hazard */
	public static final int DEATH_ID = 0;
	//TODO move to handler
	
	/** The square that is being affected*/
	private Square s1;
	private int hazardType;
	public HazardEvent(int i, Square s1, int h) {
		super(i);
		this.s1 = s1;
		this.hazardType = h;
	}

	@Override
	public int instanceOf() {
		return eventType;

	}

	public Square getS1() {
		return s1;
	}

	public int getHazardType() {
		return hazardType;
	}



}
