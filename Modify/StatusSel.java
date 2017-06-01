package Modify;
import javax.swing.JFrame;

import Main.Project;


public class StatusSel extends JFrame {
	public StatusSel() {
		setTitle("스테이터스");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setSize(Project.windowSize.x/5, Project.windowSize.y/3); // 창 크기
		setLocation((Project.screenSize.x - Project.windowSize.x) / 2-Project.windowSize.x/5, (Project.screenSize.y + Project.windowSize.y) / 2-Project.windowSize.y/3); // 정중앙
		setLayout(null); // 레이아웃을 null로 해줘야 맘대로 배치가 가능
	}
}
