package Modify;
import javax.swing.JFrame;

import Main.Project;

public class ObjectSel extends JFrame {//�̱���
	public ObjectSel() {
		setTitle("������Ʈ");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		setSize(Project.windowSize.x/5, Project.windowSize.y/3); // â ũ��
		//setLocation((Project.screenSize.x - Project.windowSize.x) / 2-Project.windowSize.x/5, (Project.screenSize.y - Project.windowSize.y) / 2); // ���߾�
		setLayout(null); // ���̾ƿ��� null�� ����� ����� ��ġ�� ����
	}
}
