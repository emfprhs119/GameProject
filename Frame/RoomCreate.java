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
	LinkedList<Monster> monsterList;	// ���� ����Ʈ
	LinkedList<Block> blockList;	// �� ����Ʈ
	JFrame mainMenu;	// �̵��� �� �ִ� ���(����)
	
	int difficulty, level;	// �������,����
	boolean stop;	//	parse�� �������� ��� ������ ���� �� �ְ� ���ִ� �Ҹ���
	public RoomCreate(JFrame mainMenu) { // ���ư� ��Ҹ� �����ϱ� ���� �ʿ�
		super();
		stop = true;	
		
	}
}
