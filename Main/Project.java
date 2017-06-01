package Main;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Frame.MainMenu;


public class Project {
	public static final Point windowSize=new Point(1280,800);	// 창크기
	public static final Point screenSize=new Point((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());	// 모니터 해상도
	public static void setLabelImage(JLabel label, String img) { // 라벨 이미지 설정 메소드(자주 쓰이므로 전역으로 만듬)
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		label.setBounds((int) label.getX(), (int) label.getY(), (int) width, (int) height);
	}
	public static void sound(String file, boolean Loop){  //볼륨 기능을 추가 할 예정 
		//사운드재생용메소드
		//사운드파일과 루프여부를 받아들임
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if ( Loop) clip.loop(-1);
			//Loop 값이true면 사운드재생을무한반복시킵니다.
			//false면 한번만재생시킵니다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MainMenu menu;
		menu= new MainMenu();
	}

}
