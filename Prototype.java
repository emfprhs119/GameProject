import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class Prototype extends JFrame {
	Player player;
	Monster monster;
	MoveObject obj;
	LinkedList<Monster> monsterList;
	public Prototype() {
		setSize(800, 800); // â ũ��
		setLocation(200, 200);	// â ��ġ
		this.setLayout(null);	//	���̾ƿ��� null�� ����� ����� ��ġ�� ����
		//////////////////�����ʵ� ���
		addKeyListener(new myListener(this));
		addMouseMotionListener(new myListener(this));
		addMouseListener(new myListener(this));
		//////////////////
		monsterList=new LinkedList<Monster>();
		addObject();
		setVisible(true); // visible�� true�� ������ â�� ���� false �� �翬 â�� �Ⱥ��̰� ��
	}

	public void addObject() {
		// ü���̳� �ɷ� �� �̱���
		
		player = new Player(this);
		
		add(player);	// �÷��̾� �߰�
		monster = new Monster(this);
		//add(monster);	// ���� �߰�
		for(int i=0;i<4;i++){
			monster = new Monster(this);
			monsterList.add(monster);
			add(monster);	
		}
	}

	public static void main(String[] args) {
		Prototype frame = new Prototype();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
////////////////////////////////////////////////////////////////////////////////////////////////////
//���콺 Ű���� �Է� ������ �����-�ٸ�Ŭ�󽺿��� Ű���尡 �ν����� �ʾƼ� ����ٰ� ���ƾ��� �ߴµ� ����� ã�� ���� Ŭ���� ������������ ������
class myListener extends MouseAdapter implements KeyListener,MouseMotionListener{
	Prototype p;
	public myListener(Prototype prototype) {
		p = prototype;
	}
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
			p.player.moveM(e.getKeyCode());
		}
	}
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {
			p.player.moveS(e.getKeyCode());
		}
	}
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == 'W' | e.getKeyCode() == 'S' | e.getKeyCode() == 'A' | e.getKeyCode() == 'D') {

			System.out.println((char) e.getKeyCode());
		}
	}
	public void mouseDragged(MouseEvent mouse) {
		// TODO Auto-generated method stub
		
		p.player.setAngle(mouse.getPoint());
	}
	public void mouseMoved(MouseEvent mouse) {
		// TODO Auto-generated method stub
		
		p.player.setAngle(mouse.getPoint());
	}
	public void mousePressed(MouseEvent mouse){
		
		p.add(new Bullet(p.player.getPoint(), mouse.getPoint(),p));
	}
}
