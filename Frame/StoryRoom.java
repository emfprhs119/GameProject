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
	
	public Player player;	//�÷��̾�
	Monster monster;	//����
	Block block;	//��
	public LinkedList<Monster> monsterList; // ���� ����Ʈ
	public LinkedList<Bullet> bulletList;	//�Ѿ�(�������� ��ǵ�) ����Ʈ
	public LinkedList<Block> blockList; // �� ����Ʈ
	int difficulty, stage; // �������,����
	public boolean stop; // parse�� �������� ��� ������ ���� �� �ְ� ���ִ� �Ҹ���
	public int step;	//������ �ӵ�(�������� ����)
	Thread thread;	//������
	
	public StoryRoom(GameFrame gameFrame) {	//������
		this();
		add(new RoomInterface(gameFrame));
		 // ��缳�� �޾ƿͼ� �ʱ�ȭ
	}

	public StoryRoom() {	//�ʱ�ȭ
		step=17;	//���� ��������(����ǻ��)
		setLayout(null); // ���̾ƿ��� null�� ����� ����� ��ġ�� ����
		monsterList = new LinkedList<Monster>();
		bulletList = new LinkedList<Bullet>();
		blockList = new LinkedList<Block>();
		stop = true; // �����Ҷ��� �������� �ϹǷ� - �غ� ������ ���鶩 true�� ��ٰ� �غ� ���� �� �Ѵ��� false�� �������ֱ�
		thread = new Thread(this);
		thread.start(); // �ѹ��� ȣ��
	}

	public void run() {	//�� ������Ʈ�� stepó�� ������
		while (true) {
			if (!stop) {
				player.step();
				try {
					for (Monster monster : monsterList)
						monster.step();	//���� step
					for (Bullet bullet : bulletList)
						bullet.step();	//�Ѿ� step
				} catch (ConcurrentModificationException e) {
					// �߰��� ���ŵǴ� ������Ʈ������ �Ͼ�� ���� �׷��� �׳� ���ϰ� ��������
				}
				if (clearStage()) { // Ŭ���� ����!
					Initialization(difficulty,stage+1);	//���� �޼��� �������� +1�� �̵�
					//���⿡ �������� �̵� �޴�â�� ��� ����(����,������������,������������,�絵�� �� ���� ����//Ŭ����Ÿ�� � ��������)
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

	private boolean clearStage() {	//�������� Ŭ����
		// ������������ Ŭ���� ������ �ٸ��� ������ ��� ���⼭ �Ǵ��� �� ����
		// ����� ���Ͱ� ���� ��������� Ŭ����
		if (monsterList.isEmpty())
			return true;
		else
			return false;
	}
	public void Initialization(String difficulty, String stage) { // ���̵��� ���ڷ� ��ȯ�� �ʱ�ȭ ȣ��
		int diff;
		if (difficulty.equals("normal"))
			diff = 0;
		else
			diff = 1;
		Initialization(diff, Integer.parseInt(stage));
	}

	public void Initialization(int difficulty, int stage) {	//�ʱ�ȭ
		stop = true;
		this.stage=stage;
		this.difficulty=difficulty;
		removeAllObject();
		stageInit();
		repaint();
		stop = false;
		requestFocus();
		if (thread.getState() == Thread.State.NEW) // �����尡 ���������� ���ٸ�
			thread.start(); // �ѹ��� ȣ��
	}

	private void stageInit() {	//�������� �ʱ�ȭ(������Ʈ ����)
		String line;
		StringTokenizer st;
		int x, y, width, height, kind;
		boolean scanFinish = false;
		try {
			Scanner scan = new Scanner(new File("stage.txt"));
			while (scan.hasNextLine()) {
				if (scan.nextLine().equals("S " + stage))	//�������� ��ȣ
					break;
			}
			while (scan.hasNextLine() && !scanFinish) {
				line = scan.nextLine();
				st = new StringTokenizer(line);
				switch (st.nextToken()) {
				case "S":
					scanFinish = true;
					break;
				case "P":	//�÷��̾� ��ġ
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());
					player = new Player(x, y, this);
					add(player);
					break;
				case "W":	//�� ����,��ġ�� ũ��
					kind = Integer.parseInt(st.nextToken());
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						width = Integer.parseInt(st.nextToken());
						height = Integer.parseInt(st.nextToken());
						block = new Block(x, y, width, height); // ���� �� ������ �ϳ��� �׳� �ϴ� ��
						blockList.add(block);
						add(block);
					}
					break;
				case "M"://���� ����,��ġ
					kind = Integer.parseInt(st.nextToken());
					
					while (st.hasMoreTokens()) {
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						switch (kind) { // ���⼭ ���� ������ ������ ����� �Ȱ���
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
			for(int i=0;i<4;i++){	//�ֿܰ� �� ����
				switch(i){
				case 0:block = new Block(0,-55, Project.windowSize.x+20, 100);break;//��
				case 1:block = new Block(0,Project.windowSize.y-95, Project.windowSize.x, 100);break; //��
				case 2:block = new Block(-41,0, 100, Project.windowSize.y);break; //��
				case 3:block = new Block(Project.windowSize.x-75,0, 10,Project.windowSize.y);break;//��
				}
				blockList.add(block);
				add(block);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void removeAllObject() { // ��� ��ü(�÷��̾�,����) ����
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