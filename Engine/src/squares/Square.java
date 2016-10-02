package squares;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.ArrayList;

import events.EventManager;
import events.types.CollisionEvent;
import events.types.Event;
import events.types.HazardEvent;
import squares.components.*;
import squares.types.HazardBlock;
import processing.core.PApplet;
/**
 * The basic representation of a game object
 */
public class Square implements Serializable, Comparable<Square>{



	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default dimension for a square */
	public static final int BASIC_SQUARE = 10;
	
	/** reference to the game loop*/
	private transient PApplet parent;
	
	/**The Object's location on the GameBoard*/
	private int curX = 0;
	private int curY = 0;
	
	
	/** Value used for logic used for special case blocks*/
	private boolean isEnabled = true;
	/** The next object (unused)*/
	private Square next = null;
	
	/** The Object's unique identifier*/
	private transient UID objID = new UID();
	
	/** ArrayList of components that comprise the behavior and properties of the object*/
	private  ArrayList<Component> componentList = new ArrayList<Component>();
	//TODO: perhaps add an update list for components that have logic

	
	/**
	 * Extensible constructor.
	 * TODO: Note --> all other constructors likely deprecated
	 */
	public Square(){
		
	}
	
	/**
	 * Default constructor. Creates an invisible object with no dimensions
	 */
	public Square(PApplet p){
		this.parent = p;
	}

	/**
	 * Constructor for black bordered rectangle object with infinite mass
	 * @param x
	 * @param y
	 * @param l
	 */
	public Square( PApplet p, int x, int y){
		this.parent = p;
		this.curX = x;
		this.curY = y;
//		this.wid = w;
//		this.hgt = h;
//		this.mass = 0;
//		for(int i = 0; i < 2; i++){
//			this.borderColor[i] = 0;
//		}
	}
	/**
	 * Constructor for a square object with mass and color
	 * @param x
	 * @param y
	 * @param w
	 * @param l
	 * @param m
	 */
	public Square( PApplet p, int x, int y, int w, int h, int m, int[] backColor, int[] borderColor){
		this.parent = p;
		this.curX = x;
		this.curY = y;
//		this.wid = w;
//		this.hgt = h;
//		this.mass = m;
//
//		for(int i = 0; i < 2; i++){
//			this.backColor[i] = backColor[i];
//		}
//		
//
//		for(int i = 0; i < 2; i++){
//			this.borderColor[i] = borderColor[i];
//		}
	}
	
//	public boolean isEmpty(){
//		if( (this.curX + this.wid == 0) && (this.curY + this.hgt == 0)){
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	//used to order the list based on their current position along the horizontal axis
	@Override
	public int compareTo(Square compareSquare) {
		int comparex=((Square)compareSquare).getCurX();
		return this.curX-comparex;
	}
	
	public ArrayList<Component> getComponentList() {
		return componentList;
	}
	/**
	 * returns the Object's component matching the passed ID. 
	 * @param i
	 * @return
	 */
	public Component getComponent(int i){
		Component match = null;
		for (Component component : componentList){
			if(component.instanceOf() == i){
				match = component;
				break;
			}
		}
		return match;
		
	}
	
	/**
	 * appends the specified component to the Object's list
	 * @param c
	 */
	public void addComponent(Component c) {
		this.componentList.add(c);
	}
	/**
	 * Checks if the object is an instance of the specified component ID number
	 * @param i
	 * @return
	 */
	public boolean isInstanceOf(int i){
		boolean match = false;
		for(Component c : this.componentList){
			if(i == c.instanceOf()){
				match = true;
				break;
			}
		}
		return match;
	}
	
	public void getComponent( Event e){
		
	}
	/**
	 * update command used for updates resulting from special cases
	 * @param i
	 */
	public void updateComponent(int i){
		for(Component c : this.componentList){
			if(i == c.instanceOf()){
				c.update(this);
			}
		}
	}
	
	public int getCurX() {
		return curX;
	}
	
	public void setCurX(int curX) {
		this.curX = curX;
	}
	
	public int getCurY() {
		return curY;
	}
	
	public void setCurY(int curY) {
		this.curY = curY;
	}
	
	//Unused
	public Square getNext() {
		
		return next;
	}

	//Unused
	public void setNext(Square next) {
		this.next = next;
	}
	
	public UID getObjID() {
		return objID;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void updateComponents() {

		EventManager eventManager = EventManager.getEventManager();
		for (Component component: this.componentList) {
			//update all components marked to recieve them
			if (component.isUpdatable()) {
				
				switch (component.instanceOf()) {//look for special cases
				case 2://movable
					CollisionEvent collisionEvent;
					HazardEvent hazardEvent;
					HazardBlock hazardBlock;
					Square collision = null;
					Movable compMovable = (Movable)component;
					collision = compMovable.checkCollision(this); //check to see if this 
																//update cause a collision
																//with a "physical" object	
					
					if(collision != null){
						if(collision.isInstanceOf(Component.HAZARD_ID)){
							hazardBlock = (HazardBlock) collision;
							Hazard hazard = (Hazard) hazardBlock.getComponent(Component.HAZARD_ID);
							hazardEvent = new HazardEvent(EventManager.HAZARD_ID, this, hazard.getHazardType());
							eventManager.raiseEvent(hazardEvent);
						} else {
							collisionEvent = new CollisionEvent(EventManager.COLLISION_ID, this, collision);
							eventManager.immediateEvent(collisionEvent);
						}
						compMovable.update(this);
					}
					break;
							
				default://default case for an update-able component
					component.update(this);//is to simply call update() on it
					break;
				}
			}
		}
		// TODO Auto-generated method stub
		
	}
	


	

	




}

	
	
		
