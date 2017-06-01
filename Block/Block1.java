package Block;

import java.awt.Point;

import Frame.StoryRoom;
import Object.Block;

public class Block1 extends Block {
	public Block1(Point xy,Point wh,StoryRoom room){	// xy,width,height
		super(xy,wh,room);
		setLocation((int)x,(int)y);
		setSize(wh.x,wh.y);
	}
}
