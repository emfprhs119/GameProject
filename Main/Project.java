package Main;

import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import Frame.GameFrame;


public class Project {	//main�޼ҵ带 ���� ���� Ŭ����
	public static final Point windowSize=new Point(1280,800);	// âũ��
	public static final Point monitorSize=new Point((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());	// ����� �ػ�
	public static void setLabelImage(JLabel label, String img) { // �� �̹��� ���� �޼ҵ�(���� ���̹Ƿ� �������� ���� BUT ���� �ȵ�� ���а� ġ���;��)
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
