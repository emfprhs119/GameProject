package Frame;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Listener.GotoFrame;
import Main.Project;

public class StoryMode extends JFrame {
	JButton difficulty, back;
	JLabel label;
	JFrame mainMenu;	// 메인으로 돌아가기 위한 프레임
	StoryRoom gameRoom;	// 게임룸 생성
	StoryMode(JFrame mainMenu) {
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLocation((Project.screenSize.x - Project.windowSize.x) / 2, (Project.screenSize.y - Project.windowSize.y) / 2); // 정중앙
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainMenu = mainMenu;
		setLayout(null); // 레이아웃을 null로 해줘야 맘대로 배치가 가능
		initObject(); // 버튼과 라벨을 초기화 시켜주는 메소드
		addObject(); // 버튼과 라벨을 추가 시켜주는 메소드
		setVisible(true);
	}

	private void initObject() {	// 초기화
		gameRoom = new StoryRoom(mainMenu,this);	// 게임 룸 초기화
		
		difficulty = new JButton("normal");
		setButtonImage(difficulty, "normal.png"); // 디폴트 노멀 -스위치 하드
		difficulty.addMouseListener(new difficultyAction());
		difficulty.setLocation(100, 250);

		back = new JButton("back");
		back.addMouseListener(new GotoFrame(this, mainMenu));
		back.setBounds(15, 15, 100, 50);
		
	}

	private void addObject() {	// 추가
		add(difficulty);
		add(back);
		for(int i=1;i<5;i++){
			label = new JLabel(String.valueOf(i));
			Project.setLabelImage(label, "stage.png");
			label.addMouseListener(new GotoFrame(this,gameRoom));
			label.addMouseListener(new StageAction());
			label.setLocation(220+55*i, 250);
			add(label);
			}
		
	}

	private void setButtonImage(JButton button, String img) { // 이미지의 주소값만 넣어주면 알아서 척척 위치까지 잡아줌
		ImageIcon icon = new ImageIcon(img);
		button.setIcon(icon);
		int width = icon.getIconWidth() - 20;
		int height = icon.getIconHeight();
		button.setBounds((int) button.getX(), (int) button.getY(), (int) width, (int) height);
	}

	

	class difficultyAction extends MouseAdapter {		// difficulty 스위치 구현
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) { // 왼쪽버튼
				// ///////////////////////////////////////////////// level 스위치 구현
				if (((JButton) e.getComponent()).getLabel().equals("normal")) {
					((JButton) e.getComponent()).setLabel("hard");
					setButtonImage((JButton) e.getComponent(), "hard.png");
				} else if (((JButton) e.getComponent()).getLabel().equals("hard")) {
					((JButton) e.getComponent()).setLabel("normal");
					setButtonImage((JButton) e.getComponent(), "normal.png");
				}
			}
		}
	}
	class StageAction extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) { // 왼쪽버튼
				gameRoom.Initialization(difficulty.getLabel(),((JLabel) e.getComponent()).getText());	// 어떤걸 클릭했는지 어려움모드는 뭔지 여기서 판단하고 초기화함수 호출
			}
		}
	}
}
class Back extends Container {
	JButton button;
	Back(StoryRoom room) {
		setSize(1280/3, 800); // 창 크기
		setLocation(1280/3, 0); // 창 위치
		this.setLayout(null);
		button = new JButton("메인메뉴로");
		button.setBounds(700, 0, 20, 20);
		add(button);
		button.addMouseListener(new GotoFrame(room.mainMenu,room));
	}
}