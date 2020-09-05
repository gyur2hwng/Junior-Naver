package gameBoard;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.FontFormatException;


public class snake_fail_Panel extends JPanel implements ActionListener 
{

	JButton rechallengeBtn;				//재도전
	JButton otherGameBtn;					//다른 게임

	public snake_fail_Panel() 
	{
		setLayout(null);
		_WordGame.frame.setTitle("실 패");
		
		Font font = new Font("", Font.BOLD, 50);		//폰트설정
		
		JLabel label = new JLabel("실    패");
		label.setFont(font);
		label.setBackground(Color.PINK);
		label.setOpaque(true);
		label.setBounds(430, 150, 170, 70);
		add(label);
		
		rechallengeBtn = new JButton("재 도 전");
		rechallengeBtn.setBackground(Color.GRAY);		
		rechallengeBtn.setForeground(Color.WHITE);
		rechallengeBtn.setBounds(350, 250, 150, 80);
		rechallengeBtn.addActionListener(this);
		add(rechallengeBtn);
		
		otherGameBtn = new JButton("나가기");
		otherGameBtn.setBackground(Color.GRAY);		
		otherGameBtn.setForeground(Color.WHITE);
		otherGameBtn.setBounds(520, 250, 150, 80);
		otherGameBtn.addActionListener(this);
		add(otherGameBtn);


	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//재도전 하는 순간 -> id와 stage 초기화 되야 한다
		if(e.getSource() == rechallengeBtn)
		{
			snake_Rank.instance.setInfo(FileManager.LOG, 3);
			try {
				_WordGame.frame.setContentPane(new snake_Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == otherGameBtn)
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