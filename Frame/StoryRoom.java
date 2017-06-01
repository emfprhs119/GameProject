package Frame;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JPanel;
import Main.Project;
import Object.Block;
import Object.Bullet;
import Object.Monster;
import Object.Player;
import Monster.*;
@SuppressWarnings("serial")
public class StoryRoom extends JPanel implements Runnable {
	
	public Player player;	//플레이어
	Monster monster;	//몬스터
	Block block;	//벽
	public LinkedList<Monster> monsterList; // 몬스터 리스트
	public LinkedList<Bullet> bulletList;	//총알(공격판정 모션들) 리스트
	public LinkedList<Block> blockList; // 블럭 리스트
	int difficulty, stage; // 어려움모드,레벨
	public boolean stop; // parse를 눌렀을때 모든 동작을 멈출 수 있게 해주는 불리안
	public int step;	//스레드 속도(낮을수록 고성능)
	Thread thread;	//스레드
	
	public StoryRoom(GameFrame gameFrame) {	//생성자
		this();
		add(new RoomInterface(gameFrame));
		 // 사양설정 받아와서 초기화
	}

	public StoryRoom() {	//초기화
		step=17;	//현재 최적설정(내컴퓨터)
		setLayout(null); // 레이아웃을 null로 해줘야 맘대로 배치가 가능
		monsterList = new LinkedList<Monster>();
		bulletList = new LinkedList<Bullet>();
		blockList = new LinkedList<Block>();
		stop = true; // 시작할때는 움직여야 하므로 - 준비 시작을 만들땐 true를 썼다가 준비 시작 끝 한다음 false로 변경해주기
		thread = new Thread(this);
		thread.start(); // 한번만 호출
	}

	public void run() {	//각 오브젝트의 step처리 스레드
		while (true) {
			if (!stop) {
				player.step();
				try {
					for (Monster monster : monsterList)
						monster.step();	//몬스터 step
					for (Bullet bullet : bulletList)
						bullet.step();	//총알 step
				} catch (ConcurrentModificationException e) {
					// 중간에 제거되는 오브젝트때문에 일어나는 오류 그러니 그냥 쿨하게 무시하자
				}
				if (clearStage()) { // 클리어 조건!
					Initialization(difficulty,stage+1);	//조건 달성시 스테이지 +1로 이동
					//여기에 스테이지 이동 메뉴창을 띄울 예정(메인,스테이지선택,다음스테이지,재도전 등 넣을 예정//클리어타임 등도 넣을거임)
				}
			}
			try {
				if (step <= 0)
					break;
				Thread.sleep(step); // Step-반복의 시간을 결정 숫자가 작을수록 고성능 요구
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean clearStage() {	//스테이지 클리어
		// 스테이지별로 클리어 조건을 다르게 설정할 경우 여기서 판단할 수 있음
		// 현재는 몬스터가 전부 사라졌을시 클리어
		if (monsterList.isEmpty())
			return true;
		else
			return false;
	}
	public void Initialization(String difficulty, String stage) { // 난이도를 숫자로 변환후 초기화 호출
		int diff;
		if (difficulty.equals("normal"))
			diff = 0;
		else
			diff = 1;
		Initialization(diff, Integer.parseInt(stage));
	}

	public void Initialization(int difficulty, int stage) {	//초기화
		stop = true;
		this.stage=stage;
		this.difficulty=difficulty;
		removeAllObject();
		stageInit();
		repaint();
		stop = false;
		requestFocus();
		if (thread.getState() == Thread.State.NEW) // 스레드가 시작한적이 없다면
			thread.start(); // 한번만 호출
	}

	private void stageInit() {	//스테이지 초기화(오브젝트 생성)
		String line;
		StringTokenizer st;
		int x, y, width, height, kind;
		boolean scanFinish = false;
		try {
			Scanner scan = new Scanner(new File("stage.txt"));
			while (scan.hasNextLine()) {
				if (scan.nextLine().equals("S " + stage))	//스테이지 번호
					break;
			}
			while (scan.hasNextLine() && !scanFinish) {
				line = scan.nextLine();
				st = new StringTokenizer(line);
				switch (st.nextToken()) {
				case "S":
					scanFinish = true;
					break;
				case "P":	//플레이어 위치
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());
					player = new Player(x, y, this);
					add(player);
					break;
				case "W":	//벽 종류,위치와 크기
					kind = Integer.parseInt(st.nextToken());
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						width = Integer.parseInt(st.nextToken());
						height = Integer.parseInt(st.nextToken());
						block = new Block(x, y, width, height); // 아직 블럭 종류가 하나라서 그냥 일단 씀
						blockList.add(block);
						add(block);
					}
					break;
				case "M"://몬스터 종류,위치
					kind = Integer.parseInt(st.nextToken());
					
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						switch (kind) { // 여기서 몬스터 종류가 갈리지 현재는 안갈림
						case 1:monster = new Monster1(new Point(x, y), this);break;
						case 2:monster = new Monster2(new Point(x, y), this);break;
						case 3:monster = new Monster3(new Point(x, y), this);break;
						}
						monsterList.add(monster);
						add(monster);
					}
					break;
				}
			}
			for(int i=0;i<4;i++){	//최외각 벽 생성
				switch(i){
				case 0:block = new Block(0,-55, Project.windowSize.x+20, 100);break;//상
				case 1:block = new Block(0,Project.windowSize.y-95, Project.windowSize.x, 100);break; //하
				case 2:block = new Block(-41,0, 100, Project.windowSize.y);break; //좌
				case 3:block = new Block(Project.windowSize.x-75,0, 10,Project.windowSize.y);break;//우
				}
				blockList.add(block);
				add(block);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void removeAllObject() { // 모든 객체(플레이어,몬스터) 제거
		if (player != null) {
			player.remove();
		}
		while (!monsterList.isEmpty())
			monsterList.pop().remove();
		while (!bulletList.isEmpty())
			bulletList.pop().remove();
		while (!blockList.isEmpty()){
			Block block=blockList.pop();
			this.remove(block);
			blockList.remove(block);
		}
}
}