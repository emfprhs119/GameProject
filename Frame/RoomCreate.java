package Frame;
import java.util.LinkedList;
import javax.swing.JFrame;
import Object.Block;
import Object.Monster;
import Object.Player;

@SuppressWarnings("serial")
public class RoomCreate extends StoryRoom {	//�̱���
	GameFrame gameFrame;
	Player player;
	Monster monster;
	Block block;
	LinkedList<Monster> monsterList;	// ���� ����Ʈ
	LinkedList<Block> blockList;	// �� ����Ʈ
	JFrame mainMenu;	// �̵��� �� �ִ� ���(����)
	
	int difficulty, level;	// �������,����
	boolean stop;	//	parse�� �������� ��� ������ ���� �� �ְ� ���ִ� �Ҹ���
	public RoomCreate(GameFrame gameFrame) { // ���ư� ��Ҹ� �����ϱ� ���� �ʿ�
		super();
		this.gameFrame=gameFrame;
		stop = true;	
		
	}
}
