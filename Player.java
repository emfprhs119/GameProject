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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Player extends MoveObject { 
	protected float vertical, horizon;
	protected int flagV,flagH;
	Player(Prototype p) {
		super(p); // MoveObject에서 기본적인 내용들 전~~~부 싹 초기화시켜버림 good!
		speed = 0.2f;
		flagV = 0;
		flagH = 0;
		setImage("plane.png"); // 프레임별 이미지는 다음번에 생각해봄 그렇게 어려울것같진 않은데 글쎄 (gif로 되려나 일단 정리하고 생각해보고)
		t.start();
	}

	public void paintComponent(Graphics g) { // 그리기
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

	// //////////////////////////////////////////////////////////
	//이동 제어 함수 나중에 수정하던지 따로 클래스 만들어서
	// 쳐박아두던지 생각중
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

	public void Moving() {
		setLocation((int) x, (int) y);
		x += horizon;
		y += vertical;
	}
	// //////////////////////////////////////////////////////////////여기까지 이동 제어



}
