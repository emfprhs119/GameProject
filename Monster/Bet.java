package Monster;

import java.awt.Point;
import java.util.LinkedList;

import BulletOfMonster.AimBullet;
import BulletOfMonster.Missile;
import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.Monster;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Bet extends Monster {
	public enum Motion { // ��� ����
		Init, Move, Attack, Attack2
	};

	// int time;
	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	boolean attackFlag;
	int bulletIndex = 0;
	public LinkedList<Bullet> bulletList[] = new LinkedList[2];

	public Bet(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(100, xy, room); // ü�¼���
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

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.
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

	public void move() { // �̵�
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
		// collider(); // �浹Ȯ��
		if (flagX != 0 || flagY != 0) {// �浹
			if (flagX != 0) // x�� �浹
				x = flagX;
			if (flagY != 0) // y�� �浹
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

	public void attack() { // ����
		// setAngle(room.player.getPoint());
		super.attack(bulletList[bulletIndex++ % 2], getPoint(), room.player.getPoint());// room.player.getPoint());

	}
}
