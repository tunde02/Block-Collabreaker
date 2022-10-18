import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login implements ActionListener{
	TextField id;
	ClientMain c;
	
	public Login(TextField id, ClientMain c) {
		this.id = id;
		this.c = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.c.m_clientStub.setAppEventHandler(this.c.m_eventHandler);
		this.c.m_clientStub.startCM();
		this.c.m_clientStub.loginCM(id.getText(), "1234");
		c.requestRoomList();
		c.myID = id.getText();
	}


}
