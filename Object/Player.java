package Object;
import java.awt.Point;

import Frame.StoryRoom;
@SuppressWarnings("serial")
public class Player extends MoveObject {
	public enum Motion {	//�÷��̾��� ��ǻ���
		Init, Move, Attack, Dash
	};// ��Ÿ �߰� ����
	public float vertical,horizon;	//�����̵�,�¿��̵�
	Hp hp;	//ü��
	protected int flagV, flagH;	//key���� �÷���
	// ī��Ʈ ��ġ�� �Ѱ��� ���� �ٸ��� ��� ���ؼ� �и��Ͽ����� �뽬�� ������ �ȵǰ� ������ �ϳ��� ���ĵ� ��
	int attackCount;	//����ī��Ʈ
	int dashCount;	//�뽬ī��Ʈ
	//public Motion motion;	//��ǻ���
	private float dashV;	//�뽬���ι���
	private float dashH;	//�뽬���ι���
	private Point mouse;	//���콺 ��ġ(ȸ���� ���)
	private float xStep,yStep;	//x,y�̵���
	public Motion motion;	//���� ���

	public Player(int x, int y, StoryRoom p) { 	//������-�ʱ�ȭ
		super(p); // MoveObject �ʱ�ȭ
		hp = new Hp(100, room, true);
		//motion = Motion.Init;	//ó���� �⺻����
		speed = 0.12f;
		colliderSpeed = speed;
		flagV = 0;
		flagH = 0;
		this.x = x;
		this.y = y;
		attackCount = 0;
		dashCount = 0;
		mouse=new Point(0,0);
		setImage("playerInit.gif");
		motion = Motion.Init;
		// setSize(64,64);
		setLocation((int) x, (int) y);
		// thread.start();
	}
	public void setMouse(Point point){	//���콺 ��� �����ʿ��� ���콺 ��ġ����
		mouse=point;
	}
	public void step() {	//�Žð� �ݺ�
		//setOrigin();
		attackStep();
		if (motion.equals(Motion.Dash)) 
			dashStep();
		else
			move(vertical, horizon);
		setImage(name+motion.name()+".gif");
		setAngle(mouse);
	}

	private void dashStep() {
		{ // ���߿� ��ų �迭�� �� ���� �ϴ� ����Ȯ���� ���� �߰�
			if (dashV == 0 && dashH == 0) {
				dashV = vertical * 4;
				dashH = horizon * 4;
				colliderSpeed = speed * 4;
			}
			dashCount += room.step / 2;
			if (dashCount > 70) {
				motion = Motion.Init;
				dashCount = 0;
				dashV = 0;
				dashH = 0;
				colliderSpeed = speed;
			}
			move(dashV, dashH);
		}
	}
	private void attackStep() {
		if (motion.equals(Motion.Attack)) {
			attackCount += room.step / 2;
			if (attackCount > 100) {
				motion = Motion.Init;
				attackCount = 0;
			}
		}
	}


	public void damage(int power) {	//�ǰ�
		int damage;
		soundDamage.play();
		damage = power;
		hp.damage(damage);
		if (hp.getHp() <= 0) {
			System.exit(0);	//����� ����� ������ �絵�� �� â�� �߰� �߰� ����
		}
		// TODO Auto-generated method stub
	}

	public void remove() {	//����
		hp.remove();
		super.remove();
	}

	public void move(float vertical, float horizon) {	//�̵�

		// �밢�� �̵��� ��Ʈ2�� �������༭ �ݰ�ӵ��� ����
		if (horizon != 0 && vertical != 0) {
			xStep = (float) (horizon / Math.sqrt(2));
			yStep = (float) (vertical / Math.sqrt(2));
		} else {
			xStep = horizon;
			yStep = vertical;
		}

		x += xStep * room.step;
		y += yStep * room.step;
		collider(); // �浹Ȯ��
		if (flagX != 0) // x�� �浹
			x = flagX;
		if (flagY != 0) // y�� �浹
			y = flagY;
		if (motion != Motion.Attack && motion != Motion.Dash) {
			if (xStep != 0 || yStep != 0)
				motion = Motion.Move;
			else
				motion = Motion.Init;
		}
		setLocation((int) x, (int) y);

	}

	// ////////////////////////////////////////////////////////////////////////////////////////������ �ʿ���
	// ����
	public void moveS(int keyCode) {	//Ű���� �������� �̵�

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

	public void moveM(int keyCode) {//Ű���� �������� �̵�
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
