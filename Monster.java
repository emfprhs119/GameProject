import java.awt.Point;


public class Monster  extends MoveObject {	// 다양한 몬스터를 하나의 객체로 초기화를 다르게 해서 만들 예정
	Player player;
	int count;
	double gotoX,gotoY;
	Prototype windows;
	
	public Monster(Prototype p) {
		super(p); 
		
		x=250;
		y=330;
		speed = 0.5f;
		count=0;
		setImage("monster.png");	// 예시용(실제로는 변수를 받아와 해석해서 그에 맞는 몬스터 생성(ex)N001 몬스터A))
		t.start();
		gotoXY();
	}

	public void Moving() {
		//몬스터 이동패턴 구현장소(//공격패턴은 다른 함수로 정의한다.)
		count++;
		if(count>150)
		{
			gotoXY();
			count=0;
		}
		angle = (float)Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle);
		y += speed * (angleY / angle);
		setLocation((int) x, (int) y);
	}

	private void gotoXY() {	// 어느방향으로 가게 만들지 명시
		gotoX=(Math.random())*800;
		gotoY=(Math.random())*800;
		setAngle(new Point((int)gotoX,(int)gotoY));
	}
	

}
