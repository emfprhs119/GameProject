package Block;

import java.awt.Point;

import Frame.StoryRoom;
import Object.Block;

public class PassBlock extends Block {
	public PassBlock(Point xy, Point wh, StoryRoom room) {
		super(xy, wh, room);
		setLocation((int)x,(int)y);
		setSize(wh.x,wh.y);
	}
}
