package Monster;

import java.awt.Component;
import java.awt.Point;

import Frame.*;
import Object.Bullet;
import Object.BulletOfMonster;
import Object.Monster;

@SuppressWarnings("serial")
public class Monster3 extends Monster { // �̸� ����Ī-�̸��� ����� �����ݽô�! ����1�̶��! ��� �˾ƸԳ���!

	private Component bullet;



	public Monster3(Point xy,StoryRoom room) {
		super(xy,room);
		setImage("monster3.gif");
		speed = 0.07f; // �̰� �� �ָ��ѵ�  �ӵ���..
		colliderSpeed=speed;
		// TODO Auto-generated constructor stub
		
	}
	public void attackStep() {
		attackCount += room.step / 2;
		if (attackCount > 500) {
			setImage("monster3Attack.gif");
			if (attackCount < 510)// �ѹ߸� �߻��ϰ� ����� ���� ���� ī��Ʈ��������
				attack();
			if (attackCount > 600) {
				setImage("monster3.gif");
				attackCount = 0;
			}
		}
	}
	public void moveStep() {
		moveCount += room.step / 2;
		if (moveCount > 150) {
			gotoXY();
			moveCount = 0;
		}
	}
	public void move(){
		angle = (float) Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle)*room.step;
		y += speed * (angleY / angle)*room.step;
		super.move();
		 }
	public void gotoXY() { // ����������� ���� ������ ���	// ���� ���� �ʿ�!!!!!
		/*gotoX = x+(Math.random()) * 800-400;
		gotoY = y+(Math.random()) * 800-400;*/
		switch((int)Math.round(Math.random()*100)%8){
		case 0:gotoX=x-300;gotoY=y-300;break;
		case 1:gotoX=x+300;gotoY=y+300;break;
		case 2:gotoX=x+300;gotoY=y-300;break;
		case 3:gotoX=x-300;gotoY=y+300;break;
		case 4:gotoX=x-0;gotoY=y-300;break;
		case 5:gotoX=x-300;gotoY=y-0;break;
		case 6:gotoX=x-0;gotoY=y+300;break;
		case 7:gotoX=x+300;gotoY=y-0;break;
		}
		setAngle(new Point((int) gotoX, (int) gotoY));
	}

	
	
	public void attack() {
			//double bulltX = room.player.getPoint().x;
			//double bulltY = room.player.getPoint().y + (Math.random() * 30 + 10);	//���� ���߿� �̿��غ�����	�̰��� ���Ͽ��� �ʿ�
			bullet = new BulletOfMonster(getPoint(), room.player.getPoint(), room);
			room.bulletList.add((Bullet) bullet);
			room.add(bullet);
	}
	
	
}
