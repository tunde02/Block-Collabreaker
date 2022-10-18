

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class clientSender {

	CMClientStub m_clientStub;
	public clientSender(CMClientStub m_clientStub) {
		this.m_clientStub =  m_clientStub;
	}
	
	public void sendString(String str) {
		CMDummyEvent due = new CMDummyEvent();
		due.setDummyInfo(str);
		m_clientStub.send(due, "SERVER");
		due = null;
	}
	
	public void castString(String str) {

		CMInteractionInfo interInfo = m_clientStub.getCMInfo().getInteractionInfo();
		CMUser myself = interInfo.getMyself();
		
		if(myself.getState() != CMInfo.CM_SESSION_JOIN)
		{
			System.out.println("You should join a session and a group!");
			return;
		}
		CMDummyEvent due = new CMDummyEvent();
		
		due.setHandlerSession(myself.getCurrentSession());
		due.setHandlerGroup(myself.getCurrentGroup());
		due.setDummyInfo(str);
		m_clientStub.cast(due, myself.getCurrentSession(), myself.getCurrentGroup());
		due = null;
	}
}
