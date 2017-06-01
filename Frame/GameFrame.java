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
public class GameFrame extends JFrame { // 틀이 되는 프라임
	MainMenu mainMenu; // 메인메뉴
	RoomCreate roomCreate; // 스테이지생성(미구현)
	StoryMode storyMode; // 스토리모드 스테이지 선택창
	StoryRoom storyRoom; // 스토리모드 게임룸
	Option mainOption, stopOption;
	CardLayout cardLayout; // PANEL방식 레이아웃
	Sound sound;
	ImageIcon icon;

	public GameFrame() { // 생성자
		init();
		panelSet();
		cardLayout.show(this.getContentPane(), "mainMenu");// 시작화면(새로운 시작화면 추가되면 여기서 변경)
		sound = new Sound("backSound.wav", Sound.Kind.background, true);
		sound.play();
		icon = new ImageIcon("resource/base/main.jpg");
		repaint();
	}

	private void panelSet() { // 각 창들을 생성후 레이아웃에 위치시킨다.
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

	private void init() { // 프레임 생성속성
		setTitle("GameFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 생성시 이게 없으면 종료시 자바가상머신이 종료가 되지 않음
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLocation((Project.monitorSize.x - Project.windowSize.x) / 2, (Project.monitorSize.y - Project.windowSize.y) / 2); // 정중앙
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		setVisible(true);
	}

	public void changePanel(String panel) { // 화면 전환 함수 추가시킨 패널 이름대로 이동
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
