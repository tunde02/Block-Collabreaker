package server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import kr.ac.konkuk.ccslab.cm.entity.CMGroup;
import kr.ac.konkuk.ccslab.cm.entity.CMMember;
import kr.ac.konkuk.ccslab.cm.entity.CMSession;
import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMInterestEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMConfigurationInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class server_EventHandler implements CMAppEventHandler{

private CMServerStub m_serverStub;

	serverSender ss;
	SimulatorThread[] st;
	
	public server_EventHandler(CMServerStub serverStub) {
		m_serverStub = serverStub;
		ss = new serverSender(m_serverStub);
		st = new SimulatorThread[20];
		for (int i=0; i<20; i++)
			st[i] = new SimulatorThread(ss);
	}
	
	@Override
	public void processEvent(CMEvent cme) {
		//CMSessionEvent se = (CMSessionEvent) cme;
		System.out.println(cme.getType());
		switch(cme.getType()) {//event Type구분하여 switch
		
		case CMInfo.CM_SESSION_EVENT: //login, logout, request_session_info,chage_session, ...
			processSessionEvent(cme);
			break;
		case CMInfo.CM_INTEREST_EVENT: //User_enter, User_leave, User_talk
			processInterestEvent(cme);
			break;
		case CMInfo.CM_DUMMY_EVENT:
			processDummyEvent(cme);
			break;
		
		default:
			System.out.println("default : "+cme.getType());
				return;
		}
	}
	private void processSessionEvent(CMEvent cme) {
		CMConfigurationInfo confInfo = m_serverStub.getCMInfo().getConfigurationInfo();
		CMSessionEvent se = (CMSessionEvent) cme;
		System.out.println("\n!!");
		switch(se.getID()) { //event ID구분하여 switch
		case CMSessionEvent.LOGIN:
			System.out.println("["+se.getUserName()+"] requests login");
			if(confInfo.isLoginScheme()) {
				boolean ret = CMDBManager.authenticateUser(se.getUserName(),se.getPassword(), m_serverStub.getCMInfo());
				if(!ret)
				{
					System.out.println("["+se.getUserName()+"] authentication fails!\n");
					m_serverStub.replyEvent(cme, 0);
				}
				else
				{
					System.out.println("["+se.getUserName()+"] authentication succeeded.\n");
					m_serverStub.replyEvent(cme, 1);
				}
			}
			break;
		case CMSessionEvent.LOGOUT:
			//System.out.println("["+se.getUserName()+"] logs out.");
			System.out.println("["+se.getUserName()+"] logs out.\n");
			break;
		case CMSessionEvent.REQUEST_SESSION_INFO:
			//System.out.println("["+se.getUserName()+"] requests session information.");
			System.out.println("["+se.getUserName()+"] requests session information.\n");
			showGroupInfo();
			break;
		case CMSessionEvent.CHANGE_SESSION:
			//System.out.println("["+se.getUserName()+"] changes to session("+se.getSessionName()+").");
			System.out.println("["+se.getUserName()+"] changes to session("+se.getSessionName()+").\n");
			break;
		case CMSessionEvent.JOIN_SESSION:
			//System.out.println("["+se.getUserName()+"] requests to join session("+se.getSessionName()+").");
			System.out.println("["+se.getUserName()+"] requests to join session("+se.getSessionName()+").\n");
			break;
		case CMSessionEvent.LEAVE_SESSION:
			//System.out.println("["+se.getUserName()+"] leaves a session("+se.getSessionName()+").");
			System.out.println("["+se.getUserName()+"] leaves a session("+se.getSessionName()+").\n");
			break;
		case CMSessionEvent.ADD_NONBLOCK_SOCKET_CHANNEL:
			//System.out.println("["+se.getChannelName()+"] request to add SocketChannel with index("
			//		+se.getChannelNum()+").");
			System.out.println("["+se.getChannelName()+"] request to add a nonblocking SocketChannel with key("
			+se.getChannelNum()+").\n");
			break;
		case CMSessionEvent.REGISTER_USER:
			//System.out.println("User registration requested by user["+se.getUserName()+"].");
			System.out.println("User registration requested by user["+se.getUserName()+"].\n");
			break;
		case CMSessionEvent.DEREGISTER_USER:
			//System.out.println("User deregistration requested by user["+se.getUserName()+"].");
			System.out.println("User deregistration requested by user["+se.getUserName()+"].\n");
			break;
		case CMSessionEvent.FIND_REGISTERED_USER:
			//System.out.println("User profile requested for user["+se.getUserName()+"].");
			System.out.println("User profile requested for user["+se.getUserName()+"].\n");
			break;
		default:
			return;
		}
		
	}
	
	private void processInterestEvent(CMEvent cme)
	{
		CMInterestEvent ie = (CMInterestEvent) cme;
		switch(ie.getID())
		{
		case CMInterestEvent.USER_ENTER:
			//System.out.println("["+ie.getUserName()+"] enters group("+ie.getCurrentGroup()+") in session("
			//		+ie.getHandlerSession()+").");
			System.out.println("["+ie.getUserName()+"] enters group("+ie.getCurrentGroup()+") in session("
					+ie.getHandlerSession()+").\n");
			break;
		case CMInterestEvent.USER_LEAVE:
			//System.out.println("["+ie.getUserName()+"] leaves group("+ie.getHandlerGroup()+") in session("
			//		+ie.getHandlerSession()+").");
			System.out.println("["+ie.getUserName()+"] leaves group("+ie.getHandlerGroup()+") in session("
					+ie.getHandlerSession()+").\n");
			break;
		case CMInterestEvent.USER_TALK:
			//System.out.println("("+ie.getHandlerSession()+", "+ie.getHandlerGroup()+")");
			System.out.println("("+ie.getHandlerSession()+", "+ie.getHandlerGroup()+")\n");
			//System.out.println("<"+ie.getUserName()+">: "+ie.getTalk());
			System.out.println("<"+ie.getUserName()+">: "+ie.getTalk()+"\n");
			break;
		default:
			return;
		}
	}
	
	public void processDummyEvent(CMEvent cme) {
		CMDummyEvent due = (CMDummyEvent) cme;
		String str = due.getDummyInfo();
		//System.out.println("session : "+due.getHandlerSession()+", group : "+due.getHandlerGroup());
		System.out.println("클라이언트로 부터 받은 CMDummyEvent message : "+str);
		//ss.sendString("hihihihi",due.getSender());
		
//		0. 존재하는 방 목록 불러오는 이벤트  - #SYSTEM#getRoomList
//		1. 방 들어가는 이벤트 -  #SYSTEM#enterRoom#room_index - 방입장
//		2. 게임시작누르는 이벤트 - #SYSTEM#gameStart
//		3. 방 나가는 이벤트 - #SYSTEM#exitRoom
		
		String particle[] = str.split("#");
		if(particle[0].equals("SYSTEM")) {
			if(particle[1].equals("getRoomList")) {
				String response = "SYSTEM#roomList#";
				CMInteractionInfo interInfo = m_serverStub.getCMInfo().getInteractionInfo();
				Vector<CMSession> sessions = interInfo.getSessionList();
				Vector<CMGroup> groups = sessions.get(0).getGroupList();//첫번째 세션 그룹정보들
				
				String userCounts = "";
				int s = groups.size();
				for(int j=1;j<s;j++) {
					String groupName = groups.get(j).getGroupName();
					CMMember users = groups.get(j).getGroupUsers();
					int num = users.getMemberNum();
					
					System.out.println(" group name : "+groupName+", group member 수 : "+num);
					
					userCounts += (Integer.toString(num)+",");
					
				}
				response += userCounts;
				
				ss.sendString(response,due.getSender());
				
			}
			else if(particle[1].equals("enterRoom")) {
				String response = "SYSTEM#enterRoom#";
				CMInteractionInfo interInfo = m_serverStub.getCMInfo().getInteractionInfo();
				Vector<CMSession> sessions = interInfo.getSessionList();
				Vector<CMGroup> groups = sessions.get(0).getGroupList();//첫번째 세션 그룹정보들
				int gNum = Integer.parseInt(particle[2]);
				String groupName = groups.get(gNum).getGroupName(); //groups.get(0)은 로비임
				CMMember users = groups.get(gNum).getGroupUsers();
				int num = users.getMemberNum();
				if(num>=4) {// 방이 꽉참
					System.out.println(particle[2]+"번 방이 꽉 찼습니다.");
					response += "false";
					ss.sendString(response,due.getSender());//SYSTEM#enterRoom#false
				}
				else { //방에 들어갈수 있음
					System.out.println(particle[2]+"번 방에 입장합니다.");
					response += ("true#"+particle[2]+"#");
					Vector<CMUser> members = users.getAllMembers();
					String response2 = "";
					for(int i=0;i<num;i++) {
						response2 += members.get(i).getName()+",";
					}
					response += response2;
					ss.sendString(response,due.getSender());//SYSTEM#enterRoom#true#1(1~20)#ID1,ID2..(자신 제외)
					//response = "";
					
					response = "SYSTEM#setUser#"+Integer.valueOf(num+1);
					ss.sendString(response,due.getSender());//자신의 번호 전달 SYETEM#setUser#1(1~4)
				}
				
			}
			else if(particle[1].equals("gameStart")) {
				// Check users number in room
				// 1 ~ 3 : send "SYSTEM#gameStart#false"
				// 4	 : send "SYSTEM#gameStart#true"
				
				String response = "SYSTEM#gameStart#";
				CMInteractionInfo interInfo = m_serverStub.getCMInfo().getInteractionInfo();
				Vector<CMSession> sessions = interInfo.getSessionList();
				Vector<CMGroup> groups = sessions.get(0).getGroupList();
				int gNum = Integer.parseInt(particle[2]);
				CMMember users = groups.get(gNum).getGroupUsers();
				Vector<CMUser> members = users.getAllMembers();
				
				if (members.size() < 4)
				{
					ss.sendString(response + "false", due.getSender());
				}
				else
				{
					for (int i=0; i<members.size(); i++)
						ss.sendString(response + "true", members.get(i).getName());
					
					st[gNum - 1] = new SimulatorThread(ss);
					st[gNum - 1].setMembers(members);
					st[gNum - 1].start();
				}
			}
			else if(particle[1].equals("exitRoom")) {
				
				boolean flag = true;
				String response = "SYSTEM#exitRoom#";
				if(flag==true) {
					response += "true";
					ss.sendString(response, due.getSender());
				}
				else {
					response += "false";
					ss.sendString(response, due.getSender());
				}
			}
			else {
				System.out.println("unexpected requirement : " + particle[1]);
			}
		}
		else if(particle[0].equals("GAME")) {
			int gNum = Integer.parseInt(particle[2]);
			
			st[gNum - 1].executeKeyEvent(due.getDummyInfo());
		}
		else
		{
			System.out.println("### RECEIVED INVALID MESSAGE : " + due.getDummyInfo() + " ###");
		}
	}
	
	public void showGroupInfo() {
		CMInteractionInfo interInfo = m_serverStub.getCMInfo().getInteractionInfo();
		Vector<CMSession> sessions = interInfo.getSessionList();
		int s = sessions.size();
		System.out.println("<모든 그룹의 정보 출력>");
		System.out.println("session 수 : "+s);
		for(int i=0;i<s;i++) {
			Vector<CMGroup> groups = sessions.get(i).getGroupList();
			int ss = groups.size();
			System.out.println("-"+sessions.get(i).getSessionName()+"의 그룹 정보."+ss+"그룹-");
			for(int j=0;j<ss;j++) {
				String str = groups.get(j).getGroupName();
				CMMember users = groups.get(j).getGroupUsers();
				int num = users.getMemberNum();
				Vector<CMUser> members = users.getAllMembers();
				System.out.println(" group name : "+str+", group member 수 : "+num);
				for(int k=0;k<num;k++) {
					System.out.print(members.get(k).getName()+" ");
				}
				System.out.println();
			}
		}
	}
	
	
}
