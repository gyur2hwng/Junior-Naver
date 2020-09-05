package gameBoard;

import javax.swing.ImageIcon;

public class Horse 
{
	static final int WAIT = 0;
	static final int RUN = 1;
	static final int GOAL = 2;
	
	int x;	int y;
	int width;	int height;	int speed;
	
	String fileName;	ImageIcon horeseImage;
	
	int state;	int number;	int rank;
}
