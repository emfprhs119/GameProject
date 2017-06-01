package Object;

import java.awt.Point;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class Monster  extends MoveObject {	// �پ��� ���͸� �ϳ��� ��ü�� �ʱ�ȭ�� �ٸ��� �ؼ� ���� ���� ������ ȣ�� ����
	
	public Hp hp;	//ü��
	public int moveCount;	//�̵� �ð� ����
	public int attackCount;//���� �ð� ����
	public int count;
	public double gotoX,gotoY;	//�̵��ϰ����ϴ� ���
	
	public Monster(Point xy, StoryRoom room) {	//������
		super(room); 
		hp=new Hp(1000,room,false);// ���̵��� ����������ȣ -���������� �������� �� �ϼ�!
		this.x=xy.x;
		this.y=xy.y;
		count=0;
		gotoX=1;
		gotoY=1;
		moveCount=0;
		setAngle(room.player.getPoint());
	}
	public void step() {	
		colliderSpeed = speed;
		count++;
		count%=10000;
		setOrigin();
		moveStep();
		attackStep();
	}
	public void attackStep() {
	}
	public void moveStep() {
	}
	public void hpMove(){
	}
//�ϴ� �������� ���ξ����� �ݵ�� �ʿ��Ѱ�� �� ��ü�� �ٽ� �̵� ����
	public void move() {		//�̵�
		angle = (float) Math.sqrt(Math.pow(gotoX, (float) 2) + Math.pow(gotoY, (float) 2));
		x += speed * (gotoX / angle) * room.step;
		y += speed * (gotoY / angle) * room.step;
		collider(); // �浹Ȯ��
		if (flagX!=0 || flagY!=0) {// �浹
			if (flagX != 0) // x�� �浹
				x = flagX;
			if (flagY != 0) // y�� �浹
				y = flagY;// vertical*step;
		}
		hp.setLocation((int) x, (int) y-30);
		setLocation((int) x, (int) y);
	}
//��������
	public void damage(int power) {		//������
		int damage;
		soundDamage.play();
		//���⼭ ������ �������� ���ݷ��� �ݰ���Ų��
		damage=power;
		hp.damage(damage);
		if (hp.getHp()<=0){
			remove();
		}
	}
	
	public void remove(){	//����
		hp.remove();
		super.remove();
	}
}
