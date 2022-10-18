package block_breaker;

public class Stage
{
	private int screenWidth 	= GameSimulator.SCREENWIDTH;
	private int screenHeight	= GameSimulator.SCREENHEIGHT;
	private int ballRadius		= GameSimulator.BALLRADIUS;
	private int barLength		= GameSimulator.BARLENGTH;
	private int blockWidth 		= GameSimulator.BLOCKWIDTH;
	private int blockHeight 	= GameSimulator.BLOCKHEIGHT;
	private int offset 			= 3;
	
	private int nStage;
	private int[][] bars;
	private int[][] balls;
	private int[][] blocks;
	
	public Stage(int nStage)
	{
		this.nStage = nStage;
		createStage();
	}
	
	private void createStage()
	{
		switch(this.nStage)
		{
		case 1:
			this.bars = new int[4][2];
			this.balls = new int[4][2];
			this.blocks = new int[16][2];
			
			bars[0][0] = (screenWidth - barLength) / 2;
			bars[0][1] = screenHeight - ballRadius - offset;
			bars[1][0] = screenWidth - ballRadius - offset;
			bars[1][1] = (screenHeight + barLength) / 2;
			bars[2][0] = (screenWidth + barLength) / 2;
			bars[2][1] = ballRadius + offset;
			bars[3][0] = ballRadius + offset;
			bars[3][1] = (screenHeight - barLength) / 2;
			
			balls[0][0] = bars[0][0] + (barLength / 2);
			balls[0][1] = bars[0][1] - (ballRadius * 2);
			balls[1][0] = bars[1][0] - (ballRadius * 2);
			balls[1][1] = bars[1][1] - (barLength / 2);
			balls[2][0] = bars[2][0] - (barLength / 2);
			balls[2][1] = bars[2][1] + (ballRadius * 2);
			balls[3][0] = bars[3][0] + (ballRadius * 2);
			balls[3][1] = bars[3][1] + (barLength / 2);
			
			int[] blockXs = {
					(screenWidth / 2) - 3 * offset - 2 * blockWidth,
					(screenWidth / 2) - 1 * offset - 1 * blockWidth,
					(screenWidth / 2) + 1 * offset + 0 * blockWidth,
					(screenWidth / 2) + 3 * offset + 1 * blockWidth
			};
			int[] blockYs = {
					(screenHeight / 2) - 3 * offset - 2 * blockHeight,
					(screenHeight / 2) - 1 * offset - 1 * blockHeight,
					(screenHeight / 2) + 1 * offset + 0 * blockHeight,
					(screenHeight / 2) + 3 * offset + 1 * blockHeight
			};
			
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					blocks[4 * i + j][0] = blockXs[j];
					blocks[4 * i + j][1] = blockYs[i];
				}
			}
			break;
			
		default:
			break;
		}
	}
	
	public int[][] getBarPositions()
	{
		return this.bars;
	}
	
	public int[][] getBallPositions()
	{
		return this.balls;
	}
	
	public int[][] getBlockPositions()
	{
		return this.blocks;
	}
}
