package server;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class serverSender {

	CMServerStub m_serverStub;
	public serverSender(CMServerStub m_serverStub) {
		this.m_serverStub = m_serverStub;
	}
	
	public void sendString(String str, String strTarget) {
		//strTarget : connect된 클라이언트 ID <- 상대방으로 부터 온 이벤트에서 구할 수 있음
		
		CMDummyEvent due = null;
		CMInteractionInfo interInfo = m_serverStub.getCMInfo().getInteractionInfo();
		CMUser myself = interInfo.getMyself();
		
		due = new CMDummyEvent();
		
		//System.out.println("상대client : "+strTarget);
		due.setDummyInfo(str);
		m_serverStub.send(due, strTarget);
		due = null;
	}
}
