package block_breaker;

enum Side { BOTTOM, LEFT, RIGHT, TOP };

public class Bar extends GameObject
{
	public Side side;
	public int x2, y2;
	public boolean isLaunched = false;
	public double speed; // -1.5 / -1.499 ~ -1 / 0 / 1 ~ 1.499 / 1.5
	public int length	= GameSimulator.BARLENGTH;
	public int r		= GameSimulator.BALLRADIUS;
	public int k		= GameSimulator.K;
	
	public Bar(int x, int y, int x2, int y2, Side side)
	{
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.speed = 0.0;
		this.side = side;
	}
	
	public void setSpeed(double value)
	{
		if (value == 0)
		{
			this.speed = 0;
		}
		else if (this.speed == 0)
		{
			// If now speed is zero, then it become 1 or -1 according to value's sign
			this.speed = value > 0 ? 1 : -1;
		}
		else if (this.speed * value < 0)
		{
			// When user try to change Bar's direction
			this.speed = this.speed > 0 ? -1 : 1;
		}
		else
		{
			// Add value to speed and check if speed is still in range
			this.speed += value;
			this.speed = this.speed > 1.5 ? 1.5 : this.speed;
			this.speed = this.speed < -1.5 ? -1.5 : this.speed;
		}
	}
	
	public void move()
	{
		// Move Bar's position.
		// Bar's position is limited to stay in the screen.
		
		switch (this.side)
		{
		case BOTTOM:
			this.x += this.speed * k;
			
			if (this.x < r)
				this.x = r;
			else if (this.x > GameSimulator.SCREENWIDTH - (length + r))
				this.x = GameSimulator.SCREENWIDTH - (length + r);
			
			this.x2 = this.x + length;
			
			break;
			
		case RIGHT:
			this.y += this.speed * k;
			
			if (this.y > GameSimulator.SCREENHEIGHT - r)
				this.y = GameSimulator.SCREENHEIGHT - r;
			else if (this.y < length + r)
				this.y = length + r;
			
			this.y2 = this.y - length;
			
			break;
			
		case TOP:
			this.x += this.speed * k;
			
			if (this.x > GameSimulator.SCREENWIDTH - r)
				this.x = GameSimulator.SCREENWIDTH - r;
			else if (this.x < GameSimulator.BARLENGTH + r)
				this.x = GameSimulator.BARLENGTH + r;
			
			this.x2 = this.x - length;
			
			break;
			
		case LEFT:
			this.y += this.speed * k;
			
			if (this.y < r)
				this.y = r;
			else if (this.y > GameSimulator.SCREENHEIGHT - (length + r))
				this.y = GameSimulator.SCREENHEIGHT - (length + r);
			
			this.y2 = this.y + length;
			
			break;
		}
	}
	
	@Override
	public boolean isCollidedWith(GameObject obj)
	{
		// Check if Bar collide with Ball.
		// Based on distance between two points in coordinate plane.
		
		Ball ball = (Ball) obj;
		boolean onCorner1 = false, onCorner2 =false;
		double distance = 0;
		
		switch (this.side)
		{
		case BOTTOM:
			onCorner1 = ball.x < this.x;
			onCorner2 = ball.x > this.x2;
			distance = Math.abs(this.y - ball.y);
			break;
			
		case LEFT:
			onCorner1 = ball.y < this.y;
			onCorner2 = ball.y > this.y2;
			distance = Math.abs(this.x - ball.x);
			break;
			
		case RIGHT:
			onCorner1 = ball.y > this.y;
			onCorner2 = ball.y < this.y2;
			distance = Math.abs(this.x - ball.x);
			break;
			
		case TOP:
			onCorner1 = ball.x > this.x;
			onCorner2 = ball.x < this.x2;
			distance = Math.abs(this.y - ball.y);
			break;
			
		default:
			return false;
		}
		
		if (onCorner1 || onCorner2)
		{
			int x = onCorner1 ? this.x : this.x2;
			int y = onCorner2 ? this.y : this.y2;
			
			return Math.pow((ball.x - x), 2) + Math.pow((ball.y - y), 2) <= 4 * r * r;
		}
		else
		{
			return distance <= 2 * r;
		}
	}
	
