package Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Listener.GotoPanel;
import Main.Project;

@SuppressWarnings("serial")
public class StoryMode extends JPanel {	//�������� ����
	JButton difficulty, back;	//���̵� ��ư,�ڷΰ���(���θ޴�) ��ư
	JLabel label;	//�ӽ� ��
	StoryRoom gameRoom;	// ���ӷ� ����
	private GameFrame gameFrame;	//������ ������ ����
	StoryMode(GameFrame gameFrame) {	//������
		this.gameFrame=gameFrame;
		gameRoom=gameFrame.storyRoom;
		setLayout(null); // ���̾ƿ��� null�� ����� ����� ��ġ�� ����
		initObject(); // ��ư�� ���� �ʱ�ȭ �����ִ� �޼ҵ�
		addObject(); // ��ư�� ���� �߰� �����ִ� �޼ҵ�
		setVisible(true);
	}

	private void initObject() {	// �ʱ�ȭ
		//gameRoom = new StoryRoom(mainMenu,this);	// ���� �� �ʱ�ȭ
		
		difficulty = new JButton("normal");
		setButtonImage(difficulty, "normal.png"); // ����Ʈ ��� -����ġ �ϵ�
		difficulty.addMouseListener(new difficultyAction());
		difficulty.setLocation(100, 250);

		back = new JButton("back");
		back.addMouseListener(new GotoPanel(gameFrame, "mainMenu"));
		back.setBounds(15, 15, 100, 50);
		
	}

	private void addObject() {	// �߰�
		add(difficulty);
		add(back);
		for(int i=1;i<5;i++){
			label = new JLabel(String.valueOf(i));
			Project.setLabelImage(label, "stage.png");
			label.addMouseListener(new GotoPanel(gameFrame,"storyRoom"));
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
		@SuppressWarnings("deprecation")
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
		@SuppressWarnings("deprecation")
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) { // ���ʹ�ư
				gameRoom.Initialization(difficulty.getLabel(),((JLabel) e.getComponent()).getText());	// ��� Ŭ���ߴ��� �������� ���� ���⼭ �Ǵ��ϰ� �ʱ�ȭ�Լ� ȣ��
			}
		}
	}
}