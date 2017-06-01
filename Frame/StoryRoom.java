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
	public LinkedList<Monster> monsterList; // ���� ����Ʈ
	public LinkedList<Block> blockList; // �� ����Ʈ
	JFrame mainMenu, storymode; // �̵��� �� �ִ� ���(���ΰ� ���丮���)
	int difficulty, stage; // �������,����
	public boolean stop; // parse�� �������� ��� ������ ���� �� �ְ� ���ִ� �Ҹ���

	public StoryRoom(JFrame mainMenu, JFrame storymode) { // ���ư� ��Ҹ� �����ϱ� ���� �ʿ�
		this();
		this.mainMenu = mainMenu;
		this.storymode = storymode;
		add(new RoomInterface(this));
		// ////////////////////////////////////////////////////////////////////������
		addKeyListener(new MyListener(this));
		addMouseMotionListener(new MyListener(this));
		addMouseListener(new MyListener(this));
	}

	public StoryRoom() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLocation((Project.screenSize.x - Project.windowSize.x) / 2, (Project.screenSize.y - Project.windowSize.y) / 2); // ���߾�
		setLayout(null); // ���̾ƿ��� null�� ����� ����� ��ġ�� ����
		monsterList = new LinkedList<Monster>();
		blockList = new LinkedList<Block>();
		// addObject();
		stop = true; // �����Ҷ��� �������� �ϹǷ� - �غ� ������ ���鶩 true�� ��ٰ� �غ� ���� �� �Ѵ��� false�� �������ֱ�
		setVisible(false); // visible�� true�� ������ â�� ���� false �� �翬 â�� �Ⱥ��̰� ��
		monster = new Monster1(new Point(0, 0), this); // ���׷� ���� �ӽ��ڵ�
		monster.remove();
	}

	/*public void addObject() { // ����뵵
		// �ɷ� �̱���

		// monster = new Monster(this);

		// add(monster); // ���� �߰�
		for (int i = 0; i < 2; i++) {
			monster = new Monster1(new Point(160, 400), this);

			monsterList.add(monster);
			add(monster);
		}

		// �� �߰� -�� ������ Ȱ��//�̰͵� ��� �����
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
		add(player); // �÷��̾� �߰�
	}
*/
	public void Initialization(String difficulty, String stage) { // ���ڿ��� �޾ƿͼ� ������ ��ȯ���ְ� �� ������ �ʱ�ȭ �Լ�
																																// ȣ��
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
					block = new Block(x, y);	//���� �� ������ �ϳ��� �׳� �ϴ� ��
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

	private void removeAllObject() { // ��� ��ü(�÷��̾�,����) ����
		// TODO Auto-generated method stub
		if (player != null) {
			player.remove();
		}
		while (!monsterList.isEmpty())
			monsterList.pop().remove();
	}

}
