package Object;

import java.awt.Point;
import java.util.Iterator;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class Bullet extends MoveObject {//�Ѿ� ������Ʈ-�÷��̾�� ������ ���ݾ׼��� ���(�Ѵ� ��ӹ޾Ƽ� ���)
	Iterator<?> it; //�ǰ������� ���� �ݺ���
	MoveObject other;	//�ǰݴ��
	public Bullet(Point xy, Point gotoXY,StoryRoom room) {	// ������ ��ġ�� ������ ��ġ,����â�� �޾ƿ�
		super(room); // ��� ��¥ good!!!!!!!!!
		x = xy.x;
		y = xy.y;
		speed = 0.5f;
		setImage("bullet.png");
		setAngle(gotoXY);
		soundAttack.play();
	}
	public void step(){		//�Žð� �۵�
		moving();
		attackedDecision();
	}
	public void attackedDecision() {
		// TODO Auto-generated method stub
		
	}
	public void moving() {	//�̵�
		
		angle = (float)Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle)*room.step;
		y += speed * (angleY / angle)*room.step;
		collider(); // �� �浹Ȯ��
		if (flagX!=0 || flagY!=0) {// �浹{
			remove();
		}
		setLocation((int) x, (int) y);
	}
	@Override
	void damage(int power) {	//������� ����(���߿� ��������� ��)
		// TODO Auto-generated method stub
	}
}
