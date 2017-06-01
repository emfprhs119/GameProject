package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Frame.GameFrame;

public class GotoPanel implements ActionListener {	// 마우스클릭 어뎁터를 사용 이동하고자 하는 창으로 이동 
	GameFrame gameFrame;		// 현재창,가고자 하는 창
	String panel;

	public GotoPanel(GameFrame gameFrame, String panel) {
		this.gameFrame = gameFrame;
		this.panel = panel;
	}
	public void actionPerformed(ActionEvent e) {
		gameFrame.changePanel(panel);
	}
}