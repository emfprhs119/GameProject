package Object;

import java.awt.Point;

import Frame.StoryRoom;

public class BulletOfMonster extends Bullet {

	public BulletOfMonster(Point xy, Point point, StoryRoom room) {
		super(xy, point, room);
		// TODO Auto-generated constructor stub
	}
	public void attackedDecision() {
		setOrigin();
		it=room.monsterList.iterator();
		while(it.hasNext()){
			obj=(MoveObject) it.next();
			if (distance(obj)){
				//obj.remove();
				//it.remove();
				//remove();
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
