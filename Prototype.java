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
		setSize(800, 800); // 창 크기
		setLocation(200, 200);	// 창 위치
		this.setLayout(null);	//	레이아웃을 null로 해줘야 맘대로 배치가 가능
		//////////////////리스너들 등록
		addKeyListener(new myListener(this));
		addMouseMotionListener(new myListener(this));
		addMouseListener(new myListener(this));
		//////////////////
		monsterList=new LinkedList<Monster>();
		addObject();
		setVisible(true); // visible을 true로 만들어야 창이 보임 false 는 당연 창이 안보이게 됨
	}

	public void addObject() {
		// 체력이나 능력 등 미구현
		
		player = new Player(this);
		
		add(player);	// 플레이어 추가
		monster = new Monster(this);
		//add(monster);	// 몬스터 추가
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
//마우스 키보드 입력 리스너 제어부-다른클라스에선 키보드가 인식하지 않아서 여기다가 몰아쓰긴 했는데 방법을 찾아 따로 클래스 정의해줬으면 좋겠음
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
