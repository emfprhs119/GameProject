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
		setTitle("���θ޴�");
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
		
		
		button = new JButton("���丮 ���");
		button.setBounds(x, y+40, 200, 40);
		button.addMouseListener(new GotoFrame(this,story));
		add(button);
		
		button = new JButton("���� ���");
		button.setBounds(x, y+100, 200, 40);
		add(button);
		
		button = new JButton("�� ��");
		button.setBounds(x, y+160, 200, 40);
		add(button);
		
		button = new JButton("�� ��");
		button.setBounds(x, y+220, 200, 40);
		add(button);

		button = new JButton("�� ���� ������");
		button.setBounds(10, 10, 200, 40);
		button.addMouseListener(new GotoFrame(this,objectSel));
		button.addMouseListener(new GotoFrame(this,stageSel));
		button.addMouseListener(new GotoFrame(this,statusSel));
		button.addMouseListener(new GotoFrame(this,roomCreate));
		add(button);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// â ������ �̰� ������ ����� �ڹٰ���ӽ��� ���ᰡ ���� ����
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLocation((Project.screenSize.x - Project.windowSize.x) / 2, (Project.screenSize.y - Project.windowSize.y) / 2); // ���߾�
		setLayout(null);
		setVisible(true); // visible�� true�� ������ â�� ���� false �� �翬 â�� �Ⱥ��̰� ��
	}

}
