package Frame;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import Main.Project;

@SuppressWarnings("serial")
public class RoomInterface extends Container {	//StoryRoom(게임화면)에 사용되는 배경과 stop버튼 정의
	JLabel stop;
	StopMenu stopMenu;
	StoryRoom room;
	private GameFrame gameFrame;
	RoomInterface(GameFrame gameFrame) {	//생성자 (마스터 프레임을 받아옴)
		this.gameFrame=gameFrame;
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLocation(0, 0); // 창 위치
		this.setLayout(null);
		addStopMenu();
	}
	private void addStopMenu() {
		// TODO Auto-generated method stub
		
		stopMenu=new StopMenu(gameFrame);	// 일시정지 메뉴
		this.add(stopMenu);
		stopMenu.setVisible(false);
		stop = new JLabel("stop");	// 일시정지 라벨 이미지 수정 요망
		Project.setLabelImage(stop,"pause.png");
		stop.setLocation(Project.windowSize.x-70-50,30);	//이걸로 위치를 잡아줌
		add(stop);
		stop.addMouseListener(new MouseAdapter() {	// 일시정지 하나만 처리
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
