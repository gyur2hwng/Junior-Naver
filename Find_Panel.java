package gameBoard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameBoard.Title_Panel.myPanel;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Find_Panel extends JPanel implements ActionListener{
	JButton find_pw_button;
	JButton back_main;
	JButton find_id_button;
	JTextField name_id_tf;
	JTextField mobile_id_tf;
	JTextField name_pw_tf;
	JTextField mobile_pw_tf;
	JTextField id_pw_tf;
	
	BufferedImage img = null;
	
	public Find_Panel(){
		
		
		//추가
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(1000, 800);
		layeredPane.setLayout(null);
		
		try
		{
			img = ImageIO.read(new File("./src/gameBoard/아이디 비번 찾기 전체 화면.png"));
		}
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
			System.exit(0);
		}
		
		myPanel panel = new myPanel();
		panel.setSize(1000, 800);
		
		setLayout(null);
		
		setBounds(600,400,1000,800);
		
		//ID찾기 라벨
		Font font = new Font("맑은 고딕", Font.BOLD, 15);
		
		//이름 넣기 텍스트 필드
		name_id_tf = new JTextField("이름 입력", 10);
		name_id_tf.setBounds(380, 128, 228, 26);
		add(name_id_tf);
		
		//전화 번호 넣기 텍스트 필드
		mobile_id_tf = new JTextField("전화번호 입력",10);
		mobile_id_tf.setBounds(380, 159, 228, 26);
		add(mobile_id_tf);
		
		//ID찾기 버튼 -> 클릭하면 찾아진다
		find_id_button = new JButton("ID 찾기");
		find_id_button.setBackground(Color.WHITE);
		find_id_button.setFont(font);
		find_id_button.setForeground(Color.BLACK);
		find_id_button.setBounds(730, 230, 120, 30);
		find_id_button.addActionListener(this);//이벤트 처리
		add(find_id_button);
		//-----------------------------------
		
		//ID넣기 텍스트 필드
		id_pw_tf = new JTextField("아이디 입력", 10);
		id_pw_tf.setBounds(380, 550, 245, 29);
		add(id_pw_tf);
		
		//이름넣기 텍스트 필드
		name_pw_tf = new JTextField("이름 입력",10);
		name_pw_tf.setBounds(380, 426, 228, 26);
		add(name_pw_tf);
		
		//전화번호 넣기 텍스트 필드
		mobile_pw_tf = new JTextField("전화번호 입력",10);
		mobile_pw_tf.setBounds(380, 457, 228, 26);
		add(mobile_pw_tf);
		
		//PW 찾기 버튼
		find_pw_button = new JButton("PW 찾기");
		find_pw_button.setBackground(Color.WHITE);
		find_pw_button.setFont(font);
		find_pw_button.setForeground(Color.BLACK);
		find_pw_button.setBounds(730, 600, 120, 30);
		find_pw_button.addActionListener(this);
		add(find_pw_button);
		
		//처음 화면 버튼
		back_main = new JButton("처음화면");
		back_main.setBackground(Color.WHITE);
		back_main.setFont(font);
		back_main.setForeground(Color.BLACK);
		back_main.setBounds(400, 700, 120, 30);
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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back_main)
		{
			_WordGame.frame.setContentPane(new
					Title_Panel());
			_WordGame.frame.revalidate();
		}
		else if (e.getSource() == find_id_button)
			//아이디 찾기 버튼 눌렀을 때
		{
			//name이랑 전화번호 일치하는거 들어간다
			String id = FileManager.instance.findID(name_id_tf.getText(),
							mobile_id_tf.getText());
			if (id == "")
			{
				JOptionPane.showMessageDialog(null, "찾는 아이디가 없습니다.", "아이디 찾기", JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "아이디 : " + id, "아이디 찾기", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if (e.getSource() == find_pw_button)
			//비밀번호 찾기 버튼 눌렀을 때
		{
			String pw =
					FileManager.instance.findPW(name_pw_tf.getText(),
							mobile_pw_tf.getText(), id_pw_tf.getText());
			if (pw == "")
			{
				JOptionPane.showMessageDialog(null, "찾는 비밀번호가 없습니다.", "비밀번호 찾기", JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "비밀번호 : " + pw , "비밀번호 찾기", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}