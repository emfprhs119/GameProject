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

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class BulletBet extends Monster { //거미외형//착 달라붙어있다가 공격해오는 스타일
	public enum Motion { // 모션 정의
		Init, Move, Attack
	};
	int healingCount = 0;
	Motion motion;
	boolean attackFlag;
	public LinkedList<Bullet> bulletList[]=new LinkedList[2];
	int bulletIndex=0;
	int damage=20;
	public BulletBet(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
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
	public void step() { // 몬스터별 필요한 작업은 추가로 재정의해서 사용해야한다.
		super.step();
		setImage(name + motion.name() + ".gif");
		if(distanceTo(room.player)<room.player.width/2+width/2){
			room.player.damage(damage); // 데미지
			remove();
		}
	}
	}
