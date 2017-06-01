package Frame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

import Main.Project;
import Listener.GotoPanel;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {	//���θ޴�
	JButton button;	//��ư�� ����
	int x,y;	//��ư ��ġ����
	public MainMenu(GameFrame gameFrame) {	//������
		x=(Project.windowSize.x-200)/2;
		y= 200;
		
		button = new JButton("���丮 ���");
		button.setBounds(x, y+40, 200, 40);
		button.addMouseListener(new GotoPanel(gameFrame,"storyMode"));
		add(button);
		
		button = new JButton("���� ���");
		button.setBounds(x, y+100, 200, 40);
		add(button);
		
		button = new JButton("�� ��");
		button.setBounds(x, y+160, 200, 40);
		add(button);
		
		button = new JButton("�� ��");
		button.setBounds(x, y+220, 200, 40);
		add(button);

		button = new JButton("�� ��");
		button.setBounds(x, y+280, 200, 40);
		button.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
			System.exit(0);
		}});
		add(button);
		
		button = new JButton("�� ���� ������");
		button.setBounds(10, 10, 200, 40);
		add(button);
		setLayout(null);
		
	}

}
