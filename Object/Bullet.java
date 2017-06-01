package Object;

import java.awt.Point;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;

public class Bullet extends MoveObject {
	/*�ʿ� �˰��� 
	 * ���Ϳ� �ε������� ����(���� ü�� ���ϸ� ���⼭ �ұ� �����ʿ��� �ұ�)
	 */
	Iterator it; //�ǰ������� ���� �ݺ���
	MoveObject obj;
	Block obj1;
	double di;
	
	public Bullet(Point xy, Point gotoXY,StoryRoom p) {	// �÷��̾�� ������ ���ݾ׼��� ���(�Ѵ� ��ӹ޾Ƽ� ���)
		super(p); // ��� ��¥ good!!!!!!!!!
		x = xy.x;
		y = xy.y;
		speed = 1f;
		setImage("bullet.png");
		setAngle(gotoXY);
		thread.start();
		//removeAll();
		
	}
	public void step(){
		moving();
		attackedDecision();
	}
	public void attackedDecision() {
		// TODO Auto-generated method stub
		
	}
	public void moving() {
		angle = (float)Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle);
		y += speed * (angleY / angle);
		setLocation((int) x, (int) y);
		if (x>room.getWidth()-50-13 || x<50-8+3 || y>room.getHeight()-50-30-3 || y<40+3){
			remove();
			/*�̰� �ϴ� �����̱� �ѵ� new�� ����������̱� ������ 
			 * �ڽ��� null������ ���� ����� ���� ���ο��� �ϳ��� �Ҵ��ϰ� null�� �����ص� ������ 
			 * �׳� �̴�� �ֵ� �ɰͰ����� ��� 
			 */
		}
	}
	
	public boolean distance(MoveObject object){
		object.setOrigin();
		di = Math.sqrt(Math.pow(originX-object.originX, (float) 2) + Math.pow(originY-object.originY, (float) 2));
		if (di<object.width/2)
			return true;
		else
			return false;
	}
	@Override
	void damage(int power) {
		// TODO Auto-generated method stub
		
	}
}
