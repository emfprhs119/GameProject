package Monster;

import java.awt.Point;
import java.util.LinkedList;

import BulletOfMonster.AimBullet;
import BulletOfMonster.Missile;
import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.Monster;

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class Bet extends Monster {
	public enum Motion { // 모션 정의
		Init, Move, Attack, Attack2
	};

	// int time;
	Motion motion; // motion과 충돌이 일어나므로 사용하지 않을경우 null로 만들어줌(좋은 생각 떠오르면 수정예정)
	boolean attackFlag;
	int bulletIndex = 0;
	public LinkedList<Bullet> bulletList[] = new LinkedList[2];

	public Bet(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
		super(100, xy, room); // 체력설정
		time = 1;
		// speed = 0.04f;
		bulletList[0] = new LinkedList<Bullet>();
		bulletList[1] = new LinkedList<Bullet>();
		motion = Motion.Init;
		attackFlag = false;
		bulletList[0].add(new AimBullet(20, room));
		bulletList[1].add(new AimBullet(20, room));
		width = 80;
		height = 80;
		flag=-2;
	}
	public void init(){
		boolean after = false;
		for (Monster m : room.monsterList) {
			if (x == m.x && y == m.y && m.time==time && !m.equals(this)) {
				m.time += 50;
			}
		}
		flag=-1;
	}

	public void step() { // 몬스터별 필요한 작업은 추가로 재정의해서 사용해야한다.
		if (flag==-2){
			init();
		}
		if (time > 0) {
			time--;
		}
		if (time == 0) {
			super.step();
			setImage(name + motion.name() + ".gif");
		}
	}

	public void attackStep() {
		if (x < Project.windowSize.x && x > 0 && y < Project.windowSize.y && y>0)
			attackCount++;
		else
			attackCount=0;
		if (attackCount>130){
			attack();
			attackCount=0;
		}
	}

	/*
	 * public void moveStep() { speed = 0.16f; angleX = -10; if (x < Project.windowSize.x / 2) { if (y
	 * > Project.windowSize.y / 2) angleY = +4; else angleY = -4; } super.move(); }
	 */
	public void moveStep() {
		speed = 0.1f;
		setAngle(new Point(Project.windowSize.x,Project.windowSize.y/2));
		/*
		 * float angle=(angleX*angleX)+(angleY*angleY); angleX=-(float) Math.sin(angleX/angle);
		 * //angleY=(float) Math.cos(y-200); System.out.println(angleX+" "+angleY);
		 */
		this.move();
	}

	public void move() { // 이동
		angle = (float) Math.sqrt(angleX * angleX + angleY * angleY);
		speed = angle / 1800;
		if (angleY > 0)
			x += speed * Math.sin(angleY / angle) * room.step;
		else
			x -= speed * Math.sin(-angleY / angle) * room.step;
		if (angleX > 0)
			y -= speed * Math.sin(angleX / angle) * room.step;
		else
			y += speed * Math.sin(-angleX / angle) * room.step;
		// collider(); // 충돌확인
		if (flagX != 0 || flagY != 0) {// 충돌
			if (flagX != 0) // x축 충돌
				x = flagX;
			if (flagY != 0) // y축 충돌
				y = flagY;// vertical*step;
		}
		moveHp();
		setLocation((int) x, (int) y);
	}

	public void initMove() {
		if (x > Project.windowSize.x) {
			speed = 0.16f;
			setVisible(true);
			hp.hpVisible(true);
			flag = 0;
		}
		move();
	}

	public void attack() { // 공격
		// setAngle(room.player.getPoint());
		super.attack(bulletList[bulletIndex++ % 2], getPoint(), room.player.getPoint());// room.player.getPoint());

	}
}
