package Object;

import java.awt.Point;

import Frame.StoryRoom;

@SuppressWarnings("serial")
public class Monster  extends MoveObject {	// 다양한 몬스터를 하나의 객체로 초기화를 다르게 해서 만들 예정 실제로 호출 안함
	
	public Hp hp;	//체력
	public int moveCount;	//이동 시간 간격
	public int attackCount;//공격 시간 간격
	public int count;
	public double gotoX,gotoY;	//이동하고자하는 장소
	
	public Monster(Point xy, StoryRoom room) {	//생성자
		super(room); 
		hp=new Hp(1000,room,false);// 난이도와 스테이지번호 -점차적으로 강해지는 적 완성!
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
//일단 공통으로 빼두었으나 반드시 필요한경우 각 개체로 다시 이동 가능
	public void move() {		//이동
		angle = (float) Math.sqrt(Math.pow(gotoX, (float) 2) + Math.pow(gotoY, (float) 2));
		x += speed * (gotoX / angle) * room.step;
		y += speed * (gotoY / angle) * room.step;
		collider(); // 충돌확인
		if (flagX!=0 || flagY!=0) {// 충돌
			if (flagX != 0) // x축 충돌
				x = flagX;
			if (flagY != 0) // y축 충돌
				y = flagY;// vertical*step;
		}
		hp.setLocation((int) x, (int) y-30);
		setLocation((int) x, (int) y);
	}
//마찬가지
	public void damage(int power) {		//데미지
		int damage;
		soundDamage.play();
		//여기서 몬스터의 방어력으로 공격력을 반감시킨다
		damage=power;
		hp.damage(damage);
		if (hp.getHp()<=0){
			remove();
		}
	}
	
	public void remove(){	//제거
		hp.remove();
		super.remove();
	}
}
