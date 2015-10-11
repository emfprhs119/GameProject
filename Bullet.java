import java.awt.Point;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bullet extends MoveObject {
	/*�ʿ� �˰��� 
	 * ���Ϳ� �ε������� ����(���� ü�� ���ϸ� ���⼭ �ұ� �����ʿ��� �ұ�)
	 */
	Iterator it; //�ǰ������� ���� �ݺ���
	MoveObject obj;
	double di;
	public Bullet(Point xy, Point point,Prototype p) {
		super(p); // ��� ��¥ good!!!!!!!!!
		x = xy.x;
		y = xy.y;
		speed = 2f;
		setImage("bullet.png");
		setAngle(point);
		t.start();
		//removeAll();
		
	}

	public void Moving() {
		angle = (float)Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle);
		y += speed * (angleY / angle);
		setLocation((int) x, (int) y);
		if (x>windows.getWidth()-50 || x<10 || y>windows.getHeight()-50 || y<10){
			remove();
			/*�̰� �ϴ� �����̱� �ѵ� new�� ����������̱� ������ 
			 * �ڽ��� null������ ���� ����� ���� ���ο��� �ϳ��� �Ҵ��ϰ� null�� �����ص� ������ 
			 * �׳� �̴�� �ֵ� �ɰͰ����� ��� 
			 */
		}
	}
	public void attackedDecision() {
		it=windows.monsterList.iterator();
		while(it.hasNext()){
			obj=(MoveObject) it.next();
			if (distance(obj)){
				obj.remove();
				it.remove();
				remove();
				break;
			}
		}
	}
	public boolean distance(MoveObject object){
		di = Math.sqrt(Math.pow(x-object.getPoint().x, (float) 2) + Math.pow(y-object.getPoint().y, (float) 2));
		if (di<20)
			return true;
		else
			return false;
	}
	
	public void run() {	// �������̵�
		while (true) {
			Moving();
			attackedDecision();
			try {
				Thread.sleep(2); // Step-�ݺ��� �ð��� ���� ���ڰ� �������� ���� �䱸
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
