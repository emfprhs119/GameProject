package Frame;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import Main.Project;

@SuppressWarnings("serial")
public class RoomInterface extends Container {	//StoryRoom(����ȭ��)�� ���Ǵ� ���� stop��ư ����
	JLabel stop;
	StopMenu stopMenu;
	StoryRoom room;
	private GameFrame gameFrame;
	RoomInterface(GameFrame gameFrame) {	//������ (������ �������� �޾ƿ�)
		this.gameFrame=gameFrame;
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLocation(0, 0); // â ��ġ
		this.setLayout(null);
		addStopMenu();
	}
	private void addStopMenu() {
		// TODO Auto-generated method stub
		
		stopMenu=new StopMenu(gameFrame);	// �Ͻ����� �޴�
		this.add(stopMenu);
		stopMenu.setVisible(false);
		stop = new JLabel("stop");	// �Ͻ����� �� �̹��� ���� ���
		Project.setLabelImage(stop,"pause.png");
		stop.setLocation(Project.windowSize.x-70-50,30);	//�̰ɷ� ��ġ�� �����
		add(stop);
		stop.addMouseListener(new MouseAdapter() {	// �Ͻ����� �ϳ��� ó��
      public void mouseClicked(MouseEvent e) {
      	room=gameFrame.storyRoom;
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
