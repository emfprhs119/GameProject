package Frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

import Main.Project;
import Listener.GotoPanel;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {	//메인메뉴
	JButton button;	//버튼들 정의
	int x,y;	//버튼 위치지정
	public MainMenu(GameFrame gameFrame) {	//생성자
		x=(Project.windowSize.x-200)/2;
		y= 200;
		
		button = new JButton("스토리 모드");
		button.setBounds(x, y+40, 200, 40);
		button.addMouseListener(new GotoPanel(gameFrame,"storyMode"));
		add(button);
		
		button = new JButton("무한 모드");
		button.setBounds(x, y+100, 200, 40);
		add(button);
		
		button = new JButton("상 점");
		button.setBounds(x, y+160, 200, 40);
		add(button);
		
		button = new JButton("옵 션");
		button.setBounds(x, y+220, 200, 40);
		add(button);

		button = new JButton("종 료");
		button.setBounds(x, y+280, 200, 40);
		button.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
			System.exit(0);
		}});
		add(button);
		
		button = new JButton("맵 구성 관리자");
		button.setBounds(10, 10, 200, 40);
		add(button);
		setLayout(null);
		
	}

}
