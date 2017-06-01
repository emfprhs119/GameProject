package BulletOfMonster;

import java.awt.Point;

import Frame.StoryRoom;
import Object.BulletOfMonster;
import Object.MoveObject;

public class Missile extends BulletOfMonster{
	MoveObject obj;
	public Missile(int damage,StoryRoom room) {
		super(damage,room);
		setImage("bullet3.gif");
		speed = 0.5f;
	}
	public void attack(Point xy, MoveObject obj){
		remove();
		super.attack(xy,obj.getPoint());
		this.obj=obj;
		angle = (float)Math.sqrt(angleX*angleX + angleY*angleY);
		stepX=angleX / angle;
		stepY=angleY / angle;
		currSpeed=speed;
	}
	public void step(){
		super.step();
		if (0==time%50)
			homing();
	}
	private void homing() {
		setAngle(obj.getPoint());
		angle = (float)Math.sqrt(angleX*angleX + angleY*angleY);
		stepX=angleX / angle;
		stepY=angleY / angle;
	}
}
