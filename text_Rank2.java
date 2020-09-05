package gameBoard;
public class text_Rank2 {
	static text_Rank instance = new text_Rank();
	String id;
	String time;
	final int SIZE = 3;
	String[][] rank_text;
	text_Rank2() {
		rank_text = new String[SIZE][SIZE];
		rank_text[0][0] = "blank1"; rank_text[0]
				[1] = "100:00:01";
		rank_text[1][0] = "blank2"; rank_text[1]
				[1] = "100:00:02";
		rank_text[2][0] = "blank3"; rank_text[2]
				[1] = "100:00:03";
	}
	public void text_setInfo(int log, String time) {
		//로그인한 아이디를 변수 id에 저장한다
		id =
				FileManager.instance.userManager.get(log).getId();
		this.time = time;
		System.out.println("ID = " + id);
		System.out.println("Time = " + time);
		if (rank_text[2][1].compareTo(time) > 0) {
			rank_text[2][0] = id;
			rank_text[2][1] = time;
		}
		text_sortRank();
	}
	public void text_sortRank() {
		for (int i=0;i<SIZE;i++) {
			for (int j=i;j<SIZE;j++) {
				if (rank_text[i]
						[1].compareTo(rank_text[j][1]) > 0) {
					String tempId = rank_text[i]
							[0];
					String tempTime = rank_text[i]
							[1];
					rank_text[i][0] = rank_text[j]
							[0];
					rank_text[i][1] = rank_text[j]
							[1];
					rank_text[j][0] = tempId;
					rank_text[j][1] = tempTime;
				}
			}
		}
		for (int i=0;i<SIZE;i++) {
			System.out.println(rank_text[i][0] + " : " +
					rank_text[i][1]);
		}
	}
}