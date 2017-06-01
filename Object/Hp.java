package Object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;

import Frame.StoryRoom;
import Main.Project;

public class Hp extends JLabel{
	JLabel base;
	JLabel hpBar;
	float hp,hpMax;
	int width;
	StoryRoom room;
	public Hp(int hp,StoryRoom room){
		this.room=room;
		base=new JLabel();
		hpBar=new JLabel();
		
		Project.setLabelImage(hpBar, "HP.png");
		Project.setLabelImage(base, "baseHP.png");
		
		room.add(hpBar);
		room.add(base);
		width = hpBar.getWidth();
		this.hp=hp;
		hpMax=hp;
	}
	public void step(int x,int y){
		setLocation(x,y);
		hpBar.setBounds(hpBar.getX(), hpBar.getY(),(int)( width*(hp/hpMax)), hpBar.getHeight());
	}
	public void setLocation(int x,int y){
		base.setLocation(x, y);
		hpBar.setLocation(x, y);
	}
	public void damage(int damage) {
		hp-=damage;
	}
	public void remove() { // 오브젝트 제거!
		room.remove(hpBar);
		room.remove(base);
		room.repaint();
	}
	public float getHp(){
		return hp;
	}
}
