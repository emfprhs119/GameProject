package Frame;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import Listener.GotoFrame;
import Main.Project;

public class StopMenu extends Container {
	JButton button;
	int x, y;
	StoryRoom room;

	StopMenu(StoryRoom room, JFrame mainMenu, JFrame storymode) {
		this.room = room;
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
					room.Initialization(room.difficulty, room.level);
				}
			}
		});
		add(button);
		button = new JButton("스테이지");
		button.setBounds(x, y + 220, 200, 40);
		button.addMouseListener(new VisibleFalse());
		button.addMouseListener(new GotoFrame(room, storymode));
		add(button);
		button = new JButton("메인 메뉴");
		button.setBounds(x, y + 280, 200, 40);
		button.addMouseListener(new VisibleFalse());
		button.addMouseListener(new GotoFrame(room, mainMenu));
		add(button);
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLayout(null);
	}

	class VisibleFalse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				setVisible(false);
				room.stop = false;
				room.requestFocus();
			}
		}
	}
}
