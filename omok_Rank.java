package gameBoard;

public class omok_Rank {
	static omok_Rank instance = new omok_Rank();
	
	String id1;
	String id2;
	String id3;
	int max = 0;
	int maxidx;
	int max1 = 0;
	int max1idx;
	int max2 = 0;
	int max2idx;
	final int SIZE = 3;
	String[][] rank;
	
	omok_Rank()
	{
		rank = new String[SIZE][SIZE];
		rank[0][0] = "blank1";		rank[0][1] = "0";
		rank[1][0] = "blank2";		rank[1][1] = "0";
		rank[2][0] = "blank3";		rank[2][1] = "0";
	}
	
	public void setInfo()
	{
		boolean check = true;
		int duplicateId = 0;

		System.out.println("ID = " + FileManager.instance.userManager.get(FileManager.LOG).getId());
		System.out.println("Wins = " + FileManager.instance.userManager.get(FileManager.LOG).getOmokWin());
		
		//1st user
		for (int i=0; i<FileManager.instance.userManager.size(); i++) {
			if (FileManager.instance.userManager.get(i).getOmokWin() > max) {
				max = FileManager.instance.userManager.get(i).getOmokWin();
				maxidx = i;
			}
		}
		id1 = FileManager.instance.userManager.get(maxidx).getId();
		System.out.println("맥스 1: " + id1);
		
		for (int i=0; i<3; i++) {
			if (rank[i][0] == id1) {
				check = false;
				duplicateId = i;
				break;
			}
		}
		if (check == true) {
			rank[0][0] = id1;
			rank[0][1] = String.valueOf(max);
		}
		else {
			rank[duplicateId][1] = String.valueOf(max);
		}
		
		check = true;
		duplicateId = 0;
		if (FileManager.instance.userManager.size() > 1) {
			//2nd user
			for (int i=0; i<FileManager.instance.userManager.size(); i++) {
				if (i != maxidx) {
					if (FileManager.instance.userManager.get(i).getOmokWin() > max1) {
						max1 = FileManager.instance.userManager.get(i).getOmokWin();
						max1idx = i;
					}
				}
			}
			id2 = FileManager.instance.userManager.get(max1idx).getId();
			System.out.println("맥스 2: " + id2);
			
			for (int i=0; i<3; i++) {
				if (rank[i][0] == id2) {
					check = false;
					duplicateId = i;
					break;
				}
			}
			if (check == true) {
				rank[1][0] = id2;
				rank[1][1] = String.valueOf(max1);
			}
			else {
				rank[duplicateId][1] = String.valueOf(max1);
			}
		}
		
		check = true;
		duplicateId = 0;
		if (FileManager.instance.userManager.size() > 2) {
			//3rd user
			for (int i=0; i<FileManager.instance.userManager.size(); i++) {
				if (i != maxidx && i != max1idx) {
					if (FileManager.instance.userManager.get(i).getOmokWin() > max2) {
						max2 = FileManager.instance.userManager.get(i).getOmokWin();
						max2idx = i;
					}
				}
			}
			id3 = FileManager.instance.userManager.get(max2idx).getId();
			
			for (int i=0; i<3; i++) {
				if (rank[i][0] == id3) {
					check = false;
					duplicateId = i;
					break;
				}
			}
			if (check == true) {
				rank[2][0] = id3;
				rank[2][1] = String.valueOf(max2);
			}
			else {
				rank[duplicateId][1] = String.valueOf(max2);
			}
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
					String tempWins = rank[i][1];		//tempWins <- 누적승수 저장
					
					rank[i][0] = rank[j][0];
					rank[i][1] = rank[j][1];
					
					rank[j][0] = tempId;
					rank[j][1] = tempWins;

				}
			}
		}
		
		for(int i=0; i<SIZE; i++) 
		{
			System.out.println(rank[i][0] + " : " + rank[i][1]);
		}

	}
}