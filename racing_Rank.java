package gameBoard;

public class racing_Rank {
	static racing_Rank instance = new racing_Rank();
	
	String id;
	String score;
	final int SIZE = 3;
	String[][] rank;
	
	racing_Rank() 
	{
		rank = new String[SIZE][SIZE];
		rank[0][0] = "blank1";		rank[0][1] = "0";
		rank[1][0] = "blank2";		rank[1][1] = "0";
		rank[2][0] = "blank3";		rank[2][1] = "0";
	}
	
	public void setInfo(int log, String score)
	{
		id = FileManager.instance.userManager.get(log).getId();
		this.score = score;
		
		for(int i=0;i<SIZE;i++)
		{
			if(rank[i][0].equals(id))
			{
				int num_socre = Integer.parseInt(score);
				int num_rank = Integer.parseInt(rank[i][1]);
				num_socre = num_rank + num_socre;
				
				score = Integer.toString(num_socre);
				rank[i][0] = "";
				rank[i][1] = "";
			}
		}
		
		System.out.println("ID = " + id);
		System.out.println("Score = " + score);
		
		if(rank[2][1].compareTo(score) < 0)
		{
			rank[2][0] = id;
			rank[2][1] = score;
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
					String tempScore = rank[i][1];		//tempTime <- time시간 저장
					
					rank[i][0] = rank[j][0];
					rank[i][1] = rank[j][1];
					
					rank[j][0] = tempId;
					rank[j][1] = tempScore;

				}
			}
		}
		
		for(int i=0; i<SIZE; i++) 
		{
			System.out.println(rank[i][0] + " : " + rank[i][1]);
		}
	}
}