package Listener;


import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GotoFrame extends MouseAdapter {	// 마우스클릭 어뎁터를 사용 이동하고자 하는 창으로 이동 
	JFrame curr, next;		// 현재창,가고자 하는 창

	public GotoFrame(JFrame curr, JFrame next) {
		this.curr = curr;
		this.next = next;
	}

	public void mouseClicked(MouseEvent e) {	

		if (e.getButton() == MouseEvent.BUTTON1) {	//왼쪽버튼만 처리
				curr.setVisible(false);	// 현재창을 안보이게
				next.setVisible(true);	// 다음창을 보이게
		}
	}
}