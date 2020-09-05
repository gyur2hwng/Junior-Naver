package gameBoard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

class SettingPanel extends JPanel implements ActionListener{
	Ladder ladder = null;
	LadderList ladderlist = null;
	JButton back_title = new JButton();
    ImageIcon image = new ImageIcon();
	
	 public void paintComponent(Graphics g) {
			image = new ImageIcon("./src/gameBoard/사다리배경.jpg");
			g.drawImage(image.getImage(), 0, 0, null);
	}
	
	SettingPanel() {
		
		setLayout(null);
		
		ladderlist = new LadderList();
		ladderlist.setSize(400, 600);
		ladderlist.setBackground(new Color(0, 206, 206));
		ladderlist.setLocation(500,100);
		add(ladderlist);
		
		ladder = new Ladder(ladderlist);
		ladder.setSize(330,250);
		ladder.setLocation(50,100);
		ladder.setBackground(new Color(0, 206, 206));
		add(ladder);
		
		back_title.setText("뒤로 가기");
		back_title.setSize(200, 80);
		back_title.setFont(new Font("Dialog", Font.BOLD, 23));
		back_title.setLocation(120, 400);
		back_title.setBackground(new Color(254, 206, 50));
		back_title.addActionListener(this);
		add(back_title);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back_title) {						// 뒤로 가기
			_WordGame.frame.setContentPane(new ladder_Game_Panel());
			_WordGame.frame.revalidate();
		}
	}
}
class Ladder extends JPanel implements ActionListener{
	
	Font font = new Font("", Font.BOLD, 15);
	JButton insert_btn = new JButton();
	JTextField kor_tf = new JTextField();
	JLabel kor_lb = new JLabel();
	LadderList ladderlist = null;
	
	JLabel playerC = new JLabel("총 플레이어 : " + ladder_Game_Panel.player+"명");
	JLabel listcount = new JLabel();
	
	Ladder(LadderList ladderlist){
		this.ladderlist = ladderlist;
		setLayout(null);
		
		playerC.setBounds(30, 30, 250, 50);
		playerC.setHorizontalAlignment(JLabel.CENTER);
		playerC.setFont(new Font("Dialog", Font.BOLD, 15));
		playerC.setBackground(new Color(254, 206, 50));
		add(playerC);
		
		listcount.setBounds(30, 190, 250, 50);
		listcount.setHorizontalAlignment(JLabel.CENTER);
		listcount.setFont(new Font("Dialog", Font.BOLD, 15));
		listcount.setBackground(new Color(254, 206, 50));
		add(listcount);

		kor_lb.setSize(100, 30);
		kor_lb.setLocation(30, 80);
		kor_lb.setFont(font);
		kor_lb.setText("항목 입력");
		kor_lb.setBackground(Color.ORANGE);
		kor_lb.setOpaque(true);
		add(kor_lb);
	
		kor_tf.setSize(150, 30);
		kor_tf.setLocation(150, 80);
		add(kor_tf);
		
		insert_btn.setSize(100, 40);
		insert_btn.setLocation(115, 130);
		insert_btn.setBackground(new Color(254, 206, 50));
		insert_btn.setText("추가 하기");
		add(insert_btn);
		insert_btn.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (insert_btn == e.getSource()) {
			set_insert_btn();
			return;
		}
	}
	
	void set_insert_btn() {
		
		if (kor_tf.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "단어를 입력하세요.");
			return;
		}
		
		if (ladderlist.word_list.size() == ladder_Game_Panel.player) {
			JOptionPane.showMessageDialog(null, "더 이상 추가 할 수 없습니다.");
			return;
		}
		
		for (int i = 0; i < ladderlist.word_list.size(); i++) {
			if (ladderlist.word_list.get(i).kor.equals(kor_tf.getText())) {
				JOptionPane.showMessageDialog(null, "같은 단어가 있습니다.");
				return;
			}
		}
		
		JOptionPane.showMessageDialog(null, "["+ kor_tf.getText() + "] 단어를 추가합니다.");		
		ladderlist.insert_word(kor_tf.getText());
	}
}

class LadderList extends JPanel implements ActionListener{
	
