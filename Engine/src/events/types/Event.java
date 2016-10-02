package events.types;

import java.io.Serializable;

/**
 *abstract class for a generic event
 *Events are merely containers for data to be use by corresponding handler
 *
 *TODO to implement new events:
 *Generate event types w/ relevant event data e.g.: objects involved, conditions
 *attached message, time stamp(later?), mode, etc.
 *establish set IDs for event type to be used as id for switch in manager
 *
 */
public abstract class Event implements Serializable, Comparable<Event>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int eventType;
	/**
	 * Returns the event type associated with this event
	 */
	public abstract int instanceOf();
	
	/**
	 * generic constructor for an event object
	 * @param i the id number to be associated with this event
	 */
	public Event(int i){
		this.eventType = i;
	}
	
	public int compareTo(Event compareEvent){
		int compareType = compareEvent.instanceOf();
		return (this.eventType - compareType);
	}
}
