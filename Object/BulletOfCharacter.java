package Object;

import java.awt.Point;

import Frame.StoryRoom;

public class BulletOfCharacter extends Bullet {

	public BulletOfCharacter(Point xy, Point point, StoryRoom room) {
		super(xy, point, room);
		speed=2f;
		// TODO Auto-generated constructor stub
	}
	public void attackedDecision() {
		setOrigin();
		it=room.monsterList.iterator();
		while(it.hasNext()){
			obj=(MoveObject) it.next();
			if (distance(obj)){
				obj.damage(10);
				remove();
				break;
			}
		}
		it=room.blockList.iterator();
		while(it.hasNext()){
			obj1=(Block) it.next();
		if (originY+height/2>=obj1.originY-(obj1.height/2) && originY-height/2<=obj1.originY+(obj1.height/2))
			if ((originX-width/2<obj1.originX+obj1.width/2) &&(originX+width/2>obj1.originX-obj1.width/2)){
				remove();
			}
		}
	}
}