	private Font font = new Font("", Font.BOLD, 20);
	static public Vector<Word> word_list = new Vector<Word>();
	static public Vector<List> ladderlist = new Vector<>();
	
	private int page_num = 1;
	public int page_word_max = 10;
	int page_max = 0;
	int page_min = 0;
	static int wordcount = 0;
	private JButton start_btn = null;
	private JButton reset_btn = null;
	
	LadderList() {
		setLayout(null);
		start_btn();
		reset_btn();
		print_word_list();
	}
	
	void start_btn() {
		start_btn = new JButton();
		start_btn.setSize(150, 40);
		start_btn.setLocation(40, 550);
		start_btn.setBackground(new Color(254, 206, 50));
		start_btn.setText("◁ 사다리 생성 ▷");
		add(start_btn);
		start_btn.addActionListener(this);
	}
	
	void reset_btn() {
		reset_btn = new JButton();
		reset_btn.setSize(150, 40);
		reset_btn.setLocation(220, 550);
		reset_btn.setBackground(new Color(254, 206, 50));
		reset_btn.setText("◁ 목록 초기화 ▷");
		add(reset_btn);
		reset_btn.addActionListener(this);
	}
	
	void print_word_list() {
		System.out.println("print_word_list()");
		Component[] componentList = this.getComponents();
		for(Component c : componentList) {
			if(c instanceof List) {
				this.remove(c);
			}
		}
		int start_index = (page_num - 1) * page_word_max;
		int total = word_list.size() - start_index;
		int count = page_word_max;
		if(total < page_word_max) {
			count = total;
		}
		ladderlist = new Vector<>();
		for(int i = 0; i < count; i++) {
			List wb = new List(i + start_index, word_list.get(start_index+i),this);
			add(wb);
			ladderlist.add(wb);
		}
		repaint();
	}
	
	public void insert_word(String kor) {
		Word word = new Word();
		word.kor = kor;
		if(word_list.size()<ladder_Game_Panel.player) {
			word_list.add(word);
		}
		print_word_list();
	}
	
	public void delete_word(int index) {
		word_list.remove(index);
		print_word_list();
	}
	
	public void update_word(int index, String kor) {
		word_list.get(index).kor = kor;
		print_word_list();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(start_btn == e.getSource()) {
			if(ladder_Game_Panel.player == word_list.size()) {
				_WordGame.frame.setContentPane(new LadderPanel());
				_WordGame.frame.revalidate();
			}else {
				JOptionPane.showMessageDialog(null, "항목을 모두 채워주세요.");
				return;
			}
		}
		
		if (reset_btn == e.getSource()) {
			int result = JOptionPane.showConfirmDialog(null, "초기화 하시겠습까?", "초기화", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				Component[] componenetList = this.getComponents();
				for (Component c : componenetList) {
					if (c instanceof List) { 
						this.remove(c);
					}
				}
				repaint();
			}
			
			word_list.clear();
		}
	}	
}

class LadderPanel extends JPanel implements ActionListener{
	
	Player player = null;
	LadderSet ladderset = null;
	JButton back_main = new JButton();
	JButton back_title = new JButton();
	JButton back_setting = new JButton();

	 public void paintComponent(Graphics g) {
			ImageIcon image = new ImageIcon("./src/gameBoard/사다리배경.jpg");
			g.drawImage(image.getImage(), 0, 0, null);
	}
	 
