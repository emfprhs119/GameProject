package Frame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Listener.MyListener;
import Main.Project;
import Object.Block;
import Object.Monster;
import Object.Player;
import Monster.*;

public class StoryRoom extends JFrame {
	public Player player;
	Monster monster;
	Block block;
	public LinkedList<Monster> monsterList; // 몬스터 리스트
	public LinkedList<Block> blockList; // 블럭 리스트
	JFrame mainMenu, storymode; // 이동할 수 있는 장소(메인과 스토리모드)
	int difficulty, stage; // 어려움모드,레벨
	public boolean stop; // parse를 눌렀을때 모든 동작을 멈출 수 있게 해주는 불리안

	public StoryRoom(JFrame mainMenu, JFrame storymode) { // 돌아갈 장소를 설정하기 위해 필요
		this();
		this.mainMenu = mainMenu;
		this.storymode = storymode;
		add(new RoomInterface(this));
		// ////////////////////////////////////////////////////////////////////리스너
		addKeyListener(new MyListener(this));
		addMouseMotionListener(new MyListener(this));
		addMouseListener(new MyListener(this));
	}

	public StoryRoom() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLocation((Project.screenSize.x - Project.windowSize.x) / 2, (Project.screenSize.y - Project.windowSize.y) / 2); // 정중앙
		setLayout(null); // 레이아웃을 null로 해줘야 맘대로 배치가 가능
		monsterList = new LinkedList<Monster>();
		blockList = new LinkedList<Block>();
		// addObject();
		stop = true; // 시작할때는 움직여야 하므로 - 준비 시작을 만들땐 true를 썼다가 준비 시작 끝 한다음 false로 변경해주기
		setVisible(false); // visible을 true로 만들어야 창이 보임 false 는 당연 창이 안보이게 됨
		monster = new Monster1(new Point(0, 0), this); // 버그로 인한 임시코드
		monster.remove();
	}

	/*public void addObject() { // 시험용도
		// 능력 미구현

		// monster = new Monster(this);

		// add(monster); // 몬스터 추가
		for (int i = 0; i < 2; i++) {
			monster = new Monster1(new Point(160, 400), this);

			monsterList.add(monster);
			add(monster);
		}

		// 블럭 추가 -맵 구성에 활용//이것도 잠시 시험용
		for (int i = 0; i < 3; i++) {
			block = new Block(150 + i * 64, 200);
			blockList.add(block);
			add(block);
		}
		for (int i = 0; i < 3; i++) {
			block = new Block(150 + i * 64, 450);
			blockList.add(block);
			add(block);
		}
		for (int i = 0; i < 3; i++) {
			block = new Block(150 + 4 * 64, 200 + 64 * i);
			blockList.add(block);
			add(block);
		}
		for (int i = 0; i < 3; i++) {
			block = new Block(150 - 64, 200 + 64 * i);
			blockList.add(block);
			add(block);
		}
		player = new Player(this);
		add(player); // 플레이어 추가
	}
*/
	public void Initialization(String difficulty, String stage) { // 문자열로 받아와서 정수로 변환해주고 그 정수로 초기화 함수
																																// 호출
		if (difficulty.equals("normal"))
			this.difficulty = 0;
		else
			this.difficulty = 1;
		this.stage = Integer.parseInt(stage);
		Initialization(this.difficulty, this.stage);
	}

	public void Initialization(int difficulty, int stage) {
		removeAllObject();
		// addObject(); //
		stageInit();
		stop=false;
	}

	private void stageInit() {
		String line;
		StringTokenizer st;
		int x,y,kind;
		boolean scanFinish=false;
		try {
			Scanner scan = new Scanner(new File("stage.txt"));
			while (scan.hasNextLine()) {
				if (scan.nextLine().equals("S " + stage))
					break;
			}
			while (scan.hasNextLine() && !scanFinish) {
				line = scan.nextLine();
				st = new StringTokenizer(line);
				switch (st.nextToken()) {
				case "S":
					scanFinish=true;
					break;
				case "P":
					x=Integer.parseInt(st.nextToken());
					y=Integer.parseInt(st.nextToken());
					player = new Player(x,y,this);
					add(player);
					break;
				case "W":
					kind=Integer.parseInt(st.nextToken());
					while(st.hasMoreTokens()){
					x=Integer.parseInt(st.nextToken());
					y=Integer.parseInt(st.nextToken());
					block = new Block(x, y);	//아직 블럭 종류가 하나라서 그냥 일단 씀
					blockList.add(block);
					add(block);
					}
					break;
				case "M":
					kind=Integer.parseInt(st.nextToken());
					switch(kind){
					case 0:
					}
					while(st.hasMoreTokens()){
					x=Integer.parseInt(st.nextToken());
					y=Integer.parseInt(st.nextToken());
					block = new Block(x, y);
					blockList.add(block);
					add(block);
					}
					break;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void removeAllObject() { // 모든 객체(플레이어,몬스터) 제거
		// TODO Auto-generated method stub
		if (player != null) {
			player.remove();
		}
		while (!monsterList.isEmpty())
			monsterList.pop().remove();
	}

}
