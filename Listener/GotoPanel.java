package Listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Frame.GameFrame;

public class GotoPanel extends MouseAdapter {	// ���콺Ŭ�� ��͸� ��� �̵��ϰ��� �ϴ� â���� �̵� 
	GameFrame gameFrame;		// ����â,������ �ϴ� â
	String panel;

	public GotoPanel(GameFrame gameFrame, String panel) {
		this.gameFrame = gameFrame;
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {	

		if (e.getButton() == MouseEvent.BUTTON1) {	//���ʹ�ư�� ó��
				gameFrame.changePanel(panel);
		}
	}
}