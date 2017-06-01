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
	public Player player; // �÷��̾�
	Monster monster; // ����
	Block block; // ��
	public Creator creator;
	public LinkedList<Monster> monsterList; // ���� ����Ʈ
	public LinkedList<Monster> tmpMonsterList[]; // ���� ����Ʈ
	public LinkedList<Bullet> bulletList; // �Ѿ�(�������� ��ǵ�) ����Ʈ
	public LinkedList<Block> blockList; // �� ����Ʈ
	public LinkedList<Explode> explodeList; // �� ����Ʈ
	Iterator<?> it;	//	�ݺ���
	BaseObject other; // �⺻ ������Ʈ
	public int difficulty; // �������,����
	public int stage;
	public boolean stop; // parse�� �������� ��� ������ ���� �� �ְ� ���ִ� �Ҹ���
	public int step; // ������ �ӵ�(�������� ����)
	Thread thread; // ������
	ImageIcon icon;
	GameFrame gameFrame;
	public int clearNum;
	public String clearObject;
	public String clearTime;
	//private MissionStory missionStory;
	public boolean creatorRoom;
	public int time;
	private Scanner scan;
	public int stageInitNum;

	public StoryRoom(GameFrame gameFrame) { // ������
		this();
		this.gameFrame = gameFrame;
		add(new RoomInterface(gameFrame));
		creatorRoom=false;
		/*missionStory = new MissionStory(this);
		add(missionStory);*/
	}

	public StoryRoom() { // �ʱ�ȭ
		step = 17; // ���� ��������(����ǻ��)
		setLayout(null); // ���̾ƿ��� null�� ����� ����� ��ġ�� ����
		monsterList = new LinkedList<Monster>();
		bulletList = new LinkedList<Bullet>();
		blockList = new LinkedList<Block>();
		explodeList = new LinkedList<Explode>();
		stop = true; // �����Ҷ��� �������� �ϹǷ� - �غ� ������ ���鶩 true�� ��ٰ� �غ� ���� �� �Ѵ��� false�� �������ֱ�
		thread = new Thread(this);
		thread.start(); // �ѹ��� ȣ��
		creator = new Creator(this);
		icon = new ImageIcon("resource/background/" + "background" + ".png");// �ӽû�� ��������
		addKeyListener(new MyListener(this));
		addMouseMotionListener(new MyListener(this));
		addMouseListener(new MyListener(this));
		stageInitNum=0;
		try {
			scan = new Scanner(new File("stage.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tmpMonsterList=new LinkedList[1];
		tmpMonsterList[0]=new LinkedList();
	}
	public void paintComponent(Graphics g) {
		g.drawImage(icon.getImage(), 0, 0, null);
		setOpaque(false);
		
		super.paintComponent(g);
		if (clearNum==3){
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		g.setColor(Color.RED);
		g.drawString(time+"/"+(Integer.parseInt(clearTime)*step*10), Project.windowSize.x/2-50, 100);
		}
		
	}

	public void run() { // �� ������Ʈ�� stepó�� ������
		while (true) {
			if (!stop) {
				time++;
				player.step();
				try {
					for (Monster monster : monsterList){
						monster.step(); // ���� step
					}
					for (Bullet bullet : bulletList)
						bullet.step(); // �Ѿ� step
					for (Explode explode : explodeList)
						explode.step(); // �Ѿ� step
				} catch (ConcurrentModificationException e) {
					// �߰��� ���ŵǴ� ������Ʈ������ �Ͼ�� ���� �׷��� �׳� ���ϰ� ��������
				}
				if (isClearStage(clearNum)) // Ŭ���� ����!
					clearStage();
				if (isFailStage())
					failStage();
				if (time>=Integer.parseInt(clearTime)*step){
					stageNext(stage += 1);
					time=0;
				}
			}
			try {
				if (step <= 0)
					break;
				Thread.sleep(step); // Step-�ݺ��� �ð��� ���� ���ڰ� �������� ���� �䱸
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	void failStage() {
		int choiceNum = 0;
		String[] choices = { "���ư���", "�ٽ��ϱ�", "���� ����" };
		choiceNum = JOptionPane.showOptionDialog(null, "Stage " + (stage + 1) + " �̼ǿ� �����ϼ̽��ϴ�.", "�̼ǽ���",
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
			if (stage>0)
			Initialization(difficulty, stage -= 1);
			else
				Initialization(difficulty, stage);
			break;
		}
	}

	void clearStage() {
		// ���⿡ �������� �̵� �޴�â�� ��� ����(����,������������,������������,�絵�� �� ���� ����//Ŭ����Ÿ�� � ��������)
		// ���� �޼��� �������� +1�� �̵�
		
		//stageInit();
		int choiceNum = 0;
		String[] choices = { "���ư���", "�ٽ��ϱ�", "���� ����" };
		choiceNum = JOptionPane.showOptionDialog(null, "Stage " + (stage + 1) + " �̼ǿ� �����ϼ̽��ϴ�.", "�̼Ǽ���",
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
		if (i>1)
			return;
		while(!tmpMonsterList[i].isEmpty())
			monsterList.add(tmpMonsterList[i].poll());
	}

	private boolean isClearStage(int clearNum) { // �������� Ŭ����
		// ������������ Ŭ���� ������ �ٸ��� ������ ��� ���⼭ �Ǵ��� �� ����
		clearNum=1;//��� ���� �� ��°ɷ� ���� ����
		clearTime="25";
		switch (clearNum) {
		case 0:
			it = blockList.iterator();
			while (it.hasNext()) {
				other = (BaseObject) it.next();
				if (other.name.equals(clearObject)) 
					if (player.distanceTo(other)<30)
						return true;
			}
			break;
		case 1:
			if (monsterList.isEmpty()) {
				boolean clear=true;
				for(int i=0;i<gameFrame.roomCreate.stageSel.table.getRowCount();i++){
					if (!tmpMonsterList[i].isEmpty())
						clear=false;
				}
				return clear;
			}
			break;
		case 2:
			Iterator<?> it; // �ǰ������� ���� �ݺ���
			Monster other; // �ǰݴ��
			it = monsterList.iterator();
			while (it.hasNext()) {
				other = (Monster) it.next();
				if (other.name.equals(clearObject)) {
					return false;
				}
			}
			return true;
			//break;
		case 3:
			//System.out.println(time+"/"+(Integer.parseInt(clearTime)*step));
			if (time>=Integer.parseInt(clearTime)*step){
				stageNext(stage += 1);
				time=0;
				return true;
			}
			else
					return false;
		default:
			return false;
		}
		return false;
	}

	private boolean isFailStage() { // �������� ����
		// ������������ ���� ������ �ٸ��� ������ ��� ���⼭ �Ǵ��� �� ����
		// ����� �÷��̾� hp�� 0���ϸ� ����
		if (player.getHp() <= 0)
			return true;
		else
			return false;
	}

	public void Initialization(String difficulty, int stageNum) { // ���̵��� ���ڷ� ��ȯ�� �ʱ�ȭ ȣ��
		int diff;
		if (difficulty.equals("normal"))
			diff = 0;
		else
			diff = 1;
		Initialization(diff, stageNum);
	}

	public void Initialization(int difficulty, int stage) { // �ʱ�ȭ
		stop = true;
		this.stage = stage;
		this.difficulty = difficulty;
		removeAllObject();
		stagesInit(stage);
		stageBackground(stage);
		//missionStory.Init(stage, clearNum);
		//missionStory.setVisible(true);
		stop=false;
		repaint();
		requestFocus();
		if (thread.getState() == Thread.State.NEW) // �����尡 ���������� ���ٸ�
			thread.start(); // �ѹ��� ȣ��
		time=0;
	}

	private void stagesInit(int stage) {
		tmpMonsterList=new LinkedList[gameFrame.roomCreate.stageSel.table.getRowCount()];
		for(int i=0;i<gameFrame.roomCreate.stageSel.table.getRowCount();i++){
			tmpMonsterList[i]=new LinkedList();
			stageInitNum=i;
			stageInit(i);
			//System.out.println(i);
		}
		stageNext(stage);
	}

	private void stageBackground(int stage) { // ���������� ��׶���
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

	public void stageInit(int stage) { // �������� �ʱ�ȭ(������Ʈ ����)
		String line;
		StringTokenizer st;
		int x, y, width, height, num;
		boolean scanFinish = false;
		try {
			Scanner scan = new Scanner(new File("stage.txt"));
			while (scan.hasNextLine()) {
				line = scan.nextLine();

				if (line.charAt(0) == 'S') // �������� ��ȣ
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
				case "P": // �÷��̾� ��ġ
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());
					//player = new Player(x, y, this);
					//add(player);
					break;
				case "B": // �� ����,��ġ�� ũ��
					num = Integer.parseInt(st.nextToken());
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						width = Integer.parseInt(st.nextToken());
						height = Integer.parseInt(st.nextToken());
						block = creator.getBlock(num, new Point(x, y), new Point(width, height));
					}
					break;
				case "M":// ���� ����,��ġ
					num = Integer.parseInt(st.nextToken());
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						monster=creator.getMonster(num, new Point(x, y));
						if (monster.x < 0 || monster.x > Project.windowSize.x  || monster.y < 0 || monster.y > Project.windowSize.y ){
							monster.remove();
						}
						if (!creatorRoom)
							monster.x+=Project.windowSize.x;
					}
					break;
				case "C":
					clearNum = Integer.parseInt(st.nextToken());
					clearObject = st.nextToken();
					clearTime=st.nextToken();
					break;
				}
			}
			if (player == null) {
				player = new Player(100,100, this);
				player.y=(Project.windowSize.y/2-player.icon.getIconHeight()/2);
				//player.setLocation(100, (int) (Project.windowSize.y/2-player.height/2));
				clearNum = 0;
				clearObject = "null";
				clearTime="";
				add(player);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void removeAllObject() { // ��� ��ü(�÷��̾�,����) ����
		if (player != null) {
			player.remove();
			player = null;
		}
		while (!monsterList.isEmpty())
			monsterList.pop().remove();
		for (Bullet bullet:bulletList)
			bullet.remove();
		while (!blockList.isEmpty()) {
			Block block = blockList.pop();
			this.remove(block);
			blockList.remove(block);
		}
		bulletList=new LinkedList<Bullet>();
	}
	
}