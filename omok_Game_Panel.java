package gameBoard;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class omok_Game_Panel extends JPanel implements ActionListener {
	
	int size = 9;
	JButton[][] board= new JButton[size][size];
	int nodeSize = 60;
	int turn = 0;
	JButton resetBtn = new JButton("리셋");
	Random ran = new Random();   // 컴퓨터 플레이어에 사용할 숫자
	JButton gamePanel = new JButton("게임 목록");
	JButton rank = new JButton("랭킹 보기");
	Color myback = new Color(255, 255, 170);
	Color myboard = new Color(170, 92, 30);
	Color buttons = new Color(255,140,0);
	//if win = 1 player wins; if win = 2 computer wins
	
	//누적 승수 
	File file = new File("miniGame.txt");
	FileWriter fout = null;		//쓰기
	FileReader reader = null;		//읽기
	BufferedReader br = null;		//한줄씩 읽기
	String data = "";
	
	omok_Game_Panel() throws FontFormatException, IOException{
		setLayout(null);
		setBounds(0,0, 1000 , 800);
		setBackground(myback);
		
		//오목 할아버지들 그림 
		ImageIcon imageIcon = new ImageIcon("./src/gameBoard/오목할아버지.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		JLabel grandpa = new JLabel(new ImageIcon(newimg));
		grandpa.setSize(300, 300);
		grandpa.setLocation(640, 420);
		add(grandpa);
		
		//보드
		for(int i = 0; i < size; i++) {
			for(int n = 0; n < size; n ++) {
				board[i][n] = new JButton();
				board[i][n].setBounds((n+1) * nodeSize , (i+1) * nodeSize , nodeSize , nodeSize);
				board[i][n].setBackground(Color.WHITE);
				board[i][n].addActionListener(this);	
				board[i][n].setBackground(myboard);
				board[i][n].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(board[i][n]);
			}
		}
		
		Font tempfont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/gameBoard/Binggrae.ttf"));
		Font font = tempfont.deriveFont(Font.BOLD, 15);
		
		//리셋 버튼 
		resetBtn.setBounds(730 , 100 , 120, 70);
		resetBtn.addActionListener(this);	
		//resetBtn.setBorder(new RoundedBorder(30));
		resetBtn.setBackground(buttons);
		resetBtn.setForeground(Color.WHITE);
		resetBtn.setFont(font);
		add(resetBtn);
		
		//게임 목록으로 돌아가는 버튼
		gamePanel.setBounds(730, 210, 120, 70);
		gamePanel.addActionListener(this);
		//gamePanel.setBorder(new RoundedBorder(30));
		gamePanel.setBackground(buttons);
		gamePanel.setForeground(Color.WHITE);
		gamePanel.setFont(font);
		add(gamePanel);
		
		//랭킹 보는 버튼 
		rank.setBounds(730, 320, 120, 70);
		rank.addActionListener(this);
		//rank.setBorder(new RoundedBorder(30));
		rank.setBackground(buttons);
		rank.setForeground(Color.WHITE);
		rank.setFont(font);
		add(rank);
		
		
		
		int i = ran.nextInt(9);
		int n = ran.nextInt(9);
		if(turn == 1 ) {  //Computer's
			if (board[i][n].getBackground() != myboard && board[i][n].getBackground() != Color.RED) {
				board[i][n].setBackground(Color.BLACK);
				turn = 0;
			}
		}
		
		
	}
	
	void Reset() {
		for(int i = 0; i < size; i++) {
			for(int n = 0; n < size; n ++) {				
				board[i][n].setBackground(myboard);				
			}
		}
		turn = 0;
	}
	
	void CompTurn() {
		if(turn == 1 ) {  //Computer's
			int i = ran.nextInt(9);
			int n = ran.nextInt(9);
			if (board[i][n].getBackground() == myboard) {
				board[i][n].setBackground(Color.BLACK);
				turn = 0;
			}
		}
	}
	
	void CheckWin() {
		int win = 0;
		
		// 가로 검사
		for(int i = 0; i < size; i++) {
			for(int n= 0; n < size - 4; n++) {	
				int countp1 = 0;
				int countp2 = 0;
				for(int k = 0; k < 5; k++) {
					Color temp1 = board[i][n + k].getBackground();
					if(temp1 == Color.WHITE) {
						countp1 += 1;
					}else {
						countp1 = 0;
					}
					Color temp2 = board[i][n + k].getBackground();
					if(temp2 == Color.BLACK) {
						countp2 += 1;
					}else {
						countp2 = 0;
					}
				}
				if(countp1 == 5) {
					win = 1;				
					break;
				}
				if(countp2 == 5) {
					win = 2;
					break;
				}
			}
			if(win == 1) {
				JOptionPane.showMessageDialog(null, FileManager.instance.userManager.get(FileManager.LOG).getName()  
						+ "님 승리 !", "오목게임 결과", JOptionPane.WARNING_MESSAGE);
				int newWin = FileManager.instance.userManager.get(FileManager.LOG).getOmokWin();
				newWin++;
				FileManager.instance.userManager.get(FileManager.LOG).setOmokWin(newWin);
				omok_Rank.instance.setInfo();
				FileManager.instance.saveRankData();
				Update();
				return;
			}
			if (win == 2) {
				JOptionPane.showMessageDialog(null, FileManager.instance.userManager.get(FileManager.LOG).getName() 
						+ "님 패배", "오목게임 결과", JOptionPane.WARNING_MESSAGE);
				return;
			}	
		}
		
		// 세로 검사 
		for(int i = 0; i < size - 4; i++) {
			for(int n= 0; n < size; n++) {	
				int countp1 = 0;
				int countp2 = 0;
				for(int k = 0; k < 5; k++) {
					Color temp1 = board[i + k][n].getBackground();
					if(temp1 == Color.WHITE) {
						countp1 += 1;
					}else {
						countp1 = 0;
					}
					Color temp2 = board[i + k][n].getBackground();
					if(temp2 == Color.BLACK) {
						countp2 += 1;
					}else {
						countp2 = 0;
					}
				}
				if(countp1 == 5) {
					win = 1;				
					break;
				}
				if(countp2 == 5) {
					win = 2;
					break;
				}
			}
			if(win == 1) {
				JOptionPane.showMessageDialog(null, FileManager.instance.userManager.get(FileManager.LOG).getName()  
						+ "님 승리 !", "오목게임 결과", JOptionPane.WARNING_MESSAGE);
				int newWin = FileManager.instance.userManager.get(FileManager.LOG).getOmokWin();
				newWin++;
				FileManager.instance.userManager.get(FileManager.LOG).setOmokWin(newWin);
				omok_Rank.instance.setInfo();
				FileManager.instance.saveOmokRankData();
				Update();
				return;
			}
			if (win == 2) {
				JOptionPane.showMessageDialog(null, FileManager.instance.userManager.get(FileManager.LOG).getName() 
						+ "님 패배", "오목게임 결과", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		//대각선 확인 1
		for(int i = 0; i < size - 4; i++) {
			for(int n= 0; n < size - 4; n++) {	
				int countp1 = 0;
				int countp2 = 0;
				for(int k = 0; k < 5; k++) {
					Color temp1 = board[i + k][n + k].getBackground();
					if(temp1 == Color.WHITE) {
						countp1 += 1;
					}else {
						countp1 = 0;
					}
					Color temp2 = board[i + k][n + k].getBackground();
					if(temp2 == Color.BLACK) {
						countp2 += 1;
					}else {
						countp2 = 0;
					}
				}
				if(countp1 == 5) {
					win = 1;				
					break;
				}
				if(countp2 == 5) {
					win = 2;
					break;
				}
			}
			if(win == 1) {
				JOptionPane.showMessageDialog(null, FileManager.instance.userManager.get(FileManager.LOG).getName()  
						+ "님 승리 !", "오목게임 결과", JOptionPane.WARNING_MESSAGE);
				int newWin = FileManager.instance.userManager.get(FileManager.LOG).getOmokWin();
				newWin++;
				FileManager.instance.userManager.get(FileManager.LOG).setOmokWin(newWin);
				omok_Rank.instance.setInfo();
				FileManager.instance.saveOmokRankData();
				Update();
				return;
			}
			if (win == 2) {
				JOptionPane.showMessageDialog(null, FileManager.instance.userManager.get(FileManager.LOG).getName() 
						+ "님 패배", "오목게임 결과", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
				
		//대각선 확인 2
		for(int i = size-1; i > size-6; i--) {
			for(int n= 0; n < size-4; n++) {	
				int countp1 = 0;
				int countp2 = 0;
				for(int k = 0; k < 5; k++) {
					Color temp1 = board[i - k][n + k].getBackground();
					if(temp1 == Color.WHITE) {
						countp1 += 1;
					}else {
						countp1 = 0;
					}
					Color temp2 = board[i - k][n + k].getBackground();
					if(temp2 == Color.BLACK) {
						countp2 += 1;
					}else {
						countp2 = 0;
					}
				}
				if(countp1 == 5) {
					win = 1;				
					break;
				}
				if(countp2 == 5) {
					win = 2;
					break;
				}
			}
			if(win == 1) {
				JOptionPane.showMessageDialog(null, FileManager.instance.userManager.get(FileManager.LOG).getName()  
						+ "님 승리 !", "오목게임 결과", JOptionPane.WARNING_MESSAGE);
				int newWin = FileManager.instance.userManager.get(FileManager.LOG).getOmokWin();
				newWin++;
				FileManager.instance.userManager.get(FileManager.LOG).setOmokWin(newWin);
				omok_Rank.instance.setInfo();
				FileManager.instance.saveOmokRankData();
				Update();
				return;
			}
			if (win == 2) {
				JOptionPane.showMessageDialog(null, FileManager.instance.userManager.get(FileManager.LOG).getName() 
						+ "님 패배", "오목게임 결과", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
	}
	
	
	void Update() {
		String omokData = "";
		data = "";
		
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
				if (line == null){
					System.out.println("유저 파일이 비었습니다.");
					break;
				}
				
				omokData += line;
				omokData += "\n";
			}
			
			omokData = omokData.substring(0, omokData.length() - 1);
			
			String[] temp = omokData.split("\n");
			String[][] user_info = new String[temp.length][6];
			for(int i=0;i<temp.length;i++)
			{
				String[] info = temp[i].split("/");
				user_info[i][0] = info[0];    //name
				user_info[i][1] = info[1];    //age
				user_info[i][2] = info[2];    //mobile
				user_info[i][3] = info[3];    //id
				user_info[i][4] = info[4];    //pw
				if (i != FileManager.LOG) {
					user_info[i][5] = info[5];    //wins
				}
			} 
			user_info[FileManager.LOG][5] = String.valueOf(FileManager.instance.userManager.get(FileManager.LOG).getOmokWin());
			
			for (int i=0; i<temp.length; i++) {
				data += user_info[i][0];
				data += "/";
				data += user_info[i][1];
				data += "/";
				data += user_info[i][2];
				data += "/";
				data += user_info[i][3];
				data += "/";
				data += user_info[i][4];
				data += "/";
				data += user_info[i][5];
				data += "\n";
			}
			
			saveOmokWins();
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
	
	void saveOmokWins() {
		try
		{
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			file.createNewFile();
			fout = new FileWriter("miniGame.txt");
			fout.write(data);
			fout.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < size; i++) {
			for(int n= 0; n < size; n++) {
				if(e.getSource() == board[i][n] 
						&& board[i][n].getBackground() == myboard) {
					if(turn == 0 ) {   //Player's
						board[i][n].setBackground(Color.WHITE);						
						turn = 1;	
					}else {
						CompTurn();
					}
					CheckWin();
				}
			}
		}
		if(e.getSource() == resetBtn) {
			Reset();
		}
		if (e.getSource() == gamePanel) {
			try {
				_WordGame.frame.setContentPane(new Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		if (e.getSource() == rank) {
			try {
				_WordGame.frame.setContentPane(new omok_Rank_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
	}

}