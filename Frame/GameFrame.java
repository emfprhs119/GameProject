package Frame;

import java.awt.CardLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.tools.JavaFileObject.Kind;

import Listener.MyListener;
import Main.Project;
import Main.Sound;

@SuppressWarnings("serial")
public class GameFrame extends JFrame { // Ʋ�� �Ǵ� ������
	MainMenu mainMenu; // ���θ޴�
	RoomCreate roomCreate; // ������������(�̱���)
	StoryMode storyMode; // ���丮��� �������� ����â
	StoryRoom storyRoom; // ���丮��� ���ӷ�
	Option mainOption, stopOption;
	CardLayout cardLayout; // PANEL��� ���̾ƿ�
	Sound sound;
	ImageIcon icon;

	public GameFrame() { // ������
		init();
		panelSet();
		cardLayout.show(this.getContentPane(), "mainMenu");// ����ȭ��(���ο� ����ȭ�� �߰��Ǹ� ���⼭ ����)
		sound = new Sound("backSound.wav", Sound.Kind.background, true);
		sound.play();
		icon = new ImageIcon("resource/base/main.jpg");
		repaint();
	}

	private void panelSet() { // �� â���� ������ ���̾ƿ��� ��ġ��Ų��.
		mainMenu = new MainMenu(this);
		roomCreate = new RoomCreate(this);
		storyRoom = new StoryRoom(this);
		storyMode = new StoryMode(this);
		mainOption = new Option(this, "mainMenu");
		stopOption = new Option(this, "storyRoom");

		storyRoom.add(new RoomInterface(this));
		add("mainMenu", mainMenu);
		add("roomCreate", roomCreate);
		add("storyMode", storyMode);
		add("storyRoom", storyRoom);
		add("mainOption", mainOption);
		add("stopOption", stopOption);
		// add("option",option);
	}

	private void init() { // ������ �����Ӽ�
		setTitle("GameFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â ������ �̰� ������ ����� �ڹٰ���ӽ��� ���ᰡ ���� ����
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLocation((Project.monitorSize.x - Project.windowSize.x) / 2, (Project.monitorSize.y - Project.windowSize.y) / 2); // ���߾�
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		setVisible(true);
	}

	public void changePanel(String panel) { // ȭ�� ��ȯ �Լ� �߰���Ų �г� �̸���� �̵�
		cardLayout.show(this.getContentPane(), panel);
		if(panel.equals("storyMode")){
			storyMode.addObject();
		}
		sound.stop();
		sound.play();
	}

	public void paintComponents(Graphics g) {
		g.drawImage(icon.getImage(), 0, 0,Project.windowSize.x,Project.windowSize.y, null);
	}
}
