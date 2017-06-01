package Object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;
import Main.Sound;

@SuppressWarnings("serial")
public abstract class MoveObject extends BaseObject {

	// 생성자-init - 생성을 위한 객체가 아니라 움직이는 오브젝트 상속 추상클래스인 이유는 인터페이스 적용을 위해
	// 상속받은 후 직접 사용해야하기 때문에 protected로 초기화
	public float currSpeed; //속도
	public float speed; // 속도

	protected float angleX, angleY; // 각도에 사용되는 상대방과의 x,y거리차
	protected float angle; // 각도

	protected Iterator<Monster> itMonster; // 몬스터 반복자
	protected float flagX, flagY; // block에 붙었을때의 x,y좌표
	protected Sound soundDamage; // 피격음
	protected Sound soundAttack; // 공격음
	protected float colliderSpeed; // 충돌할때의 속도

	MoveObject(StoryRoom room) { // 생성자
		super(room);
		// 모든 오브젝트에 공격음과 데미지음이 같게 설정 나중에 음향 추가시 변경 예정

		soundDamage = new Sound("damage.wav",Sound.Kind.Effect, false);
		soundAttack = new Sound("attack.wav",Sound.Kind.Effect, false);
		this.room = room;
		angle = 0;
		speed = 0f;
		x = 1;
		y = 1;

	}

	public double distanceTo(BaseObject other) { // 오브젝트와의 거리
		try{
		return Math.sqrt(Math.pow(originX - other.originX, (float) 2) + Math.pow(originY - other.originY, (float) 2));
		}
		catch (NullPointerException e){
			return 0;
		}
	}

	public void setAngle(Point point) { // 포인트와의 x,y거리 사용처가 각도라 편의상 angle에 저장
		// %주의 맵 전체 기준
		angleY = (float) (point.y - height / 2 - y);
		angleX = (float) (point.x - width / 2 - x);
	}

	protected void collider() { // 벽 충돌 판정
		flagX = 0;// 플레이어가 block에 붙었을때의 x좌표
		flagY = 0;// 플레이어가 block에 붙었을때의 y좌표
		// /////충돌시 가장자리에 부착했을때의 위치를 저장(flag를 어떻게 쓸건지는 각 move에서 결정) ///////////////////
		for (Block blockObj : room.blockList) {
			if (!blockObj.name.equals("Block1") && !blockObj.name.equals("Block2") &&!blockObj.name.equals("PassBlock") )
				continue;
			if (this instanceof Bullet) 
				if (blockObj.name.equals("PassBlock"))
					continue;
		colliderX();
		colliderY();
		}
	}

	protected void colliderX() { // 벽 충돌 판정
		setOrigin();
		flagX = 0;// 플레이어가 block에 붙었을때의 x좌표
		// /////충돌시 가장자리에 부착했을때의 위치를 저장(flag를 어떻게 쓸건지는 각 move에서 결정) ///////////////////
		try {
			for (Block blockObj : room.blockList) {
				if (!blockObj.name.equals("Block1") && !blockObj.name.equals("Block2") &&!blockObj.name.equals("PassBlock") )
					continue;
				if (this instanceof Bullet) 
					if (blockObj.name.equals("PassBlock"))
						continue;
				if (originY + height / 2 - (colliderSpeed * room.step) > blockObj.originY - (blockObj.height / 2)
						&& originY - height / 2 + (colliderSpeed * room.step) < blockObj.originY + (blockObj.height / 2)) {

					if ((originX - width / 2 < blockObj.originX + blockObj.width / 2)
							&& (originX + width / 2 > blockObj.originX - blockObj.width / 2)) {

						if (originX < blockObj.originX)
							flagX = blockObj.originX - blockObj.width / 2 - width;
						else 
							flagX = blockObj.originX + blockObj.width / 2;

					}
				}
			}
		} catch (NullPointerException e) {
		} catch (ConcurrentModificationException e) { // 중간에 제거되는 오브젝트때문에 일어나는 오류
			remove();
		}
	}

	protected void colliderY() { // 벽 충돌 판정
		setOrigin();
		flagY = 0;// 플레이어가 block에 붙었을때의 y좌표
		// /////충돌시 가장자리에 부착했을때의 위치를 저장(flag를 어떻게 쓸건지는 각 move에서 결정) ///////////////////
		try {
			for (Block blockObj : room.blockList) {
				if (!blockObj.name.equals("Block1") && !blockObj.name.equals("Block2") &&!blockObj.name.equals("PassBlock") )
					continue;
				if (this instanceof Bullet) 
					if (blockObj.name.equals("PassBlock"))
						continue;
				if (originX + width / 2 - (colliderSpeed * room.step) > blockObj.originX - (blockObj.width / 2)
						&& originX - width / 2 + (colliderSpeed * room.step) < blockObj.originX + (blockObj.width / 2)) {
					if ((originY - height / 2 < blockObj.originY + blockObj.height / 2)
							&& (originY + height / 2 > blockObj.originY - blockObj.height / 2)) {

						if (originY < blockObj.originY)
							flagY = blockObj.originY - blockObj.height / 2 - height;
						else 
							flagY = blockObj.originY + blockObj.height / 2 ;
					}
				}
			}
		} catch (NullPointerException e) {
		} catch (ConcurrentModificationException e) { // 중간에 제거되는 오브젝트때문에 일어나는 오류
			remove();
		}
	}

	public void paintComponent(Graphics g) { // 그리기(회전)
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform aT = g2.getTransform();
		Shape oldshape = g2.getClip();
		angle = angleY / angleX;
		double widthG=g2.getClipBounds().getWidth();
		double heightG=g2.getClipBounds().getHeight();
		aT.translate(-(widthG-width)/2, -(heightG-height)/2);
		setOrigin();
		if (!name.equals("Player")){
		if (angleX > 0)
			aT.rotate(Math.atan(angle) + (270 / 180.0) * Math.PI, widthG / 2, heightG / 2);
		else
			aT.rotate(Math.atan(angle) + Math.PI + (270 / 180.0) * Math.PI, widthG / 2, heightG / 2);
		}
		g2.setTransform(aT);
		
		g2.setClip(oldshape);
		super.paintComponent(g);
		room.repaint();
	}

	// abstract void step(); // 각각의 이동방식을 정의한다.

	// abstract void damage(int power); //각각의 데미지(방어막or무적) 받는 방식 지정

}
