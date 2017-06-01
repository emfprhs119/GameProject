package Object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import Frame.StoryRoom;
import Main.Project;

@SuppressWarnings("serial")
public class Bullet extends MoveObject {// 총알 오브젝트-플레이어와 몬스터의 공격액션을 담당(둘다 상속받아서 사용)
	Iterator<?> it; // 피격판정을 위한 반복자
	MoveObject other; // 피격대상
	public double stepX, stepY;
	protected int time = 0;
	int damage;
	boolean flag;
	/*
	 * public Bullet(Point xy, Point gotoXY,StoryRoom room) { // 생성자 위치와 공격할 위치,게임창을 받아옴 super(room);
	 * // 상속 진짜 good!!!!!!!!! room.bulletList.add(this); room.add(this); x = xy.x; y = xy.y;
	 * setAngle(gotoXY); soundAttack.play(); }
	 */
	public Bullet(int damage,StoryRoom room) {
		super(room);
		this.damage=damage;
		this.setVisible(false);
		flag=false;
		room.bulletList.add(this);
		room.add(this);
	}
	public void attack(Point xy,Point point) {
		//remove();
		soundAttack.play();
		this.setVisible(true);
		x =xy.x;
		y = xy.y;
		setAngle(point);
		flag=true;
	}

	public void step() { // 매시간 작동
		time++;
		moving();
		attackedDecision();
	}

	public void setImage(String img) {
		super.setImage("bullet/" + img);
	}

	public void attackedDecision() {
		// TODO Auto-generated method stub

	}

	public void moving() { // 이동
		x += currSpeed * (stepX) * room.step;
		y += currSpeed * (stepY) * room.step;
		setLocation((int) x, (int) y);
		//collider(); // 벽 충돌확인
		/*
		 if (flagX != 0 || flagY != 0) {// 충돌{
			remove();
		}
		*/
		if (x < 0 || y < 0 || x>Project.windowSize.x || y>Project.windowSize.y) {// 최외각
			remove();
		}
	}
	public void remove(){
		flag = false;
		this.setVisible(false);
	}
	void damage(int power) { // 사용하지 않음(나중에 사용할지도 모름)
		// TODO Auto-generated method stub
	}
	public void attack(Point xy, MoveObject obj) {
		// TODO Auto-generated method stub
		
	}
}
