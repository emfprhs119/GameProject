package Frame;

import java.awt.CardLayout;

import javax.swing.JFrame;

import Listener.MyListener;
import Main.Project;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {	//Ʋ�� �Ǵ� ������
	MainMenu mainMenu;	//���θ޴�
	RoomCreate roomCreate;	//������������(�̱���)
	StoryMode storyMode;	//���丮��� �������� ����â
	StoryRoom storyRoom;	//���丮��� ���ӷ�
	CardLayout cardLayout;	//PANEL��� ���̾ƿ�
	public GameFrame() {	//������
		init();
		panelSet();
		cardLayout.show(this.getContentPane(),"mainMenu");//����ȭ��(���ο� ����ȭ�� �߰��Ǹ� ���⼭ ����)
		storyRoom.addKeyListener(new MyListener(storyRoom));
		storyRoom.addMouseMotionListener(new MyListener(storyRoom));
		storyRoom.addMouseListener(new MyListener(storyRoom));
		
	}
	private void panelSet() {	//�� â���� ������ ���̾ƿ��� ��ġ��Ų��.
		mainMenu=new MainMenu(this);
		roomCreate=new RoomCreate(this);
		storyRoom=new StoryRoom(this);
		storyMode=new StoryMode(this);
		//stopMenu=new StopMenu(this);
		
		storyRoom.add(new RoomInterface(this));
		add("mainMenu",mainMenu);
		add("roomCreate",roomCreate);
		add("storyMode",storyMode);
		add("storyRoom",storyRoom);
	}
	private void init() {	//������ �����Ӽ�
		setTitle("GameFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â ������ �̰� ������ ����� �ڹٰ���ӽ��� ���ᰡ ���� ����
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLocation((Project.monitorSize.x - Project.windowSize.x) / 2, (Project.monitorSize.y - Project.windowSize.y) / 2); // ���߾�
		cardLayout=new CardLayout();
		setLayout(cardLayout);
		setVisible(true);
	}
	public void changePanel(String panel){	//ȭ�� ��ȯ �Լ� �߰���Ų �г� �̸���� �̵�
		cardLayout.show(this.getContentPane(),panel);
	}
}
