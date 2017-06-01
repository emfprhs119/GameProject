package Object;

import java.awt.Point;

import javax.swing.JOptionPane;

import Frame.StoryRoom;
import Main.Project;
import Object.Player.Motion;

@SuppressWarnings("serial")
public class BulletOfMonster extends Bullet {
	/*
	 * public BulletOfMonster(Point xy, Point point, StoryRoom room) { super(xy, point, room); }
	 */
	public BulletOfMonster(int damage, StoryRoom room) {
		super(damage, room);
	}

	public void attackedDecision() {// ���Ϳ� �÷��̾��� �ǰݴ���� �ٸ�

		setOrigin();
		other = room.player;
		try {
			if (distanceTo(other) <= other.width / 2 && flag) {
				((Player) other).damage(damage); // ������
				remove();
			}
		} catch (NullPointerException e) {
		}
	}
}
