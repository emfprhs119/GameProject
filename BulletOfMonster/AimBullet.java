package BulletOfMonster;

import java.awt.Point;
import java.util.LinkedList;

import Frame.StoryRoom;
import Object.Bullet;
import Object.BulletOfMonster;

@SuppressWarnings("serial")
public class AimBullet extends BulletOfMonster {
	float degree;
	public AimBullet(int damage,StoryRoom room) {	// 기본 aim
		super(damage,room);
		setImage("aim.gif");
		speed = 0.3f;
		degree=0;
	}
	public AimBullet(float degree,int damage,StoryRoom room) {	// 각도 aim 각도 설정시 플러스 마이너스 두개 추가해줄것
		this(damage,room);
		this.degree=degree;
	}
	public void attack(Point xy, Point gotoXY){
		super.attack(xy,gotoXY);
		angle = (float)Math.sqrt(angleX*angleX + angleY*angleY);
		if (angleX > 0)
			stepX=Math.acos(angleX / angle) + (degree/180)*Math.PI;
		else
			stepX=Math.acos(angleX / angle) -(degree/180)* Math.PI;
		if (angleY > 0)
			stepY=Math.asin(angleY / angle) + (degree/180)*Math.PI;
		else
			stepY=Math.asin(angleY / angle) - (degree/180)*Math.PI;
		stepX=Math.cos(stepX);
		stepY=Math.sin(stepY);
		currSpeed=speed;
	}
}
