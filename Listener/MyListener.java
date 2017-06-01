package Listener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Frame.StoryRoom;
import Object.Bullet;
import Object.BulletOfCharacter;

public class MyListener extends MouseAdapter implements KeyListener, MouseMotionListener {	// 슈팅룸에서 이루어지는 키보드와 마우스 동작 구현
	StoryRoom room;

	public MyListener(StoryRoom StoryRoom) {
		room = StoryRoom;
	}

	public void keyPressed(KeyEvent e) {
		if (room.stop == false)
			if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
				room.player.moveM(e.getKeyCode());
			}
	}

	public void keyReleased(KeyEvent e) {
		if (room.stop == false)
			if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
				room.player.moveS(e.getKeyCode());
			}
	}

	public void keyTyped(KeyEvent e) {
		if (room.stop == false)
			if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {

				System.out.println((char) e.getKeyCode());
			}
	}

	public void mouseDragged(MouseEvent mouse) {
		// TODO Auto-generated method stub
		if (room.stop == false)

			room.player.setAngle(mouse.getPoint());
	}

	public void mouseMoved(MouseEvent mouse) {
		// TODO Auto-generated method stub
		if (room.stop == false)
			room.player.setAngle(mouse.getPoint());
	}

	public void mousePressed(MouseEvent mouse) {
		if (room.stop == false)
			if (mouse.getButton() == MouseEvent.BUTTON1)
				room.add(new BulletOfCharacter(room.player.getPoint(), mouse.getPoint(), room));
	}
}