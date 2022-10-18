import java.awt.*;
import java.awt.event.*;

public class UserCard extends Label{
	public int personCounter;
	public String userName;
	
	public UserCard(String userName, int userIndex) {
		personCounter = 1;
		this.userName = userName;
		
		setText(userName + " user");
		setAlignment(this.CENTER);
		setBounds(100,80+40*userIndex,200,20);
		Color c = new Color(192,192,192);
		setBackground(c);
		
//		EnterRoom e = new EnterRoom();
//		addActionListener(e);
	}
}
