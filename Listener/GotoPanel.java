package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Frame.GameFrame;

public class GotoPanel implements ActionListener {	// ���콺Ŭ�� ��͸� ��� �̵��ϰ��� �ϴ� â���� �̵� 
	GameFrame gameFrame;		// ����â,������ �ϴ� â
	String panel;

	public GotoPanel(GameFrame gameFrame, String panel) {
		this.gameFrame = gameFrame;
		this.panel = panel;
	}
	public void actionPerformed(ActionEvent e) {
		gameFrame.changePanel(panel);
	}
}