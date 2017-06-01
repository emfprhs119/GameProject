package Object;

import java.awt.Color;
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
public abstract class MoveObject extends BaseObject {

	// ������-init - ������ ���� ��ü�� �ƴ϶� �����̴� ������Ʈ ��� �߻�Ŭ������ ������ �������̽� ������ ����
	// ��ӹ��� �� ���� ����ؾ��ϱ� ������ protected�� �ʱ�ȭ
	public float currSpeed; //�ӵ�
	public float speed; // �ӵ�

	protected float angleX, angleY; // ������ ���Ǵ� ������� x,y�Ÿ���
	protected float angle; // ����

	protected Iterator<Monster> itMonster; // ���� �ݺ���
	protected float flagX, flagY; // block�� �پ������� x,y��ǥ
	protected Sound soundDamage; // �ǰ���
	protected Sound soundAttack; // ������
	protected float colliderSpeed; // �浹�Ҷ��� �ӵ�

	MoveObject(StoryRoom room) { // ������
		super(room);
		// ��� ������Ʈ�� �������� ���������� ���� ���� ���߿� ���� �߰��� ���� ����

		soundDamage = new Sound("damage.wav",Sound.Kind.Effect, false);
		soundAttack = new Sound("attack.wav",Sound.Kind.Effect, false);
		this.room = room;
		angle = 0;
		speed = 0f;
		x = 1;
		y = 1;

	}

	public double distanceTo(BaseObject other) { // ������Ʈ���� �Ÿ�
		try{
		return Math.sqrt(Math.pow(originX - other.originX, (float) 2) + Math.pow(originY - other.originY, (float) 2));
		}
		catch (NullPointerException e){
			return 0;
		}
	}

	public void setAngle(Point point) { // ����Ʈ���� x,y�Ÿ� ���ó�� ������ ���ǻ� angle�� ����
		// %���� �� ��ü ����
		angleY = (float) (point.y - height / 2 - y);
		angleX = (float) (point.x - width / 2 - x);
	}

	protected void collider() { // �� �浹 ����
		flagX = 0;// �÷��̾ block�� �پ������� x��ǥ
		flagY = 0;// �÷��̾ block�� �پ������� y��ǥ
		// /////�浹�� �����ڸ��� ������������ ��ġ�� ����(flag�� ��� �������� �� move���� ����) ///////////////////
		for (Block blockObj : room.blockList) {
			if (!blockObj.name.equals("Block1") && !blockObj.name.equals("Block2") &&!blockObj.name.equals("PassBlock") )
				continue;
			if (this instanceof Bullet) 
				if (blockObj.name.equals("PassBlock"))
					continue;
		colliderX();
		colliderY();
		}
	}

	protected void colliderX() { // �� �浹 ����
		setOrigin();
		flagX = 0;// �÷��̾ block�� �پ������� x��ǥ
		// /////�浹�� �����ڸ��� ������������ ��ġ�� ����(flag�� ��� �������� �� move���� ����) ///////////////////
		try {
			for (Block blockObj : room.blockList) {
				if (!blockObj.name.equals("Block1") && !blockObj.name.equals("Block2") &&!blockObj.name.equals("PassBlock") )
					continue;
				if (this instanceof Bullet) 
					if (blockObj.name.equals("PassBlock"))
						continue;
				if (originY + height / 2 - (colliderSpeed * room.step) > blockObj.originY - (blockObj.height / 2)
						&& originY - height / 2 + (colliderSpeed * room.step) < blockObj.originY + (blockObj.height / 2)) {

					if ((originX - width / 2 < blockObj.originX + blockObj.width / 2)
							&& (originX + width / 2 > blockObj.originX - blockObj.width / 2)) {

						if (originX < blockObj.originX)
							flagX = blockObj.originX - blockObj.width / 2 - width;
						else 
							flagX = blockObj.originX + blockObj.width / 2;

					}
				}
			}
		} catch (NullPointerException e) {
		} catch (ConcurrentModificationException e) { // �߰��� ���ŵǴ� ������Ʈ������ �Ͼ�� ����
			remove();
		}
	}

	protected void colliderY() { // �� �浹 ����
		setOrigin();
		flagY = 0;// �÷��̾ block�� �پ������� y��ǥ
		// /////�浹�� �����ڸ��� ������������ ��ġ�� ����(flag�� ��� �������� �� move���� ����) ///////////////////
		try {
			for (Block blockObj : room.blockList) {
				if (!blockObj.name.equals("Block1") && !blockObj.name.equals("Block2") &&!blockObj.name.equals("PassBlock") )
					continue;
				if (this instanceof Bullet) 
					if (blockObj.name.equals("PassBlock"))
						continue;
				if (originX + width / 2 - (colliderSpeed * room.step) > blockObj.originX - (blockObj.width / 2)
						&& originX - width / 2 + (colliderSpeed * room.step) < blockObj.originX + (blockObj.width / 2)) {
					if ((originY - height / 2 < blockObj.originY + blockObj.height / 2)
							&& (originY + height / 2 > blockObj.originY - blockObj.height / 2)) {

						if (originY < blockObj.originY)
							flagY = blockObj.originY - blockObj.height / 2 - height;
						else 
							flagY = blockObj.originY + blockObj.height / 2 ;
					}
				}
			}
		} catch (NullPointerException e) {
		} catch (ConcurrentModificationException e) { // �߰��� ���ŵǴ� ������Ʈ������ �Ͼ�� ����
			remove();
		}
	}

	public void paintComponent(Graphics g) { // �׸���(ȸ��)
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform aT = g2.getTransform();
		Shape oldshape = g2.getClip();
		angle = angleY / angleX;
		double widthG=g2.getClipBounds().getWidth();
		double heightG=g2.getClipBounds().getHeight();
		aT.translate(-(widthG-width)/2, -(heightG-height)/2);
		setOrigin();
		if (!name.equals("Player")){
		if (angleX > 0)
			aT.rotate(Math.atan(angle) + (270 / 180.0) * Math.PI, widthG / 2, heightG / 2);
		else
			aT.rotate(Math.atan(angle) + Math.PI + (270 / 180.0) * Math.PI, widthG / 2, heightG / 2);
		}
		g2.setTransform(aT);
		
		g2.setClip(oldshape);
		super.paintComponent(g);
		room.repaint();
	}

	// abstract void step(); // ������ �̵������ �����Ѵ�.

	// abstract void damage(int power); //������ ������(��or����) �޴� ��� ����

}
