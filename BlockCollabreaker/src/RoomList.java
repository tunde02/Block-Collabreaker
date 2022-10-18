import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionListener;

public class RoomList extends Panel{
	RoomCard r[];
	Button makeRoom;
	Label title;
	
	public RoomList(int roomCounter) {
		//룸리스트 판넬  
//		p = new Panel();
		setLayout(null);
		setSize(400,roomCounter*40+40);
		
		//타이틀 라벨 
		this.title = new Label("ROOM LIST",Label.CENTER);
		this.title.setBounds(0,0,400,20);			
		
		this.r = new RoomCard[roomCounter];
		for(int i=0; i<roomCounter;i++) {
			this.r[i] = new RoomCard(i+1);
			this.r[i].setFocusable(false);
			add(this.r[i]);
		}
		
		add(this.title);
	}
	
	public void registerEnterRoomAction(ActionListener e, int roomIndex) {
		this.r[roomIndex].registerAction(e);
	}
	
	public void setRoomNumber(int i, String j) {
		this.r[i].setNumber(Integer.parseInt(j));
	}
}
