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
		button.addActionListener(new VisibleFalse());
		add(button);
		button = new JButton("�ɼ�");
		button.setBounds(x, y + 100, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame,"stopOption"));
		add(button);
		button = new JButton("�絵��");
		button.setBounds(x, y + 160, 200, 40);
		button.addActionListener(new  ActionListener() {
			public void actionPerformed(ActionEvent e) {
					room.Initialization(room.difficulty, room.stage);
			}
		});
		button.addActionListener(new VisibleFalse());
	
		add(button);
		button = new JButton("��������");
		button.setBounds(x, y + 220, 200, 40);
		button.addActionListener(new VisibleFalse());
		button.addActionListener(new GotoPanel(gameFrame,"storyMode"));
		add(button);
		button = new JButton("���� �޴�");
		button.setBounds(x, y + 280, 200, 40);
		button.addActionListener(new VisibleFalse());
		button.addActionListener(new GotoPanel(gameFrame,"mainMenu"));
		add(button);
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLayout(null);
	}

	class VisibleFalse implements ActionListener {//�����޴� �Ⱥ��̰� ����(��ҽ� ���� �簳)

		public void actionPerformed(ActionEvent e) {	
			room=gameFrame.storyRoom;
				if (((JButton) e.getSource()).getText() == "���" || ((JButton) e.getSource()).getText() == "�絵��"){
					room.stop = false;
					room.requestFocus();
				}
				setVisible(false);
				//room.requestFocus();
		}
	}
}
