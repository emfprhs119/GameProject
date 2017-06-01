package Object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;
import Main.Sound;

@SuppressWarnings("serial")
public abstract class MoveObject extends JLabel {

	// ������-init - ������ ���� ��ü�� �ƴ϶� �����̴� ������Ʈ ��� �߻�Ŭ������ ������ �������̽� ������ ����
	// ��ӹ��� �� ���� ����ؾ��ϱ� ������ protected�� �ʱ�ȭ
	protected float speed;	//�ӵ�
	protected float x, y;	//x,y��ǥ(�������)
	public float originX, originY;	//������Ʈ�� ���߾���ǥ
	//protected JLabel label;
	
	protected float angleX,angleY;	//������ ���Ǵ� ������� x,y�Ÿ���
	protected float angle;	//����
	protected float width;	//����ũ��
	protected float height;	//����ũ��
	protected StoryRoom room;	//�������� ���� ��ü��� ���� ��ü
	protected Iterator<Monster> itMonster;	//���� �ݺ���
	protected float flagX,flagY; //block�� �پ������� x,y��ǥ
	protected Sound soundDamage;	//�ǰ���
	protected Sound soundAttack;			//������
	protected float colliderSpeed;			//�浹�Ҷ��� �ӵ�
	protected String name;	//��ü�� �̸�

	MoveObject(StoryRoom room) {	//������
		//super();
		//��� ������Ʈ�� �������� ���������� ���� ���� ���߿� ���� �߰��� ���� ����
		name=this.getClass().getName().split("\\.")[1];
		soundDamage=new Sound("damage.wav", false);
		soundAttack=new Sound("attack.wav", false);
		this.room = room;
		angle = 0;
		speed = 0f;
		x = 1;
		y = 1;
	}

	public MoveObject() {
	}

	public void setImage(String img) { // �̹����� �ּҰ��� �־��ָ� �˾Ƽ� ôô ��ġ���� �����
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		setBounds((int) x, (int) y, (int) width, (int) height);
	}
	public void setAngle(Point point) {	//����Ʈ���� x,y�Ÿ� ���ó�� ������ ���ǻ� angle�� ����
		//%���� �� ��ü ���� 
		angleY = (float) (point.y  - height / 2 - y);
		angleX = (float) (point.x - width / 2 - x);
	}

	public Point getPoint() { // �ڽ��� ��ġ��(���߾�)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}

	public void setOrigin() { // �ڽ��� ��ġ��(���߾�)
		originX = (x + width / 2);
		originY = (y + height / 2);
	}
	public double distanceTo(MoveObject object){	//������Ʈ���� �Ÿ�
		return Math.sqrt(Math.pow(originX-object.originX, (float) 2) + Math.pow(originY-object.originY, (float) 2));
	}
	protected void collider() { // �� �浹 ����
		setOrigin();
		flagX = 0;// �÷��̾ block�� �پ������� x��ǥ
		flagY = 0;// �÷��̾ block�� �پ������� y��ǥ
		///////�浹�� �����ڸ��� ������������ ��ġ�� ����(flag�� ��� �������� �� move���� ����) ///////////////////
		try {
			for (Block blockObj : room.blockList) { 
				if (originY + height / 2 - (colliderSpeed * room.step) > blockObj.originY - (blockObj.height / 2)
						&& originY - height / 2 + (colliderSpeed * room.step) < blockObj.originY + (blockObj.height / 2)) {
					if ((originX - width / 2 < blockObj.originX + blockObj.width / 2)
							&& (originX + width / 2 > blockObj.originX - blockObj.width / 2)) {
						if (originX < blockObj.originX)
							flagX = blockObj.originX - blockObj.width / 2 - width;
						else
							flagX = blockObj.originX + blockObj.width / 2;
					}
				} else if (originX + width / 2 - (colliderSpeed * room.step) > blockObj.originX - (blockObj.width / 2)
						&& originX - width / 2 + (colliderSpeed * room.step) < blockObj.originX + (blockObj.width / 2)) {
					if ((originY - height / 2 < blockObj.originY + blockObj.height / 2)
							&& (originY + height / 2 > blockObj.originY - blockObj.height / 2)) {
						if (originY < blockObj.originY)
							flagY = blockObj.originY - blockObj.height / 2 - height;
						else
							flagY = blockObj.originY + blockObj.height / 2;
					}
				}
			}
			////////////////////////////////////////////////////////////////////////////////////////
		} catch (NullPointerException e) {
		} catch (ConcurrentModificationException e) {	//�߰��� ���ŵǴ� ������Ʈ������ �Ͼ�� ����
			remove();
		}
	}
	public void paintComponent(Graphics g) { // �׸���(ȸ��)
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform aT = g2.getTransform();
		Shape oldshape = g2.getClip();
		angle = angleY / angleX;
		if (angleX > 0)
			aT.rotate(Math.atan(angle)+(270/180.0) *Math.PI, width / 2, height / 2);
		else
			aT.rotate(Math.atan(angle) + Math.PI +(270/180.0) *Math.PI, width / 2, height / 2);
		g2.setTransform(aT);
		g2.setClip(oldshape);
		super.paintComponent(g);
		room.repaint();
	} 
	public void remove() { // ������Ʈ ����!
		room.remove(this);
		//Bullet t;
		if (this instanceof Bullet){
			room.bulletList.remove(this);
			}
		if (this instanceof Monster){
			room.monsterList.remove(this);
			}
		room.repaint();
	}

	abstract void step(); // ������ �̵������ �����Ѵ�.

	abstract void damage(int power);	//������ ������(��or����) �޴� ��� ����

}
