package Monster;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import BulletOfMonster.AimBullet;
import BulletOfMonster.TwoWayBullet;
import Frame.StoryRoom;
import Object.Bullet;
import Object.Monster;

@SuppressWarnings("serial")
public class fireBall extends Monster {
	public LinkedList<Bullet> bulletList[]=new LinkedList[2];
	int bulletIndex=0;
	public fireBall(Point xy, StoryRoom room) {
		super(400, xy, room);
		bulletList[0]=new LinkedList<Bullet>();
		bulletList[1]=new LinkedList<Bullet>();
		
		for(int i=0;i<10;i++){	// 전방위 탄
			new TwoWayBullet(bulletList[0],20*i,20, room);	//20도 간격으로 전방위탄
			new TwoWayBullet(bulletList[1],20*i,20, room);
		}
	}
	public void attackStep() {
		setAngle(room.player.getPoint());
		if (distanceTo(room.player) > 150) {
			if (count % (100 *20 / room.step) == 0) {
				attack();
			}
		}
	}
	private void attack() {
		super.attack(bulletList[bulletIndex++%2],getPoint(), new Point((int)originX,(int)originY+10));//room.player.getPoint());
	}
}
