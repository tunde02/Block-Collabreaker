import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ball extends Object{
	
	
	public Ball(int x, int y) {
		String imgUrl = "images/ball.png";
		img = new ImageIcon(imgUrl);
		imgBox = new JLabel(img);
		this.imgBox.setBounds(x-12,y-12,24,24);
	}
	
	public void print(int x, int y) {
		this.imgBox.setLocation(x-12,y-12);
	}
}
