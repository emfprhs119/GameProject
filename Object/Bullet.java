package Object;

import java.awt.Point;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;

public class Bullet extends MoveObject {
	/*필요 알고리즘 
	 * 몬스터와 부딪쳤을시 제거(몬스터 체력 저하를 여기서 할까 몬스터쪽에서 할까)
	 */
	Iterator it; //피격판정을 위한 반복자
	MoveObject obj;
	Block obj1;
	double di;
	
	public Bullet(Point xy, Point gotoXY,StoryRoom p) {	// 플레이어와 몬스터의 공격액션을 담당(둘다 상속받아서 사용)
		super(p); // 상속 진짜 good!!!!!!!!!
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
			/*이거 일단 제거이긴 한데 new로 만들어진것이기 때문에 
			 * 자신을 null값으로 만들 방법이 없음 메인에서 하나씩 할당하고 null로 제거해도 되지만 
			 * 그냥 이대로 둬도 될것같은데 어떨까 
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
