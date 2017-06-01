package Frame;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		button.addActionListener(new VisibleFalse());
		add(button);
		button = new JButton("옵션");
		button.setBounds(x, y + 100, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame,"stopOption"));
		add(button);
		button = new JButton("재도전");
		button.setBounds(x, y + 160, 200, 40);
		button.addActionListener(new  ActionListener() {
			public void actionPerformed(ActionEvent e) {
					room.Initialization(room.difficulty, room.stage);
			}
		});
		button.addActionListener(new VisibleFalse());
	
		add(button);
		button = new JButton("스테이지");
		button.setBounds(x, y + 220, 200, 40);
		button.addActionListener(new VisibleFalse());
		button.addActionListener(new GotoPanel(gameFrame,"storyMode"));
		add(button);
		button = new JButton("메인 메뉴");
		button.setBounds(x, y + 280, 200, 40);
		button.addActionListener(new VisibleFalse());
		button.addActionListener(new GotoPanel(gameFrame,"mainMenu"));
		add(button);
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLayout(null);
	}

	class VisibleFalse implements ActionListener {//정지메뉴 안보이게 해줌(취소시 게임 재개)

		public void actionPerformed(ActionEvent e) {	
			room=gameFrame.storyRoom;
				if (((JButton) e.getSource()).getText() == "취소" || ((JButton) e.getSource()).getText() == "재도전"){
					room.stop = false;
					room.requestFocus();
				}
				setVisible(false);
				//room.requestFocus();
		}
	}
}
