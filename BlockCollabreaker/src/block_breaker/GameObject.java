package block_breaker;

public abstract class GameObject {
	public int x;
	public int y;
	public boolean isAlive = true;
	public abstract boolean isCollidedWith(GameObject obj);
	public abstract void onCollision(GameObject obj);
}
