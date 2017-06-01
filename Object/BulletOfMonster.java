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

	public void attackedDecision() {// 몬스터와 플레이어의 피격대상이 다름

		setOrigin();
		other = room.player;
		try {
			if (distanceTo(other) <= other.width / 2 && flag) {
				((Player) other).damage(damage); // 데미지
				remove();
			}
		} catch (NullPointerException e) {
		}
	}
}
