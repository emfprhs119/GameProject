package Object;

import java.awt.Point;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class BulletOfMonster extends Bullet {

	public BulletOfMonster(Point xy, Point point, StoryRoom room) {
		super(xy, point, room);
		speed = 0.8f;
		// TODO Auto-generated constructor stub
	}

	public void attackedDecision() {//���Ϳ� �÷��̾��� �ǰݴ���� �ٸ�
		setOrigin();
		other = room.player;
		if (distanceTo(other)<=other.width/2) {
			other.damage(5); // ������
			remove();
		}
	}
}
