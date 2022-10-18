package block_breaker;

public class Vector
{
	public double x;
	public double y;
	public double realX;
	public double realY;
	public double size;
	
	public Vector()
	{
		this.x = 0;
		this.y = 0;
		this.realX = 0;
		this.realY = 0;
		this.size = 0;
	}
	
	public Vector(double x, double y)
	{
		this.size = Math.sqrt(x * x + y * y);
		if (this.size < 10)
			this.size = 10;
		else if (this.size > 20)
			this.size = 20;
		
		this.x = x / Math.sqrt(x * x + y * y);
		this.y = y / Math.sqrt(x * x + y * y);
		this.realX = x;
		this.realY = y;
	}
	
	public static Vector sum(Vector a, Vector b)
	{
		return new Vector(a.x * a.size + b.x * b.size, a.y * a.size + b.y * b.size);
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ") | " + size;
	}
	
	public void setSize(double size)
	{
		this.size = size;
		if (this.size < 10)
			this.size = 10;
		else if (this.size > 20)
			this.size = 20;
	}
}
