package gameBoard;

import java.util.Random;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//장애물을 빨리 먹어 치우는 게임
//장애물 먹어 치우면 다음 스테이지로 이동
//장애물 먹으면 길이 증가 
//하나 먹을때 마다 장애물 생성

public class snake_Game_Panel extends JPanel implements ActionListener
{	
	Random ran = new Random();
	final int SIZE = 9;			//map의 크기
	JButton[][] map;			//map 만들어주는 배열
	JButton[] dir;				//방향
	int[][] data;				//data
	int snakeSize = 4;				//뱀 길이
	int apple;			        //사과
	int wall;					//벽(장애물)
	int crush = -1;
	
	JButton backBtn;
	JButton nextStageBtn;
	JButton ranking_button;
	
	ImageIcon img = new ImageIcon();
	
	//뱀 위치 좌표
	int[] x = new int[snakeSize];
	int[] y = new int[snakeSize];
	int[] snake = new int[snakeSize];
	
	//사과 위치 좌표
	int[] apple_x;
	int[] apple_y;
	
	//벽(장애물) 위치 좌표
	int[] wall_x;
	int[] wall_y;
	
	snake_Game_Panel() throws FontFormatException, IOException
	{
		setLayout(null);
		
		map = new JButton[SIZE][SIZE];
		dir = new JButton[4];
		data = new int[SIZE][SIZE];
		
/*		//뱀 길이
		
		x = new int[snakeSize];
		y = new int[snakeSize];
		snake = new int[snakeSize];*/
		
		//먹이
		apple = 5;
		
		apple_x = new int[apple];
		apple_y = new int[apple];
		
		wall = snake_Wall.instance.getWall();
		
		wall_x = new int[wall];
		wall_y = new int[wall];
		
		setNumber();
		setApple();
		setWall();
		setMap();
		setSnake();
		setButton();
		
		Font tempfont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/gameBoard/Binggrae.ttf"));
	    Font font = tempfont.deriveFont(Font.BOLD, 30);
		
//		Font font = new Font("", Font.BOLD, 30);		//폰트설정
		
		JLabel label = new JLabel("  STAGE");
		label.setFont(font);
		label.setBackground(new Color(222, 186, 172));
		label.setOpaque(true);
		label.setBounds(700, 150, 160, 70);
		add(label);
		
//		font = new Font("", Font.BOLD, 25);
		font = tempfont.deriveFont(Font.BOLD, 25);
		
		nextStageBtn = new JButton("Next");
		nextStageBtn.setBackground(new Color(222, 186, 172));
		nextStageBtn.setForeground(Color.WHITE);
		nextStageBtn.setFont(font);
		nextStageBtn.setBounds(700, 250, 150, 80);
		nextStageBtn.addActionListener(this);
		add(nextStageBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBackground(new Color(222, 186, 172));
		backBtn.setFont(font);
		backBtn.setBounds(700, 350, 150, 80);
		backBtn.addActionListener(this);
		add(backBtn);
		
		ranking_button = new JButton("  랭킹  ");	
		ranking_button.setBackground(new Color(222, 186, 172));
		ranking_button.setForeground(Color.BLACK);
		ranking_button.setFont(font);
		ranking_button.setBounds(700, 450, 150, 80);
		ranking_button.addActionListener(this);	
		add(ranking_button);
	}
	
	//==============================================
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		img = new ImageIcon("./src/gameBoard/snake_back.png");
		g.drawImage(img.getImage(),  0,  0,  null);	
	}
	//==============================================
	
	void setNumber()
	{
		//뱀 머리 + 몸통 좌표 설정
		for(int i=0; i<snakeSize; i++)
		{
			x[i] = i;
			snake[i] = i + 1;    //뱀 번호
			data[y[i]][x[i]] = snake[i];
		}
	}
	
	//먹이 설치
	void setApple()
	{
		for(int i=0;i<apple;i++)
		{
			apple_x[i] = ran.nextInt(SIZE);
			apple_y[i] = ran.nextInt(SIZE);
			
			if(data[apple_y[i]][apple_x[i]] == 0)
			{
				data[apple_y[i]][apple_x[i]] = 7;
			}
			else
			{
				i--;
			}
		}
		
	}
	
	//벽 설치
	void setWall()
	{
		for(int i=0;i<wall;i++)
		{
			wall_x[i] = ran.nextInt(SIZE);
			wall_y[i] = ran.nextInt(SIZE);
			
			if(data[wall_y[i]][wall_x[i]] == 0)
			{
				data[wall_y[i]][wall_x[i]] = 8;
			}
			else
			{
				i--;
			}
		}
	}
	
