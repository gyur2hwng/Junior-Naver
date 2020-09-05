package gameBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.FontFormatException;
import java.awt.Graphics;

public class game_before_Panel extends JPanel implements ActionListener {
	BufferedImage img = null;
	JButton gameStartBtn = new JButton();
	
	public game_before_Panel() 
	{
		//추가
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(1000, 800);
		layeredPane.setLayout(null);
		
		try
		{
			img = ImageIO.read(new File("./src/gameBoard/쥬니버 네이버 시작 창.png"));
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
		
		gameStartBtn = new JButton(new ImageIcon("./src/gameBoard/놀이학습.png"));
		gameStartBtn.setSize(244, 40);
		gameStartBtn.setLocation(450, 308);
		gameStartBtn.setBorderPainted(false);
		gameStartBtn.setContentAreaFilled(false);
		gameStartBtn.addActionListener(this);
		add(gameStartBtn);
		
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
		if(gameStartBtn == e.getSource())			//로그인 버튼 눌렀을 때
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