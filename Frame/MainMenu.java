package Frame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.Player;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Main.Project;
import Listener.GotoPanel;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {	//���θ޴�
	Player p;
	JButton button;	//��ư�� ����
	int x,y;	//��ư ��ġ����
	ImageIcon icon;
	GameFrame  gameFrame;
	public MainMenu(GameFrame gameFrame) {	//������
		this.gameFrame=gameFrame;
		//icon=new ImageIcon("resource/base/main.jpg");
		x=(Project.windowSize.x-200)/2;
		y= 200;
		
		button = new JButton("���丮 ���");
		button.setBounds(x, y+40, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame,"storyMode"));
		add(button);
		
		button = new JButton("���� ���");
		button.setBounds(x, y+100, 200, 40);
		add(button);
		
		button = new JButton("�� ��");
		button.setBounds(x, y+160, 200, 40);
		add(button);
		
		button = new JButton("�� ��");
		button.setBounds(x, y+220, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame,"mainOption"));
		add(button);

		button = new JButton("�� ��");
		button.setBounds(x, y+280, 200, 40);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}});
		add(button);
		
		button = new JButton("�� ���� ������");
		button.setBounds(10, 10, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame,"roomCreate"));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				gameFrame.roomCreate.roomModify();
		}});
		add(button);
		setLayout(null);
	}
	public void paintComponent(Graphics g) {
		gameFrame.paintComponents(g);
	}
}
