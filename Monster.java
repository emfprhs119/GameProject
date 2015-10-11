import java.awt.Point;


public class Monster  extends MoveObject {	// �پ��� ���͸� �ϳ��� ��ü�� �ʱ�ȭ�� �ٸ��� �ؼ� ���� ����
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
		setImage("monster.png");	// ���ÿ�(�����δ� ������ �޾ƿ� �ؼ��ؼ� �׿� �´� ���� ����(ex)N001 ����A))
		t.start();
		gotoXY();
	}

	public void Moving() {
		//���� �̵����� �������(//���������� �ٸ� �Լ��� �����Ѵ�.)
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

	private void gotoXY() {	// ����������� ���� ������ ���
		gotoX=(Math.random())*800;
		gotoY=(Math.random())*800;
		setAngle(new Point((int)gotoX,(int)gotoY));
	}
	

}
