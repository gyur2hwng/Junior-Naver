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

public class text_stage2_Panel extends JPanel implements ActionListener {
	JButton game_button; //게임 버튼
	JButton ranking_button; //랭킹 버튼
	JButton reset_button; //리셋 버튼
	JButton back_main; //처음으로
	JButton check_button; //답 확인 버튼
	JButton cheatkey; //치트키 버튼
	JButton countLb; //정답카운트 레이블
	JLabel inputLb; //입력 레이블
	static JTextField in_tf = null; //입력란
	String quiz[]; //게임 문제
	int WIN_COUNT = 0; //정답을 맞추는 count
	static JLabel watchLb; //스탑와치 레이블
	text_StopWatch2 sw; //스탑와치
	
	ImageIcon img = new ImageIcon();
	JLabel bottom;
	int check = 0; //리셋 가능 여부를 확인하기 위한 체크
	
	text_stage2_Panel() throws FontFormatException, IOException {
		setLayout(null);
		
		Font tempfont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/gameBoard/Binggrae.ttf"));
	    Font font = tempfont.deriveFont(Font.BOLD, 10);
		
		_WordGame.frame.setTitle("타자 게임");
		game_button = new JButton("게임 시작");
		game_button.setBackground(Color.WHITE);
		game_button.setForeground(Color.BLACK);
		game_button.setBounds(340, 10, 100, 30);
		game_button.setFont(font);
		game_button.addActionListener(this);
		add(game_button);
		
		reset_button = new JButton("리셋");
		reset_button.setBackground(Color.WHITE);
		reset_button.setForeground(Color.BLACK);
		reset_button.setBounds(460, 10, 80, 30);
		reset_button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		reset_button.setFont(font);
		reset_button.addActionListener(this);
		add(reset_button);
		
		ranking_button = new JButton("랭킹");
		ranking_button.setBackground(Color.WHITE);
		ranking_button.setForeground(Color.BLACK);
		ranking_button.setBounds(560, 10, 80, 30);
		ranking_button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ranking_button.setFont(font);
		ranking_button.addActionListener(this);
		add(ranking_button);
		
		check_button = new JButton("확인");
		check_button.setBackground(Color.WHITE);
		check_button.setForeground(Color.BLACK);
		check_button.setBounds(440, 450, 80, 30);
		check_button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		check_button.setFont(font);
		check_button.addActionListener(this);
		add(check_button);
		
		back_main = new JButton("뒤로");
		back_main.setBackground(Color.WHITE);
		back_main.setForeground(Color.BLACK);
		back_main.setBounds(710, 10, 80, 30);
		back_main.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		back_main.setFont(font);
		back_main.addActionListener(this);
		add(back_main);
		
		cheatkey = new JButton("치트키");
		cheatkey.setBackground(Color.WHITE);
		cheatkey.setForeground(Color.BLACK);
		cheatkey.setBounds(810, 10, 80, 30);
		cheatkey.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cheatkey.setFont(font);
		cheatkey.addActionListener(this);
		add(cheatkey);
		
		//게임 패널
		setGamePanel();
	}
	
