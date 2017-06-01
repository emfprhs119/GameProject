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
	public static final Point windowSize=new Point(1280,800);	// âũ��
	public static final Point screenSize=new Point((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());	// ����� �ػ�
	public static void setLabelImage(JLabel label, String img) { // �� �̹��� ���� �޼ҵ�(���� ���̹Ƿ� �������� ����)
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		label.setBounds((int) label.getX(), (int) label.getY(), (int) width, (int) height);
	}
	public static void sound(String file, boolean Loop){  //���� ����� �߰� �� ���� 
		//���������޼ҵ�
		//�������ϰ� �������θ� �޾Ƶ���
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if ( Loop) clip.loop(-1);
			//Loop ����true�� ������������ѹݺ���ŵ�ϴ�.
			//false�� �ѹ��������ŵ�ϴ�.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MainMenu menu;
		menu= new MainMenu();
	}

}
