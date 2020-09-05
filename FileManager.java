package gameBoard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	File file = null;		//파일 존재 체크 여부
	FileWriter fout = null;		//쓰기
	FileReader reader = null;		//읽기
	BufferedReader br = null;		//한줄씩 읽기
	String data ="";			//데이터
	boolean isLoad = false;
	ArrayList<UserInfo> userManager = null;

	final String path = "miniGame.txt";			//경로
	final String rankPath = "gameRank.txt";			//경로

	final String omokRankPath = "omokGameRank.txt";  //***

	final String snake_rankPath = "gameRank.txt";
	final String racing_rankPath = "racingRank.txt";

	final String text_stage1_rankPath = "text_stage1_rankPath.txt";
	final String text_stage2_rankPath = "text_stage2_rankPath.txt";

	static int LOG = -1;						//로그인 정	보 

	//싱글톤 패턴
	//FileManager의 객체를 하나만 만든다
	public static FileManager instance = new FileManager();

	private FileManager()
	{
		userManager = new ArrayList<>();
	}

	public void addUser(UserInfo user)			//UserInfo는 회원 정보가 들어가있다.
	{
		//어레이 리스트에 user(회원 정보) 넣는다.
		userManager.add(user);
		//데이터 넣는다
		addData(user);
		//데이터 저장
		saveData();
	}

	//데이터 넣기
	public void addData(UserInfo user)
	{
		int lastIndex = userManager.size() - 1;
		System.out.println("lastIndex: "+lastIndex);
		//ArrayList의 n번째(lastIndex)의 회원 정보가 temp에 저장된다
		UserInfo temp = userManager.get(lastIndex);
		data += temp.name;
		data += "/";
		data += temp.age;
		data += "/";
		data += temp.mobile;
		data += "/";
		data += temp.id;
		data += "/";
		data += temp.pw;
		data += "/";
		data += temp.omok_win;   //***
		data += "\n";            //***
		System.out.println("= =  S A V E = =\n" +  data);
	}

	//데이터 저장
	public void saveData()
	{
		try
		{
			fout = new FileWriter(path);
			fout.write(data);
			fout.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//모든 회원 출력
	public void printAllUser() 
	{
		System.out.println("모든 회원 출력");
		for(int i = 0; i < userManager.size(); i++) 
		{
			System.out.print(userManager.get(i).name + " ");
			System.out.print(userManager.get(i).age + " ");
			System.out.print(userManager.get(i).mobile + " ");
			System.out.print(userManager.get(i).id + " ");
			System.out.print(userManager.get(i).pw + " ");
			System.out.println(userManager.get(i).omok_win);     //***
		}
	}

	//유저 로드
	public void loadUser(String userTxt)
	{
		String userinfo[] = new String[6];
		String userText[] = userTxt.split("/");

		if (!file.exists()) {
			for (int i=0; i<5; i++) {
				userinfo[i] = userText[i];
			}
			userinfo[5] = String.valueOf(0);
		}
		else {
			for (int i=0; i<6; i++) {
				userinfo[i] = userText[i];
			}
		}
		

		System.out.println("lastIdx: "+userManager.size());
		System.out.println("유저 로드");
		for(int i=0;i<userinfo.length;i++)
		{
			System.out.println("userinfo: "+userinfo[i]);
		}

		UserInfo temp = new UserInfo();
		temp.name = userinfo[0];
		temp.age = Integer.parseInt(userinfo[1]);
		temp.mobile = userinfo[2];
		temp.id = userinfo[3];
		temp.pw = userinfo[4];
		temp.omok_win = Integer.parseInt(userinfo[5]);    //***
		userManager.add(temp);
		printAllUser();
	}

	//데이터 로드
	public void loadData()
	{
		file = new File(path);

		if(!file.exists())			//파일이 존재하지 않으면
		{
			System.out.println("[메세지]파일이 존재하지 않는다.");
			return;
		}
		try{
			reader = new FileReader(path);
			br = new BufferedReader(reader);
			isLoad = false;
			data = "";
			while(true){
				String userTxt = br.readLine();

				if(userTxt == null)
				{
					break;
				}

				data += userTxt;
				data += "\n";

				loadUser(userTxt);
				isLoad = true;
			}

			reader.close();
			br.close();

			if(isLoad){
				System.out.println("= = L O A D = = \n" + data);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void loadRankData()
	{
		file = new File(rankPath);
		String rankData = "";

		try
		{
			if(!file.exists())
			{
				System.out.println("[메세지]파일이 비었습니다.");
			}

			reader = new FileReader(file);
			br = new BufferedReader(reader);

			while(true)
			{
				String line = br.readLine();
				if(line == null)
				{
					System.out.println("랭크 파일이 비었습니다.");
					break;
				}

				rankData += line;
				rankData += "\n";
			}
			rankData = rankData.substring(0, rankData.length() - 1);

			String[] temp = rankData.split("\n");
			for(int i=0;i<temp.length;i++)
			{
				String[] info = temp[i].split("/");
				Rank.instance.rank[i][0] = info[0];
				Rank.instance.rank[i][1] = info[1];
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(reader != null) { try { reader.close(); } catch (IOException e) {} }
			if(br != null) { try { br.close(); } catch (IOException e) {} }
		}
	}

	public void saveRankData()
	{
		String info = "";
		for(int i=0;i<Rank.instance.rank.length;i++)
		{
			info += Rank.instance.rank[i][0];		//아이디
			info += "/";
			info += Rank.instance.rank[i][1];		//시간
			if(i != Rank.instance.rank.length - 1) 
			{
				info += "\n";
			}
		}

		try
		{
			fout = new FileWriter(rankPath);
			fout.write(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(fout != null) { try { fout.close(); } catch (IOException e) {} }
		}
	}

	//로그인
	int login(String id, String pw)
	{
		LOG = - 1;
		String check_id = "";

		for(int i = 0;i<userManager.size();i++)
		{
			if(id.equals(userManager.get(i).id)&&pw.equals(userManager.get(i).pw))
			{
				LOG = i;
			}
		}
		return LOG;
	}

	//==========snake game===========
	public void loadSnakeRankData()
	{
		file = new File(snake_rankPath);
		String rankData = "";
		try
		{
			if(!file.exists())
			{
				System.out.println("[메세지]파일이 비었습니다.");
			}
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			while(true)
			{
				String line = br.readLine();
				if(line == null)
				{
					System.out.println("랭크 파일이비었습니다.");
					break;
				}
				rankData += line;
				rankData += "\n";
			}
			rankData = rankData.substring(0,
					rankData.length() - 1);
			String[] temp = rankData.split("\n");
			for(int i=0;i<temp.length;i++)
			{
				String[] info = temp[i].split("/");
				snake_Rank.instance.rank[i][0] =
						info[0];
				snake_Rank.instance.rank[i][1] =
						info[1];
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(reader != null) { try { reader.close(); }
			catch (IOException e) {} }
			if(br != null) { try { br.close(); } catch
				(IOException e) {} }
		}
	}

	public void saveSnakeRankData()
	{
		String info = "";
		for(int i=0;i<snake_Rank.instance.rank.length;i++)
		{
			info += snake_Rank.instance.rank[i][0];
			//아이디
			info += "/";
			info += snake_Rank.instance.rank[i][1];
			//시간
			if(i != snake_Rank.instance.rank.length - 1)
			{
				info += "\n";
			}
		}
		try
		{
			fout = new FileWriter(snake_rankPath);
			fout.write(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(fout != null) { try { fout.close(); } catch
				(IOException e) {} }
		}
	}
	//==========snake game===========


	//==========racing game===========
	public void loadRacingRankData()
	{
		file = new File(racing_rankPath);
		String rankData = "";
		try
		{
			if(!file.exists())
			{
				System.out.println("[메세지]파일이 비었습니다.");
			}
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			while(true)
			{
				String line = br.readLine();
				if(line == null)
				{
					System.out.println("랭크 파일이 비었습니다.");
					break;
				}
				rankData += line;
				rankData += "\n";
			}
			rankData = rankData.substring(0,
					rankData.length() - 1);
			String[] temp = rankData.split("\n");
			for(int i=0;i<temp.length;i++)
			{
				String[] info =
						temp[i].split("/");
				racing_Rank.instance.rank[i]
						[0] = info[0];
				racing_Rank.instance.rank[i]
						[1] = info[1];
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(reader != null) { try {
				reader.close(); } catch (IOException e) {} }
			if(br != null) { try { br.close(); }
			catch (IOException e) {} }
		}
	}

	public void saveRacingRankData()
	{
		String info = "";
		for(int
				i=0;i<racing_Rank.instance.rank.length;i++)
		{
			info += racing_Rank.instance.rank[i]
					[0]; //아이디
			info += "/";
			info += racing_Rank.instance.rank[i]
					[1]; //시간
			if(i !=
					racing_Rank.instance.rank.length - 1)
			{
				info += "\n";
			}
		}
		try
		{
			fout = new
					FileWriter(racing_rankPath);
			fout.write(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(fout != null) { try { fout.close();
			} catch (IOException e) {} }
		}
	}
	//==========racing game===========

	//=============== omok Game =============
	public void loadOmokRankData()   //***
	{
		file = new File(rankPath);
		String rankData = "";

		try
		{
			if(!file.exists())
			{
				System.out.println("[메세지]파일이 비었습니다.");
			}

			reader = new FileReader(file);
			br = new BufferedReader(reader);

			while(true)
			{
				String line = br.readLine();
				if(line == null)
				{
					System.out.println("랭크 파일이 비었습니다.");
					break;
				}

				rankData += line;
				rankData += "\n";
			}
			rankData = rankData.substring(0, rankData.length() - 1);

			String[] temp = rankData.split("\n");
			for(int i=0;i<temp.length;i++)
			{
				String[] info = temp[i].split("/");
				omok_Rank.instance.rank[i][0] = info[0];
				omok_Rank.instance.rank[i][1] = info[1];
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(reader != null) { try { reader.close(); } catch (IOException e) {} }
			if(br != null) { try { br.close(); } catch (IOException e) {} }
		}
	}

	public void saveOmokRankData()  //***
	{
		String info = "";
		for(int i=0;i<omok_Rank.instance.rank.length;i++)
		{
			info += omok_Rank.instance.rank[i][0];		//아이디
			info += "/";
			info += omok_Rank.instance.rank[i][1];		//누적승
			if(i != omok_Rank.instance.rank.length - 1) 
			{
				info += "\n";
			}
		}

		try
		{
			fout = new FileWriter(omokRankPath);
			fout.write(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(fout != null) { try { fout.close(); } catch (IOException e) {} }
		}
	}
	//=============== omok Game =============

	//=============== text Game =============
	public void loadTextStage1RankData() {
		file = new File(text_stage1_rankPath);
		String rankData = "";
		if (file.exists()) {
			try {
				reader = new FileReader(file);
				br = new BufferedReader(reader);
				while (true) {
					String line = br.readLine();
					if (line == null) {
						System.out.println("랭크 파일이 비었습니다.");
						break;
					}
					rankData +=line;
					rankData += "\n";
				}
				rankData = rankData.substring(0, rankData.length() - 1);
				String[] temp = rankData.split("\n");
				for(int i=0;i<temp.length;i++) {
					String[] info = temp[i].split("/");
					text_Rank.instance.rank_text[i][0] = info[0];
					text_Rank.instance.rank_text[i][1] = info[1];
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {try {reader.close();} catch (IOException e)
				{}}
				if (br != null) {try {reader.close();} catch (IOException e) {}}
			}
		}
		else {
			System.out.println("[메세지] 파일이 비었습니다.");
		}
	}

	public void loadTextStage2RankData() {
		file = new File(text_stage2_rankPath);
		String rankData = "";
		if (file.exists()) {
			try {
				reader = new FileReader(file);
				br = new BufferedReader(reader);
				while (true) {
					String line = br.readLine();
					if (line == null) {
						System.out.println("랭크 파일이 비었습니다.");
						break;
					}
					rankData += line;
					rankData += "\n";
				}
				rankData = rankData.substring(0, rankData.length() - 1);
				String temp [] = rankData.split("\n");
				for (int i=0;i<temp.length;i++) {
					String info[] = temp[i].split("/");
					text_Rank2.instance.rank_text[i][0] = info[0];
					text_Rank2.instance.rank_text[i][1] = info[1];
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {try {reader.close();} catch (IOException e)
				{}}
				if (br != null) {try {reader.close();} catch (IOException e) {}}
			}
		}
		else {
			System.out.println("[메세지] 파일이 비었습니다.");
		}
	}
	public void saveTextStage1RankData() {
		String info = "";
		for (int i=0;i<text_Rank.instance.rank_text.length;i++) {
			info += text_Rank.instance.rank_text[i][0]; //아이디
			info += "/";
			info += text_Rank.instance.rank_text[i][1]; //시간
			if (i != text_Rank.instance.rank_text.length - 1) {
				info += "\n";
			}
		}
		try {
			fout = new FileWriter(text_stage1_rankPath);
			fout.write(info);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fout != null) { try { fout.close(); } catch (IOException e) {}}
		}
	}
	public void saveTextStage2RankData() {
		String info = "";
		for (int i=0;i<text_Rank2.instance.rank_text.length;i++) {
			info += text_Rank2.instance.rank_text[i][0]; //아이디
			info += "/";
			info += text_Rank2.instance.rank_text[i][1]; //시간
			if (i != text_Rank2.instance.rank_text.length - 1) {
				info += "\n";
			}
		}
		try {
			fout = new FileWriter(text_stage2_rankPath);
			fout.write(info);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fout != null) { try { fout.close(); } catch (IOException e) {}}
		}
	}
	//=============== text Game =============

	//ID 찾기
	String findID(String name, String mobile)
	{
		String id = "";
		for(int i=0;i<userManager.size();i++)
		{
			if(name.equals(userManager.get(i).name) && mobile.equals( userManager.get(i).mobile))
			{
				id = userManager.get(i).id;
				break;
			}
		}
		return id;
	}

	//PW 찾기
	String findPW(String name, String mobile, String id)
	{
		String pw = "";
		for(int i=0;i<userManager.size();i++)
		{
			if(name.equals(userManager.get(i).name) && mobile.equals( userManager.get(i).mobile))
			{
				if(id.equals(userManager.get(i).id) && mobile.equals( userManager.get(i).mobile))
				{
					pw = userManager.get(i).pw;
					break;
				}
			}
		}
		return pw;
	}

	//ID 중복체
	boolean checkId(String id)
	{
		boolean check = true;
		for(int i=0;i<userManager.size();i++)
		{
			if(userManager.get(i).getId().equals(id))
			{
				check = false;
				break;
			}
		}
		return check;
	}
}
