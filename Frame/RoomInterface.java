package Frame;


import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Main.Project;

public class RoomInterface extends Container {
	JLabel stop;
	JLabel backGround;
	StopMenu stopMenu;
	StoryRoom room;
	RoomInterface(StoryRoom room) {
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLocation(0, 0); // â ��ġ
		this.setLayout(null);
		this.room=room;
		backGround=new JLabel();
		Project.setLabelImage(backGround, "background.png");	// ��׶��� ����
		backGround.setLocation(-8, -25);
		addStopMenu();	// �������� �޴� �߰� -������� �޼ҵ�� ���� ������
		add(backGround);	// �߰� ������ �ٲܽ� �յ� ������ �ֱ� ������ �� �� ��ü�� �� ���߿� �߰�
        
	}
	private void addStopMenu() {
		// TODO Auto-generated method stub
		stopMenu=new StopMenu(room,room.mainMenu,room.storymode);	// �Ͻ����� �޴�
		room.add(stopMenu);
		stopMenu.setVisible(false);
		stop = new JLabel("stop");	// �Ͻ����� �� �̹��� ���� ���
		Project.setLabelImage(stop,"pause.png");
		stop.setLocation(Project.windowSize.x-70-50,30);	//�̰ɷ� ��ġ�� �����
		add(stop);
		stop.addMouseListener(new MouseAdapter() {	// �Ͻ����� �ϳ��� ó��
      public void mouseClicked(MouseEvent e) {
      	if (e.getButton()==MouseEvent.BUTTON1){
      		stopMenu.setVisible(true);
      		room.stop=true;
      		room.player.horizon=0;
      		room.player.vertical=0;
      	}
      }
		});
	}
}

