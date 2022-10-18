import java.awt.*;
import java.awt.Event;
import java.awt.event.*;

public class RoomCard extends Button{
	public int personCounter;
	int roomIndex;
	
	
	public RoomCard(int roomIndex) {
		this.roomIndex = roomIndex;
		
		personCounter = 0;
		String roomName = Integer.toString(roomIndex) + " room(" + Integer.toString(personCounter) + "/4)";
		setLabel(roomName);
		setLocation(0,40*roomIndex);
		setSize(400,20);
//		addActionListener(e);
		setFocusable(false);
	}
	
	public void registerAction(ActionListener e) {
		addActionListener(e);
	}
	
	public void setNumber(int i) {
		this.personCounter = i;
		String roomName = Integer.toString(roomIndex) + " room(" + Integer.toString(this.personCounter) + "/4)";
		setLabel(roomName);
	}
}