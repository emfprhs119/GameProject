package Object;

import Frame.StoryRoom;

public class Explode extends BaseObject{
	private int step;

	Explode(int x,int y,StoryRoom room) {
		super(room);
		step=0;
		this.x=x;
		this.y=y;
		room.explodeList.add(this);
		setImage("/explode/explode.gif");
		room.add(this);
		if (room.stop)
			remove();
	}

	public void step() {
		step++;
		if (step>80){
			remove();
		}
	}
}
