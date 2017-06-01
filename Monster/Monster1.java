package Monster;

import java.awt.Point;

import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Monster;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class Monster1 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init, Move, Attack
	};

	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	private Bullet bullet; // ���� �Ѿ�
	boolean attackFlag;

	public Monster1(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(xy, room);
		setImage("Monster1Init.gif");
		speed = 0.04f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
		if (distanceTo(room.player) > 250) {
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
					motion = Motion.Attack;
					speed = 0.2f;
					if (distanceTo(room.player) < 80) {
						attack();
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
					gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
					gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
					setAngle(new Point((int) gotoX, (int) gotoY));
					}
				}
			}
		super.move();
	}

	public void attack() { // ���� ���
		bullet = new BulletOfMonster(getPoint(), room.player.getPoint(), room);
		room.bulletList.add((Bullet) bullet);
		room.add(bullet);
	}
}
