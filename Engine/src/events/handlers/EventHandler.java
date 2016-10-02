package events.handlers;

import java.io.Serializable;

import events.types.Event;
/**
 * Abstract class defining a basic event handler object
 * @author Corey
 *
 */
public abstract class EventHandler implements Serializable, Comparable<EventHandler>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** id value representing the type of event this handler manages*/
	protected int eventType;
	public EventHandler(int i){
		this.eventType = i;
	}
	/**
	 * Call the event handler to resolve an event
	 */
	public abstract void onEvent(Event e);
	/**
	 * Returns the integer ID that represents the type of event
	 * associated with this handler
	 * @return eventType
	 */
	public int instanceOf(){
		return eventType;
	}
	/**
	 * compares event handlers based on the order of their id's
	 * Id's represent priority, event supertype, and event subtypes
	 * lower super/sub types of events have higher priority
	 * @param e
	 * @return
	 */
	public int compareTo(EventHandler e){
		int compId = e.instanceOf();
		return (this.eventType - compId);
	}

	}