	LadderPanel() {
		
		setLayout(null);
		
		ladderset = new LadderSet();
		ladderset.setSize(600, 660);
		ladderset.setBackground(Color.white);
		ladderset.setLocation(350, 50);
		add(ladderset);
		
		player = new Player();
		player.setSize(280,400);
		player.setBackground(new Color(0, 206, 206));
		player.setLocation(20, 50);
		add(player);
		
		back_main.setText("메인으로");
		back_main.setSize(200, 40);
		back_main.setFont(new Font("Dialog", Font.BOLD, 15));
		back_main.setLocation(60, 500);
		back_main.setBackground(new Color(254, 206, 50));
		back_main.addActionListener(this);
		add(back_main);
		
		back_title.setText("새 게임");
		back_title.setSize(200, 40);
		back_title.setFont(new Font("Dialog", Font.BOLD, 15));
		back_title.setLocation(60, 570);
		back_title.setBackground(new Color(254, 206, 50));
		back_title.addActionListener(this);
		add(back_title);

		back_setting.setText("다시 입력");
		back_setting.setSize(200, 40);
		back_setting.setFont(new Font("Dialog", Font.BOLD, 15));
		back_setting.setLocation(60, 640);
		back_setting.setBackground(new Color(254, 206, 50));
		back_setting.addActionListener(this);
		add(back_setting);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (back_main == e.getSource()) {
			try {
				_WordGame.frame.setContentPane(new Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		if (back_title == e.getSource()) {
			_WordGame.frame.setContentPane(new ladder_Game_Panel());
			_WordGame.frame.revalidate();
		}
		if (back_setting == e.getSource()) {
			_WordGame.frame.setContentPane(new SettingPanel());
			_WordGame.frame.revalidate();
		}
	}
}

class LadderSet extends JPanel implements ActionListener{
	
	private Font font = new Font("", Font.BOLD, 15);

	int playerNum = ladder_Game_Panel.player;
	int[][] ladder = new int [20][playerNum];
	int yIdx = 0;
	int xIdx = 0;
	
	JLabel ladderlabel[][] = new JLabel[20][playerNum];
	JLabel lable_kor2[] = new JLabel[playerNum];
	Random ran = new Random();
	
	JButton ladderstart_btn = new JButton();
	
	LadderSet() {
		setLayout(null);
		setLadder();
		showLadder();
		showList();
		ladderstart_btn();
		
	}

	public void setLadder() {
		for(int i = 1; i < ladder.length; i++) {
			for(int j = 0; j < ladder[i].length -1; j++) {
				int r = ran.nextInt(2);
				if(ladder[i][j] == 0 && i < ladder.length -1) {
					ladder[i][j] = r;
					if (j < ladder[i].length -1) {
						if(r != 0) {
							ladder[i][j+1] = r+1;
						}
					}
				} else if (i == ladder.length - 1) {
					ladder[i][j] = 0;
				}

			}
		}
	}

	
	void showLadder() {
		Component[] componentList = this.getComponents(); 		
		for(Component c : componentList){ 		   
			if(c instanceof JLabel){ 			    			    		
				this.remove(c); 	    	    	
			}	    
		}

		for(int i = 0; i < 20; i++) {
			for (int j = 0; j < ladder[i].length; j++) {
				if (ladder[i][j] == 0) {
					if (yIdx == i && xIdx == j) {
						ladderlabel[i][j] = new JLabel(ladder[i][j]+"");
						ladderlabel[i][j].setBounds(40+(j*50), 55+(i*25), 50, 25);
						ladderlabel[i][j].setBackground(Color.white);
						ladderlabel[i][j].setIcon(new ImageIcon("./src/gameBoard/player.png"));
						add(ladderlabel[i][j]);
					} else {
						ladderlabel[i][j] = new JLabel(ladder[i][j]+"");
						ladderlabel[i][j].setBounds(40+(j*50), 55+(i*25), 50, 25);
						ladderlabel[i][j].setBackground(Color.white);
						ladderlabel[i][j].setIcon(new ImageIcon("./src/gameBoard/ladder1.jpg"));
						add(ladderlabel[i][j]);
					}
				} else if (ladder[i][j] == 1) {
					if (yIdx == i && xIdx == j) {
						ladderlabel[i][j] = new JLabel(ladder[i][j]+"");
						ladderlabel[i][j].setBounds(40+(j*50), 55+(i*25), 50, 25);
						ladderlabel[i][j].setBackground(Color.white);
						ladderlabel[i][j].setIcon(new ImageIcon("./src/gameBoard/player.png"));
						add(ladderlabel[i][j]);
					} else {
						ladderlabel[i][j] = new JLabel(ladder[i][j]+"");
						ladderlabel[i][j].setBounds(40+(j*50), 55+(i*25), 50, 25);
						ladderlabel[i][j].setBackground(Color.white);
						ladderlabel[i][j].setIcon(new ImageIcon("./src/gameBoard/ladder2.jpg"));
						add(ladderlabel[i][j]);
					}
				} else if (ladder[i][j] == 2) {
					if (yIdx == i && xIdx == j) {
						ladderlabel[i][j] = new JLabel(ladder[i][j]+"");
						ladderlabel[i][j].setBounds(40+(j*50), 55+(i*25), 50, 25);
						ladderlabel[i][j].setBackground(Color.white);
						ladderlabel[i][j].setIcon(new ImageIcon("./src/gameBoard/player.png"));
						add(ladderlabel[i][j]);
					} else {
						ladderlabel[i][j] = new JLabel(ladder[i][j]+"");
						ladderlabel[i][j].setBounds(40+(j*50), 55+(i*25), 50, 25);
						ladderlabel[i][j].setBackground(Color.white);
						ladderlabel[i][j].setIcon(new ImageIcon("./src/gameBoard/ladder3.jpg"));
						add(ladderlabel[i][j]);
					}
				}
			}
			System.out.println();
		}
	}
	
	void showList() {
		for(int i = 0; i < ladder_Game_Panel.player; i++) {
			lable_kor2[i] = new JLabel();
			lable_kor2[i].setSize(60,30);
			lable_kor2[i].setFont(font);
			lable_kor2[i].setLocation(40 + (i * 50), 560);
			lable_kor2[i].setText(LadderList.word_list.get(i).kor);
			lable_kor2[i].setHorizontalAlignment(SwingConstants.CENTER);
			add(lable_kor2[i]);
		}
	}
	
	void moveLadder(int x) {
		yIdx = 0;
		xIdx = x;
		showLadder();
		this.update(this.getGraphics());
		boolean run = true;
		while (run) {
			if (ladder[yIdx][xIdx] == 0) {
				yIdx += 1;
				showLadder();
				System.out.println("Down");
			} else if (ladder[yIdx][xIdx] == 1) {
				xIdx += 1;
				showLadder();
				this.update(this.getGraphics());
				yIdx += 1;
				showLadder();
				System.out.println("Right");
			} else if (ladder[yIdx][xIdx] == 2) {
				xIdx -= 1;
				showLadder();
				this.update(this.getGraphics());
				yIdx += 1;
				showLadder();
				System.out.println("Left");
			}

			if (yIdx == 19) {
				showLadder();
				System.out.println(xIdx);
				run = false;
			}
			try { Thread.sleep(350); } catch (Exception e) { e.printStackTrace(); }
			this.update(this.getGraphics());
		}
		showList();
		JOptionPane.showMessageDialog(null, Player.Cplayer + "플레이어 : " + 
										LadderList.word_list.get(xIdx).kor + "당첨");
		this.update(this.getGraphics());
	}
	
	void ladderstart_btn() {
		ladderstart_btn = new JButton();
		ladderstart_btn.setSize(150, 40);
		ladderstart_btn.setLocation(225, 600);
		ladderstart_btn.setBackground(new Color(254, 206, 50));
		ladderstart_btn.setText("◁ GAME START ▷");
		add(ladderstart_btn);
		ladderstart_btn.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (ladderstart_btn == e.getSource()) {
			if (Player.Cplayer != 0) {
				int ChoicePlayer = Player.Cplayer - 1;
				moveLadder(ChoicePlayer);
			} else {
				JOptionPane.showMessageDialog(null, "플레이어를 선택해주세요.");
				return;
			}
		}
	}
}

class Word {
	String kor;
}

class List extends JPanel implements ActionListener{
	public JLabel lable_num = new JLabel();
	int num = ladder_Game_Panel.player;
	
	JLabel lable_kor = new JLabel();
	JButton update_btn = new JButton();
	JButton delete_btn = new JButton();
	LadderList ladderlist = null;
	
	List(int index, Word word, LadderList panel) {
		ladderlist = panel;
		setLayout(null);
		int pos = index % panel.page_word_max;
		setLocation(40, 40+pos*50);
		setSize(320, 30);
		
		num = index;
		
		lable_num.setSize(80,30);
		lable_num.setLocation(10,0);
		lable_num.setText((num + 1) + "번 항목");
		add(lable_num);
		lable_kor.setSize(80, 30);
		lable_kor.setLocation(90, 0);
		lable_kor.setText(word.kor);
		add(lable_kor);
		
		update_btn.setSize(60, 30);
		update_btn.setLocation(200, 0);
		update_btn.setBackground(Color.LIGHT_GRAY);
		update_btn.setText("수정");
		update_btn.addActionListener(this);
		add(update_btn);
		
		delete_btn.setSize(60, 30);
		delete_btn.setLocation(260, 0);
		delete_btn.setBackground(Color.LIGHT_GRAY);
		delete_btn.setText("삭제");
		delete_btn.addActionListener(this);
		add(delete_btn);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == delete_btn) {
			int result = JOptionPane.showConfirmDialog(null, "정말 삭제를 합니까?", "삭제", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				ladderlist.delete_word(num);
			}
		}
		if(e.getSource() == update_btn) {
			String kor = JOptionPane.showInputDialog("[수정] 내용을 입력하세요");
			ladderlist.update_word(num, kor);
		}
	}
}
class Player extends JPanel implements ActionListener{
	Font font = new Font("", Font.BOLD, 15);
	JButton[] player_Btn = new JButton[ladder_Game_Panel.player];
	JLabel kor_lb = new JLabel();
	LadderList ladderlist = null;
	JLabel playerC = new JLabel("총 플레이어 : " + ladder_Game_Panel.player+"");
	
	static int Cplayer = 0;
	JLabel ChoicePlayer = new JLabel();
	
	Player(){
		
		setLayout(null);
		play_btn_init();
		
		playerC.setBounds(20, 20, 250, 50);
		playerC.setHorizontalAlignment(JLabel.CENTER);
		playerC.setFont(new Font("Dialog", Font.BOLD, 15));
		playerC.setBackground(new Color(254, 206, 50));
		add(playerC);
		
	}
	
	void play_btn_init() {
		Component[] componentList = this.getComponents();
		for(Component c : componentList) {
			if(c instanceof JButton) {
				this.remove(c);
			}
		}
		int playerNum = ladder_Game_Panel.player;
		int width = 115;
		int height = 40;
		for (int i = 0; i < playerNum; i++) {
			player_Btn[i] = new JButton();
			player_Btn[i].setSize(width, height);
			player_Btn[i].setLocation(22 + i % 2 * 125, 130 + ((i)/2 * 50));
			player_Btn[i].setBackground(new Color(254, 206, 50));
			player_Btn[i].setFont(font);
			player_Btn[i].setText((i + 1) + "번 플레이");
			add(player_Btn[i]);
			player_Btn[i].addActionListener(this); 	
		}
	}
	
	void print_player_list() {
		if(Cplayer != 0) {
			ChoicePlayer.setSize(250, 50);
			ChoicePlayer.setLocation(20,70);
			ChoicePlayer.setHorizontalAlignment(JLabel.CENTER);
			ChoicePlayer.setFont(new Font("Dialog", Font.BOLD, 15));
			ChoicePlayer.setBackground(new Color(254, 206, 50));
			ChoicePlayer.setText(Cplayer + "번 플레이어 선택");
			add(ChoicePlayer);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < ladder_Game_Panel.player; i++) {
			if(e.getSource() == player_Btn[i]) {
				play_btn_click(i);				
				break;
			}
		}
	}
	
	void play_btn_click(int i) {
		
		if (ladder_Game_Panel.player == 10) {
			if (i == 9) {
				String num = "10";	
				Cplayer = Integer.parseInt(num);	
				print_player_list();
			} else {
				String num = player_Btn[i].getText().substring(0,1);	
				Cplayer = Integer.parseInt(num);	
				print_player_list();
			}
		} else {
			String num = player_Btn[i].getText().substring(0,1);	
			Cplayer = Integer.parseInt(num);	
			print_player_list();
		}
	}
}

public class ladder_Game_Panel extends JPanel implements ActionListener {
	
	JButton startBtn = new JButton();
	JButton playerBtn = new JButton();
	JButton addButton = new JButton();
    JButton minButton = new JButton();
    JButton back_main = new JButton();
    JLabel subText = new JLabel();
    JLabel subText2 = new JLabel();
    LadderList ladderlist = new LadderList();
    
    
    static int player = 2;

    JLabel playerCount = new JLabel(player+"");
    
    ImageIcon image = new ImageIcon();
    public void paintComponent(Graphics g) {
		image = new ImageIcon("./src/gameBoard/사다리배경.jpg");
		g.drawImage(image.getImage(), 0, 0, null);
	}
    
    ladder_Game_Panel() {
    	
		this.player = player;

		setLayout(null);

		subText.setText("랜덤 사다리게임");
		subText.setLocation(250, 150);
		subText.setFont(new Font("", Font.BOLD, 55));
		subText.setForeground(Color.WHITE);
		subText.setSize(500, 100);
		subText.setHorizontalAlignment(JLabel.CENTER);
		add(subText);

		playerBtn.setText("플레이어");
		playerBtn.setFont(new Font("", Font.BOLD, 15));
		playerBtn.setSize(100, 50);
		playerBtn.setLocation(450, 300);
		playerBtn.setForeground(Color.WHITE);
		playerBtn.setContentAreaFilled(false);
		playerBtn.setBorderPainted(false);
		playerBtn.addActionListener(this);
		playerBtn.setHorizontalAlignment(JLabel.CENTER);
		add(playerBtn);

		playerCount.setBounds(450, 350, 100, 50);
		playerCount.setHorizontalAlignment(JLabel.CENTER);
		playerCount.setFont(new Font("Dialog", Font.BOLD, 30));
		playerCount.setBackground(new Color(254, 206, 50));
		playerCount.setForeground(Color.WHITE);
		add(playerCount);

		addButton = new JButton(new ImageIcon("./src/gameBoard/plusButton2.png"));
		addButton.setFocusable(false);
		addButton.setBackground(new Color(254, 206, 50));
		addButton.setBounds(550, 350, 50, 50);
		addButton.addActionListener(this);
		add(addButton);

		minButton = new JButton(new ImageIcon("./src/gameBoard/minusButton2.png"));
		minButton.setFocusable(false);
		minButton.setBackground(new Color(254, 206, 50));
		minButton.setBounds(400, 350, 50, 50);
		minButton.addActionListener(this);
		add(minButton);

		subText2.setText("* 플레이어는 최소 2명에서 최대 10명까지 가능 *");
		subText2.setLocation(250, 400);
		subText2.setFont(new Font("Dialog", Font.BOLD, 17));
		subText2.setForeground(Color.WHITE);
		subText2.setSize(500, 100);
		subText2.setHorizontalAlignment(JLabel.CENTER);
		add(subText2);

		startBtn.setText("◁ 게임 만들기 ▷");
		startBtn.setSize(200, 80);
		startBtn.setFont(new Font("Dialog", Font.BOLD, 20));
		startBtn.setLocation(400, 500);
		startBtn.setBackground(new Color(254, 206, 50));
		startBtn.addActionListener(this);
		add(startBtn);
		
		back_main.setText("뒤로 가기");
		back_main.setSize(200, 80);
		back_main.setFont(new Font("Dialog", Font.BOLD, 20));
		back_main.setLocation(400, 620);
		back_main.setBackground(new Color(254, 206, 50));
		back_main.addActionListener(this);
		add(back_main);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (startBtn == e.getSource()) {
			_WordGame.frame.setContentPane(new SettingPanel());
			_WordGame.frame.revalidate();
			Component[] componentList = ladderlist.getComponents(); 		
			for(Component c : componentList){ 		   
				if(c instanceof LadderList){ 			    			    		
					this.remove(c); 	    	    	
				}	    
			}
			repaint();
			
		}
		
		if (back_main == e.getSource()) {
			try {
				_WordGame.frame.setContentPane(new Game_Panel());
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_WordGame.frame.revalidate();
		}
		
		if (addButton == e.getSource()) {
			if (player < 10) {
				playerCount.setText(++player + "");
			} else {
				playerCount.setText(player + "");
			}
		}
		if (minButton == e.getSource()) {
			if (player > 2) {
				playerCount.setText(--player + "");
			} else {
				playerCount.setText(player + "");
			}
		}
	}
	



}