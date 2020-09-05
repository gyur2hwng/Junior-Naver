package gameBoard;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class text_Game_Panel extends JPanel implements ActionListener
{
	JLabel titleLb;
	JButton back_main;
	JButton stage1;
	JButton stage2;
	JLabel bottom;
	
	ImageIcon img = new ImageIcon();

	text_Game_Panel() throws FontFormatException, IOException {
		setLayout(null);
		
		Font tempfont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/gameBoard/Binggrae.ttf"));
	    Font font = tempfont.deriveFont(Font.BOLD, 10);

		back_main = new JButton("BACK");
		back_main.setBackground(Color.WHITE);
		back_main.setForeground(Color.BLACK);
		back_main.setBounds(710, 10, 80, 30);
		back_main.setFont(font);
		back_main.addActionListener(this);
		add(back_main);
		
		font = tempfont.deriveFont(Font.BOLD, 30);
		stage1 = new JButton("난이도[하]");
		stage1.setBackground(Color.WHITE);
		stage1.setForeground(Color.BLACK);
		stage1.setBounds(280, 300, 200, 200);
		stage1.setFont(font);
		stage1.setBorder(BorderFactory.createLineBorder(new Color(33, 106, 4)));
		stage1.addActionListener(this);
		add(stage1);
		
		stage2 = new JButton("난이도[상]");
		stage2.setBackground(Color.WHITE);
		stage2.setForeground(Color.BLACK);
		stage2.setBounds(500, 300, 200, 200);
		stage2.setFont(font);
		stage2.setBorder(BorderFactory.createLineBorder(new Color(33, 106, 4)));
		stage2.addActionListener(this);
		add(stage2);
		
		bottom = new JLabel();
		bottom.setBounds(0,  720,  1000,  80);
		bottom.setOpaque(true);
		bottom.setBackground(new Color(33, 106, 4));
		add(bottom);
		
	}
	
	//==============================================
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		img = new ImageIcon("./src/gameBoard/text_back1.jpg");
		g.drawImage(img.getImage(),  0,  0,  null);	
	}
	//==============================================
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back_main) {
			try {
				_WordGame.frame.setContentPane(new Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		if (e.getSource() == stage1) {
			try {
				_WordGame.frame.setContentPane(new
						text_stage1_Panel());
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		if (e.getSource() == stage2) {
			try {
				_WordGame.frame.setContentPane(new
						text_stage2_Panel());
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
	}
}