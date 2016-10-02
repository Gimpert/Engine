package networkthreads;

import java.io.Serializable;

import processing.core.PApplet;

public abstract class GameSession implements Serializable, Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mode = "";
	private PApplet parent;
	public GameSession(String m,PApplet p) {
		this.mode = m;
		
	}
	
	public String getMode() {
		return mode;
	}
	
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public abstract void takeStep();
	
	public abstract GameSession setup();

	public PApplet getParent() {
		return parent;
	}

	public void setParent(PApplet parent) {
		this.parent = parent;
	}

}
