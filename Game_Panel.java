package gameBoard;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gameBoard.Title_Panel.myPanel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;


public class Game_Panel extends JPanel implements ActionListener {
	BufferedImage img = null;
	
	JButton oneto50_button;				//게임 버튼(클릭하면 게임 실행)
	JButton snake_button;
	JButton racing_button;
	JButton omok_button;
	JButton ladder_button;
	JButton text_button;
	JButton back_main;					//뒤로 가기 버튼(로그인 창)
	
	Font font;
	
	StopWatch sw;
	
	Game_Panel() throws FontFormatException, IOException 
	{
		
		_WordGame.frame.setTitle("G A M E");
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(1000, 800);
		layeredPane.setLayout(null);
		
		try
		{
			img = ImageIO.read(new File("./src/gameBoard/쥬니버 네이버 게임 창.png"));
		}
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
			System.exit(0);
		}
		
		myPanel panel = new myPanel();
		panel.setSize(1000, 800);
		
		setLayout(null);
		
		setBounds(500,400,1000,800);
		
		Font tempfont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/gameBoard/Binggrae.ttf"));
		Font font = tempfont.deriveFont(Font.BOLD, 15);
		
//---------------------------[게임버튼]--------------------------------------
		//[1] 1 to 50
		
		JLabel label = new JLabel("1 to 50");
		label.setFont(font);
		label.setForeground(Color.WHITE);
		label.setBounds(200, 480, 100, 50);
		add(label);
		
		oneto50_button = new JButton(new ImageIcon("./src/gameBoard/1to50.png"));
		oneto50_button.setBackground(Color.GRAY);		
		oneto50_button.setForeground(Color.WHITE);
		oneto50_button.setBorderPainted(false);
		oneto50_button.setContentAreaFilled(false);
		oneto50_button.setBounds(150, 330, 150, 150);
		oneto50_button.addActionListener(this);
		add(oneto50_button);
		
		//[2] snake_game
		
		label = new JLabel("스네이크 게임");
		label.setFont(font);
		label.setForeground(Color.WHITE);
		label.setBounds(425, 480, 150, 50);
		add(label);
		
		snake_button = new JButton(new ImageIcon("./src/gameBoard/snakeGame.png"));
		snake_button.setBackground(Color.GRAY);		
		snake_button.setForeground(Color.WHITE);
		snake_button.setBorderPainted(false);
		snake_button.setContentAreaFilled(false);
		snake_button.setBounds(400, 330, 150, 150);
		snake_button.addActionListener(this);
		add(snake_button);
		
		//[3] 경마 게임
		
		label = new JLabel("거북이 경주 게임");
		label.setFont(font);
		label.setForeground(Color.WHITE);
		label.setBounds(700, 480, 150, 50);
		add(label);
		
		racing_button = new JButton(new ImageIcon("./src/gameBoard/경마게임.png"));
		racing_button.setBackground(Color.GRAY);		
		racing_button.setForeground(Color.WHITE);
		racing_button.setBorderPainted(false);
		racing_button.setContentAreaFilled(false);
		racing_button.setBounds(670, 330, 150, 150);
		racing_button.addActionListener(this);
		add(racing_button);
		
		//[4] 오목 게임
		
		label = new JLabel("오목 게임");
		label.setFont(font);
		label.setForeground(Color.WHITE);
		label.setBounds(200, 670, 100, 50);
		add(label);
		
		omok_button = new JButton(new ImageIcon("./src/gameBoard/오목게임.png"));
		omok_button.setBackground(Color.GRAY);		
		omok_button.setForeground(Color.WHITE);
		omok_button.setBorderPainted(false);
		omok_button.setContentAreaFilled(false);
		omok_button.setBounds(150, 520, 150, 150);
		omok_button.addActionListener(this);
		add(omok_button);
		
		//[5] 사다리 게임
		
		label = new JLabel("사다리 게임");
		label.setFont(font);
		label.setForeground(Color.WHITE);
		label.setBounds(430, 670, 100, 50);
		add(label);
		
		ladder_button = new JButton(new ImageIcon("./src/gameBoard/사다리 게임.png"));
		ladder_button.setBackground(Color.GRAY);		
		ladder_button.setForeground(Color.WHITE);
		ladder_button.setBorderPainted(false);
		ladder_button.setContentAreaFilled(false);
		ladder_button.setBounds(400, 520, 150, 150);
		ladder_button.addActionListener(this);
		add(ladder_button);
		
		//[6] 타자 게임
		
		label = new JLabel("타자 게임");
		label.setFont(font);
		label.setForeground(Color.WHITE);
		label.setBounds(710, 670, 100, 50);
		add(label);
		
		text_button = new JButton(new ImageIcon("./src/gameBoard/단어 찾기.png"));
		text_button.setBackground(Color.GRAY);		
		text_button.setForeground(Color.WHITE);
		text_button.setBorderPainted(false);
		text_button.setContentAreaFilled(false);
		text_button.setBounds(670, 520, 150, 150);
		text_button.addActionListener(this);
		add(text_button);
//-----------------------------------------------------------------------
		
		//뒤로 가기 버튼
		font = tempfont.deriveFont(Font.BOLD, 15);
		back_main = new JButton("뒤로가기");
		back_main.setBackground(Color.WHITE);	
		back_main.setBounds(425, 720, 120, 30);
		back_main.addActionListener(this);
		add(back_main);
		
		layeredPane.add(panel);
		add(layeredPane);
		setVisible(true);
	}
	
	class myPanel extends JPanel
	{
		public void paint(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == oneto50_button)
		{
			_WordGame.frame.setContentPane(new oneto50_Game_Panel());
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == snake_button)
		{
			try {
				_WordGame.frame.setContentPane(new snake_Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == racing_button)
		{
			_WordGame.frame.setContentPane(new racing_Game_Panel());
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == omok_button)
		{
			try {
				_WordGame.frame.setContentPane(new omok_Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == ladder_button)
		{
			_WordGame.frame.setContentPane(new ladder_Game_Panel());
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == text_button)
		{
			try {
				_WordGame.frame.setContentPane(new text_Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == back_main)
		{
			_WordGame.frame.setContentPane(new Login_Panel());
			_WordGame.frame.revalidate();
		}
		
	}

}