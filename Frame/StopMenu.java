package Frame;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import Listener.GotoPanel;
import Main.Project;

@SuppressWarnings("serial")
public class StopMenu extends Container {	//���� �޴�
	JButton button;
	int x, y;
	private GameFrame gameFrame;
	StoryRoom room;
	StopMenu(GameFrame gameFrame) {	//������
		this.gameFrame=gameFrame;
		
		x = (Project.windowSize.x - 200) / 2;
		y = 200;
		button = new JButton("���");
		button.setBounds(x, y + 40, 200, 40);
		button.addMouseListener(new VisibleFalse());
		add(button);
		button = new JButton("�ɼ�");
		button.setBounds(x, y + 100, 200, 40);
		add(button);
		button = new JButton("�絵��");
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
		button = new JButton("��������");
		button.setBounds(x, y + 220, 200, 40);
		button.addMouseListener(new VisibleFalse());
		button.addMouseListener(new GotoPanel(gameFrame,"storyMode"));
		add(button);
		button = new JButton("���� �޴�");
		button.setBounds(x, y + 280, 200, 40);
		button.addMouseListener(new VisibleFalse());
		button.addMouseListener(new GotoPanel(gameFrame,"mainMenu"));
		add(button);
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLayout(null);
	}

	class VisibleFalse extends MouseAdapter {	//�����޴� �Ⱥ��̰� ����(��ҽ� ���� �簳)
		public void mouseClicked(MouseEvent e) {
			room=gameFrame.storyRoom;
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (((JButton) e.getComponent()).getText() == "���"){
					room.stop = false;
					room.requestFocus();
				}
				setVisible(false);
				//room.requestFocus();
			}
		}
	}
}
