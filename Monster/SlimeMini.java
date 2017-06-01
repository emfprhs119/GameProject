package Monster;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import BulletOfMonster.AimBullet;
import BulletOfMonster.TwoWayBullet;
import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Monster;

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class SlimeMini extends Monster { // 이름 가명칭-이름을 제대로 지어줍시다! 몬스터1이라니! 어떻게 알아먹나요!
	public enum Motion { // 모션 정의
		Init, Move, Attack
	};
	int healingCount = 0;
	Motion motion; // motion과 충돌이 일어나므로 사용하지 않을경우 null로 만들어줌(좋은 생각 떠오르면 수정예정)
	public LinkedList<Bullet> bulletList[]=new LinkedList[2];
	int bulletIndex=0;

	public SlimeMini(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
		super(300, xy, room); 
		speed = 0.04f;
		motion = Motion.Init;
		bulletList[0]=new LinkedList<Bullet>();
		bulletList[1]=new LinkedList<Bullet>();
		new TwoWayBullet(bulletList[0],0,20, room);
		new TwoWayBullet(bulletList[0],20,20, room);
		new TwoWayBullet(bulletList[1],0,20, room);
		new TwoWayBullet(bulletList[1],20,20, room);
	}

	public void step() { // 몬스터별 필요한 작업은 추가로 재정의해서 사용해야한다.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
			if (count % (100 * 17 / room.step) == 0 && !(distanceTo(room.player) < 150)) {
				setAngle(room.player.getPoint());
				motion = Motion.Attack;
				attack();
			}
			if (count % (120 * 17 / room.step) == 0) {
				motion = Motion.Init;
				count = 0;
			}
	}

	//랜덤으로 움직이다. 플레이어와 접근하면 빠르게 움직임
	public void moveStep() {
		room.player.setOrigin();
		motion = Motion.Move;
		if (distanceTo(room.player) < 250){
			speed = 0.2f;
		}
		if (count % (120 * 17 / room.step) == 0){
			if(speed==0.2f){
			speed = 0.08f;
			}
			gotoX = (Math.random() * Project.windowSize.x);
			gotoY = (Math.random() * Project.windowSize.y);
			setAngle(new Point((int) gotoX,(int)gotoY));
		}
		super.move();
	}
	public void attack() { // 공격
		setAngle(room.player.getPoint());
		super.attack(bulletList[bulletIndex++%2],getPoint(),room.player.getPoint());
	}
	}
