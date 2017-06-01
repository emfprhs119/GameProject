package Block;

import java.awt.Point;

import Frame.StoryRoom;
import Object.BaseObject;
import Object.Block;

public class ClearSite extends Block {

	public ClearSite(Point xy,Point wh,StoryRoom room){
		super(xy,wh,room);
		//setLocation(xy.x-wh.x/2,xy.y-wh.y/2);
	}

}
