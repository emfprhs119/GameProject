package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Block.Block1;
import Listener.Creator;
import Listener.MyListener;
import Main.Project;
import Object.BaseObject;
import Object.Block;
import Object.Bullet;
import Object.Explode;
import Object.Monster;
import Object.Player;
import Monster.*;

@SuppressWarnings("serial")
public class StoryRoom extends JPanel implements Runnable {
	public Player player; // 플레이어
	Monster monster; // 몬스터
	Block block; // 벽
	public Creator creator;
	public LinkedList<Monster> monsterList; // 몬스터 리스트
	public LinkedList<Monster> tmpMonsterList[]; // 몬스터 리스트
	public LinkedList<Bullet> bulletList; // 총알(공격판정 모션들) 리스트
	public LinkedList<Block> blockList; // 블럭 리스트
	public LinkedList<Explode> explodeList; // 블럭 리스트
	Iterator<?> it; // 반복자
	BaseObject other; // 기본 오브젝트
	public int difficulty; // 어려움모드,레벨
	public int stage;
	public boolean stop; // parse를 눌렀을때 모든 동작을 멈출 수 있게 해주는 불리안
	public int step; // 스레드 속도(낮을수록 고성능)
	Thread thread; // 스레드
	ImageIcon icon;
	GameFrame gameFrame;
	public int clearNum;
	public String clearObject;
	public String clearTime;
	public int nextTime;
	// private MissionStory missionStory;
	public boolean creatorRoom;
	public int time;
	private Scanner scan;
	public int stageInitNum;
 float imgX;
	public StoryRoom(GameFrame gameFrame) { // 생성자
		this();
		this.gameFrame = gameFrame;
		add(new RoomInterface(gameFrame));
		creatorRoom = false;
		/*
		 * missionStory = new MissionStory(this); add(missionStory);
		 */
	}

