package events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import events.handlers.*;
import events.types.*;
import processing.core.PApplet;
import squares.GameBoard;
/**
 * 
 * @author Owner
 *
 */
public class EventManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//TODO: Maybe establish system such that the event id represents sub-type
	//		and is modded to determine super-type
	/** list of global constants that maps each event type to an int ID*/
	public static final int CLIENT_ID = 10;
	
	public static final int INPUT_ID = 20;

	public static final int SPAWN_ID = 30;
	
	public static final int COLLISION_ID = 40;
	
	public static final int HAZARD_ID = 50;
	
	
	
	//TODO
	/** A java array list containing all the handlers needed by the game */
	public ArrayList<EventHandler> handlerList = new ArrayList<EventHandler>();
	
	/** A java array list containing all the events that have been raised*/
	public ArrayList<Event> eventList = new ArrayList<Event>();
	
	/** A java array list containing all the events raised for this session*/
	//TODO: insignificant until timestamps have been added to events
	//needed for replays (unimplemented)
	public ArrayList<Event> eventHistory = new ArrayList<Event>();
	
	/** Singleton instance of the event manager for the game session*/
	private static EventManager inst = null;
	
	
	


	public static EventManager getEventManager(){
		if( inst == null){
			inst = new EventManager();
		}
		return inst;
	}
	/**
	 * Constructor
	 * @param p
	 */
	private EventManager() {
	}
	
//	private void registerHandlers(){
//		handlerList.add( new ClientHandler(CLIENT_ID) );
//		handlerList.add(new InputHandler(PLAYER_ID));
//		handlerList.add( new CollisionHandler(COLLISION_ID) );
//		handlerList.add(new HazardHandler(HAZARD_ID));
//		handlerList.add(new SpawnHandler(SPAWN_ID));
//	}
	
	public void handleQueue(){
		//sort for priority
		Collections.sort(this.eventList);
		Event e = null;
		while( !this.eventList.isEmpty()){
			//Check if time value is appropriate?
			e = this.eventList.get(0);
			for( EventHandler eHandler: handlerList){
				if( eHandler.instanceOf() == e.instanceOf()){
					eHandler.onEvent(e);
				}
			}
			
			this.eventList.remove(0);
		}
	}
	/**
	 * Registers an EventHandler with the Event Manager
	 * @param h the Handler to be registered
	 */
	public void registerHandler(EventHandler h){
		handlerList.add(h);
		Collections.sort(handlerList);
	}
	/**
	 * Raises Event e to be handled regularly
	 * @param e the event the be queued
	 */
	public void raiseEvent(Event e){
		//TODO: if replay record- log all events
		eventList.add(e);
	}
	
	/**
	 * Raises Event e to be handled immediately
	 * @param e the event to be handled 
	 */
	public void immediateEvent(Event e){
		for( EventHandler eHandler: handlerList){
			if(eHandler.instanceOf() == e.instanceOf()){
				eHandler.onEvent(e);
			}
		}
		//TODO: if replay record - do not record.
		//		event is raised by normal updating
		//		of components or handling of other 
		//		events and the event will be raised 
		//		for the replay without logging
		
	}
	/**
	 * Special case for when Event e is raised
	 * by another event
	 * @param e
	 */
	public void chainEvent(Event e){
		for( EventHandler eHandler: handlerList){
			if(eHandler.instanceOf() == e.instanceOf()){
				eventList.add(e);
			}
			//TODO: if replay record - do not record.
			//		event is raised by normal updating
			//		of components or handling of other 
			//		events and the event will be raised 
			//		for the replay without logging

		}
	}
	
}
