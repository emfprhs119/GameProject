package Object;

import javax.swing.JLabel;

import Frame.StoryRoom;
import Main.Project;

@SuppressWarnings("serial")
public class Hp extends JLabel {	//hp
	JLabel base;	//hp�� �޹��
	JLabel hpBar;//hp�� 
	float hp, hpMax;	//����hp,�ְ�hp
	int width;	//hp�� ����ũ��
	StoryRoom room;	//����â

	public Hp(int hp, StoryRoom room, boolean player) {	//hp��,����â,�÷��̾� ����
		this(hp);
		this.room = room;	
		if (player) {	//�÷��̾��϶��� hp��
			Project.setLabelImage(hpBar, "playerHP.png");
			Project.setLabelImage(base, "playerBaseHP.png");
			room.add(hpBar);
			room.add(base);
			setLocation(60, Project.windowSize.y - base.getHeight() -80);
		} else {	//�����϶��� hp��
			Project.setLabelImage(hpBar, "HP.png");
			Project.setLabelImage(base, "baseHP.png");
		}
		room.add(hpBar);
		room.add(base);
		width = hpBar.getWidth();
	}

	public Hp(int hp) {	//hp�ʱ�ȭ
		base = new JLabel();
		hpBar = new JLabel();
		this.hp = hp;
		hpMax = hp;
	}


	public void setLocation(int x, int y) {	//��ġ�̵�-���Ϳ����� ����
		base.setLocation(x, y);
		hpBar.setLocation(x, y);
	}

	public void damage(int damage) {	//�������� �޾������
		hp -= damage;
		hpBar.setBounds(hpBar.getX(), hpBar.getY(), (int) (width * (hp / hpMax)), hpBar.getHeight());
	}

	public void remove() { // ������Ʈ ����!
		room.remove(hpBar);
		room.remove(base);
		room.repaint();
	}

	public float getHp() {	//hp�� Ȯ��
		return hp;
	}
}
