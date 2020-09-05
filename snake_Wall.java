package gameBoard;

public class snake_Wall {
	int wall = 3;
	
	public static snake_Wall instance = new snake_Wall();
	
	public int getWall()
	{
		return wall;
	}
	
	public void setWall(int wall)
	{
		this.wall = wall;
	}
}