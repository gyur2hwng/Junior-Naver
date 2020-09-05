package gameBoard;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;


public class oneto50_Game_Panel extends JPanel implements ActionListener {

	
	static int BTN_CNT = 5;
	static int BTN_SIZE = 60;
	
	JButton game_button;						//게임 버튼
	JButton ranking_button;						//랭킹 버튼
	JButton reset_button;						//초기화 버튼
	JButton back_main;							//처음 화면으로 돌아가는 버튼
	
	JButton[][] buttons;						//1 to 50 버튼들
	int[][] front;								//1 to 25
	int[][] back;								//26 to 50
	
	Label gameLb;								//게임 숫자 레이블
	static Label watchLb;						//스톱 와치 레이블
	StopWatch sw;
	
	Font font;
	ImageIcon img = new ImageIcon();
	
	int gameNum;
	
	oneto50_Game_Panel()
	{
		setLayout(null);
		
		_WordGame.frame.setTitle("1 to 50");
		
		//------------------------------------------------------------------
		//게임 버튼
		game_button = new JButton("게임 시작");	
		game_button.setBackground(new Color(15, 32, 130));		
		game_button.setForeground(Color.WHITE);
		game_button.setBounds(340, 10, 100, 30);
		game_button.addActionListener(this);		
		add(game_button);
		
		//------------------------------------------------------------------
		//리셋 버튼
		reset_button = new JButton("리셋");
		reset_button.setBackground(new Color(15, 32, 130));
		reset_button.setForeground(Color.WHITE);
		reset_button.setBounds(460, 10, 80, 30);
		reset_button.addActionListener(this);
		add(reset_button);
		
		//------------------------------------------------------------------
		//랭킹 버튼
		ranking_button = new JButton("랭킹");	
		ranking_button.setBackground(new Color(15, 32, 130));		
		ranking_button.setForeground(Color.WHITE);
		ranking_button.setBounds(560, 10, 80, 30);
		ranking_button.addActionListener(this);	
		add(ranking_button);
		
		//------------------------------------------------------------------
		//처음화면 버튼
		back_main = new JButton("뒤로");	
		back_main.setBackground(new Color(98, 112, 191));		
		back_main.setForeground(Color.WHITE);
		back_main.setBounds(450, 520, 100, 30);
		back_main.addActionListener(this);		
		add(back_main);
				
		//------------------------------------------------------------------
		//게임 패널
		setGamePanel();
	}
	
	//==============================================
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		img = new ImageIcon("./src/gameBoard/1to50_back.png");
		g.drawImage(img.getImage(),  0,  0,  null);	
	}
	//==============================================
	
	void setNumber()
	{
		front = new int[BTN_CNT][BTN_CNT];
		back = new int[BTN_CNT][BTN_CNT];
		
		int k = 0;
		for(int i=0;i<BTN_CNT;i++)
		{
			for(int j=0;j<BTN_CNT;j++)
			{
				front[i][j] = 1 + k;
				back[i][j] = 26 + k;
				k++;
			}
		}
	}
	
	//버튼 넣기
	void setGamePanel() 
	{
		font = new Font("", Font.BOLD, 9);
		
		setNumber();
		shuffleNumber();
		
//		450, 520, 100, 30
		
		gameLb = new Label("");
		gameLb.setFont(new Font("", Font.BOLD, 10));
		gameLb.setBounds(450, 380, 30, 50);
		gameLb.setBackground(new Color(161, 168, 204));
		add(gameLb);
		
		watchLb = new Label();
		watchLb.setFont(new Font("", Font.BOLD, 15));
		watchLb.setBounds(450, 450, 100, 30);
		watchLb.setBackground(new Color(161, 168, 204));
		add(watchLb);
		
		int k = 0;
		buttons = new JButton[BTN_CNT][BTN_CNT];
		for(int y=0;y<BTN_CNT;y++)
		{
			for(int x=0;x<BTN_CNT;x++)
			{
				buttons[y][x] = new JButton();
				buttons[y][x].setLocation(340+x*BTN_SIZE, 70+y*BTN_SIZE);
				buttons[y][x].setSize(BTN_SIZE, BTN_SIZE);
				buttons[y][x].setFont(font);
				buttons[y][x].setBackground(Color.WHITE);
				buttons[y][x].setForeground(Color.BLACK);
				buttons[y][x].addActionListener(this);
				
				add(buttons[y][x]);
				k++;
			}
		}
	}
	
	//번호 전부 0으로 셋팅
	void setInit()
	{
		gameNum = 0;
		gameLb.setText("");
		
		setNumber();
		//shuffleNumber();
		
		for(int y=0;y<BTN_CNT;y++)
		{
			for(int x=0;x<BTN_CNT;x++)
			{
				buttons[y][x].setText(front[y][x] + "");
				add(buttons[y][x]);
			}
		}
	}
	//번호들 무작위로 섞기
	void shuffleNumber()
	{
		Random ran = new Random();
		
		for(int i=0;i<100;i++)
		{
			int rY = ran.nextInt(BTN_CNT);
			int rX = ran.nextInt(BTN_CNT);
			
			int temp = front[0][0];
			front[0][0] = front[rY][rX];
			front[rY][rX] = temp;
			
			rY = ran.nextInt(BTN_CNT);
			rX = ran.nextInt(BTN_CNT);
			
			temp = back[0][0];
			back[0][0] = back[rY][rX];
			back[rY][rX] = temp;
		}
	}
	
    @Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == back_main )				//뒤로가기 버튼을 눌렀을 때
		{
			try {
				_WordGame.frame.setContentPane(new Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == ranking_button ) 	//랭킹 버튼 눌렀을 때
		{
			try {
				_WordGame.frame.setContentPane(new Rank_Panel());
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == game_button ) 		//게임 버튼을 눌렀을 때
		{
			setInit();
			gameNum = 1;
			gameLb.setText(gameNum + "");
			sw = new StopWatch();
			sw.start();
		}
		else if(e.getSource() == reset_button)		//리셋 버튼을 눌렀을 때
		{
			setInit();
			for(int y=0;y<BTN_CNT;y++)
			{
				for(int x=0;x<BTN_CNT;x++)
				{
					buttons[y][x].setText("");
					add(buttons[y][x]);
				}
			}
			sw.play = false;
			sw.interrupt();
			watchLb.setText("");
		}
		
		//1 to 50 게임 숫자 버튼을 눌렀을 때
		for(int y=0;y<BTN_CNT;y++)
		{
			for(int x=0;x<BTN_CNT;x++)
			{
				if(e.getSource() == buttons[y][x])
				{
					if(buttons[y][x].getText().equals(gameNum + ""))
					{
						if(1 <= gameNum && gameNum <= 25)
						{
							buttons[y][x].setText(back[y][x] + "");
						}
						else
						{
							buttons[y][x].setText("");
						}
						gameNum += 1;
						gameLb.setText(gameNum + "");
						
						if(gameNum == 51)
						{
							gameNum = 50;
							gameLb.setText(gameNum + "");
							sw.play = false;
							sw.interrupt();
							
							JOptionPane.showMessageDialog(null, "총 소요시간은 " + sw.timeText + "초 입니다.", "게임 종료", JOptionPane.INFORMATION_MESSAGE);
							watchLb.setText("");
							gameLb.setText("");
							
						
							Rank.instance.setInfo(FileManager.LOG, sw.timeText);
							FileManager.instance.saveRankData();

						}
					}
				}
			}
		}
		
	}
}