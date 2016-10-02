package squares.components;

import squares.Square;

public class Player extends Component{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String playerName = "";
	private int playerNumber;
	private boolean jumpReleased;
	

	public Player(int i, String pName, int pNumber) {
		super(i);
		this.playerName = pName;
		this.playerNumber = pNumber;
		setUpdateable(false);
		}

	@Override
	public int instanceOf() {
		return componentType;
		
	}



	public int getPlayerNumber() {
		return playerNumber;
	}

	public String getPlayerName() {
		return playerName;
	}

	public boolean isJumpReleased() {
		return jumpReleased;
	}

	public void setJumpReleased(boolean jumpReleased) {
		this.jumpReleased = jumpReleased;
	}

	@Override
	public void update(Square s1) {
		// TODO Auto-generated method stub
		
	}
}
