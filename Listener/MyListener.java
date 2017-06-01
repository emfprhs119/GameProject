package Listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Frame.StoryRoom;
import Object.Bullet;
import Object.BulletOfCharacter;
import Object.Player.Motion;

public class MyListener extends MouseAdapter implements KeyListener, MouseMotionListener { 
	// ���� �÷��� ��ҿ��� �̷������ Ű����� ���콺 ���� ����
	//��ü ����� �ϳ��ۿ� ���� �ʴ� ����� ���������� �������̽��� ���
	StoryRoom room;	//���� â
	Bullet bullet;	//�Ѿ�

	public MyListener(StoryRoom StoryRoom) {	//�ʱ�ȭ
		room = StoryRoom;
	}

	public void keyPressed(KeyEvent e) {	//�÷��̾� �̵� ����(����Ʈ�� �뽬���� ��Ÿ�� �ʿ�)
		if (!room.stop) {
			if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
				room.player.moveM(e.getKeyCode());
			}
			if (room.player.motion != Motion.Dash) {	// �ӽ÷� ����� ��Ÿ������ ������ ����
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					room.player.motion = Motion.Dash;
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {	//�÷��̾� �̵� ����
		if (!room.stop)
			if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
				room.player.moveS(e.getKeyCode());
			}
	}

	public void keyTyped(KeyEvent e) {	//������� ���� -�������̽� ��Ӷ����� ��������
	}

	public void mouseDragged(MouseEvent mouse) {	//������� ���� -�������̽� ��Ӷ����� ��������
	}

	public void mouseMoved(MouseEvent mouse) {	//���콺 �̵��� �÷��̾�� ��ġ���� ����
		// TODO Auto-generated method stub
		if (!room.stop)
			room.player.setMouse(mouse.getPoint());
	}
	public void mousePressed(MouseEvent mouse) {	//���콺 Ŭ���� �÷��̾��� �Ѿ� ����
		if (!room.stop)
			if (mouse.getButton() == MouseEvent.BUTTON1) {
				if (room.player.motion != Motion.Attack) {// �÷��̾��� ����
					room.player.motion = Motion.Attack;
					bullet = new BulletOfCharacter(room.player.getPoint(), mouse.getPoint(), room);
					room.add(bullet);
					room.bulletList.add(bullet);
				}
			}
	}
}