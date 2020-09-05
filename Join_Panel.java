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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameBoard.Title_Panel.myPanel;

public class Join_Panel extends JPanel implements ActionListener{
	JTextField name_tf;
	JTextField age_tf;
	JTextField mobile_tf;
	JTextField id_tf;
	JTextField pw_tf;
	JButton join_membership_button;
	JButton back_button;
	JButton check_id;
	boolean check_i; //아이디 중복검사
	
	BufferedImage img = null;
	
	Join_Panel()
	{
		//추가
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(1000, 800);
		layeredPane.setLayout(null);
		
		try
		{
			img = ImageIO.read(new File("./src/gameBoard/회원가입 전체 창.png"));
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
		
		Font font = new Font("맑은 고딕", Font.BOLD, 15);
		
		
		id_tf = new JTextField(10);
		id_tf.setBounds(262, 255, 417, 50);
		add(id_tf);
		
		pw_tf = new JTextField(10);
		pw_tf.setBounds(262, 340, 417, 50);
		add(pw_tf);
		
		name_tf = new JTextField(10);
		name_tf.setBounds(262, 417, 417, 50);
		add(name_tf);
		
		mobile_tf = new JTextField(10);
		mobile_tf.setBounds(262, 550, 310, 48);
		add(mobile_tf);
		
		age_tf = new JTextField("나이를 입력해주세요",10);
		age_tf.setBounds(262, 613, 415, 50);
		add(age_tf);
		
		name_tf.addActionListener(this);
		age_tf.addActionListener(this);
		mobile_tf.addActionListener(this);
		id_tf.addActionListener(this);
		pw_tf.addActionListener(this);
		
		join_membership_button = new JButton("가입");
		join_membership_button.setBackground(Color.WHITE);
		join_membership_button.setForeground(Color.BLACK);
		join_membership_button.setBounds(530, 700, 100, 30);
		join_membership_button.addActionListener(this);
		join_membership_button.setFont(font);
		add(join_membership_button);
		
		back_button = new JButton("홈으로");
		back_button.setBackground(Color.WHITE);
		back_button.setForeground(Color.BLACK);
		back_button.setBounds(360, 700, 100, 30);
		back_button.setFont(font);
		back_button.addActionListener(this);
		add(back_button);
		
		check_id = new JButton("중복 체크");
		check_id.setBackground(Color.WHITE);
		check_id.setForeground(Color.BLACK);
		check_id.setBounds(679, 255, 90, 50);
		check_id.addActionListener(this);
		add(check_id);
		
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
		if(e.getSource() ==back_button ) //뒤로가기
		{
			_WordGame.frame.setContentPane(new Title_Panel());
			_WordGame.frame.revalidate();
		}
		else if(e.getSource() == check_id) {
			check_i = FileManager.instance.checkId(id_tf.getText());
			if(check_i)
			{
				JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다",
						"아이디 중복확인",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "중복된 아이디입니다",
						"아이디 중복확인",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getSource() ==join_membership_button ) //회원가입
			{
			if (name_tf.getText().equals("") ||
					age_tf.getText().equals("") ||
					mobile_tf.getText().equals("")||
					id_tf.getText().equals("") ||
					pw_tf.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null,
						"모든필드를 입력하세요",
						"빈 필드 존재",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!check_i)
			{
				JOptionPane.showMessageDialog(null, "중복 확인을 해주세요",
						"중복 확인 미실행",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			UserInfo user = new UserInfo();
			user.name = name_tf.getText();
			user.age = Integer.parseInt(age_tf.getText());
			user.mobile = mobile_tf.getText();
			user.id = id_tf.getText();
			user.pw = pw_tf.getText();
			user.omok_win = 0;
			FileManager.instance.addUser(user);
			JOptionPane.showMessageDialog(null, "회원가입 축하드립니다.",
					"메세지",
					JOptionPane.WARNING_MESSAGE);
			_WordGame.frame.setContentPane(new Title_Panel());
			_WordGame.frame.revalidate();
			}
	}
}