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
	// 게임 플레이 장소에서 이루어지는 키보드와 마우스 동작 구현
	//객체 상속은 하나밖에 되지 않는 관계로 나머지들은 인터페이스로 상속
	StoryRoom room;	//게임 창
	Bullet bullet;	//총알

	public MyListener(StoryRoom StoryRoom) {	//초기화
		room = StoryRoom;
	}

	public void keyPressed(KeyEvent e) {	//플레이어 이동 구현(쉬프트는 대쉬구현 쿨타임 필요)
		if (!room.stop) {
			if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
				room.player.moveM(e.getKeyCode());
			}
			if (room.player.motion != Motion.Dash) {	// 임시로 사용중 쿨타임으로 지정할 예정
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					room.player.motion = Motion.Dash;
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {	//플레이어 이동 제어
		if (!room.stop)
			if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
				room.player.moveS(e.getKeyCode());
			}
	}

	public void keyTyped(KeyEvent e) {	//사용하지 않음 -인터페이스 상속때문에 써줬을뿐
	}

	public void mouseDragged(MouseEvent mouse) {	//사용하지 않음 -인터페이스 상속때문에 써줬을뿐
	}

	public void mouseMoved(MouseEvent mouse) {	//마우스 이동시 플레이어에게 위치정보 제공
		// TODO Auto-generated method stub
		if (!room.stop)
			room.player.setMouse(mouse.getPoint());
	}
	public void mousePressed(MouseEvent mouse) {	//마우스 클릭시 플레이어의 총알 생성
		if (!room.stop)
			if (mouse.getButton() == MouseEvent.BUTTON1) {
				if (room.player.motion != Motion.Attack) {// 플레이어의 상태
					room.player.motion = Motion.Attack;
					bullet = new BulletOfCharacter(room.player.getPoint(), mouse.getPoint(), room);
					room.add(bullet);
					room.bulletList.add(bullet);
				}
			}
	}
}