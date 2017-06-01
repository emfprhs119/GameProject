package Object;

import java.awt.Point;
import java.util.Iterator;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class Bullet extends MoveObject {//총알 오브젝트-플레이어와 몬스터의 공격액션을 담당(둘다 상속받아서 사용)
	Iterator<?> it; //피격판정을 위한 반복자
	MoveObject other;	//피격대상
	public Bullet(Point xy, Point gotoXY,StoryRoom room) {	// 생성자 위치와 공격할 위치,게임창을 받아옴
		super(room); // 상속 진짜 good!!!!!!!!!
		x = xy.x;
		y = xy.y;
		speed = 0.5f;
		setImage("bullet.png");
		setAngle(gotoXY);
		soundAttack.play();
	}
	public void step(){		//매시간 작동
		moving();
		attackedDecision();
	}
	public void attackedDecision() {
		// TODO Auto-generated method stub
		
	}
	public void moving() {	//이동
		
		angle = (float)Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle)*room.step;
		y += speed * (angleY / angle)*room.step;
		collider(); // 벽 충돌확인
		if (flagX!=0 || flagY!=0) {// 충돌{
			remove();
		}
		setLocation((int) x, (int) y);
	}
	@Override
	void damage(int power) {	//사용하지 않음(나중에 사용할지도 모름)
		// TODO Auto-generated method stub
	}
}
