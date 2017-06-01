package BulletOfMonster;

import java.awt.Point;
import java.util.LinkedList;

import Frame.StoryRoom;
import Object.Bullet;

public class TwoWayBullet { // degree´Â 0~180
	public Bullet twoWay[];

	public TwoWayBullet(LinkedList<Bullet> bullets, float degree, int damage, StoryRoom room) {
		twoWay = new Bullet[2];
		twoWay[0] = new AimBullet(degree, damage, room);
		twoWay[0].setImage("twoWay.png");
		bullets.add(twoWay[0]);
		if (degree != 0 && degree != 180) {
			twoWay[1] = new AimBullet(-degree, damage, room);
			twoWay[1].setImage("twoWay.png");
			bullets.add(twoWay[1]);
		}
	}

	private Bullet twoWay(double degree, Point xy, Point point, int damage, StoryRoom room) {
		Bullet bullet = new AimBullet(damage, room);
		double angleX = point.x - xy.x;
		double angleY = point.y - xy.y;
		double angle = Math.sqrt(angleX * angleX + angleY * angleY);
		double stepX, stepY;
		if (angleX > 0)
			stepX = Math.acos(angleX / angle) + (degree / 180) * Math.PI;
		else
			stepX = Math.acos(angleX / angle) - (degree / 180) * Math.PI;
		if (angleY > 0)
			stepY = Math.asin(angleY / angle) + (degree / 180) * Math.PI;
		else
			stepY = Math.asin(angleY / angle) - (degree / 180) * Math.PI;
		bullet.stepX = Math.cos(stepX);
		bullet.stepY = Math.sin(stepY);
		bullet.setImage("twoWay.png");
		return bullet;
	}
}
