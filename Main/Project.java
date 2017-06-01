package Main;

import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.GameFrame;

public class Project { // main메소드를 갖는 시작 클래스
	public static float Sound_Background = 0;
	public static float Sound_Effect = 0;
	public static final Point windowSize = new Point(1280, 800); // 창크기
	public static final Point monitorSize = new Point((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit
			.getDefaultToolkit().getScreenSize().getHeight()); // 모니터 해상도

	public static void setLabelImage(JLabel label, String img) { // 라벨 이미지 설정 메소드(종종 쓰이므로 전역으로 만듬 BUT
																																// 맘에 안드네 어디론가 치우고싶어라)
		ImageIcon icon = new ImageIcon("resource/base/"+img);
		label.setIcon(icon);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		label.setBounds((int) label.getX(), (int) label.getY(), (int) width, (int) height);
	}

	static java.util.Scanner config;

	public static void main(String[] args) {
		try {
			config = new java.util.Scanner(new File("config.ini"));
			config.nextLine();
			Sound_Background = Float.parseFloat(config.nextLine().split("Sound_Background=")[1]);
			Sound_Effect = Float.parseFloat(config.nextLine().split("Sound_Effect=")[1]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new GameFrame();
	}
}
