package Object;

import java.awt.Point;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;

public abstract class MoveObject extends JLabel implements Runnable {
	// ������-init - ������ ���� ��ü�� �ƴ϶� �����̴� ������Ʈ ��� �߻�Ŭ������ ������ �������̽� ������ ����
	// ��ӹ��� �� ���� ����ؾ��ϱ� ������ protected�� �ʱ�ȭ
	protected float speed;
	protected float x, y;

	public float originX, originY;
	protected JLabel label;
	protected Thread thread;
	protected float angleY;
	protected float angleX;
	protected float angle;
	protected float width;
	protected float height;
	boolean runflag;
	protected StoryRoom room;
	private double distance;
	private Iterator<Block> itBlock;
	protected Iterator<Monster> itMonster;
	protected boolean flagX;
	protected boolean flagY;
	private Block objTemp;
	private double tempDi;
	private Block blockObj;

	MoveObject(StoryRoom room) {
		super();
		this.room = room; // ������-��� ������ ������ �ִ� â
		angle = 0;
		speed = 0f;
		x = 1;
		y = 1;
		thread = new Thread(this);
	}

	public MoveObject() {
		// TODO Auto-generated constructor stub
	}

	public void setImage(String img) { // �̹����� �ּҰ��� �־��ָ� �˾Ƽ� ôô ��ġ���� �����
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		setBounds((int) x, (int) y, (int) width, (int) height);
	}

	public void setAngle(Point point) {
		// ����Ʈ�� ��ġ�� ��������� ���� ���� �ʺ�����(�� ����Ÿ�� ���� ������ �����) ��ü���� �ʿ��� ������ �ٸ��Ƿ� �̰͸� ����
		// 30�� 8�� ������ �������� â�� ���ʹٿ� ����â �β� �������� ��س��� �� ��ġ��ŭ ����
		angleY = (float) (point.y - 30 - height / 2 - y);
		angleX = (float) (point.x - 8 - width / 2 - x);
	}

	public Point getPoint() { // �ڽ��� ��ġ��(���߾�)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}

	void setOrigin() { // �ڽ��� ��ġ��(���߾�)
		originX = (x + width / 2);
		originY = (y + height / 2);
	}

	protected void collider() { // �� �浹 ����
		setOrigin();
		itBlock = room.blockList.iterator();
		distance = 1000;
		flagX = false;
		flagY = false;
		try {
			for (Block blockObj : room.blockList) { // ���� for loop �ϴ� �� �� �ִ��� �ôµ� �� �۵��ϳ�
				if (originY + height / 2 - (speed + 0.5) >= blockObj.originY - (blockObj.height / 2)
						&& originY - height / 2 + (speed + 0.5) <= blockObj.originY + (blockObj.height / 2))
					if ((originX - width / 2 < blockObj.originX + blockObj.width / 2)
							&& (originX + width / 2 > blockObj.originX - blockObj.width / 2))
						flagX = true;
				if (originX + width / 2 - (speed + 0.5) >= blockObj.originX - (blockObj.width / 2)
						&& originX - width / 2 + (speed + 0.5) <= blockObj.originX + (blockObj.width / 2))
					if ((originY - height / 2 < blockObj.originY + blockObj.height / 2)
							&& (originY + height / 2 > blockObj.originY - blockObj.height / 2))
						if (!flagX)
							flagY = true;
			}
		} catch (NullPointerException e) {
			flagX = true;
			flagY = true;
		} catch (ConcurrentModificationException e) {
			remove();
		}
	}

	public void run() {
		while (true) {
			if (!room.stop) {
				step();
				repaint();
			}
			try {
				Thread.sleep(2); // Step-�ݺ��� �ð��� ���� ���ڰ� �������� ���� �䱸
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void remove() { // ������Ʈ ����!
		room.remove(this);
		room.repaint();
		thread.stop();
	}

	abstract void step(); // ������ �̵������ �����Ѵ�.

	abstract void damage(int power);

}
