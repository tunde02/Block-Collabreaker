import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import javax.swing.JOptionPane;
import block_breaker.GameSimulator;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class ClientMain{
		//선언부 
		Frame f;
		RoomList rl;
		ChangePanel toRL;
		Room r;
		ChangePanel toR;
		InGame ig;
		boolean inGame;
		
		public CMClientStub m_clientStub;
		public client_EventHandler m_eventHandler;
		static BufferedReader br;
		public clientSender cs;
		
		GameSimulator gs;
		
		int userIndex;
		int nowRoomIndex;
		String myID;
		
		public ClientMain() {
			
			ClientMain _this = this;
			
			m_clientStub = new CMClientStub();
			m_eventHandler = new client_EventHandler(this);
			cs = new clientSender(m_clientStub);
			
			//초기화 
			inGame=true;
			//프레임 
			this.f = new Frame("BLOCK BREAKER");//프레임생성
			this.f.setBounds(300,300,800,900);//프레임 위치 및 크기 
			this.f.addWindowListener(new WindowAdapter() {//프레임 종료버튼 활성화 
	            public void windowClosing(WindowEvent we) {
	                System.exit(0);
	            }
	        });
			
			//룸 판넬 만들기 
			this.r = new Room();
			this.toR = new ChangePanel(this,this.r);//룸 판넬로 가는 이벤트 
			
			//인게임 판넬 만들기 
			this.ig = new InGame();

//			this.r.registerGameStartAction(this.toIG);//룸에서 인게임으로 들어가는 버튼 활성화
			this.r.registerGameStartAction(new ActionListener(){ 
	            public void actionPerformed(ActionEvent e) {
	            	_this.requestStartGame();
	            }
	        });
			
			//룸리스트 생성 
			this.rl = new RoomList(20);
			this.toRL = new ChangePanel(this,this.rl);//룸리스트 판넬로 가는 이벤트 
			
			for (int i=0; i<20; i++) {
				int j= i+1;
				this.rl.registerEnterRoomAction(new ActionListener(){
		            public void actionPerformed(ActionEvent e) {
		                _this.requestEnterRoom(j);
		            }
		        },i);
			}
			
			this.r.registerExitRoomAction(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					requestExitRoom();
				}
			});
			