	//==============================================
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		img = new ImageIcon("./src/gameBoard/text_back2.png");
		g.drawImage(img.getImage(),  0,  0,  null);	
	}
	//==============================================
	
	//버튼 넣기
	public void setGamePanel() throws FontFormatException, IOException {
		
		Font tempfont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/gameBoard/Binggrae.ttf"));
	    Font font = tempfont.deriveFont(Font.BOLD, 20);
		
	    bottom = new JLabel();
		bottom.setBounds(0,  713,  1000,  87);
		bottom.setOpaque(true);
		bottom.setBackground(new Color(33, 106, 4));
		add(bottom);
		
		countLb = new JButton("문제 " + WIN_COUNT + "번");
		countLb.setFont(font);
		countLb.setBounds(150, 200, 120, 50);
		countLb.setOpaque(true);
		countLb.setBorderPainted(false);
		countLb.setBackground(new Color(216, 255, 254));
		countLb.setForeground(Color.BLACK);
		add(countLb);
		
		font = tempfont.deriveFont(Font.BOLD, 50);
		
		inputLb = new JLabel("");
		inputLb.setFont(font);
		inputLb.setBounds(370, 200, 400, 100);
		inputLb.setOpaque(true);
		inputLb.setBackground(Color.CYAN);
		inputLb.setForeground(Color.BLACK);
		add(inputLb);
		
		font = tempfont.deriveFont(Font.BOLD, 20);
		
		in_tf = new JTextField();
		in_tf.setFont(font);
		in_tf.setBounds(440, 400, 150, 50);
		in_tf.setBackground(Color.WHITE);
		add(in_tf);
		
		watchLb = new JLabel();
		watchLb.setFont(font);
		watchLb.setBounds(750, 200, 100, 30);
		watchLb.setOpaque(true);
		watchLb.setBackground(Color.CYAN);
		watchLb.setForeground(Color.BLACK);
		add(watchLb);
	}
	
	//리셋
	public void setInit() {
		inputLb.setText("");
		in_tf.setText("");
		WIN_COUNT = 0;
		check = 0;
		countLb.setText("문제 " + WIN_COUNT + "번");
		setGameStr();
	}
	
	//게임 문제 만들기
	public void setGameStr() {
		
		check = 1;
		
		//전체 문제 리스트
		String gameStr[] = {
				"페퍼민트", "옥토버페스트", "인터페이스",
				"라이브러리", "오스트리아",
				"엔지니어링", "소프트웨어", "프랑크푸르트",
				"애플리케이션", "물랑루즈",
				"가상메모리", "리시안셔스", "해바라기", "유칼립투스", "스토리보드",
				"셰익스피어", "빈센트반고흐", "잘츠부르크",
				"지베르니", "할슈타트"
		};
		Random ran = new Random();
		quiz = new String[10];
		//이번 게임 문제 리스트 만들기
		int QUIZ_CNT = 0; //게임 문제 리스트 배열 크기 카운트
		for (int i=0;i<10;i++) {
			int r = ran.nextInt(20);
			if (QUIZ_CNT == 0) {
				quiz = new String[1];
			}
			else {
				String temp [] = quiz;
				quiz = new String[QUIZ_CNT+1];
				for (int j=0;j<temp.length;j++) {
					quiz[j] = temp[j];
				}
				temp = null;
			}
			quiz[QUIZ_CNT] = gameStr[r];
			QUIZ_CNT++;
		}
		inputLb.setText(quiz[0] + "");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//뒤로가기 버튼 눌렀을 때
		if (e.getSource() == back_main) {
			try {
				_WordGame.frame.setContentPane(new text_Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		//랭킹 버튼 눌렀을 때
		else if (e.getSource() == ranking_button) {
			try {
				_WordGame.frame.setContentPane(new
						text_rank_Panel2());
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		//게임 버튼 눌렀을 때
		else if (e.getSource() == game_button) {
			setInit();
			sw = new text_StopWatch2();
			sw.start();
		}
		//리셋 버튼 눌렀을 때
		else if (e.getSource() == reset_button) {
			
			try {
				if (check == 1) {
					setInit();
					sw.play = false;
					sw.interrupt();
					watchLb.setText("");
				}
			} catch (Exception e2) {
				
			}
		}
		//치트키 버튼 눌렀을 때
		else if (e.getSource() == cheatkey) {
			JOptionPane.showMessageDialog(null, "총 소요시간은 00:30:00초 입니다.", "게임 종료합니다.",
					JOptionPane.INFORMATION_MESSAGE);
			in_tf.requestFocus();
		}

		//확인 버튼 눌렀을 때
		else if (e.getSource() == check_button) {
			if(in_tf.getText().equals(inputLb.getText()))
			{
				WIN_COUNT++;
				if (WIN_COUNT < 10) {
					countLb.setText("WIN_COUNT = "
							+ WIN_COUNT);
					inputLb.setText(quiz[WIN_COUNT]);
					in_tf.setText("");
				}
				else if (WIN_COUNT >= 10) {
					WIN_COUNT = 9;
					sw.play = false;
					sw.interrupt();
					JOptionPane.showMessageDialog(null, "총 소요시간은 " + sw.timeText + "초입니다.", "게임 종료",
							JOptionPane.INFORMATION_MESSAGE);
					watchLb.setText("");
					inputLb.setText("");
					in_tf.setText("");
					text_Rank2.instance.text_setInfo(FileManager.LOG, sw.timeText);
					FileManager.instance.saveTextStage2RankData();
					in_tf.requestFocus();
				}
			}
		}
	}
}