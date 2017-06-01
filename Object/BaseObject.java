package Object;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;

public class BaseObject extends JLabel{
	protected StoryRoom room;	//�������� ���� ��ü��� ���� ��ü
	public float x;	//x,y��ǥ(�������)
	public float y;
	public float originX, originY;	//������Ʈ�� ���߾���ǥ
	public float width;	//����ũ�� 
	public float height;	//����ũ��
	public String name; // ��ü�� �̸�
	public ImageIcon icon;
	BaseObject( StoryRoom room){
		this.room=room;
		name = this.getClass().getName().split("\\.")[1];
	}
	
	public void setImage(String img) { // �̹����� �ּҰ��� �־��ָ� �˾Ƽ� ôô ��ġ���� �����
		icon = new ImageIcon("resource/"+img);
		setIcon(icon);
		setBounds((int) x, (int) y, (int) icon.getIconWidth(), (int)  icon.getIconHeight());

	}
	public Point getPoint() { // �ڽ��� ��ġ��(���߾�)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}
	public void setOrigin() { // �ڽ��� ��ġ��(���߾�)
		originX = (x + width / 2);
		originY = (y + height / 2);
	}
	
	public void remove() { // ������Ʈ ����!
		//this.setVisible(false);
		room.remove(this);
		if (this instanceof Monster){
			room.monsterList.remove(this);
			}
		if (this instanceof Block){
			room.blockList.remove(this);
			}
		if (this instanceof Explode){
			room.explodeList.remove(this);
			}
		room.repaint();
	}
}
