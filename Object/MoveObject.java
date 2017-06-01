package Object;

import java.awt.Point;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;

public abstract class MoveObject extends JLabel implements Runnable {
	// 생성자-init - 생성을 위한 객체가 아니라 움직이는 오브젝트 상속 추상클래스인 이유는 인터페이스 적용을 위해
	// 상속받은 후 직접 사용해야하기 때문에 protected로 초기화
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
		this.room = room; // 마스터-모든 정보를 가지고 있는 창
		angle = 0;
		speed = 0f;
		x = 1;
		y = 1;
		thread = new Thread(this);
	}

	public MoveObject() {
		// TODO Auto-generated constructor stub
	}

	public void setImage(String img) { // 이미지의 주소값만 넣어주면 알아서 척척 위치까지 잡아줌
		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		setBounds((int) x, (int) y, (int) width, (int) height);
	}

	public void setAngle(Point point) {
		// 포인트가 위치한 방향까지의 가로 세로 너비측정(이 데이타를 토대로 각도를 계산함) 객체마다 필요한 각도가 다르므로 이것만 정의
		// 30과 8은 실제로 눌러보니 창의 위쪽바와 왼쪽창 두께 때문인지 어극나서 그 수치만큼 보정
		angleY = (float) (point.y - 30 - height / 2 - y);
		angleX = (float) (point.x - 8 - width / 2 - x);
	}

	public Point getPoint() { // 자신의 위치값(정중앙)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}

	void setOrigin() { // 자신의 위치값(정중앙)
		originX = (x + width / 2);
		originY = (y + height / 2);
	}

	protected void collider() { // 벽 충돌 판정
		setOrigin();
		itBlock = room.blockList.iterator();
		distance = 1000;
		flagX = false;
		flagY = false;
		try {
			for (Block blockObj : room.blockList) { // 향상된 for loop 일단 쓸 수 있는지 봤는데 잘 작동하네
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
				Thread.sleep(2); // Step-반복의 시간을 결정 숫자가 작을수록 고성능 요구
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void remove() { // 오브젝트 제거!
		room.remove(this);
		room.repaint();
		thread.stop();
	}

	abstract void step(); // 각각의 이동방식을 정의한다.

	abstract void damage(int power);

}
