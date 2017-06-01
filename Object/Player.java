package Object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;
import Main.Project;

public class Player extends MoveObject { 
	public float vertical;
	public float horizon;
	public int hp,fullHp;
	protected int flagV,flagH;
	//boolean flagX,flagY;
	Iterator<Block> it;
	double distance;
	Block obj;
	Block objTemp;
	Double tempDi;
	public Player(int x,int y,StoryRoom p) {		// 따로 hp바 크게 만들어줄 예정
		super(p); // MoveObject 초기화
		speed = 0.5f;
		flagV = 0;
		flagH = 0;
		this.x=x;
		this.y=y;
		setImage("plane.png");
		setLocation((int) x, (int) y);
		thread.start();
	}
	public void updateComponent(Graphics g) { // 그리기
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform aT = g2.getTransform();
		Shape oldshape = g2.getClip();
		angle = angleY / angleX;
		if (angleX > 0)
			aT.rotate(Math.atan(angle), width / 2, height / 2);
		else
			aT.rotate(Math.atan(angle) + (1.5646 * 2), width / 2, height / 2);
		g2.setTransform(aT);
		g2.setClip(oldshape);
		super.paintComponent(g);
	}

	public void step(){
		move();
		if (room.monsterList.isEmpty())
			System.exit(0);
	}
	public void move() {
		
		x += horizon;
		y += vertical;
		if (getPoint().x<width/2+50 || getPoint().x>Project.windowSize.x-width/2-50-8 )	//	최외각 벽 디폴트
			x -= horizon;
		if (getPoint().y<height/2+50-25 || getPoint().y>Project.windowSize.y-height/2-50-30)	//	최외각 벽	디폴트
			y-=vertical;
		collider();	// 충돌확인
		if (flagX)	// x축 충돌
			x-=horizon;
		if (flagY)	// y축 충돌
			y-=vertical;
		setLocation((int) x, (int) y);
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////무빙에 필요한 동작
	public void moveS(int keyCode) {

		switch (keyCode) {
		case 'W':
			if (flagV == 2) {
				vertical = speed;
				flagV = 1;
			} else {
				flagV = 0;
				vertical = 0;
			}
			break;
		case 'S':
			if (flagV == 2) {
				flagV = -1;
				vertical = -speed;
			} else {
				flagV = 0;
				vertical = 0;
			}
			break;
		case 'D':
			if (flagH == 2) {
				flagH = -1;
				horizon = -speed;
			} else {
				flagH = 0;
				horizon = 0;
			}
			break;
		case 'A':
			if (flagH == 2) {
				flagH = 1;
				horizon = speed;
			} else {
				flagH = 0;
				horizon = 0;
			}
			break;
		}
	}
	public void moveM(int keyCode) {
		switch (keyCode) {
		case 'W':
			vertical = -speed;
			if (flagV == 0)
				flagV = -1;
			else if (flagV == 1)
				flagV = 2;
			break;
		case 'S':
			vertical = speed;
			if (flagV == 0)
				flagV = 1;
			else if (flagV == -1)
				flagV = 2;
			break;
		case 'D':
			horizon = speed;
			if (flagH == 0)
				flagH = 1;
			else if (flagH == -1)
				flagH = 2;
			break;
		case 'A':
			horizon = -speed;
			if (flagH == 0)
				flagH = -1;
			else if (flagH == 1)
				flagH = 2;
			break;
		}
	}
	@Override
	void damage(int power) {
		// TODO Auto-generated method stub
		
	}

}
