import java.awt.Button;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bar extends Object{
	public Bar(int x, int y, int user) {
		String imgUrl = "images/bar1.png";
		img = new ImageIcon(imgUrl);
		imgBox = new JLabel(img);
		
		if(user==1) {
			changeImg("images/bar1.png");
			imgBox.setBounds(x-12,y-12,174,24);
		}
		else if(user==2) {
			changeImg("images/bar2.png");
			imgBox.setBounds(x-12,y-162,24,174);
		}
		else if(user==3) {
			changeImg("images/bar1.png");
			imgBox.setBounds(x-162,y-12,174,24);
		}
		else if(user==4) {
			changeImg("images/bar2.png");
			imgBox.setBounds(x-12,y-12,24,174);
		}
	}
	
	public void changeImg(String imgUrl) {
		img = new ImageIcon(imgUrl);
		imgBox.setIcon(img);
	}
	
	public void print(int x, int y, int user) {
		
		if(user==1) {
			imgBox.setLocation(x-12,y-12);
		}
		else if(user==2) {
			imgBox.setLocation(x-12,y-162);
		}
		else if(user==3) {
			imgBox.setLocation(x-162,y-12);
		}
		else if(user==4) {
			imgBox.setLocation(x-12,y-12);
		}
		
	}
}
