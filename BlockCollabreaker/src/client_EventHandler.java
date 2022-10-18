

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

public class client_EventHandler implements CMAppEventHandler{
	ClientMain c;
	client_EventHandler(ClientMain c) {
		super();
		this.c = c;
	}
	
	@Override
	public void processEvent(CMEvent cme) {
		switch(cme.getType()) {
		
		case CMInfo.CM_SESSION_EVENT:
			processSessionEvent(cme);
			break;
		case CMInfo.CM_DUMMY_EVENT:
			processDummyEvent(cme);
			break;
		default:
			return;
		}
	}
	
	public void processSessionEvent(CMEvent cme) {
		
	}
	
	public void processDummyEvent(CMEvent cme) {
		CMDummyEvent due = (CMDummyEvent) cme;
		String str = due.getDummyInfo();
		
		this.c.handleMessage(str);
	}

}