	@Override
	public void onCollision(GameObject obj)
	{
		// Change Ball's vector which is collided with Bar.
		// There are 2 cases when Ball collide with Bar - Body, Corner
		// Body		: use Bar's vector list
		// Corner	: calculate new vector because Bar's corner is arc
		
		// accels	- Ball's speed increase weight according to Bar's speed
		// index	- expression of Bar's speed by index
		// theta	- a quarter angle of the Ball's vector
		// angles	- value of how much to tilt the Ball's new vector

		Ball ball = (Ball) obj;
		Vector newVector;
		double[] accels = { 1.0, 1.25, 1.5 };
		int[] angles = { 4, 3, 2 };
		int index = speedToIndex();
		boolean onCorner1 = false, onCorner2 = false, isOpposite = false;
		double theta = 0, newX = 0, newY = 0;
		
		switch (this.side)
		{
		case BOTTOM:
			onCorner1 = ball.x < this.x;
			onCorner2 = ball.x > this.x2;
			isOpposite = this.speed * ball.vector.x < 0;
			theta = Math.atan(ball.vector.y / ball.vector.x) / 4;
			newX = ball.vector.x;
			newY = -1 * ball.vector.x * Math.tan(theta * angles[index]);
			break;
			
		case LEFT:
			onCorner1 = ball.y < this.y;
			onCorner2 = ball.y > this.y2;
			isOpposite = this.speed * ball.vector.y < 0;
			theta = Math.atan(ball.vector.x / ball.vector.y) / 4;
			newX = -1 * ball.vector.y * Math.tan(theta * angles[index]);
			newY = ball.vector.y;
			break;
			
		case RIGHT:
			onCorner1 = ball.y > this.y;
			onCorner2 = ball.y < this.y2;
			isOpposite = this.speed * ball.vector.y > 0;
			theta = Math.atan(ball.vector.x / ball.vector.y) / 4;
			newX = -1 * ball.vector.y * Math.tan(theta * angles[index]);
			newY = ball.vector.y;
			break;
			
		case TOP:
			onCorner1 = ball.x > this.x;
			onCorner2 = ball.x < this.x2;
			isOpposite = this.speed * ball.vector.x > 0;
			theta = Math.atan(ball.vector.y / ball.vector.x) / 4;
			newX = ball.vector.x;
			newY = -1 * ball.vector.x * Math.tan(theta * angles[index]);
			break;
			
		default:
			break;
		}
		
		if (onCorner1 || onCorner2) // when collided with corner
		{
			int x = onCorner1 ? this.x : this.x2;
			int y = onCorner1 ? this.y : this.y2;
			newVector = new Vector((ball.x - x), (ball.y - y));
			
			newVector.setSize(this.speed == 0 ? ball.vector.size : ball.vector.size * accels[index]);
		}
		else // when collided with body
		{
			// speed == 0		  - incidence : reflection = 1 : 1 
			// 1.0 <= speed < 1.5 - incidence : reflection = 3 : 2
			// 1.5 <= speed		  - incidence : reflection = 3 : 1
			newVector = new Vector(newX, newY);
			newVector.setSize(this.speed == 0 ? ball.vector.size : ball.vector.size * accels[index]);
			
			// Reverse direction of Ball's vector if Bar and Ball's directions are opposite
			if (isOpposite)
			{
				if (this.side == Side.BOTTOM || this.side == Side.TOP)
					newVector.x *= -1;
				else
					newVector.y *= -1;
				newVector.size *= 0.75;
			}
		}
		
		ball.setVector(newVector);
	}
	
	private int speedToIndex()
	{
		// -1.5 / -1.499 ~ -1 / 0 / 1 ~ 1.499 / 1.5
		if (this.speed <= -1.5 || this.speed >= 1.5)
			return 2;
		else if ((-1.5 < this.speed && this.speed <= -1)
				|| (1 <= this.speed && this.speed < 1.5))
			return 1;
		else
			return 0;
	}
}
