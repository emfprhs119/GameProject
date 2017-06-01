package Object;
import javax.swing.JLabel;
import Main.Project;

@SuppressWarnings("serial")
public class Block extends JLabel {	//��
	float x,y,width,height;		//��ġ,ũ��
	float originX, originY;	//x,y�� ������ ��ǥ�� �����̹Ƿ� ������ ��ǥ�� �ʿ�(���߾� ��ġ)
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
	void setOrigin() { // �ڽ��� ��ġ��(���߾�)
		originX=(x + width / 2);
		originY=(y + height / 2);
	}
}
