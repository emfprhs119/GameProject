import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class MoveObject extends JLabel implements Runnable { 
	// ������-init - ������ ���� ��ü�� �ƴ϶� �����̴� ������Ʈ ��� �߻�Ŭ������ ������ �������̽� ������ ����
	// ��ӹ��� �� ���� ����ؾ��ϱ� ������ protected�� �ʱ�ȭ
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
		this.windows=windows;	// ó���� �������� �޾ƿ��� ������ ���ſ� ������ ��ġ�� ���� �ƴ³༮�� �̳༮���̶�
		angle = 0;
		speed = 0f;
		x = 1;
		y = 1;
		t = new Thread(this);
	}

	public void setImage(String img) {	// �̹����� �ּҰ��� �־��ָ� �˾Ƽ� ôô ��ġ���� �����
		icon = new ImageIcon(img);
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

	Point getPoint() { // �ڽ��� ��ġ��(���߾�)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}
	public void run() {	// �Žð����� ���� // �����ǰ� �ʿ��ϸ� �������ؼ� �����
		while (true) {
			Moving();
			repaint();
			try {
				Thread.sleep(2); // Step-�ݺ��� �ð��� ���� ���ڰ� �������� ���� �䱸
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void remove(){	// ������Ʈ ����!
		windows.remove(this);
		windows.repaint();
}
	abstract void Moving() ;	// ������ �̵������ �����Ѵ�.

}
