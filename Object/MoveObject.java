package Object;

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
public abstract class MoveObject extends JLabel {

	// 생성자-init - 생성을 위한 객체가 아니라 움직이는 오브젝트 상속 추상클래스인 이유는 인터페이스 적용을 위해
	// 상속받은 후 직접 사용해야하기 때문에 protected로 초기화
	protected float speed;	//속도
	protected float x, y;	//x,y좌표(좌측상단)
	public float originX, originY;	//오브젝트의 정중앙좌표
	//protected JLabel label;
	
	protected float angleX,angleY;	//각도에 사용되는 상대방과의 x,y거리차
	protected float angle;	//각도
	protected float width;	//가로크기
	protected float height;	//세로크기
	protected StoryRoom room;	//스테이지 내부 객체제어를 위한 객체
	protected Iterator<Monster> itMonster;	//몬스터 반복자
	protected float flagX,flagY; //block에 붙었을때의 x,y좌표
	protected Sound soundDamage;	//피격음
	protected Sound soundAttack;			//공격음
	protected float colliderSpeed;			//충돌할때의 속도
	protected String name;	//객체의 이름

	MoveObject(StoryRoom room) {	//생성자
		//super();
		//모든 오브젝트에 공격음과 데미지음이 같게 설정 나중에 음향 추가시 변경 예정
		name=this.getClass().getName().split("\\.")[1];
		soundDamage=new Sound("damage.wav", false);
		soundAttack=new Sound("attack.wav", false);
		this.room = room;
		angle = 0;
		speed = 0f;
		x = 1;
		y = 1;
	}

	public MoveObject() {
	}

	public void setImage(String img) { // 이미지의 주소값만 넣어주면 알아서 척척 위치까지 잡아줌
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		setBounds((int) x, (int) y, (int) width, (int) height);
	}
	public void setAngle(Point point) {	//포인트와의 x,y거리 사용처가 각도라 편의상 angle에 저장
		//%주의 맵 전체 기준 
		angleY = (float) (point.y  - height / 2 - y);
		angleX = (float) (point.x - width / 2 - x);
	}

	public Point getPoint() { // 자신의 위치값(정중앙)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}

	public void setOrigin() { // 자신의 위치값(정중앙)
		originX = (x + width / 2);
		originY = (y + height / 2);
	}
	public double distanceTo(MoveObject object){	//오브젝트와의 거리
		return Math.sqrt(Math.pow(originX-object.originX, (float) 2) + Math.pow(originY-object.originY, (float) 2));
	}
	protected void collider() { // 벽 충돌 판정
		setOrigin();
		flagX = 0;// 플레이어가 block에 붙었을때의 x좌표
		flagY = 0;// 플레이어가 block에 붙었을때의 y좌표
		///////충돌시 가장자리에 부착했을때의 위치를 저장(flag를 어떻게 쓸건지는 각 move에서 결정) ///////////////////
		try {
			for (Block blockObj : room.blockList) { 
				if (originY + height / 2 - (colliderSpeed * room.step) > blockObj.originY - (blockObj.height / 2)
						&& originY - height / 2 + (colliderSpeed * room.step) < blockObj.originY + (blockObj.height / 2)) {
					if ((originX - width / 2 < blockObj.originX + blockObj.width / 2)
							&& (originX + width / 2 > blockObj.originX - blockObj.width / 2)) {
						if (originX < blockObj.originX)
							flagX = blockObj.originX - blockObj.width / 2 - width;
						else
							flagX = blockObj.originX + blockObj.width / 2;
					}
				} else if (originX + width / 2 - (colliderSpeed * room.step) > blockObj.originX - (blockObj.width / 2)
						&& originX - width / 2 + (colliderSpeed * room.step) < blockObj.originX + (blockObj.width / 2)) {
					if ((originY - height / 2 < blockObj.originY + blockObj.height / 2)
							&& (originY + height / 2 > blockObj.originY - blockObj.height / 2)) {
						if (originY < blockObj.originY)
							flagY = blockObj.originY - blockObj.height / 2 - height;
						else
							flagY = blockObj.originY + blockObj.height / 2;
					}
				}
			}
			////////////////////////////////////////////////////////////////////////////////////////
		} catch (NullPointerException e) {
		} catch (ConcurrentModificationException e) {	//중간에 제거되는 오브젝트때문에 일어나는 오류
			remove();
		}
	}
	public void paintComponent(Graphics g) { // 그리기(회전)
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform aT = g2.getTransform();
		Shape oldshape = g2.getClip();
		angle = angleY / angleX;
		if (angleX > 0)
			aT.rotate(Math.atan(angle)+(270/180.0) *Math.PI, width / 2, height / 2);
		else
			aT.rotate(Math.atan(angle) + Math.PI +(270/180.0) *Math.PI, width / 2, height / 2);
		g2.setTransform(aT);
		g2.setClip(oldshape);
		super.paintComponent(g);
		room.repaint();
	} 
	public void remove() { // 오브젝트 제거!
		room.remove(this);
		//Bullet t;
		if (this instanceof Bullet){
			room.bulletList.remove(this);
			}
		if (this instanceof Monster){
			room.monsterList.remove(this);
			}
		room.repaint();
	}

	abstract void step(); // 각각의 이동방식을 정의한다.

	abstract void damage(int power);	//각각의 데미지(방어막or무적) 받는 방식 지정

}
