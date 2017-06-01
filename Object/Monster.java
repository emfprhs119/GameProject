package Object;


import java.awt.Point;
//현재 몬스터는 block처리에서 버그가 있는데 지금 주요한 사항이 아니라서 나중에 고칠 예정
//-몬스터의 이동방식 때문인것으로 사료됨 플레이어는 버튼때문에 속도가 일정한데 몬스터는 그렇지 못함 일단 나중에 디버그





import Frame.StoryRoom;
import Main.Project;

public class Monster  extends MoveObject {	// 다양한 몬스터를 하나의 객체로 초기화를 다르게 해서 만들 예정 실제로 호출 안함
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
		hp=new Hp(100,room);// *난이도와 스테이지번호 -점차적으로 강해지는 적 완성!
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
	//여기 아래 부분을 인터페이스화해서 각각의 몬스터로 필수 정의화 해주고 싶은데 아쉽네 정확히 모르겠어
	 public void gotoXY() {	// 움직이고 싶은 장소
	}
	public void step() {	// 움직이는 장소 
	}
	public void hpMove(){
		hp.step((int) x, (int) y-30);
	}
//일단 공통으로 빼두었으나 반드시 필요한경우 각 개체로 다시 이동 가능
	public void move() {		
		// 몬스터 이동패턴 구현장소(//공격패턴은 다른 함수로 정의한다.)

		angle = (float) Math.sqrt(Math.pow(angleX, (float) 2) + Math.pow(angleY, (float) 2));
		x += speed * (angleX / angle);
		y += speed * (angleY / angle);
		//최외각 벽 -디폴트값
		if (getPoint().x < width / 2 + 50 || getPoint().x > Project.windowSize.x - width / 2 - 50 - 8)
			x -= speed * (angleX / angle);
		if (getPoint().y < height / 2 + 50 - 25 || getPoint().y > Project.windowSize.y - height / 2 - 50 - 30) 
			y -= speed * (angleY / angle);
		collider(); // 충돌확인
		if (flagX || flagY) {// 충돌
			x -= speed * (angleX / angle);
			y -= speed * (angleY / angle);
			gotoXY();
		}
		setLocation((int) x, (int) y);
	}
//마찬가지
	public void damage(int power) {		
		int damage;
		Project.sound("damage.wav", false);
		//여기서 몬스터의 방어력으로 공격력을 반감시킨다
		damage=power;
		hp.damage(damage);
		if (hp.getHp()<=0){
			itMonster=room.monsterList.iterator();	// 맵의 몬스터루프에서 제거
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
