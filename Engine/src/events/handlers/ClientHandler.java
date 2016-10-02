package events.handlers;

import networkthreads.ServerSession;
import events.types.ClientEvent;
import events.types.Event;

public class ClientHandler extends EventHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServerSession server = null;

	public ClientHandler(int i, ServerSession s) {
		super(i);
		this.server = s;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub
		ClientEvent cEvent = (ClientEvent) e;
		switch (cEvent.getMessageCode()){
		case 0: //connection: only one truly implemented
			server.addClient(e);
			
			break;
		case 1://disconnect
		
			break;
			
		case 2://Update request
			
			break;
		}

	}

	@Override
	public int instanceOf() {
		return eventType;
	}
	
	

}
