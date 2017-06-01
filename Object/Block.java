package Object;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Frame.StoryRoom;
import Main.Project;

@SuppressWarnings("serial")
public class Block extends BaseObject {	//º®
	public int num;
	public Block(Point xy,Point wh,StoryRoom room){
		super(room);
		this.x=xy.x;
		this.y=xy.y;
		setImage(this.name+".png");
		//Project.setLabelImage(this, "box.png");
		
		this.width=wh.x;
		this.height=wh.y;
		setOrigin();
		num=0;
		room.blockList.add(this);
		room.add(this);
	}
	public void setImage(String img){
		super.setImage("block/"+img);
	}
}
