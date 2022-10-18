package server;

import java.util.Vector;

import block_breaker.GameSimulator;
import kr.ac.konkuk.ccslab.cm.entity.CMUser;

public class SimulatorThread extends Thread
{
	GameSimulator gs;
	serverSender ss;
	private int fps = 10;
	private Vector<CMUser> members;
	
	public SimulatorThread(serverSender ss)
	{
		this.ss = ss;
	}
	
	public void setMembers(Vector<CMUser> members)
	{
		this.members = members;
	}
	
	@Override
	public void run()
	{
		this.gs = new GameSimulator();
		
		while(gs.isGameEnded() == 0)
		{
			try {
				sleep(1000 / fps);
			} catch(Exception e) {}
			
			gs.simulate();
			sendGameStateToMembers();
		}
		
		// Send end game message to members
		String endMsg = gs.isGameEnded() == 1 ? "GAME#end#true" : "GAME#end#false";
		
		for (int i = 0; i < members.size(); i++)
			this.ss.sendString(endMsg, members.get(i).getName());
	}
	
	private void sendGameStateToMembers()
	{
		for (int i=0; i<members.size(); i++)
			this.ss.sendString(gs.getGameState(), members.get(i).getName());
	}
	
	public void executeKeyEvent(String keyEvent)
	{
		this.gs.executeMsg(keyEvent);
	}
}