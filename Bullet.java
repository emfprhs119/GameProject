import java.awt.Point;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bullet extends MoveObject {
	/*필요 알고리즘 
	 * 몬스터와 부딪쳤을시 제거(몬스터 체력 저하를 여기서 할까 몬스터쪽에서 할까)
	 */
	Iterator it; //피격판정을 위한 반복자
	MoveObject obj;
	double di;
	public Bullet(Point xy, Point point,Prototype p) {
		super(p); // 상속 진짜 good!!!!!!!!!
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
			/*이거 일단 제거이긴 한데 new로 만들어진것이기 때문에 
			 * 자신을 null값으로 만들 방법이 없음 메인에서 하나씩 할당하고 null로 제거해도 되지만 
			 * 그냥 이대로 둬도 될것같은데 어떨까 
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
	
	public void run() {	// 오버라이딩
		while (true) {
			Moving();
			attackedDecision();
			try {
				Thread.sleep(2); // Step-반복의 시간을 결정 숫자가 작을수록 고성능 요구
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
