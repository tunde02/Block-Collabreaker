package block_breaker;

public class GameSimulator
{
	public static final int 	SCREENWIDTH	= 800;
	public static final int 	SCREENHEIGHT	= 800;
	public static final int 	BARLENGTH		= 150;
	public static final int 	BALLRADIUS		= 12;
	public static final int 	BLOCKWIDTH		= 40;
	public static final int 	BLOCKHEIGHT	= 40;
	public static final int 	K				= 3;
	public static final double 	SPEEDWEIGHT	= 0.2;
	
	private int nStage;
	private Stage stage;
	private Bar[] bars;
	private Ball[] balls;
	private Block[] blocks;
	private GameObject[] objects;
	
	public GameSimulator()
	{
		this.nStage = 1;
		this.stage = new Stage(nStage);
		
		initObjects();
	}
	
	private void initObjects()
	{
		// Initialize objects according to stage blueprint.
		
		int[][] barPos = stage.getBarPositions();
		int[][] ballPos = stage.getBallPositions();
		int[][] blockPos = stage.getBlockPositions();
		
		this.bars = new Bar[barPos.length];
		this.balls = new Ball[ballPos.length];
		this.blocks = new Block[blockPos.length];
		this.objects = new GameObject[bars.length + balls.length + blocks.length];
		
		// Initialize Bars
		this.bars[0] = new Bar(barPos[0][0], barPos[0][1], barPos[0][0] + BARLENGTH, barPos[0][1], Side.BOTTOM);
		this.bars[1] = new Bar(barPos[1][0], barPos[1][1], barPos[1][0], barPos[1][1] - BARLENGTH, Side.RIGHT);
		this.bars[2] = new Bar(barPos[2][0], barPos[2][1], barPos[2][0] - BARLENGTH, barPos[2][1], Side.TOP);
		this.bars[3] = new Bar(barPos[3][0], barPos[3][1], barPos[3][0], barPos[3][1] + BARLENGTH, Side.LEFT);

		// Initialize Balls
		this.balls[0] = new Ball(ballPos[0][0], ballPos[0][1], new Vector());
		this.balls[1] = new Ball(ballPos[1][0], ballPos[1][1], new Vector());
		this.balls[2] = new Ball(ballPos[2][0], ballPos[2][1], new Vector());
		this.balls[3] = new Ball(ballPos[3][0], ballPos[3][1], new Vector());
		
		// Initialize Blocks
		for (int i = 0; i < blockPos.length; i++)
			this.blocks[i] = new Block(blockPos[i][0], blockPos[i][1]);
		
		// Initialize objects array
		int index = 0;
		for (int i = 0; i < this.bars.length; i++)
			this.objects[index++] = bars[i];
		for (int i = 0; i < this.balls.length; i++)
			this.objects[index++] = balls[i];
		for (int i = 0; i < this.blocks.length; i++)
			this.objects[index++] = this.blocks[i];
	}
	
	public void simulate()
	{
		// Move all objects in stage once.
		
		for (int i = 0; i < this.balls.length; i++)
		{
			if (!this.balls[i].isAlive || !this.balls[i].isLaunched)
				continue;
			
			if (!this.balls[i].move())
			{
				this.balls[i].isAlive = false;
				return;
			}
			
			// Check Collision			
			GameObject collidedTarget = this.balls[i].getCollisionTarget(this.objects);
			
			if (collidedTarget != null)
			{
				this.balls[i].onCollision(collidedTarget);
			}
		}
	}
	
	public void executeMsg(String msg)
	{
		// Interpret and execute message.
		
		String[] strs = msg.split("#");
		
		// Invalid message
		if (!strs[0].equals("GAME") || !strs[1].equals("keyboard"))
			return;
		
		int index = Integer.valueOf(strs[5]) - 1;
		
		if (strs[3].equals("space") && !bars[index].isLaunched)
		{
			// Launch Ball with new Vector
			if (bars[index].side == Side.BOTTOM)
				balls[index].setVector(new Vector(10, -10));
			else if (bars[index].side == Side.RIGHT)
				balls[index].setVector(new Vector(-10, -10));
			else if (bars[index].side == Side.TOP)
				balls[index].setVector(new Vector(-10, 10));
			else
				balls[index].setVector(new Vector(10, 10));

			bars[index].isLaunched = true;
			balls[index].isLaunched = true;
		}
		else
		{
			if (strs[4].equals("press"))
			{
				double weight = SPEEDWEIGHT;
				String direction = strs[3];
				boolean isInvalid = false;
				
				if (index == 0 || index == 2) // BOTTOM or TOP
				{
					if (direction.equals("right"))
						weight *= 1;
					else if (direction.equals("left"))
						weight *= -1;
					else
						isInvalid = true;
				}
				else // RIGHT or LEFT
				{
					if (direction.equals("up"))
						weight *= -1;
					else if (direction.equals("down"))
						weight *= 1;
					else
						isInvalid = true;
				}
				
				if (!isInvalid)
				{
					bars[index].setSpeed(weight);
					bars[index].move();
					
					// Move the ball is the bar hasn't launched yet
					if (!bars[index].isLaunched)
					{
						int offset = bars[index].side == Side.BOTTOM || bars[index].side == Side.LEFT
								? (BARLENGTH / 2) : -(BARLENGTH / 2);
								
						if (index == 0 || index == 2)
							balls[index].x = bars[index].x + offset;
						else
							balls[index].y = bars[index].y + offset;
					}
				}
			}
			else if (strs[4].equals("release"))
			{
				bars[index].setSpeed(0);
			}
		}
	}
	
	public int isGameEnded()
	{
		if (isCleared())
			return 1;
		else if (isFailed())
			return 2;
		else
			return 0;
	}
	
	private boolean isFailed()
	{
		// If there are no balls in stage, return false.
		
		for (int i = 0; i < this.balls.length; i++)
			if (this.balls[i].isAlive)
				return false;
		
		return true;
	}
	
	private boolean isCleared()
	{
		// If there are no blocks in stage, return false.
		
		for (int i = 0; i < this.blocks.length; i++)
			if (this.blocks[i].isAlive)
				return false;
		
		return true;
	}
	
	public String getGameState()
	{
		// Return a message about locations of objects in stage.
		
		String message = "GAME#OBJECT#POSITIONS&";
		
		message += "BAR/" + this.bars[0].x + "," + this.bars[0].y;
		message += "," + this.bars[1].x + "," + this.bars[1].y;
		message += "," + this.bars[2].x + "," + this.bars[2].y;
		message += "," + this.bars[3].x + "," + this.bars[3].y + "&";
		
		message += "BALL/";
		for (int i = 0; i < this.balls.length - 1; i++)
		{
			if (this.balls[i].isAlive)
			{
				message += this.balls[i].x + "," + this.balls[i].y + ",";
			}
		}
		if (this.balls[this.balls.length - 1].isAlive)
			message += this.balls[this.balls.length - 1].x + "," + this.balls[this.balls.length - 1].y + "&";
		
		message += "BLOCK/";
		for (int i = 0; i < this.blocks.length - 1; i++)
		{
			if (this.blocks[i].isAlive)
			{
				message += this.blocks[i].x + "," + this.blocks[i].y + ",";
			}
		}
		if (this.blocks[this.blocks.length - 1].isAlive)
			message += this.blocks[this.blocks.length - 1].x + "," + this.blocks[this.blocks.length - 1].y;
		
		return message;
	}
}