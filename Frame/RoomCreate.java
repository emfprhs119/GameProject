package Frame;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Object.Block;
import Object.Monster;
import Object.Player;

public class RoomCreate extends StoryRoom {
	Player player;
	Monster monster;
	Block block;
	LinkedList<Monster> monsterList;	// 몬스터 리스트
	LinkedList<Block> blockList;	// 블럭 리스트
	JFrame mainMenu;	// 이동할 수 있는 장소(메인)
	
	int difficulty, level;	// 어려움모드,레벨
	boolean stop;	//	parse를 눌렀을때 모든 동작을 멈출 수 있게 해주는 불리안
	public RoomCreate(JFrame mainMenu) { // 돌아갈 장소를 설정하기 위해 필요
		super();
		stop = true;	
		
	}
}