	public StoryRoom() { // 초기화
		step = 10; // 현재 최적설정(17)
		nextTime = 8000/step;
		imgX=0;
		setLayout(null); // 레이아웃을 null로 해줘야 맘대로 배치가 가능
		monsterList = new LinkedList<Monster>();
		bulletList = new LinkedList<Bullet>();
		blockList = new LinkedList<Block>();
		explodeList = new LinkedList<Explode>();
		stop = true; // 시작할때는 움직여야 하므로 - 준비 시작을 만들땐 true를 썼다가 준비 시작 끝 한다음 false로 변경해주기
		thread = new Thread(this);
		thread.start(); // 한번만 호출
		creator = new Creator(this);
		icon = new ImageIcon("resource/background/" + "background" + ".png");// 임시사용 삭제예정
		addKeyListener(new MyListener(this));
		addMouseMotionListener(new MyListener(this));
		addMouseListener(new MyListener(this));
		stageInitNum = 0;
		try {
			scan = new Scanner(new File("stage.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tmpMonsterList = new LinkedList[1];
		tmpMonsterList[0] = new LinkedList();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(icon.getImage(),(int)(imgX), 0, null);
		setOpaque(false);

		super.paintComponent(g);
		if (clearNum == 3) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g.setColor(Color.RED);
			g.drawString(time + "/" + (Integer.parseInt(clearTime) * step * 10), Project.windowSize.x / 2 - 50, 100);
		}

	}

	public void run() { // 각 오브젝트의 step처리 스레드
		while (true) {
			if (!stop) {
				time++;
				player.step();
				imgX-=0.16*step;
				imgX%=960;
				
				try {
					for (Monster monster : monsterList) {
						monster.step(); // 몬스터 step
					}
					for (Bullet bullet : bulletList)
						bullet.step(); // 총알 step
					for (Explode explode : explodeList)
						explode.step(); // 총알 step
				} catch (ConcurrentModificationException e) {
					// 중간에 제거되는 오브젝트때문에 일어나는 오류 그러니 그냥 쿨하게 무시하자
				}
				if (isClearStage(clearNum)) // 클리어 조건!
					clearStage();
				if (isFailStage())
					failStage();
				if (time >= nextTime && stage < gameFrame.roomCreate.stageSel.table.getRowCount()) {
					stageNext(++stage);
					time = 0;

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

	void failStage() {
		int choiceNum = 0;
		String[] choices = { "돌아가기", "다시하기", "이전 라운드" };
		choiceNum = JOptionPane.showOptionDialog(null, "Stage " + (stage + 1) + " 미션에 실패하셨습니다.", "미션실패",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		switch (choiceNum) {
		case 0:
			stop = true;
			gameFrame.changePanel("storyMode");
			break;
		case 1:
			Initialization(difficulty, stage);
			break;
		default:
			if (stage > 0)
				Initialization(difficulty, stage -= 1);
			else
				Initialization(difficulty, stage);
			break;
		}
	}

	void clearStage() {
		// 조건 달성시 스테이지 +1로 이동

		// stageInit();
		int choiceNum = 0;
		String[] choices = { "돌아가기", "다시하기", "다음 라운드" };
		choiceNum = JOptionPane.showOptionDialog(null, " 미션에 성공하셨습니다.", "미션성공",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		switch (choiceNum) {
		case 0:
			stop = true;
			gameFrame.changePanel("storyMode");
			break;
		case 1:
			Initialization(difficulty, stage);
			break;
		default:
			Initialization(difficulty, stage += 1);
			break;
		}
	}

	public void stageNext(int i) {
		if (stop == false) {
			if (i >= gameFrame.roomCreate.stageSel.table.getRowCount())
				return;
		}
		while (!tmpMonsterList[i].isEmpty())
			monsterList.add(tmpMonsterList[i].poll());
	}

	private boolean isClearStage(int clearNum) { // 스테이지 클리어
		// 스테이지별로 클리어 조건을 다르게 설정할 경우 여기서 판단할 수 있음
		// clearNum=1;//모든 몹을 다 잡는걸로 강제 설정
		switch (clearNum) {
		case 0:
			it = blockList.iterator();
			while (it.hasNext()) {
				other = (BaseObject) it.next();
				if (other.name.equals(clearObject))
					if (player.distanceTo(other) < 30)
						return true;
			}
			break;
		case 1:
			if (monsterList.isEmpty()) {
				boolean clear = true;
				for (int i = 0; i < gameFrame.roomCreate.stageSel.table.getRowCount(); i++) {
					if (!tmpMonsterList[i].isEmpty() || stage != gameFrame.roomCreate.stageSel.table.getRowCount())
						clear = false;
				}
				return clear;
			}
			break;
		case 2:
			Iterator<?> it; // 피격판정을 위한 반복자
			Monster other; // 피격대상
			it = monsterList.iterator();
			while (it.hasNext()) {
				other = (Monster) it.next();
				if (other.name.equals(clearObject)) {
					return false;
				}
			}
			return true;
			// break;
		case 3:
			// System.out.println(time+"/"+(Integer.parseInt(clearTime)*step));
			if (time >= Integer.parseInt(clearTime) * step) {
				stageNext(stage += 1);
				time = 0;
				return true;
			} else
				return false;
		default:
			return false;
		}
		return false;
	}

	private boolean isFailStage() { // 스테이지 실패
		// 스테이지별로 실패 조건을 다르게 설정할 경우 여기서 판단할 수 있음
		// 현재는 플레이어 hp가 0이하면 실패
		if (player.getHp() <= 0)
			return true;
		else
			return false;
	}

	public void Initialization(String difficulty, int stageNum) { // 난이도를 숫자로 변환후 초기화 호출
		int diff;
		if (difficulty.equals("normal"))
			diff = 0;
		else
			diff = 1;
		Initialization(diff, stageNum);
	}

	public void Initialization(int difficulty, int stage) { // 초기화
		stop = true;
		this.stage = stage;
		this.difficulty = difficulty;
		removeAllObject();
		stagesInit(stage);
		stageBackground(stage);
		// missionStory.Init(stage, clearNum);
		// missionStory.setVisible(true);
		stop = false;
		repaint();
		requestFocus();
		if (thread.getState() == Thread.State.NEW) // 스레드가 시작한적이 없다면
			thread.start(); // 한번만 호출
		time = 0;
	}

	private void stagesInit(int stage) {
		tmpMonsterList = new LinkedList[gameFrame.roomCreate.stageSel.table.getRowCount()];
		for (int i = 0; i < gameFrame.roomCreate.stageSel.table.getRowCount(); i++) {
			tmpMonsterList[i] = new LinkedList();
			stageInitNum = i;
			stageInit(i);
			// System.out.println(i);
		}
		stageNext(stage);
	}

	private void stageBackground(int stage) { // 스테이지별 백그라운드
		String background;
		switch (stage) {
		case 1:
			background = "background1";
			break;
		case 2:
			background = "background2";
			break;
		case 3:
			background = "background3";
			break;
		default:
			background = "background";
			break;
		}
		icon = new ImageIcon("resource/background/" + background + ".png");
	}

	public void stageInit(int stage) { // 스테이지 초기화(오브젝트 생성)
		String line;
		StringTokenizer st;
		int x, y, width, height, num;
		boolean scanFinish = false;
		try {
			Scanner scan = new Scanner(new File("stage.txt"));
			while (scan.hasNextLine()) {
				line = scan.nextLine();

				if (line.charAt(0) == 'S') // 스테이지 번호
					if (Integer.parseInt(line.split(" ")[1]) == stage)
						break;
			}
			while (scan.hasNextLine() && !scanFinish) {
				line = scan.nextLine();
				st = new StringTokenizer(line);
				switch (st.nextToken()) {
				case "S":
					scanFinish = true;
					break;
				case "P": // 플레이어 위치
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());
					if (player == null) {
						player = new Player(x, y, this);
						player.y = (Project.windowSize.y / 2 - player.icon.getIconHeight() / 2);
						player.move(100, Project.windowSize.y / 2 - player.icon.getIconHeight() / 2);
						add(player);
					}
					break;
				case "B": // 벽 종류,위치와 크기
					num = Integer.parseInt(st.nextToken());
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						width = Integer.parseInt(st.nextToken());
						height = Integer.parseInt(st.nextToken());
						block = creator.getBlock(num, new Point(x, y), new Point(width, height));
					}
					break;
				case "M":// 몬스터 종류,위치
					num = Integer.parseInt(st.nextToken());
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						monster = creator.getMonster(num, new Point(x, y));
						if (monster.x < 0 || monster.x > Project.windowSize.x || monster.y < 0 || monster.y > Project.windowSize.y) {
							monster.remove();
						}
						if (!creatorRoom)
							monster.x += Project.windowSize.x;
					}
					break;
				case "C":
					clearNum = Integer.parseInt(st.nextToken());
					clearObject = st.nextToken();
					clearTime = st.nextToken();
					break;
				}
			}
			if (player == null) {
				player = new Player(100, 100, this);
				player.y = (Project.windowSize.y / 2 - player.icon.getIconHeight() / 2);
				player.setLocation(100, (int) (Project.windowSize.y / 2 - player.height / 2));
				clearNum = 0;
				clearObject = "null";
				clearTime = "";
				add(player);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void removeAllObject() { // 모든 객체(플레이어,몬스터) 제거
		if (player != null) {
			player.remove();
			player = null;
		}
		while (!monsterList.isEmpty())
			monsterList.pop().remove();
		for (Bullet bullet : bulletList)
			bullet.remove();
		while (!blockList.isEmpty()) {
			Block block = blockList.pop();
			this.remove(block);
			blockList.remove(block);
		}
		bulletList = new LinkedList<Bullet>();
	}

}