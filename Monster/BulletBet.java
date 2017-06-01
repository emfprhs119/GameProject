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
import Object.Player;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class BulletBet extends Monster { //�Ź̿���//�� �޶�پ��ִٰ� �����ؿ��� ��Ÿ��
	public enum Motion { // ��� ����
		Init, Move, Attack
	};
	int healingCount = 0;
	Motion motion;
	boolean attackFlag;
	public LinkedList<Bullet> bulletList[]=new LinkedList[2];
	int bulletIndex=0;
	int damage=20;
	public BulletBet(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(50, xy, room); 
		motion = Motion.Init;
		attackFlag = true;
		bulletList[0]=new LinkedList<Bullet>();
		bulletList[1]=new LinkedList<Bullet>();
		new TwoWayBullet(bulletList[0],0,20, room);
		new TwoWayBullet(bulletList[0],20,20, room);
		new TwoWayBullet(bulletList[1],0,20, room);
		new TwoWayBullet(bulletList[1],20,20, room);
		
	}
	public void moveStep(){
		setAngle(room.player.getPoint());
		speed=0.3f;
		super.move();
	}
	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.
		super.step();
		setImage(name + motion.name() + ".gif");
		if(distanceTo(room.player)<room.player.width/2+width/2){
			room.player.damage(damage); // ������
			remove();
		}
	}
	}
