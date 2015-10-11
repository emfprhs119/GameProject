import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class MoveObject extends JLabel implements Runnable { 
	// 생성자-init - 생성을 위한 객체가 아니라 움직이는 오브젝트 상속 추상클래스인 이유는 인터페이스 적용을 위해
	// 상속받은 후 직접 사용해야하기 때문에 protected로 초기화
	protected float speed;
	protected float x, y;
	protected ImageIcon icon;
	protected JLabel label;
	protected Thread t;
	protected float angleY;
	protected float angleX;
	protected float angle;
	protected int width;
	protected int height;
	protected Prototype windows;

	MoveObject(Prototype windows) {
		super();
		this.windows=windows;	// 처음의 프레임을 받아오는 이유는 제거와 각각의 위치를 전부 아는녀석이 이녀석뿐이라
		angle = 0;
		speed = 0f;
		x = 1;
		y = 1;
		t = new Thread(this);
	}

	public void setImage(String img) {	// 이미지의 주소값만 넣어주면 알아서 척척 위치까지 잡아줌
		icon = new ImageIcon(img);
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

	Point getPoint() { // 자신의 위치값(정중앙)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}
	public void run() {	// 매시간마다 루프 // 재정의가 필요하면 재정의해서 써야함
		while (true) {
			Moving();
			repaint();
			try {
				Thread.sleep(2); // Step-반복의 시간을 결정 숫자가 작을수록 고성능 요구
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void remove(){	// 오브젝트 제거!
		windows.remove(this);
		windows.repaint();
}
	abstract void Moving() ;	// 각각의 이동방식을 정의한다.

}
