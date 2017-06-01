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
	int bulletNum;
	boolean bulletFlag;
	public MyListener(StoryRoom StoryRoom) {	//�ʱ�ȭ
		room = StoryRoom;
	}

	public void keyPressed(KeyEvent e) {	//�÷��̾� �̵� ����(����Ʈ�� �뽬���� ��Ÿ�� �ʿ�)
		if (!room.stop) {
			if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
				room.player.moveM(e.getKeyCode());
			}
			if (e.getKeyCode() == '1' | e.getKeyCode() == '2' | e.getKeyCode() == '3') {
				room.player.skill(e.getKeyCode()-48);//int ����ȯ
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				room.player.skill(0);	//�뽬
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
		if (!room.stop)
			room.player.setMouse(mouse.getPoint());
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
					bulletFlag=true;
					if (bulletFlag){
						room.player.motion = Motion.Attack;
					}
				}
			}
	}
	public void mouseReleased(MouseEvent arg0) {
		if (!room.stop)
			room.player.motion = Motion.Init;
	}
}