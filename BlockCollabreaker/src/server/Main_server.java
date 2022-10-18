package server;

import java.util.Queue;
import java.util.Vector;

import kr.ac.konkuk.ccslab.cm.entity.CMGroup;
import kr.ac.konkuk.ccslab.cm.entity.CMSession;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class Main_server {
	
	private CMServerStub m_serverStub;
	private server_EventHandler m_eventHandler;
	//private Synchronized Queue Q;
	public Main_server() {
		m_serverStub = new CMServerStub();
		m_eventHandler = new server_EventHandler(m_serverStub);
	}
	
	
	public CMServerStub getServerStub() {
		return m_serverStub;
	}
	public server_EventHandler getServerEventHandler() {
		return m_eventHandler;
	}
	
	public void printGroupInfo() {
		CMInteractionInfo interInfo = m_serverStub.getCMInfo().getInteractionInfo();
		Vector<CMSession> sessions = interInfo.getSessionList();
		int s = sessions.size();
		System.out.println("<모든 그룹의 정보 출력>");
		System.out.println("session 수 : "+s);
		for(int i=0;i<s;i++) {
			System.out.println("-"+sessions.get(i).getSessionName()+"의 그룹 정보-");
			Vector<CMGroup> groups = sessions.get(i).getGroupList();
			int ss = groups.size();
			for(int j=0;j<ss;j++) {
				String str = groups.get(j).getGroupName();
				System.out.println(" group name : "+str);
			}
		}
	}

	
	public static void main(String[] args) {

		Main_server server = new Main_server();
		CMServerStub cmStub = server.getServerStub();
		cmStub.setAppEventHandler(server.getServerEventHandler());//EventHandler 등록
		cmStub.startCM();
		System.out.println();
		//server.printGroupInfo();
		//System.out.println(cmStub.getLoginUsers().getMemberNum());
	}

}
