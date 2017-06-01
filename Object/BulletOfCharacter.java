package Object;

import java.awt.Point;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class BulletOfCharacter extends Bullet {// 플레이어의 총알

	public BulletOfCharacter(Point xy, Point point, StoryRoom room) {	//위치,이동방향,게임창 파라메터
		super(xy, point, room);
		speed=0.8f;
		// TODO Auto-generated constructor stub
	}
	public void attackedDecision() {	//몬스터와 플레이어의 피격대상이 다름
		setOrigin();
		it=room.monsterList.iterator();
		while(it.hasNext()){
			other=(MoveObject) it.next();
			if (distanceTo(other)<=other.width/2){
				other.damage(30);	//데미지
				remove();
				break;
			}
		}
	}
}
