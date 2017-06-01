package Listener;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import Frame.RoomCreate;
import Modify.ObjectSel;
import Modify.StageSel;
import Object.BaseObject;
import Object.Block;
import Object.Monster;
import Object.MoveObject;

public class DisposeListener extends MouseAdapter implements MouseMotionListener {
	RoomCreate room;
	ObjectSel objectSel;
	StageSel stageSel;
	Point xy;
	final int GRID;
	boolean charactorFlag;

	public DisposeListener(RoomCreate room) {
		this.room = room;
		this.objectSel = room.objectSel;
		this.stageSel = room.stageSel;
		GRID = room.GRID;
	}

	public void mouseDragged(MouseEvent e1) {
		if (!room.stop) {
			xy = e1.getPoint();
			if (distanceTo(xy, room.player)) {
				room.player.x = xy.x - room.player.width / 2;
				room.player.y = xy.y - room.player.height / 2;
				room.player.setLocation((int) room.player.x, (int) room.player.y);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		Point p = new Point();
		p.x = (e.getX() + GRID / 2) / GRID * GRID;
		p.y = (e.getY() + GRID / 2) / GRID * GRID;
		if (e.getButton() == MouseEvent.BUTTON1) {
			xy = p;
		}
	}

	public void mouseReleased(MouseEvent e) {
		Point p = new Point();
		p.x = (e.getX() + GRID / 2) / GRID * GRID;
		p.y = (e.getY() + GRID / 2) / GRID * GRID;
		if (distanceTo(e.getPoint(), room.player)) {
			room.player.x = p.x - room.player.width / 2;
			room.player.y = p.y - room.player.height / 2;
			room.player.setLocation((int) room.player.x, (int) room.player.y);
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			room.stop = !room.stop;
			room.requestFocus();
			if (room.stop) {
				stageSel.loadStage(stageSel.table.getSelectedRow());
				objectSel.setEnabled(true);
				stageSel.setEnabled(true);
			} else {
				room.time = 0;
				stageSel.saveStage(stageSel.table.getSelectedRow());
				objectSel.setEnabled(false);
				stageSel.setEnabled(false);
			}
		}
		if (room.stop && !distanceTo(p, room.player)) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (objectSel.tableSel == 0) {// 몬스터
					room.addMonster(objectSel.getObject(), p);
				} else if (objectSel.tableSel == 1) {// 블록
					Point wh = new Point(p.x - xy.x, p.y - xy.y);
					room.addBlock(objectSel.getObject(), xy, wh);
				}
			}

			if (e.getButton() == MouseEvent.BUTTON3) {
				Iterator objectIt;
				objectIt = room.monsterList.iterator();
				Monster monster;
				try {
					while (objectIt.hasNext()) {
						monster = (Monster) objectIt.next();
						if (distanceTo(e.getPoint(), monster)) {// <= monster.width / 2) {
							monster.remove();
						}
					}
					Block block;
					objectIt = room.blockList.iterator();
					while (objectIt.hasNext()) {
						block = (Block) objectIt.next();
						if (distanceTo(e.getPoint(), block)) {
							block.remove();
						}
					}
				} catch (ConcurrentModificationException e2) {

				}
			}
		}
		room.stageNext(0);
	}

	public boolean distanceTo(Point p1, BaseObject p2) { // 오브젝트와의 거리
		if (p1.x > p2.x && p1.x < (p2.x + p2.width) && p1.y > p2.y && p1.y < p2.y + p2.height)
			return true;
		else
			return false;
	}
}
