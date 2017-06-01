package Object;
import javax.swing.JLabel;
import Main.Project;

@SuppressWarnings("serial")
public class Block extends JLabel {	//벽
	float x,y,width,height;		//위치,크기
	float originX, originY;	//x,y는 왼쪽위 좌표가 기준이므로 오리진 좌표가 필요(정중앙 위치)
	public Block(int x,int y, int width, int height){
		this.x=x;
		this.y=y;
		Project.setLabelImage(this, "box.png");
		setLocation((int)x,(int)y);
		setSize(width, height);
		
		this.width=this.getWidth();
		this.height=this.getHeight();
		setOrigin();
	}
	void setOrigin() { // 자신의 위치값(정중앙)
		originX=(x + width / 2);
		originY=(y + height / 2);
	}
}
