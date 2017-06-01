package Object;

import java.awt.Point;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class BulletOfCharacter extends Bullet {// �÷��̾��� �Ѿ�

	public BulletOfCharacter(Point xy, Point point, StoryRoom room) {	//��ġ,�̵�����,����â �Ķ����
		super(xy, point, room);
		speed=0.8f;
		// TODO Auto-generated constructor stub
	}
	public void attackedDecision() {	//���Ϳ� �÷��̾��� �ǰݴ���� �ٸ�
		setOrigin();
		it=room.monsterList.iterator();
		while(it.hasNext()){
			other=(MoveObject) it.next();
			if (distanceTo(other)<=other.width/2){
				other.damage(30);	//������
				remove();
				break;
			}
		}
	}
}
