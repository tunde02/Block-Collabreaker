import java.awt.*;
import java.awt.event.*;

public class Room extends Panel{
	UserCard u[];
	Button exitRoom;
	Button gameStart;
	Label title;
	Label myName;
	int userNum=0;
	String myID;
	
	public Room() {
		//룸리스트 판넬  
		setLayout(null);
		setFocusable(false);
		
		//유저카드 초기화 
		u = new UserCard[4];

		//게임시작 버튼
		gameStart = new Button("게임시작");
		gameStart.setFocusable(false);
		gameStart.setBounds(40,40,120,20);
		
		//방 나가기 버튼
		exitRoom = new Button("방나가기");
		exitRoom.setFocusable(false);
		exitRoom.setBounds(240,40,120,20);
		
		myName = new Label("#");
		myName.setBounds(440,40,120,20);

		add(exitRoom);
		add(myName);
	}
	
	public void setRoomName(String roomIndex, String id) {
		//타이틀 라벨 
		String roomName = roomIndex + " ROOM";
		this.title = new Label(roomName,Label.CENTER);
		this.title.setBounds(0,0,400,20);
		add(this.title);
		
		myID = id;
	}
	
	public void pushUser(String userName, int idx) {
		u[userNum] = new UserCard(userName,userNum);
		add(u[userNum++]);
		myName.setText("id : "+myID+", idx : "+Integer.toString(idx));
	}
	
	public void resetRoom() {
		removeAll();
		add(exitRoom);
		add(myName);
		
//		for(int i=0; i<userNum; i++) {
//			remove(u[i]);
//		}
//		remove(this.gameStart);
		this.userNum = 0;
	}
	
	public void exitUser(String userName,int idx) {
		boolean find = false;
		for(int i=0; i<userNum; i++) {
			if (find) {
				remove(u[i-1]);
				u[i-1] = new UserCard(u[i].userName, i-1);
				add(u[i-1]);
			}
			else if(u[i].userName.equals(userName)) {
				remove(u[i]);
				find = true;
			}
		}
		if(find) {
			userNum --;
			remove(u[userNum]);
		}
		if(myID.equals(u[0].userName))
			add(gameStart);
		
		myName.setText("id : "+myID+", idx : "+Integer.toString(idx));
	}
	
	public void registerGameStartAction(ActionListener e) {
		gameStart.addActionListener(e);
	}
	
	public void registerExitRoomAction(ActionListener e) {
		exitRoom.addActionListener(e);
	}
}
