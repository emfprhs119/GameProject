package Frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;

import Block.Block1;
import Listener.DisposeListener;
import Listener.GotoPanel;
import Main.Project;
import Modify.ObjectSel;
import Modify.StageSel;
import Object.Block;
import Object.Monster;
import Object.Player;

@SuppressWarnings("serial")
public class RoomCreate extends StoryRoom {
	GameFrame gameFrame;
	public ObjectSel objectSel;
	public StageSel stageSel;
	private JButton back;
	public final int GRID;

	RoomCreate(GameFrame gameFrame) {
		super();
		creatorRoom=true;
		GRID = 64;
		objectSel = new ObjectSel(this);
		stageSel = new StageSel(this);
		objectSel.setVisible(false);
		stageSel.setVisible(false);
		addMouseListener(new DisposeListener(this));
		addMouseMotionListener(new DisposeListener(this));
		this.gameFrame = gameFrame;
		back = new JButton("back");
		back.addActionListener(new GotoPanel(gameFrame, "mainMenu"));
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				objectSel.setVisible(false);
				stageSel.setVisible(false);
				stop = true;
				stageSel.loadStage(stageSel.table.getSelectedRow());
			}
		});
		back.setBounds(15, 15, 100, 50);
		add(back);
	}

	void roomModify() {
		objectSel.setVisible(true);
		stageSel.setVisible(true);
		repaint();
	}

	void reset() {
		stop = true;
		requestFocus();
		stageSel.loadStage(stageSel.table.getSelectedRow());
		objectSel.setEnabled(true);
		stageSel.setEnabled(true);
		repaint();
	}

	void clearStage() {
		reset();
	}

	void failStage() {
		reset();
	}

	public void addMonster(int num, Point point) {
		// TODO Auto-generated method stub
		monster = creator.getMonster(num, point);
		monster.x -= monster.width / 2;
		monster.y -= monster.height / 2;
		monster.setLocation((int) monster.x, (int) monster.y);
		monster.moveHp();
		// monster.move();
	}

	public void addBlock(int num, Point xy, Point wh) {
		if (num == 3) {
			xy.x = xy.x + wh.x - 100 / 2;
			xy.y = xy.y + wh.y - 100 / 2;
			wh.x = 100;
			wh.y = 100;
			block = creator.getBlock(num, xy, wh);
			blockList.add(block);
			add(block);
		} else if (wh.x != 0 && wh.y != 0) {
			block = creator.getBlock(num, xy, wh);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		int plus=0;//-GRID/2;
		for (int i = 0; i < Project.windowSize.x; i += GRID) {
			g.drawLine(i +plus,0, i+plus, Project.windowSize.y);
			g.drawLine(0, i+plus, Project.windowSize.x,  i+plus);
		}
	}
}
