package Object;


import java.awt.Point;
//���� ���ʹ� blockó������ ���װ� �ִµ� ���� �ֿ��� ������ �ƴ϶� ���߿� ��ĥ ����
//-������ �̵���� �����ΰ����� ���� �÷��̾�� ��ư������ �ӵ��� �����ѵ� ���ʹ� �׷��� ���� �ϴ� ���߿� �����





import Frame.StoryRoom;
import Main.Project;

public class Monster  extends MoveObject {	// �پ��� ���͸� �ϳ��� ��ü�� �ʱ�ȭ�� �ٸ��� �ؼ� ���� ���� ������ ȣ�� ����
	Player player;
	public Hp hp;
	public int MonsterNum;
	//public int hp;
	public int moveCount;
	public int attackCount;
	public double gotoX,gotoY;
	StoryRoom windows;
	
	public Monster(Point xy, StoryRoom room) {
		super(room); 
		MonsterNum=0;
		hp=new Hp(100,room);// *���̵��� ����������ȣ -���������� �������� �� �ϼ�!
		this.x=xy.x;
		this.y=xy.y;
		speed = 0;
		moveCount=0;
		thread.start();
		gotoXY();
	}
	public Monster() {
		super();
		// TODO Auto-generated constructor stub
	}
	//���� �Ʒ� �κ��� �������̽�ȭ�ؼ� ������ ���ͷ� �ʼ� ����ȭ ���ְ� ������ �ƽ��� ��Ȯ�� �𸣰ھ�
	 public void gotoXY() {	// �����̰� ���� ���
	}
	public void step() {	// �����̴� ��� 
	}
	public void hpMove(){
		hp.step((int) x, (int) y-30);
	}
//�ϴ� �������� ���ξ����� �ݵ�� �ʿ��Ѱ�� �� ��ü�� �ٽ� �̵� ����
	public void move() {		
		// ���� �̵����� �������(//���������� �ٸ� �Լ��� �����Ѵ�.)

		angle = (float) Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle);
		y += speed * (angleY / angle);
		//�ֿܰ� �� -����Ʈ��
		if (getPoint().x < width / 2 + 50 || getPoint().x > Project.windowSize.x - width / 2 - 50 - 8)
			x -= speed * (angleX / angle);
		if (getPoint().y < height / 2 + 50 - 25 || getPoint().y > Project.windowSize.y - height / 2 - 50 - 30) 
			y -= speed * (angleY / angle);
		collider(); // �浹Ȯ��
		if (flagX || flagY) {// �浹
			x -= speed * (angleX / angle);
			y -= speed * (angleY / angle);
			gotoXY();
		}
		setLocation((int) x, (int) y);
	}
//��������
	public void damage(int power) {		
		int damage;
		Project.sound("damage.wav", false);
		//���⼭ ������ �������� ���ݷ��� �ݰ���Ų��
		damage=power;
		hp.damage(damage);
		if (hp.getHp()<=0){
			itMonster=room.monsterList.iterator();	// ���� ���ͷ������� ����
			while(itMonster.hasNext()){
				if (this==itMonster.next()){
					itMonster.remove();
					break;
				}
			}
			remove();
		}
		// TODO Auto-generated method stub
		
	}
	public void remove(){
		hp.remove();
		super.remove();
	}
}
