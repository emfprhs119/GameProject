package Object;

import javax.swing.JLabel;

import Frame.StoryRoom;
import Main.Project;

@SuppressWarnings("serial")
public class Hp extends JLabel { // hp
	public JLabel base; // hp바 뒷배경
	public JLabel hpBar;// hp바
	public float hp, hpMax; // 현재hp,최고hp
	int width; // hp바 가로크기
	StoryRoom room; // 게임창

	public Hp(int hp, StoryRoom room, boolean player) { // hp량,게임창,플레이어 여부
		this(hp);
		this.room = room;
		if (player) { // 플레이어일때의 hp바
			Project.setLabelImage(hpBar, "playerHP.png");
			Project.setLabelImage(base, "playerBaseHP.png");
			room.add(hpBar);
			room.add(base);
			setLocation(60, Project.windowSize.y - base.getHeight() - 80);
		} else { // 몬스터일때의 hp바
			Project.setLabelImage(hpBar, "HP.png");
			Project.setLabelImage(base, "baseHP.png");
		}
		room.add(hpBar);
		room.add(base);
		width = hpBar.getWidth();
		if (!room.creatorRoom){
			hpVisible(false);
		}
	}

	public void hpVisible(boolean bool) {
		hpBar.setVisible(bool);
		base.setVisible(bool);
	}

	public Hp(int hp) { // hp초기화
		base = new JLabel();
		hpBar = new JLabel();
		this.hp = hp;
		hpMax = hp;
	}

	public void setLocation(int x, int y) { // 위치이동-몬스터에서만 사용됨
		base.setLocation(x, y);
		hpBar.setLocation(x, y);
	}

	public void damage(int damage) { // 데미지를 받았을경우
		hp -= damage;
		draw();
	}

	public void healing(int heal) {
		if (hp + heal < hpMax)
			hp += heal;
		else
			hp = hpMax;
		draw();
	}

	public void draw() {
		hpBar.setBounds(hpBar.getX(), hpBar.getY(), (int) (width * (hp / hpMax)), hpBar.getHeight());
	}

	public void remove() { // 오브젝트 제거!
		room.remove(hpBar);
		room.remove(base);
		room.repaint();
	}

	public float getHp() { // hp량 확인
		return hp;
	}

}
