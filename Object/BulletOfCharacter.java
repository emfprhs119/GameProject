package Object;

import java.awt.Point;

import javax.swing.JOptionPane;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class BulletOfCharacter extends Bullet {// �÷��̾��� �Ѿ�
	public enum Bullet {
		Normel, Skill
	};
	public Monster monster;
	public int damage;
	Bullet kind;

	public BulletOfCharacter(Bullet kind, int damage, StoryRoom room) { // ��ġ,�̵�����,����â
		super(damage,room);
		this.kind = kind;
		setImage();
		this.damage = damage;
		setSpeed();
		flag = false;
		// TODO Auto-generated constructor stub
	}


	private void setSpeed() { // ������ �ӵ�
		switch (kind) {
		case Normel:
			speed = 0.3f;
			break;
		case Skill:
			speed = 0.3f;
			break;
		}
	}

	public void step() {
		if (flag){
		if (kind == Bullet.Normel)
			currSpeed += 0.03f;
		if (kind == Bullet.Skill )
			if (0==time%50)
				homing(monster);
		super.step();
		}
		}
		private void homing(Monster obj) {
			setAngle(obj.getPoint());
			angle = (float)Math.sqrt(angleX*angleX + angleY*angleY);
			stepX=angleX / angle;
			stepY=angleY / angle;
		}

	void setImage() { // ������ �̹���
		switch (kind) {
		case Normel:
			super.setImage("bullet0.png");
			break;
		case Skill:
			super.setImage("playerSkill.gif");
			break;
		}
	}

	public void attack(Point point) {
		super.attack(room.player.getPoint(), point);
		angle = (float) Math.sqrt(angleX * angleX + angleY * angleY);
		stepX = angleX / angle;
		stepY = angleY / angle;
		currSpeed = 2.2f;
		moving();
		currSpeed = speed;
	}
	public void attack(Monster obj) {
		super.attack(room.player.getPoint(),obj.getPoint());
		angle = (float) Math.sqrt(angleX * angleX + angleY * angleY);
		stepX = angleX / angle;
		stepY = angleY / angle;
		currSpeed = 2.2f;
		moving();
		currSpeed = speed*2;
		monster=obj;
	}

	public void attackedDecision() { // ���Ϳ� �÷��̾��� �ǰݴ���� �ٸ�
		setOrigin();
		it = room.monsterList.iterator();
		if (kind.equals(Bullet.Skill))
			if (!room.monsterList.contains(monster))
				remove();
		while (it.hasNext()) {
			other = (Monster) it.next();
			if (kind.equals(Bullet.Skill)){
				if (!other.equals(monster))
					continue;
			}
			if (distanceTo(other) <= other.width / 2 &&flag) {
					((Monster) other).damage(damage); // ������
					remove();
				break;
			}
		}
	}
	
}
