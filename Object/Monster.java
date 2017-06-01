package Object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import Frame.StoryRoom;
import Main.Project;

@SuppressWarnings("serial")
public class Monster extends MoveObject { // �پ��� ���͸� �ϳ��� ��ü�� �ʱ�ȭ�� �ٸ��� �ؼ� ���� ���� ������ ȣ�� ����
	public int num;
	public Hp hp; // ü��
	public int moveCount; // �̵� �ð� ����
	public int attackCount;// ���� �ð� ����
	public int count;
	public float initX;
	public double gotoX, gotoY; // �̵��ϰ����ϴ� ���
	public LinkedList<Bullet> bulletList;
	public int flag;
	public int time;
	public Monster(int MonsterHp, Point xy, StoryRoom room) { // ������
		super(room);
		time=0;
		bulletList = new LinkedList<Bullet>();
		hp = new Hp(MonsterHp, room, false);// ���̵��� ����������ȣ -���������� �������� �� �ϼ�!
		x = xy.x;
		y = xy.y;
		initX = x;
		count = 0;
		flag=-1;
		gotoX =- 10;// (Math.random() * Project.windowSize.x);
		gotoY = y;// (Math.random() * Project.windowSize.y);
		speed=200/x;
		moveCount = 0;
		attackCount=0;
		colliderSpeed = speed;
		setAngle(new Point((int) gotoX, (int) gotoY));
		setImage(name + "init.gif");
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		moveHp();
		room.tmpMonsterList[room.stageInitNum].add(this);
		if (!room.creatorRoom){
			setVisible(false);
		}
		room.add(this);
	}

	public void step() {
		setOrigin();
		if (flag==-1)
			initMove();
		else {
			colliderSpeed = speed;
			count++;
			count %= 10000;
			moveStep();
			attackStep();
		}
	}



	public void initMove() {
		
		if (initX < x){
			if (x<Project.windowSize.x+width){
				speed=0.16f;
				setVisible(true);
				hp.hpVisible(true);
			}
			move();
		}
		else{
			speed=0;
			flag=0;
		}
	}

	public void attackStep() {
	}

	public void moveStep() {
	}

	// �ϴ� �������� ���ξ����� �ݵ�� �ʿ��Ѱ�� �� ��ü�� �ٽ� �̵� ����
	public void move() { // �̵�
		angle = (float) Math.sqrt(angleX*angleX+angleY*angleY);
		x += speed * (angleX / angle) * room.step;
		y += speed * (angleY / angle) * room.step;
		// collider(); // �浹Ȯ��
		if (flagX != 0 || flagY != 0) {// �浹
			if (flagX != 0) // x�� �浹
				x = flagX;
			if (flagY != 0) // y�� �浹
				y = flagY;// vertical*step;
		}
		moveHp();
		setLocation((int) x, (int) y);
	}

	public void moveHp() {
		hp.setLocation((int) (x + (width - hp.width) / 2), (int) y - 30);
	}

	// ��������
	public void damage(int power) { // ������
		int damage;
		soundDamage.play();
		// ���⼭ ������ �������� ���ݷ��� �ݰ���Ų��
		damage = power;
		hp.damage(damage);
		if (hp.getHp() <= 0) {
			remove();
		}
	}

	public void setImage(String img) {
		super.setImage("monster/" + img);
	}

	public void remove() { // ����
		hp.remove();
		new Explode((int)x,(int)y,room);
		super.remove();
	}

	public void attack(LinkedList<Bullet> bulletList, Point xy, Point gotoXY) {
		for (Bullet bullet : bulletList)
			bullet.attack(xy, gotoXY);
	}

	public void attack(LinkedList<Bullet> bulletList, Point xy, MoveObject obj) {
		for (Bullet bullet : bulletList)
			bullet.attack(xy, obj);
	}
}
