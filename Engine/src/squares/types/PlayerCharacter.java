package squares.types;

import processing.core.PApplet;
import squares.Square;
import squares.components.Component;
import squares.components.Movable;
import squares.components.Physical;
import squares.components.Player;
import squares.components.Visible;

public class PlayerCharacter extends Square{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * deprecated
	 * @param p
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param m
	 * @param backColor
	 * @param borderColor
	 */
	public PlayerCharacter(PApplet p, int x, int y, int w, int h, int m, int[] backColor, int[] borderColor) {
		super(p, x, y, w, h, m, backColor, backColor);
	}
	/**
	 * Creates a new player character and 
	 * @param name
	 * @param number
	 * @param color
	 */
	public PlayerCharacter(String name, int number, String color){
		setEnabled(false);
		Player pl = new Player(Component.PLAYER_ID, name, number);
		pl.setUpdateable(false);
		addComponent(pl);
		Movable m = new Movable(Component.MOVABLE_ID);
		m.setUpdateable(true);
		addComponent(m);
		Physical py = new Physical(Component.PHYSICAL_ID);
		py.setUpdateable(false);
		addComponent(py);
		Visible v = new Visible(Component.VISIBLE_ID, color);
		v.setUpdateable(false);
		addComponent(v);
		
	}

	
}