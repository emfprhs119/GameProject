package Object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import Frame.StoryRoom;
import Main.Project;

@SuppressWarnings("serial")
public class Bullet extends MoveObject {// �Ѿ� ������Ʈ-�÷��̾�� ������ ���ݾ׼��� ���(�Ѵ� ��ӹ޾Ƽ� ���)
	Iterator<?> it; // �ǰ������� ���� �ݺ���
	MoveObject other; // �ǰݴ��
	public double stepX, stepY;
	protected int time = 0;
	int damage;
	boolean flag;
	/*
	 * public Bullet(Point xy, Point gotoXY,StoryRoom room) { // ������ ��ġ�� ������ ��ġ,����â�� �޾ƿ� super(room);
	 * // ��� ��¥ good!!!!!!!!! room.bulletList.add(this); room.add(this); x = xy.x; y = xy.y;
	 * setAngle(gotoXY); soundAttack.play(); }
	 */
	public Bullet(int damage,StoryRoom room) {
		super(room);
		this.damage=damage;
		this.setVisible(false);
		flag=false;
		room.bulletList.add(this);
		room.add(this);
	}
	public void attack(Point xy,Point point) {
		//remove();
		soundAttack.play();
		this.setVisible(true);
		x =xy.x;
		y = xy.y;
		setAngle(point);
		flag=true;
	}

	public void step() { // �Žð� �۵�
		time++;
		moving();
		attackedDecision();
	}

	public void setImage(String img) {
		super.setImage("bullet/" + img);
	}

	public void attackedDecision() {
		// TODO Auto-generated method stub

	}

	public void moving() { // �̵�
		x += currSpeed * (stepX) * room.step;
		y += currSpeed * (stepY) * room.step;
		setLocation((int) x, (int) y);
		//collider(); // �� �浹Ȯ��
		/*
		 if (flagX != 0 || flagY != 0) {// �浹{
			remove();
		}
		*/
		if (x < 0 || y < 0 || x>Project.windowSize.x || y>Project.windowSize.y) {// �ֿܰ�
			remove();
		}
	}
	public void remove(){
		flag = false;
		this.setVisible(false);
	}
	void damage(int power) { // ������� ����(���߿� ��������� ��)
		// TODO Auto-generated method stub
	}
	public void attack(Point xy, MoveObject obj) {
		// TODO Auto-generated method stub
		
	}
}
