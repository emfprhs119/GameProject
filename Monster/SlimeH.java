package Monster;

import java.awt.Point;
import java.util.Iterator;

import BulletOfMonster.AimBullet;
import Frame.*;
import Main.Project;
import Monster.Bet.Motion;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Monster;
import Object.MoveObject;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class SlimeH extends Monster { 
	public enum Motion { // ��� ����
		Init, Move, Attack
	};
	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	boolean attackFlag;
	Iterator<?> it; //�ǰ������� ���� �ݺ���
	Monster other;	//�ǰݴ��
	public SlimeH(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(400, xy, room);
		speed = 0.04f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.
		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
			if (count % (100 * 17 / room.step) == 0) {
				setAngle(room.player.getPoint());
				motion = Motion.Attack;
				attack();
			}
			if (count % (120 * 17 / room.step) == 0) {
				motion = Motion.Init;
				count = 0;
			}
			
	}

	public void moveStep() {
		room.player.setOrigin();
		
			if (distanceTo(room.player) < 250) {
				if (!attackFlag){
				speed = 0.10f;
				
				gotoX = (float) (room.player.originX - originX);
				gotoY = (float) (room.player.originY - originY);
				setAngle(room.player.getPoint());
				motion = Motion.Init;
				if (distanceTo(room.player) < 150) {
					motion = Motion.Move;
					speed = 0.2f;
					if (distanceTo(room.player) < 80) {
						attack2();
						gotoX = -(float) (room.player.originX - originX);
						gotoY = -(float) (room.player.originY - originY);
						attackFlag=true;
					}
				}
				}
			} else {
				attackFlag=false;
				if (!attackFlag){
				if (count % (120 * 17 / room.step) == 0) {
					speed = 0.04f;
					room.player.speed=0.12f;
					gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
					gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
					setAngle(room.player.getPoint());
					}
				}
			}
		super.move();
	}

	//�����ϸ� �÷��̿� ����
	public void attack2()  { // ���� ���
		room.player.speed=0.0f;
	}

	// �Ϲ� ���ݽ� �ֺ��� ���Ͱ� ������ ȸ��
	public void attack() { // ���� ���
		setOrigin();
		it=room.monsterList.iterator();
		while(it.hasNext()){
			other=(Monster) it.next();
			if (distanceTo(other)<=350){
				if(other.hp.hp+30<other.hp.hpMax)
				other.hp.hp+=30;
				else
					other.hp.hp=other.hp.hpMax;
				other.damage(0);
			}
		}
		//new AimBullet(getPoint(), room.player.getPoint(), room);
	}
}
