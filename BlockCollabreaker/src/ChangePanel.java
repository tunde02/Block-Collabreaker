import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class ChangePanel implements ActionListener{
	ClientMain c;
	Panel p;
	
	public ChangePanel(ClientMain c,Panel p) {
		this.c = c;
		this.p = p;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.c.setPanel(this.p);
	}

}