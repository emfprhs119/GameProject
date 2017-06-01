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

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Slime extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init, Move, Attack
	};
	int healingCount = 0;
	Motion motion;
	boolean attackFlag;
	public LinkedList<Bullet> bulletList[]=new LinkedList[2];
	int bulletIndex=0;

	public Slime(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(120, xy, room); 
		motion = Motion.Init;
		attackFlag = true;
		bulletList[0]=new LinkedList<Bullet>();
		bulletList[1]=new LinkedList<Bullet>();
		new TwoWayBullet(bulletList[0],0,20, room);
		new TwoWayBullet(bulletList[0],20,20, room);
		new TwoWayBullet(bulletList[1],0,20, room);
		new TwoWayBullet(bulletList[1],20,20, room);
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
			if (count % (100 * 17 / room.step) == 0 && !(distanceTo(room.player) < 250)) {
				setAngle(room.player.getPoint());
				attack();
			}
			if (count % (120 * 17 / room.step) == 0) {
				motion = Motion.Move;
				count = 0;
			}
	}

	//�������� �����̴�. �÷��̾�� �����ϸ� ������ ������
	/*
	public void moveStep() {
		room.player.setOrigin();
		motion = Motion.Move;
		if (distanceTo(room.player) < 250){
			speed = 0.2f;
			setAngle(room.player.getPoint());
			if (distanceTo(room.player) < 64 && attackFlag==true){
				attackFlag=false;
				new SlimeMini(getPoint(),room);
				new SlimeMini(getPoint(),room);
				remove();
			}
		}

		else if (count % (120 * 17 / room.step) == 0){
			if(speed==0.2f){
			speed = 0.08f;
			}
			gotoX = (Math.random() * Project.windowSize.x);
			gotoY = (Math.random() * Project.windowSize.y) ;
			setAngle(new Point((int) gotoX,(int)gotoY));
		}
		super.move();
	}
	*/
	public void attack() { // ����
		setAngle(room.player.getPoint());
		super.attack(bulletList[bulletIndex++%2],getPoint(), room.player.getPoint());//room.player.getPoint());
	}
	}