//			GameSimulator gs = new GameSimulator();
//			gs.simulate();
//			handleMessage(gs.getGameState());
//			this.setPanel(this.ig);
			this.requestLogin();
		}
		
		public void requestLogin() {
			//로그인 판넬 
			Panel loginWindow = new Panel();
			loginWindow.setBounds(0,20,400,400);
			loginWindow.setLayout(null);
			
			Label lid = new Label("ID :");
            TextField id = new TextField(10);
            lid.setBounds(20,140,80,20);
            id.setBounds(100,140,200,20);
            
            Button submit = new Button("접속");
            submit.setBounds(20,180,280,20);
            submit.setFocusable(false);
            
            Login l = new Login(id, this);
            submit.addActionListener(l);
            
            loginWindow.add(lid);
            loginWindow.add(id);
            loginWindow.add(submit);
			
			setPanel(loginWindow);
		}
		
		public void setPanel(Panel p) {
			this.f.removeAll();
			this.f.add(p);
			this.f.setVisible(true);//프레임 보이게 
			this.f.requestFocus();//포커스 프레임으로 
			this.f.setResizable(false);//크기 조정 불가 
			
		}
		
		private void setKeyboardEvent() {
			String defaultString = "GAME#keyboard#" + nowRoomIndex + "#";
			this.f.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e){
					if(inGame) {
//						System.out.println(e.getKeyCode());
						if(e.VK_UP == e.getKeyCode()) {
							cs.sendString(defaultString + "up#press#" + Integer.toString(userIndex));
//							handleMessage("GAME#OBJECT#POSITIONS&BAR/0,0,0,24,0,48,0,72,0,128&BALL/300,300&BLOCK/600,600");
//							gs.executeMsg(defaultString + "up#press#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						else if(e.VK_DOWN == e.getKeyCode()) {
							cs.sendString(defaultString + "down#press#" + Integer.toString(userIndex));
//							handleMessage("GAME#OBJECT#POSITIONS&BAR/0,0,0,24,0,48,0,72,0,128&BALL/300,300&BLOCK/700,600");
//							gs.executeMsg(defaultString + "down#press#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						else if(e.VK_RIGHT == e.getKeyCode()) {
							cs.sendString(defaultString + "right#press#" + Integer.toString(userIndex));
//							gs.executeMsg(defaultString + "right#press#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						else if(e.VK_LEFT == e.getKeyCode()) {
							cs.sendString(defaultString + "left#press#" + Integer.toString(userIndex));
//							gs.executeMsg(defaultString + "left#press#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						else if(e.VK_SPACE == e.getKeyCode()) {
							cs.sendString(defaultString + "space#press#" + Integer.toString(userIndex));
//							gs.executeMsg(defaultString + "space#press#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						
					}
				}
			});
			this.f.addKeyListener(new KeyAdapter() { 
				public void keyReleased(KeyEvent e){
					if(inGame) {
//						System.out.println(e.getKeyCode());
						if(e.VK_UP == e.getKeyCode()) {
							cs.sendString(defaultString + "up#release#" + Integer.toString(userIndex));
//							gs.executeMsg(defaultString + "up#release#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						else if(e.VK_DOWN == e.getKeyCode()) {
							cs.sendString(defaultString + "down#release#" + Integer.toString(userIndex));
//							gs.executeMsg(defaultString + "down#release#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						else if(e.VK_RIGHT == e.getKeyCode()) {
							cs.sendString(defaultString + "right#release#" + Integer.toString(userIndex));
//							gs.executeMsg(defaultString + "up#press#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						else if(e.VK_LEFT == e.getKeyCode()) {
							cs.sendString(defaultString + "left#release#" + Integer.toString(userIndex));
//							gs.executeMsg(defaultString + "left#release#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
						else if(e.VK_SPACE == e.getKeyCode()) {
							cs.sendString(defaultString + "space#release#" + Integer.toString(userIndex));
//							gs.executeMsg(defaultString + "space#release#" + Integer.toString(_this.userIndex));
//							gs.simulate();
//							handleMessage(gs.getGameState());
						}
					}
				}
			});
		}
		
		public void requestStartGame() {
			//게임 시작하겠다고 요청 
			this.cs.sendString("SYSTEM#gameStart#" + this.nowRoomIndex);
		}
		
		// !! REQUEST !!
		public void requestRoomList() {
			//서버에 룸 리스트 요청 
			this.cs.sendString("SYSTEM#getRoomList");
		}
		
		public void requestEnterRoom(int roomIndex) {
			String tmp = "SYSTEM#enterRoom#" + Integer.toString(roomIndex);
			this.cs.sendString(tmp);
		}
		
		public void requestExitRoom() {
			//서버에 방 만들겠다고 요청 
			this.cs.sendString("SYSTEM#exitRoom");
		}
		
		// !! RESPONSE !!
		
		public void setRoomList(String numberStr) {
			String number [] = numberStr.split(",");
			ClientMain _this = this;
			
			//룸리스트 생성 
			this.rl = new RoomList(20);
			for (int i=0; i<20; i++) {
				int j= i+1;
				this.rl.registerEnterRoomAction(new ActionListener(){
		            public void actionPerformed(ActionEvent e) {
		                _this.requestEnterRoom(j);
		            }
		        },i);
			}
			for(int i=0; i<20; i++) {
				this.rl.setRoomNumber(i, number[i]);
			}
			this.toRL = new ChangePanel(this,this.rl);//룸리스트 판넬로 가는 이벤트 
			
			this.setPanel(this.rl);
		}
		
		public void enterRoom(String roomIndex, String users) {
			String user[] = users.split(",");
			
			//방이름 설정 
			this.r.setRoomName(roomIndex,myID);
			
			this.nowRoomIndex = Integer.valueOf(roomIndex);
			
			if(!users.equals("none")) {
				for(int i=0; i< user.length; i++) {
					this.r.pushUser(user[i],userIndex);
				}
			}
			
			// 그룹 변경 
			this.m_clientStub.changeGroup("g" + Integer.toString(Integer.parseInt(roomIndex)+1));
			this.cs.castString("SYSTEM#enterUser#" + m_clientStub.getMyself().getName());
			
			
			
			this.setPanel(r);
		}
		
		public void setUser(String user) {
			this.userIndex = Integer.parseInt(user);
			this.ig.setUser(Integer.parseInt(user));
			if(this.userIndex == 1) {
				this.r.add(this.r.gameStart);
			}
			else {
				this.r.remove(this.r.gameStart);
			}
		}
		
		public void enterUser(String userName) {
			this.r.pushUser(userName,userIndex);
		}
		
		public void gameStart() {
			this.inGame = true;
			this.setKeyboardEvent();
			this.setPanel(this.ig);
		}
		
		public void exitRoom() {
			
			this.r.resetRoom();
			
			this.cs.castString("SYSTEM#exitUser#" + m_clientStub.getMyself().getName()+"#"+Integer.toString(userIndex));
			this.m_clientStub.changeGroup("g1");

			this.requestRoomList();
		}
		
		public void exitUser(String userName, String exiter_index) {
			System.out.println(userName + " : " + exiter_index);
			//this.cs.castString("SYSTEM#exitrUser#" + m_clientStub.getMyself().getName()); //중복메세지
			if(this.userIndex > Integer.parseInt(exiter_index)) {
				this.userIndex--;
				this.ig.setUser(this.userIndex);
			}
			this.r.exitUser(userName,userIndex);
		}
		
		public void endGame(String win) {
			this.inGame = false;
			this.setPanel(this.r);
			if(win.equals("true")) {
				JOptionPane.showMessageDialog(f, "victory!","BlockCollabreaker", JOptionPane.PLAIN_MESSAGE);
			}
			else if(win.equals("false")) {
				JOptionPane.showMessageDialog(f, "fail","BlockCollabreaker", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		public void setObjects(String str) {
			String[] objectStr = str.split("&");
			String[] tmp;
			for(int i=1; i<objectStr.length; i++) {
				tmp = objectStr[i].split("/");
				
				if(tmp[0].equals("BALL")) {
					this.ig.setBall(tmp[1]);
				}
				else if(tmp[0].equals("BLOCK")) {
					this.ig.setBlock(tmp[1]);
				}
				else if(tmp[0].equals("BAR")){ 
					this.ig.setBar(tmp[1]);
				}
			}
			setPanel(this.ig);
		}
		
		public void handleMessage(String str) {
			String parsingStr[] = str.split("#");
			//System.out.println("session : "+due.getHandlerSession()+", group : "+due.getHandlerGroup());
//			System.out.println("서버로부터 전송 받은 dummy message : "+str+">>");
			
			switch(parsingStr[0]) {
			case "GAME" :
				switch(parsingStr[1]) {
				case "end" :
					this.endGame(parsingStr[2]);
					break;
				case "OBJECT" : 
					this.setObjects(parsingStr[2]); 
					break;
				}
			case "SYSTEM" :
				switch(parsingStr[1]) {
				
				case "roomList" : //#SYSTEM#roomList#명수,명수,명수...
					this.setRoomList(parsingStr[2]);
					break;
					
				case "enterRoom" : 	//#SYSTEM#enterRoom#true#roomIndex#이름,이름,이름,이
					if(parsingStr[2].equals("true")) {
						if(parsingStr.length == 5)
							this.enterRoom(parsingStr[3], parsingStr[4]);
						else
							this.enterRoom(parsingStr[3], "none");
					}
					else if(parsingStr[2].equals("false")) {
						JOptionPane.showMessageDialog(f, "방이 꽉 차있습니다.","BlockCollabreaker", JOptionPane.PLAIN_MESSAGE);
						this.requestRoomList();
					}
					break;
					
				case "setUser" : //#SYSTEM#setUser#1 ( or 2 or 3 or 4)
					this.setUser(parsingStr[2]);
					break;
					
				case "enterUser" : // #SYSTEM#enterUser#유저이름
					this.enterUser(parsingStr[2]);
					break;
					
				case "gameStart" : // #SYSTEM#gameStart#false
					if(parsingStr[2].equals("true")) {
						this.gameStart();
					}
					else if(parsingStr[2].equals("false")) {
						
						JOptionPane.showMessageDialog(f, "방의 인원수가 모자랍니다." ,"BlockCollabreaker", JOptionPane.PLAIN_MESSAGE);
					}
					break;
					
				case "exitRoom" : //#SYSTEM#exitRoom#true  
					if(parsingStr[2].equals("true")) {
						this.exitRoom();
					}
					else if(parsingStr[2].equals("false")) {
						JOptionPane.showMessageDialog(f, "게임이 시작되어 방에서 나갈 수 없습니다.","BlockCollabreaker", JOptionPane.PLAIN_MESSAGE);
					}
					break;
					
				case "exitUser" : //#SYSTEM#exitUser#유저이름 #userIndex
					this.exitUser(parsingStr[2],parsingStr[3]);
					break;
				}	
			}
		}

	public static void main(String[] args) {
		//메인 클라이언트 창 선언 
		ClientMain c = new ClientMain();
	}
}