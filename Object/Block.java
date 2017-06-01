package Object;
import java.awt.Point;

import javax.swing.JLabel;

import Main.Project;


public class Block extends JLabel {	
	float x,y,width,height;	
	float originX, originY;	//x,y�� ������ ��ǥ�� �����̹Ƿ� ������ ��ǥ�� �ʿ�(���߾� ��ġ)
	public Block(float x,float y){
		this.x=x;
		this.y=y;
		Project.setLabelImage(this, "box.png");
		setLocation((int)x,(int)y);
		width=this.getWidth();
		height=this.getHeight();
		setOrigin();
	}
	void setOrigin() { // �ڽ��� ��ġ��(���߾�)
		originX=(x + width / 2);
		originY=(y + height / 2);
	}
}
