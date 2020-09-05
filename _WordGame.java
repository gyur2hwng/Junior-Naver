package gameBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


class Title_Panel extends JPanel implements ActionListener {
	JButton loginBtn = new JButton();				//로그인 버튼
	BufferedImage img = null;
	
	JButton join_button = null;			//회원 가입 버튼
	JButton find_button = null;			//아이디 비밀번호 찾기 버튼
	
	Title_Panel()
	{	
		//추가
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(1000, 800);
		layeredPane.setLayout(null);
		
		try
		{
			img = ImageIO.read(new File("./src/gameBoard/네이버 창.png"));
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
		
		//setBackground(Color.WHITE);
	
		
		//로그인 버튼 -> 회원가입 기능, 아이디 패스워드 찾기 기능, 로그인 기능 구현
		loginBtn = new JButton(new ImageIcon("./src/gameBoard/로그인.png"));
		loginBtn.setSize(244, 40);
		loginBtn.setLocation(700, 207);
		loginBtn.setBorderPainted(false);
		loginBtn.setContentAreaFilled(false);
		loginBtn.addActionListener(this);
		add(loginBtn);
		
		join_button = new JButton(new ImageIcon("./src/gameBoard/회원가입.png"));
		join_button.setSize(120, 20);
		join_button.setLocation(853, 255);
		join_button.setBorderPainted(false);
		join_button.setContentAreaFilled(false);
		join_button.addActionListener(this);
		add(join_button);
		
		find_button = new JButton(new ImageIcon("./src/gameBoard/아이디 비밀번호 찾기.png"));
		find_button.setSize(120, 20);
		find_button.setLocation(700, 255);
		find_button.setBorderPainted(false);
		find_button.setContentAreaFilled(false);
		find_button.addActionListener(this);
		add(find_button);
		
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
		if(loginBtn == e.getSource())			//로그인 버튼 눌렀을 때
		{
			_WordGame.frame.setContentPane(new Login_Panel());
			_WordGame.frame.revalidate();
		}
		else if (e.getSource() == find_button)				//찾기 버튼 눌렀을 때
		{
			_WordGame.frame.setContentPane(new Find_Panel());	
			_WordGame.frame.revalidate();
		}
		else if (e.getSource() == join_button) 			//회원 가입 버튼 눌렀을 때
		{
			Join_Panel join_panel = new Join_Panel();
			_WordGame.frame.setContentPane(join_panel);	
			_WordGame.frame.revalidate();
		}
	}

}

public class _WordGame{
	static JFrame frame = new JFrame();
	public static void main(String[] args) {
		frame.setTitle("네이버");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		frame.setVisible(true);
		
	    // ----시작 위치를 바탕화면의 중앙으로 바꾸는 코드
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize(); // 모니터 사이즈를 가져온다.
		frame.setLocation(screenSize.width / 2 - 500, screenSize.height / 2 - 400);
		
		//이어 없으면 데이터 로드 안됌
		FileManager.instance.loadData();

		Title_Panel title_panel = new Title_Panel();
		frame.setContentPane(title_panel);
	    frame.revalidate();
	}
	
}