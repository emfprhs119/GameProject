package Listener;


import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GotoFrame extends MouseAdapter {	// ���콺Ŭ�� ��͸� ��� �̵��ϰ��� �ϴ� â���� �̵� 
	JFrame curr, next;		// ����â,������ �ϴ� â

	public GotoFrame(JFrame curr, JFrame next) {
		this.curr = curr;
		this.next = next;
	}

	public void mouseClicked(MouseEvent e) {	

		if (e.getButton() == MouseEvent.BUTTON1) {	//���ʹ�ư�� ó��
				curr.setVisible(false);	// ����â�� �Ⱥ��̰�
				next.setVisible(true);	// ����â�� ���̰�
		}
	}
}