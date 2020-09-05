package gameBoard;

public class Rank {
	static Rank instance = new Rank();
	
	String id;
	String time;
	final int SIZE = 3;
	String[][] rank;
	
	Rank()
	{
		rank = new String[SIZE][SIZE];
		rank[0][0] = "blank1";		rank[0][1] = "100:00:01";
		rank[1][0] = "blank2";		rank[1][1] = "100:00:02";
		rank[2][0] = "blank3";		rank[2][1] = "100:00:03";
	}
	
	public void setInfo(int log, String time)
	{
		//로그인한 아이디를 변수 id에 저장한다
		id = FileManager.instance.userManager.get(log).getId();
		this.time = time;
		System.out.println("ID = " + id);
		System.out.println("Time = " + time);
		
		if(rank[2][1].compareTo(time) > 0)
		{
			rank[2][0] = id;
			rank[2][1] = time;
		}
		sortRank();
	}
	
	public void sortRank()
	{
		for(int i=0;i<SIZE;i++)
		{
			for(int j=i;j<SIZE;j++)
			{
				if(rank[i][1].compareTo(rank[j][1]) > 0)
				{
					String tempId = rank[i][0];			//tempId <- id저장
					String tempTime = rank[i][1];		//tempTime <- time시간 저장
					
					rank[i][0] = rank[j][0];
					rank[i][1] = rank[j][1];
					
					rank[j][0] = tempId;
					rank[j][1] = tempTime;

				}
			}
		}
		
		for(int i=0; i<SIZE; i++) 
		{
			System.out.println(rank[i][0] + " : " + rank[i][1]);
		}

	}
}