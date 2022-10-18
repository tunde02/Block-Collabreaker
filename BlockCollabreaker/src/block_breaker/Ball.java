package block_breaker;

public class Ball extends GameObject
{
	public int r = GameSimulator.BALLRADIUS;
	public boolean isLaunched = false;
	public Vector vector;
	
	public Ball()
	{
		this.x = 0;
		this.y = 0;
		this.vector = new Vector();
	}
	
	public Ball(int x, int y, Vector vector)
	{
		this.x = x;
		this.y = y;
		this.vector = vector;
	}
	
	public void setVector(Vector vector)
	{
		this.vector = vector;
	}
	
	public boolean move()
	{
		this.x += this.vector.x * this.vector.size;
		this.y += this.vector.y * this.vector.size;
		
		if (this.x <= 0 || this.x >= GameSimulator.SCREENWIDTH || this.y <= 0 || this.y >= GameSimulator.SCREENHEIGHT)
			return false;
		
		return true;
	}

	public GameObject getCollisionTarget(GameObject[] objects)
	{
		// Check collision with objects in object list.
		// return object if there is a collided object.
		
		for (GameObject obj : objects)
		{
			if (obj.isAlive && obj.isCollidedWith(this))
			{
				if (obj == this) continue; // except self
				return obj;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean isCollidedWith(GameObject obj)
	{
		if (obj instanceof Bar)
		{
			return obj.isCollidedWith(this);
		}
		else if (obj instanceof Ball && ((Ball) obj).isLaunched)
		{
			return Math.pow((this.x - obj.x), 2) + Math.pow((this.y - obj.y), 2) <= r * r;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void onCollision(GameObject obj)
	{
		if (obj instanceof Ball)
		{
			Vector vectorSum = Vector.sum(this.vector, ((Ball) obj).vector);
			Vector repulsiveVector1 = new Vector(this.x - obj.x, this.y - obj.y);
			Vector repulsiveVector2 = new Vector(obj.x - this.x, obj.y - this.y);
			
			this.setVector(Vector.sum(vectorSum, repulsiveVector1));
			((Ball) obj).setVector(Vector.sum(vectorSum, repulsiveVector2));
		}
		else
		{
			obj.onCollision(this);
		}
	}
}
