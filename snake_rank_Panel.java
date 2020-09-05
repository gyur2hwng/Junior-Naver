package gameBoard;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class snake_rank_Panel extends JPanel implements ActionListener {
	JLabel mainLb;
	JLabel[] rankLb;
	
	JButton backMain;
	ImageIcon img = new ImageIcon();
	
	snake_rank_Panel() throws FontFormatException, IOException
	{
		setLayout(null);
		
		Font tempfont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/gameBoard/Binggrae.ttf"));
	    Font font = tempfont.deriveFont(Font.BOLD, 10);
		
		backMain = new JButton("처음으로");
		backMain.setBackground(Color.WHITE);
		backMain.setForeground(Color.BLACK);
		backMain.setBounds(50, 50, 120, 30);
		backMain.setFont(font);
		backMain.addActionListener(this);
		add(backMain);
		
		font = tempfont.deriveFont(Font.BOLD, 30);
		
		mainLb = new JLabel("스네이크 게임 랭킹");
		mainLb.setFont(font);
		mainLb.setBounds(400, 200, 400, 150);
		add(mainLb);
		
		font = tempfont.deriveFont(Font.BOLD, 20);
		rankLb = new JLabel[3];
		
		for(int i=0; i<3; i++) 
		{
			rankLb[i] = new JLabel(); 
			rankLb[i].setFont(font);
			rankLb[i].setBounds(350,  290+(50*i), 200, 100);
			add(rankLb[i]);
		}
		
		for(int i=0; i<snake_Rank.instance.rank.length; i++) 
		{
			if(snake_Rank.instance.rank[i][0].equals("blank1") || snake_Rank.instance.rank[i][0].equals("blank2") || snake_Rank.instance.rank[i][0].equals("blank3"))
			{
				rankLb[i].setText("[" + (i+1) + "등] ");
			}
			else
			{
				int count = Integer.parseInt(snake_Rank.instance.rank[i][1]);
				rankLb[i].setText("[" + (i+1) + "등] " + snake_Rank.instance.rank[i][0] + " : " + (count-2));
			}
			
		}
	}
	
	//==============================================
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon img = new ImageIcon("./src/gameBoard/ranking_back.png");
		g.drawImage(img.getImage(),  0,  0,  null);	
	}
	//==============================================
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == backMain)
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
	}

}