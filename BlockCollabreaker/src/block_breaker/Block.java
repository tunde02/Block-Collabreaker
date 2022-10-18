package block_breaker;

public class Block extends GameObject
{
	public int width	= GameSimulator.BLOCKWIDTH;
	public int height	= GameSimulator.BLOCKHEIGHT;
	
	public Block()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public Block(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean isCollidedWith(GameObject obj)
	{
		Ball ball = (Ball) obj;
		int r = ball.r;
		boolean[] isBody = {
				(x <= ball.x && ball.x <= x + width) && (y - r <= ball.y && ball.y <= y),
				(x - r <= ball.x && ball.x <= x) && (y <= ball.y && ball.y <= y + height),
				(x + width <= ball.x && ball.x <= x + width + r) && (y <= ball.y && ball.y <= y + height),
				(x <= ball.x && ball.x <= x + width) && (y + height <= ball.y && ball.y <= y + height + r)
		};
		boolean[] isCorner = {
				Math.pow(ball.x - x, 2) + Math.pow(ball.y - y, 2) <= r * r,
				Math.pow(ball.x - (x + width), 2) + Math.pow(ball.y - y, 2) <= r * r,
				Math.pow(ball.x - x, 2) + Math.pow(ball.y - (y + height), 2) <= r * r,
				Math.pow(ball.x - (x + width), 2) + Math.pow(ball.y - (y + height), 2) <= r * r
		};
		
		for (int i = 0; i < 4; i++)
			if (isBody[i] || isCorner[i])
				return true;
		
		return false;
	}

	@Override
	public void onCollision(GameObject obj)
	{
		// Reflect Ball's vector and reduce Ball's speed
		
		Ball ball = (Ball) obj;
		int r = ball.r;
		boolean[] isBody = {
				(x <= ball.x && ball.x <= x + width) && (y - r <= ball.y && ball.y <= y),
				(x - r <= ball.x && ball.x <= x) && (y <= ball.y && ball.y <= y + height),
				(x + width <= ball.x && ball.x <= x + width + r) && (y <= ball.y && ball.y <= y + height),
				(x <= ball.x && ball.x <= x + width) && (y + height <= ball.y && ball.y <= y + height + r)
		};
		boolean[] isCorner = {
				Math.pow(ball.x - x, 2) + Math.pow(ball.y - y, 2) <= r * r,
				Math.pow(ball.x - (x + width), 2) + Math.pow(ball.y - y, 2) <= r * r,
				Math.pow(ball.x - x, 2) + Math.pow(ball.y - (y + height), 2) <= r * r,
				Math.pow(ball.x - (x + width), 2) + Math.pow(ball.y - (y + height), 2) <= r * r
		};
		
		if (isBody[0])
		{
			// Top
			ball.vector.y *= -1;
		}
		else if (isBody[1])
		{
			// Left
			ball.vector.x *= -1;
		}
		else if (isBody[2])
		{
			// Right
			ball.vector.x *= -1;
		}
		else if (isBody[3])
		{
			// Bottom
			ball.vector.y *= -1;
		}
		else
		{
			Vector newVector;
			
			if (isCorner[0])
			{
				// Top & Left
				newVector = new Vector(ball.x - x, ball.y - y);
			}
			else if (isCorner[1])
			{
				// Top & Right
				newVector = new Vector(ball.x - (x + width), ball.y - y);
			}
			else if (isCorner[2])
			{
				// Bottom & Left
				newVector = new Vector(ball.x - x, ball.y - (y + height));
			}
			else
			{
				// Bottom & Right
				newVector = new Vector(ball.x - (x + width), ball.y - (y + height));
			}
			
			newVector.setSize(ball.vector.size);
			ball.setVector(newVector);
		}
		
		ball.vector.setSize(ball.vector.size * 0.9);
		
		this.isAlive = false;
	}

}
