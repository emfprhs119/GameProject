package Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		back.addActionListener(new GotoPanel(gameFrame, "mainMenu"));
		back.setBounds(15, 15, 100, 50);
		
	}

	void addObject() {	// �߰�
		removeAll();
		add(difficulty);
		add(back);
		for(int i=0;i<gameFrame.roomCreate.stageSel.table.getRowCount();i++){
			label = new JLabel(String.valueOf(i));
			Project.setLabelImage(label, "stage.png");
			label.addMouseListener(new StageAction());
			label.setLocation(220+55*i, 250);
			add(label);
			}
	}
	private void setButtonImage(JButton button, String img) { // �̹����� �ּҰ��� �־��ָ� �˾Ƽ� ôô ��ġ���� �����
		ImageIcon icon = new ImageIcon("resource/base/"+img);
		button.setIcon(icon);
		int width = icon.getIconWidth() - 20;
		int height = icon.getIconHeight();
		button.setBounds((int) button.getX(), (int) button.getY(), (int) width, (int) height);
	}

	

	class difficultyAction extends MouseAdapter {		// difficulty ����ġ ����
		@SuppressWarnings("deprecation")
		public void mouseReleased(MouseEvent e) {
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
		int stageNum;
		GotoPanel gotoPanel=new GotoPanel(gameFrame,"storyRoom");
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) { // ���ʹ�ư
				stageNum=Integer.parseInt(((JLabel) e.getComponent()).getText());
				stageFrame(e);
			}
		}
		void stageFrame(MouseEvent e){
			int choiceNum = 0;
			String[] choices = { "����", "���", "���� ��������" };
			choiceNum = JOptionPane.showOptionDialog(null, "Stage "+(stageNum+1)+" �̼ǿ� �����Ͻðڽ��ϱ�?", "�̼� ����", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			switch (choiceNum) {
			case 0:
				gameFrame.changePanel("storyRoom");
				gameRoom.Initialization(difficulty.getLabel(),stageNum);	// ��� Ŭ���ߴ��� �������� ���� ���⼭ �Ǵ��ϰ� �ʱ�ȭ�Լ� ȣ��
				
				break;

			case 1:
				
				break;

			default:
				stageNum++;
				stageFrame(e);
				break;
			}
		}
	}
	public void paintComponent(Graphics g) {
		gameFrame.paintComponents(g);
	}
}