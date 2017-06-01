package Monster;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import BulletOfMonster.AimBullet;
import BulletOfMonster.TwoWayBullet;
import Frame.StoryRoom;
import Main.Project;
import Object.Bullet;
import Object.Monster;

@SuppressWarnings("serial")
public class SlimeR extends Monster {
	public enum Motion { // 플레이어의 모션상태
		None, Attack1, Attack2, Attack3, Move1, Move2
	};
	final float attack1Speed=0.4f;
	final float attack2Speed=0.3f;
	public LinkedList<Bullet> bulletList[] = new LinkedList[6];
	int bulletIndex = 0;
	int M = 0;
	Motion motion = Motion.None;
	int flag = 0;

	public SlimeR(Point xy, StoryRoom room) {
		super(400, xy, room);
		bulletList[0] = new LinkedList<Bullet>();
		bulletList[1] = new LinkedList<Bullet>();
		bulletList[2] = new LinkedList<Bullet>();
		bulletList[3] = new LinkedList<Bullet>();
		bulletList[4] = new LinkedList<Bullet>();
		bulletList[5] = new LinkedList<Bullet>();
		for (int i = 0; i < 2; i++) { // 전방위 탄
			new TwoWayBullet(bulletList[0], 20 * i, 20, room); // 20도 간격으로 전방위탄
			new TwoWayBullet(bulletList[1], 20 * i, 20, room);
			new TwoWayBullet(bulletList[2], 20 * i, 20, room);
			new TwoWayBullet(bulletList[3], 20 * i, 20, room);
			new TwoWayBullet(bulletList[4], 20 * i, 20, room);
			new TwoWayBullet(bulletList[5], 20 * i, 20, room);
		}
	}

	public void step() {
		super.step();
		if (flag!=0)
			motionStep();
		else if (count > 70) 
			continues();
	}

	public void moveStep() {
		move();

	}

	private void motionStep() {
		switch (motion) {
		case Move1:
			if (count >45) {
				speed = 0;
				flag=0;
			}
			break;
		case Attack1:
			attack1();
			break;
		case Attack2:
			attack2();
			break;
		case None:
			break;
		}

	}


	public void continues() {
		count = 0;
		flag=1;
		M = (int) (Math.random() * 10 % 4);
		System.out.println(M);
		switch (M) {
		case 0:
		case 1:
			motion = Motion.Move1;
			speed = 0.2f;
			attack();
			rendMove();
			break;
		case 2:
			motion = Motion.Attack1;
			speed = attack1Speed;
			playerMove();
			break;
		case 3:
			motion = Motion.Attack2;
			speed = attack2Speed;
			playerMove();
			break;
		}

	}

	private void attack3() {
		// TODO Auto-generated method stub

	}



	private void playerMove() {
		setAngle(room.player.getPoint());
	}
	private void rendMove() {
		gotoX = (Math.random() * Project.windowSize.x);
		gotoY = (Math.random() * Project.windowSize.y);
		setAngle(new Point((int) gotoX, (int) gotoY));
	}
	private void attack1() {
		switch (flag) {
		case 1:
			if (count > 10) {
				attack();
				speed = 0;
				flag = 2;
			}
			break;
		case 2:
			if (count > 15) {
				speed = attack1Speed;
				rendMove();
				flag = 3;
			}
			break;
		case 3:
			if (count > 25) {
				attack();
				speed = 0;
				flag = 4;
			}
			break;
		case 4:
			if (count > 30) {
				speed = attack1Speed;
				rendMove();
				flag = 5;
			}
			break;
		case 5:
			if (count > 40) {
				attack();
				speed = 0;
				flag = 0;
				count = 0;
			}
			break;
		}
	}
	private void attack2() {
		 if (count>40){
			 speed=0;
			 flag=0;
			 count=0;
		 }
	}
	private void attack() {

		super.attack(bulletList[bulletIndex++], getPoint(), room.player.getPoint());// room.player.getPoint());
		bulletIndex %= 6;
	}
	
}
