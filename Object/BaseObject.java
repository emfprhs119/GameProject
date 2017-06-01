package Object;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Frame.StoryRoom;

public class BaseObject extends JLabel{
	protected StoryRoom room;	//스테이지 내부 객체제어를 위한 객체
	public float x;	//x,y좌표(좌측상단)
	public float y;
	public float originX, originY;	//오브젝트의 정중앙좌표
	public float width;	//가로크기 
	public float height;	//세로크기
	public String name; // 객체의 이름
	public ImageIcon icon;
	BaseObject( StoryRoom room){
		this.room=room;
		name = this.getClass().getName().split("\\.")[1];
	}
	
	public void setImage(String img) { // 이미지의 주소값만 넣어주면 알아서 척척 위치까지 잡아줌
		icon = new ImageIcon("resource/"+img);
		setIcon(icon);
		setBounds((int) x, (int) y, (int) icon.getIconWidth(), (int)  icon.getIconHeight());

	}
	public Point getPoint() { // 자신의 위치값(정중앙)
		Point p = new Point((int) (x + width / 2), (int) (y + height / 2));
		return p;
	}
	public void setOrigin() { // 자신의 위치값(정중앙)
		originX = (x + width / 2);
		originY = (y + height / 2);
	}
	
	public void remove() { // 오브젝트 제거!
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
