import java.awt.*;
import java.util.Scanner;

public class InGame extends Panel{
	Ball ball[];
	Bar bar[];
	Block block[];
	int user;
	
	public InGame() {
		//인게임 판넬 
		setLayout(null);
//		setBackground(Color.BLACK);
		setSize(800,800);
		
		bar = new Bar[4];
		bar[0] = new Bar(10000, 100000, 1);
		bar[1] = new Bar(10000, 100000, 2);
		bar[2] = new Bar(10000, 100000, 3);
		bar[3] = new Bar(10000, 100000, 4);
		for(int i=0; i<bar.length; i++) {
			add(bar[i].imgBox);
		}
		ball = new Ball[0];
		block = new Block[0];
	}

	public void setBall(String location) {
		String[] str = location.split(",");
		this.removeBalls();
		
		ball = new Ball[str.length/2];
		for(int i=0; i<str.length/2;i++) {
			ball[i] = new Ball(Integer.parseInt(str[i*2]),Integer.parseInt(str[i*2+1]));
		}
		
		for(int i=0; i<ball.length; i++) {
			add(ball[i].imgBox);
		}
	}
	
	public void setBar(String location) {
		String[] str = location.split(",");
		for(int i=0; i<4; i++) {
			if (i+1 == this.user && (i == 0 || i == 2)) {
				bar[i].changeImg("images/bar3.png");
			}
			else if(i+1 == this.user && (i == 1 || i == 3)) {
				bar[i].changeImg("images/bar4.png");
			}
			bar[i].print(Integer.parseInt(str[i*2]), Integer.parseInt(str[i*2+1]), i+1);
			
		}
	}

	public void setBlock(String location) {
		String[] str = location.split(",");
		this.removeBlocks();
		
		block = new Block[str.length/2];
		for(int i=0; i<str.length/2;i++) {
			block[i] = new Block(Integer.parseInt(str[i*2]),Integer.parseInt(str[i*2+1]));
		}
		
		for(int i=0; i<block.length; i++) {
			add(block[i].imgBox);
		}
		
	}
	
	public void removeBlocks() {
		for(int i=0; i<block.length; i++) {
			remove(block[i].imgBox);
		}
	}
	
	public void removeBalls() {
		for(int i=0; i<ball.length; i++) {
			remove(ball[i].imgBox);
		}
	}
	
	public void setUser(int a) {
		this.user = a;
	}
}
