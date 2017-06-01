package Object;

import java.awt.Point;
import java.util.Iterator;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class Player extends MoveObject {
	final float Accel;

	public enum Motion { // �÷��̾��� ��ǻ���
		Init, Move, Attack
	};// ��Ÿ �߰� ����

	public enum Skill { // �÷��̾��� ��ǻ���
		None, SkillAttack, Healing, Dash, Defense
	};

	public float vertical, horizon; // �����̵�,�¿��̵�
	Hp hp; // ü��
	protected int flagV, flagH; // key���� �÷���
	final int DashSpeed = 2;
	int attackCount; // ����ī��Ʈ
	int skillImgCount; // ��ųī��Ʈ
	// public Motion motion; //��ǻ���
	private float dashV; // �뽬���ι���
	private float dashH; // �뽬���ι���
	private Point mouse; // ���콺 ��ġ(ȸ���� ���)
	double xStep, yStep; // x,y�̵���
	public Motion motion, prevMotion; // ���� ���
	public Skill skill; // ��ų
	int bulletNum;
	private BulletOfCharacter skillBullet[];
	Iterator<?> it; // �ǰ������� ���� �ݺ���
	Monster other; // �ǰݴ��
	int coolTime[];
	public BulletOfCharacter bullets[];
	public final int maxBullet = 5;

	public Player(int x, int y, StoryRoom p) { // ������-�ʱ�ȭ
		super(p); // MoveObject �ʱ�ȭ
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

	public void setMouse(Point point) { // ���콺 ��� �����ʿ��� ���콺 ��ġ����
		mouse = point;
	}

	public void step() { // �Žð� �ݺ�
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

	void skillStep() { // �ϳ��� ��ų ī��Ʈ�� ����ϹǷ� ���� ��ų �Ұ�
		switch (skill) {
		case SkillAttack:
			skillImgCount += room.step * 2;
			break;
		case Healing:
			skillImgCount += room.step;
			break;
		case Dash:
			if (dashV == 0 && dashH == 0) {
				dashV = vertical * DashSpeed; // �뽬 �ӵ�
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

	public void skill(int keyCode) { // Ű���� �������� ��ų
		if (coolTime[keyCode] != 0 || !skill.equals(Skill.None))
			return;
		switch (keyCode) {
		case 0: // �뽬
			skill = Skill.Dash;
			coolTime[keyCode] = 2000;
			break;
		case 1: // ��ų 1(���� ����) //�ϴ� ������� �ʴ´�.
			it = room.monsterList.iterator();
			int i = 0;
			while (it.hasNext()) { // ��������
				other = (Monster) it.next();
				skill = Skill.SkillAttack;
				skillBullet[i++ % maxBullet].attack(other);
			}
			coolTime[keyCode] = 2000;
			break;
		case 2: // ��ų 2(���)
			skill = Skill.Defense;
			coolTime[keyCode] = 2000;
			break;
		case 3: // ��ų 3(��)
			skill = Skill.Healing;
			hp.healing(30);
			coolTime[keyCode] = 2000;
			break;
		}
	}

	public void damage(int power) { // �ǰ�
		int damage;
		soundDamage.play();
		damage = power;
		// if (skill.equals(Skill.Defense))
		damage = 0;
		hp.damage(damage);
		// TODO Auto-generated method stub
	}

	public void remove() { // ����
		hp.remove();
		super.remove();
	}

	public void move(float vertical, float horizon) { // �̵�
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
		// ��� �̵��� ����ϰ� ������� ���ǰ��� �ּ�ó���ϰ� �ٷ� �Ʒ��� �ּ�ó���� �κ��� ���

		/*
		 * if (horizon != 0 && vertical != 0) { xStep = (float) (horizon / Math.sqrt(2)); yStep =
		 * (float) (vertical / Math.sqrt(2)); } else { xStep = horizon; yStep = vertical; }
		 */
		/**/
		x += xStep * room.step;
		y += yStep * room.step;
		if (xStep != 0) {
			colliderSpeed = (float) Math.abs(yStep);
			colliderX(); // �浹Ȯ��
			if (flagX != 0) { // x�� �浹
				x = flagX;
				xStep = 0;
			}
		}
		if (yStep != 0) {
			colliderSpeed = (float) Math.abs(xStep);
			colliderY(); // �浹Ȯ��
			if (flagY != 0) {// y�� �浹
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

	// ////////////////////////////////////////////////////////////////////////////////////////������ �ʿ���
	// ����
	/*
	 * public void moveS(int keyCode) { // Ű���� �������� �̵� switch (keyCode) { case 'W': if (flagV == 2) {
	 * vertical = speed; flagV = 1; } else { flagV = 0; vertical = 0; } break; case 'S': if (flagV ==
	 * 2) { flagV = -1; vertical = -speed; } else { flagV = 0; vertical = 0; } break; case 'D': if
	 * (flagH == 2) { flagH = -1; horizon = -speed; } else { flagH = 0; horizon = 0; } break; case
	 * 'A': if (flagH == 2) { flagH = 1; horizon = speed; } else { flagH = 0; horizon = 0; } break; }
	 * }
	 */
	public void moveS(int keyCode) { // Ű���� �������� �̵�
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

	public void moveM(int keyCode) {// Ű���� �������� �̵�
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
