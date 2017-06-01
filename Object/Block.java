package Object;
import java.awt.Point;

import javax.swing.JLabel;

import Main.Project;


public class Block extends JLabel {	
	float x,y,width,height;	
	float originX, originY;	//x,y는 왼쪽위 좌표가 기준이므로 오리진 좌표가 필요(정중앙 위치)
	public Block(float x,float y){
		this.x=x;
		this.y=y;
		Project.setLabelImage(this, "box.png");
		setLocation((int)x,(int)y);
		width=this.getWidth();
		height=this.getHeight();
		setOrigin();
	}
	void setOrigin() { // 자신의 위치값(정중앙)
		originX=(x + width / 2);
		originY=(y + height / 2);
	}
}
