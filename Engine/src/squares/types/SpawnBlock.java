package squares.types;

import processing.core.PApplet;
import squares.Square;
import squares.components.Component;
import squares.components.Physical;
import squares.components.Spawn;
import squares.components.Visible;

public class SpawnBlock extends Square {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for a Spawner
	 * @param number
	 * @param x
	 * @param y
	 */
	public SpawnBlock(int number, int x, int y) {
		setCurX(x);
		setCurY(y);
		setEnabled(false);
		Spawn sp = new Spawn(Component.SPAWN_ID, number);
		sp.setUpdateable(false);
		addComponent(sp);
		Physical py = new Physical(Component.PHYSICAL_ID, 0);
		py.setUpdateable(false);
		addComponent(py);
		Visible v = new Visible(Component.VISIBLE_ID, Visible.BLACK_COLORS);
		v .setUpdateable(false);
		addComponent(v);
	}

	public SpawnBlock(PApplet p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	public SpawnBlock(PApplet p, int x, int y, int w, int h) {
		super(p, x, y);
		// TODO Auto-generated constructor stub
	}

	public SpawnBlock(PApplet p, int x, int y, int w, int h, int m, int[] backColor,
			int[] borderColor) {
		super(p, x, y, w, h, m, backColor, borderColor);
		// TODO Auto-generated constructor stub
	}

}
