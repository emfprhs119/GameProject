package Listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Frame.GameFrame;

public class GotoPanel extends MouseAdapter {	// 마우스클릭 어뎁터를 사용 이동하고자 하는 창으로 이동 
	GameFrame gameFrame;		// 현재창,가고자 하는 창
	String panel;

	public GotoPanel(GameFrame gameFrame, String panel) {
		this.gameFrame = gameFrame;
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {	

		if (e.getButton() == MouseEvent.BUTTON1) {	//왼쪽버튼만 처리
				gameFrame.changePanel(panel);
		}
	}
}