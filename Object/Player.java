package Object;

import java.awt.Point;
import java.util.Iterator;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class Player extends MoveObject {
	final float Accel;

	public enum Motion { // 플레이어의 모션상태
		Init, Move, Attack
	};// 기타 추가 예정

	public enum Skill { // 플레이어의 모션상태
		None, SkillAttack, Healing, Dash, Defense
	};

	public float vertical, horizon; // 상하이동,좌우이동
	Hp hp; // 체력
	protected int flagV, flagH; // key상태 플래그
	final int DashSpeed = 2;
	int attackCount; // 공격카운트
	int skillImgCount; // 스킬카운트
	// public Motion motion; //모션상태
	private float dashV; // 대쉬세로방향
	private float dashH; // 대쉬가로방향
	private Point mouse; // 마우스 위치(회전에 사용)
	double xStep, yStep; // x,y이동값
	public Motion motion, prevMotion; // 현재 모션
	public Skill skill; // 스킬
	int bulletNum;
	private BulletOfCharacter skillBullet[];
	Iterator<?> it; // 피격판정을 위한 반복자
	Monster other; // 피격대상
	int coolTime[];
	public BulletOfCharacter bullets[];
	public final int maxBullet = 5;

	public Player(int x, int y, StoryRoom p) { // 생성자-초기화
		super(p); // MoveObject 초기화
		hp = new Hp(100, room, true);
		speed = 0.14f;
		Accel = 0.02f;
		colliderSpeed = speed;
		flagV = 0;
		flagH = 0;
		this.x = x;
		this.y = y;
		width = 64;
		height = 64;
		attackCount = 0;
		skillImgCount = 0;
		coolTime = new int[Skill.values().length];
		mouse = new Point(0, 0);
		setAngle(new Point(getPoint().x, getPoint().y - 1));
		setImage("playerMove.gif");
		skill = Skill.None;
		prevMotion = Motion.Init;
		motion = Motion.Init;
		bullets = new BulletOfCharacter[maxBullet];
		skillBullet = new BulletOfCharacter[maxBullet];
		for (int i = 0; i < maxBullet; i++) {
			bullets[i] = new BulletOfCharacter(BulletOfCharacter.Bullet.Normel, 50, room);
			skillBullet[i] = new BulletOfCharacter(BulletOfCharacter.Bullet.Skill, 70, room);
		}
		setLocation((int) x, (int) y);
	}

	public void setMouse(Point point) { // 마우스 모션 리스너에서 마우스 위치지정
		mouse = point;
	}

	public void step() { // 매시간 반복
		// setOrigin();
		attackStep();
		skillStep();
		if (!skill.equals(Skill.Dash))
			move(vertical, horizon);
		if (skill.equals(Skill.None))
			setImage(name + motion.name() + ".gif");
		else
			setImage(name + skill.name() + ".gif");
		setAngle(mouse);
		prevMotion = motion;
	}

	void skillStep() { // 하나의 스킬 카운트를 사용하므로 동시 스킬 불가
		switch (skill) {
		case SkillAttack:
			skillImgCount += room.step * 2;
			break;
		case Healing:
			skillImgCount += room.step;
			break;
		case Dash:
			if (dashV == 0 && dashH == 0) {
				dashV = vertical * DashSpeed; // 대쉬 속도
				dashH = horizon * DashSpeed;
			}
			move(dashV, dashH);
			skillImgCount += room.step * 3.5;
			break;
		case Defense:
			skillImgCount += room.step / 2;
			break;
		}
		if (skillImgCount > 1000) {
			skill = Skill.None;
			dashV = 0;
			dashH = 0;
			skillImgCount = 0;
		}
		for (int i = 0; i < coolTime.length; i++) {
			if (coolTime[i] > 0)
				coolTime[i] -= room.step / 2;
			else
				coolTime[i] = 0;
		}
	}

	private void attackStep() {
		if (motion != prevMotion && motion.equals(Motion.Attack)) {
			attackCount = 0;
			bullets[(bulletNum++) % (maxBullet)].attack(mouse);
		}
		if (motion.equals(Motion.Attack)) {
			attackCount += room.step / 2;
			// bullets[(bulletNum++)%(maxBullet)].attack(mouse);
			if (attackCount > 100) {
				bullets[(bulletNum++) % (maxBullet)].attack(mouse);
				attackCount = 0;
			}
		}
	}

	public void skill(int keyCode) { // 키보드 누를시의 스킬
		if (coolTime[keyCode] != 0 || !skill.equals(Skill.None))
			return;
		switch (keyCode) {
		case 0: // 대쉬
			skill = Skill.Dash;
			coolTime[keyCode] = 2000;
			break;
		case 1: // 스킬 1(마법 공격) //일단 사용하지 않는다.
			it = room.monsterList.iterator();
			int i = 0;
			while (it.hasNext()) { // 수정예정
				other = (Monster) it.next();
				skill = Skill.SkillAttack;
				skillBullet[i++ % maxBullet].attack(other);
			}
			coolTime[keyCode] = 2000;
			break;
		case 2: // 스킬 2(방어)
			skill = Skill.Defense;
			coolTime[keyCode] = 2000;
			break;
		case 3: // 스킬 3(힐)
			skill = Skill.Healing;
			hp.healing(30);
			coolTime[keyCode] = 2000;
			break;
		}
	}

	public void damage(int power) { // 피격
		int damage;
		soundDamage.play();
		damage = power;
		// if (skill.equals(Skill.Defense))
		damage = 0;
		hp.damage(damage);
		// TODO Auto-generated method stub
	}

	public void remove() { // 제거
		hp.remove();
		super.remove();
	}

	public void move(float vertical, float horizon) { // 이동
		float accel;
		if (horizon != 0 && vertical != 0) {
			accel = (float) (Accel / Math.sqrt(2));
			horizon = (float) (horizon / Math.sqrt(2));
			vertical = (float) (vertical / Math.sqrt(2));
		} else
			accel = Accel;

		if (horizon > 0) {
			if (xStep < horizon)
				xStep += accel;
			else if (xStep > horizon)
				xStep -= accel / 2;
		} else if (horizon < 0) {
			if (xStep > horizon)
				xStep -= accel;
			else if (xStep < horizon)
				xStep += accel / 2;
		} else {
			if (xStep > 0)
				xStep -= accel / 2;
			else if (xStep < 0)
				xStep += accel / 2;
		}

		if (vertical > 0) {
			if (yStep < vertical)
				yStep += accel;
			else if (yStep > vertical)
				yStep -= accel / 2;
		} else if (vertical < 0) {
			if (yStep > vertical)
				yStep -= accel;
			else if (yStep < vertical)
				yStep += accel / 2;
		} else {
			if (yStep > 0)
				yStep -= accel / 2;
			else if (yStep < 0)
				yStep += accel / 2;
		}
		if (Math.abs(xStep) < 0.01)
			xStep = 0;
		if (Math.abs(yStep) < 0.01)
			yStep = 0;
		// 즉시 이동을 사용하고 싶을경우 위의것을 주석처리하고 바로 아래의 주석처리된 부분은 사용

		/*
		 * if (horizon != 0 && vertical != 0) { xStep = (float) (horizon / Math.sqrt(2)); yStep =
		 * (float) (vertical / Math.sqrt(2)); } else { xStep = horizon; yStep = vertical; }
		 */
		/**/
		x += xStep * room.step;
		y += yStep * room.step;
		if (xStep != 0) {
			colliderSpeed = (float) Math.abs(yStep);
			colliderX(); // 충돌확인
			if (flagX != 0) { // x축 충돌
				x = flagX;
				xStep = 0;
			}
		}
		if (yStep != 0) {
			colliderSpeed = (float) Math.abs(xStep);
			colliderY(); // 충돌확인
			if (flagY != 0) {// y축 충돌
				y = flagY;
				yStep = 0;
			}
		}

		if (motion != Motion.Attack) {
			if (xStep != 0 || yStep != 0)
				motion = Motion.Move;
			else
				motion = Motion.Init;
		}
		setLocation((int) x, (int) y);

	}

	public float getHp() {
		return hp.getHp();
	}

	public void setImage(String img) {
		super.setImage("player/" + img);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////무빙에 필요한
	// 동작
	/*
	 * public void moveS(int keyCode) { // 키보드 누를시의 이동 switch (keyCode) { case 'W': if (flagV == 2) {
	 * vertical = speed; flagV = 1; } else { flagV = 0; vertical = 0; } break; case 'S': if (flagV ==
	 * 2) { flagV = -1; vertical = -speed; } else { flagV = 0; vertical = 0; } break; case 'D': if
	 * (flagH == 2) { flagH = -1; horizon = -speed; } else { flagH = 0; horizon = 0; } break; case
	 * 'A': if (flagH == 2) { flagH = 1; horizon = speed; } else { flagH = 0; horizon = 0; } break; }
	 * }
	 */
	public void moveS(int keyCode) { // 키보드 누를시의 이동
		switch (keyCode) {
		case 'W':
			if (flagV == 2) {
				vertical = speed;
				flagV = 1;
			} else {
				flagV = 0;
				vertical = 0;
			}
			break;
		case 'S':
			if (flagV == 2) {
				flagV = -1;
				vertical = -speed;
			} else {
				flagV = 0;
				vertical = 0;
			}
			break;
		case 'D':
			if (flagH == 2) {
				flagH = -1;
				horizon = -speed;
			} else {
				flagH = 0;
				horizon = 0;
			}
			break;
		case 'A':
			if (flagH == 2) {
				flagH = 1;
				horizon = speed;
			} else {
				flagH = 0;
				horizon = 0;
			}
			break;
		}
	}

	public void moveM(int keyCode) {// 키보드 뗏을시의 이동
		switch (keyCode) {
		case 'W':
			vertical = -speed;
			if (flagV == 0)
				flagV = -1;
			else if (flagV == 1)
				flagV = 2;
			break;
		case 'S':
			vertical = speed;
			if (flagV == 0)
				flagV = 1;
			else if (flagV == -1)
				flagV = 2;
			break;
		case 'D':
			horizon = speed;
			if (flagH == 0)
				flagH = 1;
			else if (flagH == -1)
				flagH = 2;
			break;
		case 'A':
			horizon = -speed;
			if (flagH == 0)
				flagH = -1;
			else if (flagH == 1)
				flagH = 2;
			break;
		}
	}

}
