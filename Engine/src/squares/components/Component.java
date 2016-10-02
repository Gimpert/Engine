package squares.components;

import java.io.Serializable;

import squares.Square;

public abstract class Component implements Serializable, Comparable<Component>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** list of global constants that maps an integer ID to each possible type of component*/
	//TODO: take lists like this and make a <whatever> factory class
	public static final int PHYSICAL_ID = 0;
	
	public static final int VISIBLE_ID = 1;
	
	public static final int MOVABLE_ID = 2;
	
	public static final int HAZARD_ID = 3;
	
	public static final int PATHABLE_ID = 4;
	
	public static final int PLAYER_ID = 5;
	
	public static final int SPAWN_ID = 6;
	
	protected int componentType;
	protected boolean updatable = false;
	
	/**
	 * Constructor for a generic component
	 */
	
	public Component(int i){
		this.componentType = i;
	}
	
	/**
	 * Returns the integer representing the type of component that is being queried
	 */
	public abstract int instanceOf();
	
	/**
	 * Calls any logic for the behavior of the component within the span of one frame or onEvent() call
	 */
	public abstract void update(Square s1);
	/**
	 * checks whether the component requires regular thiupdates each step
	 * @return
	 */
	public boolean isUpdatable(){
		return this.updatable;
	}
	public void setUpdateable(Boolean u){
		this.updatable = u;
	}
	
	public int compareTo(Component compareComponent){
		int compareType = compareComponent.instanceOf();
		return this.componentType - compareType;
	}

}
