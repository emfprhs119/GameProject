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
public class MainMenu extends JPanel {	//메인메뉴
	Player p;
	JButton button;	//버튼들 정의
	int x,y;	//버튼 위치지정
	ImageIcon icon;
	GameFrame  gameFrame;
	public MainMenu(GameFrame gameFrame) {	//생성자
		this.gameFrame=gameFrame;
		//icon=new ImageIcon("resource/base/main.jpg");
		x=(Project.windowSize.x-200)/2;
		y= 200;
		
		button = new JButton("스토리 모드");
		button.setBounds(x, y+40, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame,"storyMode"));
		add(button);
		
		button = new JButton("무한 모드");
		button.setBounds(x, y+100, 200, 40);
		add(button);
		
		button = new JButton("상 점");
		button.setBounds(x, y+160, 200, 40);
		add(button);
		
		button = new JButton("옵 션");
		button.setBounds(x, y+220, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame,"mainOption"));
		add(button);

		button = new JButton("종 료");
		button.setBounds(x, y+280, 200, 40);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}});
		add(button);
		
		button = new JButton("맵 구성 관리자");
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
