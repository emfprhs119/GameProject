package Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import Frame.StoryRoom;
import Main.Project;

@SuppressWarnings("serial")
public class Monster extends MoveObject { // 다양한 몬스터를 하나의 객체로 초기화를 다르게 해서 만들 예정 실제로 호출 안함
	public int num;
	public Hp hp; // 체력
	public int moveCount; // 이동 시간 간격
	public int attackCount;// 공격 시간 간격
	public int count;
	public float initX;
	public double gotoX, gotoY; // 이동하고자하는 장소
	public LinkedList<Bullet> bulletList;
	public int flag;
	public int time;

	public Monster(int MonsterHp, Point xy, StoryRoom room) { // 생성자
		super(room);
		time = 0;
		bulletList = new LinkedList<Bullet>();
		hp = new Hp(MonsterHp, room, false);// 난이도와 스테이지번호 -점차적으로 강해지는 적 완성!
		x = xy.x;
		y = xy.y;
		initX = x;
		count = 0;
		flag = -1;
		gotoX = -10;// (Math.random() * Project.windowSize.x);
		gotoY = y;// (Math.random() * Project.windowSize.y);
		speed = 0.16f;//200 / x;
		moveCount = 0;
		attackCount = 0;
		colliderSpeed = speed;
		setAngle(new Point((int) gotoX, (int) gotoY));
		setImage(name + "init.gif");
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		moveHp();
		room.tmpMonsterList[room.stageInitNum].add(this);
		if (!room.creatorRoom) {
			setVisible(false);
		}
		room.add(this);
	}

	public void step() {
		setOrigin();
		if (flag == -1)
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

		if (initX < x) {
			if (x < Project.windowSize.x + width) {
				speed = 0.16f;
				setVisible(true);
				hp.hpVisible(true);
			}
			move();
		} else {
			speed = 0;
			flag = 0;
		}
	}

	public void attackStep() {
	}

	public void moveStep() {
	}

	// 일단 공통으로 빼두었으나 반드시 필요한경우 각 개체로 다시 이동 가능
	public void move() { // 이동
		angle = (float) Math.sqrt(angleX * angleX + angleY * angleY);
		x += speed * (angleX / angle) * room.step;
		y += speed * (angleY / angle) * room.step;
		// collider(); // 충돌확인
		if (flagX != 0 || flagY != 0) {// 충돌
			if (flagX != 0) // x축 충돌
				x = flagX;
			if (flagY != 0) // y축 충돌
				y = flagY;// vertical*step;
		}
		moveHp();
		setLocation((int) x, (int) y);
	}

	public void moveHp() {
		hp.setLocation((int) (x + (width - hp.width) / 2), (int) y - 30);
	}

	// 마찬가지
	public void damage(int power) { // 데미지
		int damage;
		soundDamage.play();
		// 여기서 몬스터의 방어력으로 공격력을 반감시킨다
		damage = power;
		hp.damage(damage);
		if (hp.getHp() <= 0) {
			remove();
		}
	}

	

	public void setImage(String img) {
		super.setImage("monster/" + img);
	}

	public void remove() { // 제거
		hp.remove();
		new Explode((int) x, (int) y, room);
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
