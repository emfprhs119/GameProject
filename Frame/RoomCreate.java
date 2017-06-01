package Frame;
import java.util.LinkedList;
import javax.swing.JFrame;
import Object.Block;
import Object.Monster;
import Object.Player;

@SuppressWarnings("serial")
public class RoomCreate extends StoryRoom {	//미구현
	GameFrame gameFrame;
	Player player;
	Monster monster;
	Block block;
	LinkedList<Monster> monsterList;	// 몬스터 리스트
	LinkedList<Block> blockList;	// 블럭 리스트
	JFrame mainMenu;	// 이동할 수 있는 장소(메인)
	
	int difficulty, level;	// 어려움모드,레벨
	boolean stop;	//	parse를 눌렀을때 모든 동작을 멈출 수 있게 해주는 불리안
	public RoomCreate(GameFrame gameFrame) { // 돌아갈 장소를 설정하기 위해 필요
		super();
		this.gameFrame=gameFrame;
		stop = true;	
		
	}
}
