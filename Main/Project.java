package Main;

import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import Frame.GameFrame;


public class Project {	//main메소드를 갖는 시작 클래스
	public static final Point windowSize=new Point(1280,800);	// 창크기
	public static final Point monitorSize=new Point((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());	// 모니터 해상도
	public static void setLabelImage(JLabel label, String img) { // 라벨 이미지 설정 메소드(종종 쓰이므로 전역으로 만듬 BUT 맘에 안드네 어디론가 치우고싶어라)
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		label.setBounds((int) label.getX(), (int) label.getY(), (int) width, (int) height);
	}
	
	public static void main(String[] args) {
		new GameFrame();
	}
}
