package Frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;

import Main.Project;
import Listener.GotoFrame;
import Modify.ObjectSel;
import Modify.StageSel;
import Modify.StatusSel;

public class MainMenu extends JFrame {
	// JFrame room;
	JButton button;
	JFrame story,infinity,store,option,roomCreate,objectSel,stageSel,statusSel;;
	int x,y;
	public MainMenu() {
		setTitle("메인메뉴");
		x=(Project.windowSize.x-200)/2;
		y= 200;
		story=new StoryMode(this);
		story.setVisible(false);
		roomCreate=new RoomCreate(this);
		roomCreate.setVisible(false);
		objectSel=new ObjectSel();
		objectSel.setVisible(false);
		stageSel=new StageSel();
		stageSel.setVisible(false);
		statusSel=new StatusSel();
		statusSel.setVisible(false);
		
		
		button = new JButton("스토리 모드");
		button.setBounds(x, y+40, 200, 40);
		button.addMouseListener(new GotoFrame(this,story));
		add(button);
		
		button = new JButton("무한 모드");
		button.setBounds(x, y+100, 200, 40);
		add(button);
		
		button = new JButton("상 점");
		button.setBounds(x, y+160, 200, 40);
		add(button);
		
		button = new JButton("옵 션");
		button.setBounds(x, y+220, 200, 40);
		add(button);

		button = new JButton("맵 구성 관리자");
		button.setBounds(10, 10, 200, 40);
		button.addMouseListener(new GotoFrame(this,objectSel));
		button.addMouseListener(new GotoFrame(this,stageSel));
		button.addMouseListener(new GotoFrame(this,statusSel));
		button.addMouseListener(new GotoFrame(this,roomCreate));
		add(button);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 창 생성시 이게 없으면 종료시 자바가상머신이 종료가 되지 않음
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLocation((Project.screenSize.x - Project.windowSize.x) / 2, (Project.screenSize.y - Project.windowSize.y) / 2); // 정중앙
		setLayout(null);
		setVisible(true); // visible을 true로 만들어야 창이 보임 false 는 당연 창이 안보이게 됨
	}

}
