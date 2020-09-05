package gameBoard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import gameBoard.Title_Panel.myPanel;

public class Login_Panel extends JPanel implements ActionListener{
	JButton login_button = null; //로그인 버튼
	JButton home_button = null; //홈 버튼
	JTextField id_tf = null;
	JTextField pw_tf = null;
	BufferedImage img = null;
	JButton join_button = null; //회원 가입 버튼
	JButton find_button = null; //아이디 비밀번호찾기 버튼
	
	Login_Panel(){
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(1000, 800);
		layeredPane.setLayout(null);
		try
		{
			img = ImageIO.read(new
					File("./src/gameBoard/네이버 로그인 창.png"));
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
		Font font = new Font("", Font.BOLD, 50); // 글씨체 설정
		font = new Font("", Font.BOLD, 15);
		//완료
		login_button = new JButton(new
				ImageIcon("./src/gameBoard/로그인 로그인창.png"));
		login_button.setBounds(250, 230, 490, 145);
		login_button.setBorderPainted(false);
		login_button.setContentAreaFilled(false);
		login_button.addActionListener(this);
		add(login_button);
		home_button = new JButton(new
				ImageIcon("./src/gameBoard/홈버튼.png"));
		home_button.setBounds(15, 15, 50, 50);
		home_button.setBorderPainted(false);
		home_button.setContentAreaFilled(false);
		home_button.addActionListener(this);
		add(home_button);
		//아이디 입력 완료
		id_tf = new JTextField(5);
		id_tf.setText("아이디");
		id_tf.setBounds(263, 155, 465, 53);
		id_tf.addActionListener(this);
		add(id_tf);
		//비밀번호 입력
		pw_tf = new JTextField(10);
		pw_tf.setText("비밀번호");
		pw_tf.setBounds(263, 210, 465, 53);
		pw_tf.addActionListener(this);
		add(pw_tf);
		find_button = new JButton(new
				ImageIcon("./src/gameBoard/로그인에서 아이디 비번 찾기.png"));
		find_button.setSize(180, 20);
		find_button.setLocation(367, 559);
		find_button.setBorderPainted(false);
		find_button.setContentAreaFilled(false);
		find_button.addActionListener(this);
		add(find_button);
		join_button = new JButton(new
				ImageIcon("./src/gameBoard/로그인에서 회원가입.png"));
		join_button.setSize(120, 20);
		join_button.setLocation(538, 559);
		join_button.setBorderPainted(false);
		join_button.setContentAreaFilled(false);
		join_button.addActionListener(this);
		add(join_button);
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
		if(e.getSource() == home_button)
		{
			_WordGame.frame.setContentPane(new
					Title_Panel());
			_WordGame.frame.revalidate();
		}
		else if (e.getSource() == find_button)
			//찾기 버튼 눌렀을 때
		{
			_WordGame.frame.setContentPane(new
					Find_Panel());
			_WordGame.frame.revalidate();
		}
		else if (e.getSource() == join_button)
			//회원 가입 버튼 눌렀을 때
		{
			Join_Panel join_panel = new Join_Panel();
			_WordGame.frame.setContentPane(join_panel);
			_WordGame.frame.revalidate();
		}
		else if (e.getSource() == login_button)
			//로그인 버튼 눌렀을 때
		{
			if(id_tf.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "ID를 입력해주세요", "로그인 실패", JOptionPane.WARNING_MESSAGE);
			}
			else if(pw_tf.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "PW를 입력해주세요", "로그인 실패", JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				int check =
						FileManager.instance.login(id_tf.getText(), pw_tf.getText());
				if(check == -1)
				{
					JOptionPane.showMessageDialog(null, "ID와 PW를 확인해주세요", "로그인 실패",
							JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					//로그인 판넬에서 로그인 성공시 -> game 목록으로 간다
					_WordGame.frame.setContentPane(new game_before_Panel());
					_WordGame.frame.revalidate();
				}
			}
		}
	}
}