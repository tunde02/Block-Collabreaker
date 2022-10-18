import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Block extends Object{
	
	public Block(int x, int y) {
		String imgUrl = "images/block.png";
		img = new ImageIcon(imgUrl);
		imgBox = new JLabel(img);
		this.imgBox.setBounds(x,y,30,30);
	}
	
	public void print(int x, int y) {
		this.imgBox.setLocation(x,y);
	}
}
