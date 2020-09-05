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
public class text_rank_Panel2 extends JPanel implements ActionListener {	
	JButton back_main;
	
	JLabel mainLb;
	JLabel [] rankLb;
	
	ImageIcon img = new ImageIcon();
	
	text_rank_Panel2() throws FontFormatException, IOException {
		setLayout(null);
		
		Font tempfont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/gameBoard/Binggrae.ttf"));
	    Font font = tempfont.deriveFont(Font.BOLD, 10);
		
		back_main = new JButton("처음으로");
		back_main.setBackground(Color.WHITE);
		back_main.setForeground(Color.BLACK);
		back_main.setBounds(50, 50, 120, 30);
		back_main.setFont(font);
		back_main.addActionListener(this);
		add(back_main);
		
	    font = tempfont.deriveFont(Font.BOLD, 30);
		
		mainLb = new JLabel("타자 게임 랭킹");
		mainLb.setFont(font);
		mainLb.setBounds(400, 200, 400, 150);
		add(mainLb);
		
		font = tempfont.deriveFont(Font.BOLD, 20);
		rankLb = new JLabel[3];
		
		for (int i=0;i<3;i++) {
			rankLb[i] = new JLabel();
			rankLb[i].setFont(font);
			rankLb[i].setBounds(350,  290+(50*i), 200, 100);
			add(rankLb[i]);
		}
		for (int
				i=0;i<text_Rank2.instance.rank_text.length;i++) {
			if (text_Rank2.instance.rank_text[i]
					[0].equals("blank1") ||
					text_Rank2.instance.rank_text[i][0].equals("blank2") ||
					text_Rank2.instance.rank_text[i][0].equals("blank3")) {
				rankLb[i].setText("[" + (i+1) + "등]");
			}
			else {
				rankLb[i].setText("[" + (i+1) + "등] "
						+ text_Rank2.instance.rank_text[i][0] + " : " +
						text_Rank2.instance.rank_text[i][1]);
			}
		}
	}
	
	//==============================================
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		img = new ImageIcon("./src/gameBoard/ranking_back.png");
		g.drawImage(img.getImage(),  0,  0,  null);	
	}
	//==============================================
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back_main) {
			try {
				_WordGame.frame.setContentPane(new
						text_stage2_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
	}
}