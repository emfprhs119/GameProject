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
		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLocation(0, 0); // 창 위치
		this.setLayout(null);
		this.room=room;
		backGround=new JLabel();
		Project.setLabelImage(backGround, "background.png");	// 백그라운드 설정
		backGround.setLocation(-8, -25);
		addStopMenu();	// 일지정지 메뉴 추가 -길어져서 메소드로 따로 빼놓음
		add(backGround);	// 추가 순서를 바꿀시 앞뒤 구분이 있기 때문에 맨 위 객체를 맨 나중에 추가
        
	}
	private void addStopMenu() {
		// TODO Auto-generated method stub
		stopMenu=new StopMenu(room,room.mainMenu,room.storymode);	// 일시정지 메뉴
		room.add(stopMenu);
		stopMenu.setVisible(false);
		stop = new JLabel("stop");	// 일시정지 라벨 이미지 수정 요망
		Project.setLabelImage(stop,"pause.png");
		stop.setLocation(Project.windowSize.x-70-50,30);	//이걸로 위치를 잡아줌
		add(stop);
		stop.addMouseListener(new MouseAdapter() {	// 일시정지 하나만 처리
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

