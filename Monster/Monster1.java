package Monster;

import java.awt.Point;

import Frame.*;
import Main.Project;
import Object.BulletOfMonster;
import Object.Hp;
import Object.Monster;
import Object.MoveObject;

public class Monster1 extends Monster { // 이름 가명칭-이름을 제대로 지어줍시다! 몬스터1이라니! 어떻게 알아먹나요!

	public Monster1(Point xy,StoryRoom room) {
		super(xy,room);
		MonsterNum = 1;
		
		setImage("monster.png");
		speed = 0.5f; // 이건 좀 애매한데  속도라..
		// TODO Auto-generated constructor stub
		
	}
	
	public Monster1() {
		// TODO Auto-generated constructor stub
	}

	public void step(){
		moveCount++;
		attackCount++;
		if (moveCount > 150) {
			gotoXY();
			moveCount = 0;
		}
		if (attackCount > 500) {
			attack();
			attackCount = 0;
		}
		move();
		hpMove();
	}
	
	public void gotoXY() { // 어느방향으로 가게 만들지 명시	// 패턴 연구 필요!!!!!
		/*gotoX = x+(Math.random()) * 800-400;
		gotoY = y+(Math.random()) * 800-400;*/
		switch((int)Math.round(Math.random()*100)%8){
		case 0:gotoX=x-300;gotoY=y-300;break;
		case 1:gotoX=x+300;gotoY=y+300;break;
		case 2:gotoX=x+300;gotoY=y-300;break;
		case 3:gotoX=x-300;gotoY=y+300;break;
		case 4:gotoX=x-0;gotoY=y-300;break;
		case 5:gotoX=x-300;gotoY=y-0;break;
		case 6:gotoX=x-0;gotoY=y+300;break;
		case 7:gotoX=x+300;gotoY=y-0;break;
		}
		setAngle(new Point((int) gotoX, (int) gotoY));
	}

	
	
	public void attack() {
			Project.sound("attack.wav", false);
			//double bulltX = room.player.getPoint().x;
			//double bulltY = room.player.getPoint().y + (Math.random() * 30 + 10);	//랜덤 나중에 이용해봐야지	이곳도 패턴연구 필요
			room.add(new BulletOfMonster(getPoint(), room.player.getPoint(), room));
	}
	
	
}
