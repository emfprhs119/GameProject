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

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class DeathReaper extends Monster {
	public enum Motion { // 모션 정의
		Init, Move, Attack
	};

	Motion motion; // motion과 충돌이 일어나므로 사용하지 않을경우 null로 만들어줌(좋은 생각 떠오르면 수정예정)
	boolean attackFlag;

	public DeathReaper(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
		super(400, xy, room);
		speed = 0.1f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // 몬스터별 필요한 작업은 추가로 재정의해서 사용해야한다.

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

	// 깜빡이며 움직임
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
	
	public void attack2() { // 공격 모션
		//new AimBullet(getPoint(), room.player.getPoint(), room); //현재 탄이 하나뿐이여서 임시
	}

		public void attack() { // 공격 모션
			// AimBullet(getPoint(), room.player.getPoint(), room);
		}
	}
