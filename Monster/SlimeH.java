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

//첫번째 몬스터 클래스
@SuppressWarnings("serial")
public class SlimeH extends Monster { // 이름 가명칭-이름을 제대로 지어줍시다! 몬스터1이라니! 어떻게 알아먹나요!
	public enum Motion { // 모션 정의
		Init, Move, Attack
	};
	Motion motion; // motion과 충돌이 일어나므로 사용하지 않을경우 null로 만들어줌(좋은 생각 떠오르면 수정예정)
	boolean attackFlag;
	Iterator<?> it; //피격판정을 위한 반복자
	Monster other;	//피격대상
	public SlimeH(Point xy, StoryRoom room) { // 생성자 (위치와 게임창 받아옴)
		super(400, xy, room);
		speed = 0.04f;
		motion = Motion.Init;
		attackFlag = false;
	}

	public void step() { // 몬스터별 필요한 작업은 추가로 재정의해서 사용해야한다.
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

	//접근하면 플레이오 정지
	public void attack2()  { // 공격 모션
		room.player.speed=0.0f;
	}

	// 일반 공격시 주변에 몬스터가 있으면 회복
	public void attack() { // 공격 모션
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
