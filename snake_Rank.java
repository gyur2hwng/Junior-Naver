package gameBoard;

public class snake_Rank {
	static snake_Rank instance = new snake_Rank();
	
	String id;
	String wallCount;
	final int SIZE = 3;
	String[][] rank;
	
	
	snake_Rank()
	{
		rank = new String[SIZE][SIZE];
		rank[0][0] = "blank1";		rank[0][1] = "-1";
		rank[1][0] = "blank2";		rank[1][1] = "-1";
		rank[2][0] = "blank3";		rank[2][1] = "-1";
		
	}
	
	public void setInfo(int log, int count)
	{
		//로그인한 아이디를 변수 id에 저장한다
		id = FileManager.instance.userManager.get(log).getId();
		wallCount = Integer.toString(count);
		System.out.println("ID = " + id);
		System.out.println("Stage = " + (snake_Wall.instance.getWall()-2));
		System.out.println("Wall = " + wallCount);
		
		if(rank[2][1].compareTo(wallCount) < 0)
		{
			rank[2][0] = id;
			rank[2][1] = wallCount;
		}
		sortRank();
	}
	
	public void sortRank()
	{
		for(int i=0;i<SIZE;i++)
		{
			for(int j=i;j<SIZE;j++)
			{
				if(rank[i][1].compareTo(rank[j][1]) < 0)
				{
					String tempId = rank[i][0];			//tempId <- id저장
					String tempStage = rank[i][1];		//tempTime <- time시간 저장
					
					rank[i][0] = rank[j][0];
					rank[i][1] = rank[j][1];
					
					rank[j][0] = tempId;
					rank[j][1] = tempStage;

				}
			}
		}
		
		for(int i=0; i<SIZE; i++) 
		{
			System.out.println(rank[i][0] + " : " + rank[i][1]);
		}

	}
}