package Frame;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Listener.GotoFrame;
import Main.Project;

public class StoryMode extends JFrame {
	JButton difficulty, back;
	JLabel label;
	JFrame mainMenu;	// �������� ���ư��� ���� ������
	StoryRoom gameRoom;	// ���ӷ� ����
	StoryMode(JFrame mainMenu) {
		setSize(Project.windowSize.x, Project.windowSize.y); // â ũ��
		setLocation((Project.screenSize.x - Project.windowSize.x) / 2, (Project.screenSize.y - Project.windowSize.y) / 2); // ���߾�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainMenu = mainMenu;
		setLayout(null); // ���̾ƿ��� null�� ����� ����� ��ġ�� ����
		initObject(); // ��ư�� ���� �ʱ�ȭ �����ִ� �޼ҵ�
		addObject(); // ��ư�� ���� �߰� �����ִ� �޼ҵ�
		setVisible(true);
	}

	private void initObject() {	// �ʱ�ȭ
		gameRoom = new StoryRoom(mainMenu,this);	// ���� �� �ʱ�ȭ
		
		difficulty = new JButton("normal");
		setButtonImage(difficulty, "normal.png"); // ����Ʈ ��� -����ġ �ϵ�
		difficulty.addMouseListener(new difficultyAction());
		difficulty.setLocation(100, 250);

		back = new JButton("back");
		back.addMouseListener(new GotoFrame(this, mainMenu));
		back.setBounds(15, 15, 100, 50);
		
	}

	private void addObject() {	// �߰�
		add(difficulty);
		add(back);
		for(int i=1;i<5;i++){
			label = new JLabel(String.valueOf(i));
			Project.setLabelImage(label, "stage.png");
			label.addMouseListener(new GotoFrame(this,gameRoom));
			label.addMouseListener(new StageAction());
			label.setLocation(220+55*i, 250);
			add(label);
			}
		
	}

	private void setButtonImage(JButton button, String img) { // �̹����� �ּҰ��� �־��ָ� �˾Ƽ� ôô ��ġ���� �����
		ImageIcon icon = new ImageIcon(img);
		button.setIcon(icon);
		int width = icon.getIconWidth() - 20;
		int height = icon.getIconHeight();
		button.setBounds((int) button.getX(), (int) button.getY(), (int) width, (int) height);
	}

	

	class difficultyAction extends MouseAdapter {		// difficulty ����ġ ����
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) { // ���ʹ�ư
				// ///////////////////////////////////////////////// level ����ġ ����
				if (((JButton) e.getComponent()).getLabel().equals("normal")) {
					((JButton) e.getComponent()).setLabel("hard");
					setButtonImage((JButton) e.getComponent(), "hard.png");
				} else if (((JButton) e.getComponent()).getLabel().equals("hard")) {
					((JButton) e.getComponent()).setLabel("normal");
					setButtonImage((JButton) e.getComponent(), "normal.png");
				}
			}
		}
	}
	class StageAction extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) { // ���ʹ�ư
				gameRoom.Initialization(difficulty.getLabel(),((JLabel) e.getComponent()).getText());	// ��� Ŭ���ߴ��� �������� ���� ���⼭ �Ǵ��ϰ� �ʱ�ȭ�Լ� ȣ��
			}
		}
	}
}
class Back extends Container {
	JButton button;
	Back(StoryRoom room) {
		setSize(1280/3, 800); // â ũ��
		setLocation(1280/3, 0); // â ��ġ
		this.setLayout(null);
		button = new JButton("���θ޴���");
		button.setBounds(700, 0, 20, 20);
		add(button);
		button.addMouseListener(new GotoFrame(room.mainMenu,room));
	}
}