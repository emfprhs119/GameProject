package Frame;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import Listener.GotoPanel;
import Main.Project;

@SuppressWarnings("serial")
public class StopMenu extends Container {	//정지 메뉴
	JButton button;
	int x, y;
	private GameFrame gameFrame;
	StoryRoom room;
	StopMenu(GameFrame gameFrame) {	//생성자
		this.gameFrame=gameFrame;
		
		x = (Project.windowSize.x - 200) / 2;
		y = 200;
		button = new JButton("취소");
		button.setBounds(x, y + 40, 200, 40);
		button.addMouseListener(new VisibleFalse());
		add(button);
		button = new JButton("옵션");
		button.setBounds(x, y + 100, 200, 40);
		add(button);
		button = new JButton("재도전");
		button.setBounds(x, y + 160, 200, 40);
		button.addMouseListener(new VisibleFalse());
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					room.Initialization(room.difficulty, room.stage);
				}
			}
		});
		add(button);
		button = new JButton("스테이지");
		button.setBounds(x, y + 220, 200, 40);
		button.addMouseListener(new VisibleFalse());
		button.addMouseListener(new GotoPanel(gameFrame,"storyMode"));
		add(button);
		button = new JButton("메인 메뉴");
		button.setBounds(x, y + 280, 200, 40);
		button.addMouseListener(new VisibleFalse());
		button.addMouseListener(new GotoPanel(gameFrame,"mainMenu"));
		add(button);
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLayout(null);
	}

	class VisibleFalse extends MouseAdapter {	//정지메뉴 안보이게 해줌(취소시 게임 재개)
		public void mouseClicked(MouseEvent e) {
			room=gameFrame.storyRoom;
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (((JButton) e.getComponent()).getText() == "취소"){
					room.stop = false;
					room.requestFocus();
				}
				setVisible(false);
				//room.requestFocus();
			}
		}
	}
}