	void setSnake()
	{
		//화면 초기화 
		for(int i=0; i<SIZE; i++)
		{
			for(int j=0; j<SIZE; j++)
			{
				map[i][j].setBackground(Color.white);
			}
		}
		
		//장애물 설치
		for(int i=0;i<SIZE;i++)
		{
			for(int j=0;j<SIZE;j++)
			{
				if(data[i][j] == 7)
				{
					map[i][j].setBackground(Color.YELLOW);
				}
			}
		}
		
		//벽설치
		for(int i=0;i<SIZE;i++)
		{
			for(int j=0;j<SIZE;j++)
			{
				if(data[i][j] == 8)
				{
					map[i][j].setBackground(Color.BLUE);
				}
			}
		}
		

		//뱀 머리
		map[y[0]][x[0]].setBackground(Color.BLACK);
		//뱀 몸통
		for(int i=1; i<snakeSize; i++)
		{
			map[y[i]][x[i]].setBackground(Color.red);
		}
	}
	
	//맵 셋팅
	void setMap()
	{
		
		for(int i=0; i<SIZE; i++)
		{
			for(int j=0; j<SIZE; j++)
			{
				map[i][j] = new JButton();
				map[i][j].setSize(50, 50);
				map[i][j].setLocation(50*(j+1), 50*(i+1));
				map[i][j].addActionListener(this);
				
				add(map[i][j]);
			}
		}
	}
	
	void setButton()
	{
		for(int i=0;i<4;i++)
		{
			dir[i] = new JButton();
			dir[i].setSize(100, 100);
			dir[i].setBackground(new Color(144, 100, 84));
			dir[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			if(i < 3)
			{
				dir[i].setLocation(50 + 100*(i+1), 630);
			}
			else
			{
				dir[i].setLocation(50 + 100*2, 530);
			}
			dir[i].addActionListener(this);
			
			add(dir[i]);
		}
		
		dir[0].setText("←");
		dir[1].setText("↓");
		dir[2].setText("→");
		dir[3].setText("↑");
	}
	
	void setdir(int idx)
	{
		int yy = 0;		int xx = 0;
		
		if(idx == 0)
		{
			xx = x[0] - 1;
			yy = y[0];
		}
		else if(idx == 2)
		{
			xx = x[0] + 1;
			yy = y[0];
		}
		else if(idx == 1)
		{
			xx = x[0];
			yy = y[0] + 1;
		}
		else if(idx == 3)
		{
			xx = x[0];
			yy = y[0] - 1;
		}
		
		if(SIZE <= xx || xx < 0 || SIZE <= yy || yy < 0) return;
		
		if(data[yy][xx] == 7)
		{
			int[] temp_y = y;		
			int[] temp_x = x;
			int[] temp_snake = snake;
			
			y = new int[snakeSize+1];
			x = new int[snakeSize+1];
			snake = new int[snakeSize+1];
			
			for(int i=0;i<snakeSize;i++)
			{
				y[i] = temp_y[i];
				x[i] = temp_x[i];
			}
			snakeSize++;
			apple = apple - 1;
		}
		if(data[yy][xx] == 8)
		{
			JOptionPane.showMessageDialog(null, "실패하였습니다.", "메세지", JOptionPane.WARNING_MESSAGE);
			_WordGame.frame.setContentPane(new snake_fail_Panel());
			_WordGame.frame.revalidate();
			snake_Wall.instance.setWall(3);
		}
		
		//초기화
		for(int i=0; i<SIZE; i++)
		{
			for(int j=0; j<SIZE; j++)
			{
				if(data[i][j] != 7 && data[i][j] != 8)
				{

					data[i][j] = 0;
				}
			}
		}
		
		for(int i=snakeSize-1;i>0;i--)
		{
			y[i] = y[i-1];
			x[i] = x[i-1];
		}
		y[0] = yy;
		x[0] = xx;
		
		System.out.println("snakeSize: "+snakeSize);
		
		for(int i=1;i<snakeSize;i++)
		{
			if(y[0]==y[i] && x[0]==x[i])
			{
				crush=1;
			}
		}
		
		for(int i=0;i<snakeSize;i++)
		{
			data[y[i]][x[i]] = snake[i];
		}
		setSnake();
		
		if(apple == 0)
		{
			JOptionPane.showMessageDialog(null, "클리어!!", "메세지", JOptionPane.WARNING_MESSAGE);
			snake_Wall.instance.wall++;
		}
		if(crush == 1)
		{
			JOptionPane.showMessageDialog(null, "실패하였습니다.", "메세지", JOptionPane.WARNING_MESSAGE);
			_WordGame.frame.setContentPane(new snake_fail_Panel());
			_WordGame.frame.revalidate();
			snake_Wall.instance.setWall(3);
		}
		
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int idx = 0;
		
		for(int i=0; i<4; i++)
		{
			if(e.getSource() == dir[i])
			{
				idx = i;
				setdir(idx);
			}
		}
		
		if(e.getSource() == backBtn)
		{
			try {
				_WordGame.frame.setContentPane(new Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
			snake_Wall.instance.setWall(3);
		}
		else if(e.getSource() == nextStageBtn)
		{
			try {
				_WordGame.frame.setContentPane(new snake_Game_Panel());
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == ranking_button)
		{
			snake_Rank.instance.setInfo(FileManager.LOG, snake_Wall.instance.wall);
			FileManager.instance.saveSnakeRankData();
			try {
				_WordGame.frame.setContentPane(new snake_rank_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		
		
		
	}

}