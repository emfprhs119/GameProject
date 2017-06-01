package Monster;

import java.awt.Point;

import javax.swing.JLabel;

import BulletOfMonster.AimBullet;
import Frame.*;
import Main.Project;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Hp;
import Object.Monster;

//ù��° ���� Ŭ����
@SuppressWarnings("serial")
public class DeathReaper extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!
	public enum Motion { // ��� ����
		Init, Move, Attack
	};

	Motion motion; // motion�� �浹�� �Ͼ�Ƿ� ������� ������� null�� �������(���� ���� �������� ��������)
	boolean attackFlag;

	public DeathReaper(Point xy, StoryRoom room) { // ������ (��ġ�� ����â �޾ƿ�)
		super(400, xy, room);
		speed = 0.1f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // ���ͺ� �ʿ��� �۾��� �߰��� �������ؼ� ����ؾ��Ѵ�.

		super.step();
		setImage(name + motion.name() + ".gif");
	}

	public void attackStep() {
			if (count % (110 * 17 / room.step) == 0) {
				setAngle(room.player.getPoint());
				//motion = Motion.Attack;
				attack();
			}
			if (count % (130 * 17 / room.step) == 0) {
				//motion = Motion.Init;
				count = 0;
			}
	}

	// �����̸� ������
	public void moveStep() {
		room.player.setOrigin();

		if (distanceTo(room.player) < 250){
			speed = 0.2f;
			colliderSpeed=speed;
		}

		if (count % (70 * 17 / room.step) == 0){
			if(speed==0.2f){
				attack2();
			speed = 0.1f;
			}
			gotoX = (Math.random() * Project.windowSize.x) - Project.windowSize.x / 2;
			gotoY = (Math.random() * Project.windowSize.y) - Project.windowSize.y / 2;
			setVisible(false);
			hp.base.setVisible(false);
			hp.hpBar.setVisible(false);
			setAngle(new Point((int) gotoX, (int) gotoY));
		}
		
		if (count % (100 * 17 / room.step) == 0){
			motion = Motion.Move;
			setVisible(true); 
			hp.base.setVisible(true);
			hp.hpBar.setVisible(true);
		}

		super.move();
	}
	
	public void attack2() { // ���� ���
		//new AimBullet(getPoint(), room.player.getPoint(), room); //���� ź�� �ϳ����̿��� �ӽ�
	}

		public void attack() { // ���� ���
			// AimBullet(getPoint(), room.player.getPoint(), room);
		}
	}
