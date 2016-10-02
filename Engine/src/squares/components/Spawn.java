package squares.components;

import squares.GameLoop;
import squares.Square;
import squares.types.PlayerCharacter;

public class Spawn extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int spawnerNumber;
	private int spawnDelay = 0;
	
	private PlayerCharacter spawnQueue = null;
	
	
	public Spawn(int i, int n) {
		super(i);
		this.spawnerNumber = n;
		
	}

	@Override
	public int instanceOf() {
		return componentType;
		
	}

	@Override
	public void update(Square s1) {
		if( this.isUpdatable() == false ){//Spawn Event
			this.setUpdateable(true);//spawner should have it's queue set
			//allow regular operations to create the spawn.
			s1.setEnabled(true);	//enable collision and regular updates for
									//parent object
			


		}else{
			if(spawnDelay != 0){
				spawnDelay--;
			} else {
				
				spawnQueue.setEnabled(true);
				s1.setEnabled(false);
				this.setUpdateable(false);
			}
		}
	}

	public int getSpawnerNumber() {
		return spawnerNumber;
	}
	
	public int getSpawnDelay(){
		return spawnDelay;
		
	}
	
	/**
	 * 
	 * @param dIn Seconds
	 */
	public void setSpawnDelay(int d){
		this.spawnDelay = GameLoop.FRAME_RATE * d;
	}

	public PlayerCharacter getSpawnQueue() {
		return spawnQueue;
	}

	public void setSpawnQueue(PlayerCharacter spawnQueue) {
		this.spawnQueue = spawnQueue;
	}


}
