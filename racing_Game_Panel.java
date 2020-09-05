package gameBoard;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class racing_Game_Panel extends JPanel implements ActionListener
{

	Random ran = new Random();
	
	final int HORSE_COUNT = 5;
	final int GOAL_LINE = 700;
	racing_Horse[] horse = new racing_Horse[HORSE_COUNT];
	
	JButton startBtn;							//시작 버튼
	JButton resetBtn;							//초기화 버튼
	JButton rankBtn;							//랭크 보여주는 버튼
	JButton backBtn;							//뒤로 버튼
	
	JTextField firstHorse = null;
	
	int timer;	int rank;
	String select;
	
	String str_score = "";
	int num = 0;
	int[] check_rank = new int[HORSE_COUNT];
	
	ImageIcon image = new ImageIcon();
	
	//게임에 부착하는 판넬(버튼, 라벨)
	public racing_Game_Panel()
	{
		setLayout(null);
		setBackground(Color.WHITE);
		Font font = new Font("", Font.BOLD, 15);
		
		JLabel label = new JLabel("        1등 거북이 선택");
		label.setFont(font);
		label.setBackground(Color.lightGray);
		label.setOpaque(true);
		label.setBounds(250, 20, 150, 60);
		add(label);
		
		firstHorse = new JTextField(5);
		firstHorse.setFont(font);
		firstHorse.setText("1등을 입력하세요 ");
		firstHorse.setBounds(400, 20, 150, 60);
		firstHorse.addActionListener(this);
		add(firstHorse);
		
		
		font = new Font("", Font.BOLD, 25);
		startBtn = new JButton("Start");
		startBtn.setBackground(Color.ORANGE);
		startBtn.setFont(font);
		startBtn.setBounds(50, 600, 150, 80);
		startBtn.addActionListener(this);
		add(startBtn);
		
		resetBtn = new JButton("Reset");
		resetBtn.setBackground(Color.ORANGE);
		resetBtn.setFont(font);
		resetBtn.setBounds(250, 600, 150, 80);
		resetBtn.addActionListener(this);
		add(resetBtn);
		
		rankBtn = new JButton("Rank");
		rankBtn.setBackground(Color.ORANGE);
		rankBtn.setFont(font);
		rankBtn.setBounds(450, 600, 150, 80);
		rankBtn.addActionListener(this);
		add(rankBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBackground(Color.ORANGE);
		backBtn.setFont(font);
		backBtn.setBounds(650, 600, 150, 80);
		backBtn.addActionListener(this);
		add(backBtn);
		
		setHorses();
	}
	
	//말 셋팅 -> 리셋
	public void setHorses()
	{
		rank = 0;
		num = 0;
		
		for(int i=0;i<HORSE_COUNT;i++)
		{
			horse[i] = new racing_Horse();		//말 생성
			
			horse[i].width = 160;
			horse[i].height = 90;
			horse[i].x = 100;
			horse[i].y = 100 + i*100;
			horse[i].number = i + 1;
			
			horse[i].speed = ran.nextInt(5) + 1;
			horse[i].state = racing_Horse.WAIT;
			
			horse[i].fileName = String.format("./src/gameboard/turtle%d.png", i+1);
			Image image = new ImageIcon(horse[i].fileName).getImage().getScaledInstance(horse[i].width, horse[i].height, Image.SCALE_SMOOTH);
			horse[i].horeseImage = new ImageIcon(image);
		}
	}
	
	public void update()
	{
		timer += 1;
		//말 이동 속도
		if(timer % 50 == 0) 
		{
			for(int i=0; i<HORSE_COUNT; i++) 
			{
				horse[i].speed = ran.nextInt(5) + 1;
			}
		}
		
		for(int i=0; i<HORSE_COUNT; i++) 
		{
			//달리고 있을때
			if(horse[i].state == racing_Horse.RUN) 
			{
				horse[i].x += horse[i].speed;
			}
		
			//골라인 도착
			if(horse[i].x >= GOAL_LINE && horse[i].state == racing_Horse.RUN) 
			{
				System.out.println();
				System.out.println("i:"+i);
				horse[i].rank = ++rank;
				System.out.println("horse[i].rank: "+horse[i].rank);
				check_rank[num] = i;
				System.out.println("num: " + num);
				System.out.println("check_rank[num]: " + check_rank[num]);
				num++;
				horse[i].x = GOAL_LINE;
				horse[i].speed = 0;
				horse[i].state = racing_Horse.GOAL;
			}
		}
		
	
	}
	
	String checkWinner()
	{
		select = firstHorse.getText();
		System.out.println("select:" + select);
		
		int num_select = Integer.parseInt(select);
		System.out.println("num_select: " + num_select);
		
		int idx = -1;
		
		for(int i=0;i<HORSE_COUNT;i++)
		{
			System.out.println("확인");
			System.out.println("check_rank[i]: "+check_rank[i]);
			
			if(check_rank[i] == num_select)
			{
				idx = i;
			}
			
		}
		
		int score = 0;
		//메세지 추가 해주기 JOption
		if(idx == 0)
		{
			score = score + 100;
			JOptionPane.showMessageDialog(null, "1등 맞추기 성공!.", "메세지", JOptionPane.WARNING_MESSAGE);
			System.out.println("맞추기 성공");
		}
		else
		{
			//등수에 따라 부여 받는 점수가 다르다
			if(idx == 1)
			{
				score = score + 80;
			}
			else if(idx == 2)
			{
				score = score + 60;
			}
			else if(idx == 3)
			{
				score = score + 40;
			}
			else if(idx == 4)
			{
				score = score + 20;
			}
			JOptionPane.showMessageDialog(null, "당신이 선택한 "+num_select+"거북이는 "+(idx+1)+"등입니다", "메세지", JOptionPane.WARNING_MESSAGE);
			System.out.println("당신이 선택한 "+num_select+"거북이는 "+(idx+1)+"등입니다");
		}
		str_score = Integer.toString(score);		//int(score) -> String(str_score)
		return str_score;
		
	}
	
	public void render(Graphics g)
	{
		Font font = new Font("", Font.BOLD, 50);
		Image image_line = new ImageIcon("./src/gameBoard/finishline.jpg").getImage();

		g.setFont(font);
		
		g.drawImage(image_line, 830, 100, 20, 480, this);
		for(int i=0;i<HORSE_COUNT;i++)
		{
			g.drawImage(horse[i].horeseImage.getImage(), horse[i].x, horse[i].y, null);
			if(horse[i].state == racing_Horse.GOAL)
			{
				g.drawString(horse[i].rank + "", 890, horse[i].y + 60);
			}
		}
		
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) 
	{	
		super.paintComponent(g);
		image = new ImageIcon("./src/gameBoard/경마배경.png");
		g.drawImage(image.getImage(), 0, 0, null);
		try 
		{
			Thread.sleep(10);
			repaint();
		}
		catch(Exception e) {}
		
		update();
		render(g);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == startBtn) 
		{
			for(int i=0; i<HORSE_COUNT; i++) 
			{
				horse[i].state = racing_Horse.RUN;
			}		
		}
		else if(e.getSource() == resetBtn) 
		{
			for(int i=0; i<HORSE_COUNT; i++) 
			{
				setHorses();
			}
		}
		else if(e.getSource() == rankBtn)
		{
			str_score = checkWinner();
			racing_Rank.instance.setInfo(FileManager.LOG, str_score);
			//FileManager.instance.saveRacingRankData();
			try {
				_WordGame.frame.setContentPane(new racing_rank_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == backBtn) 
		{
			try {
				_WordGame.frame.setContentPane(new Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}

	}

}