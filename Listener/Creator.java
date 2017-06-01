package Listener;

import java.awt.Point;

import Block.*;
import Frame.StoryRoom;
import Monster.*;
import Object.Block;
import Object.Monster;

public class Creator {
	StoryRoom room;
	public enum ClearCase{이동,전멸,제거,버티기}
	public enum MonsterName {
		Bet, DeathReaper, Slime, SlimeR, SlimeH,fireBall,BulletBet
	}

	public enum BlockName {
		Block1, Block2,PassBlock,ClearSite
	}

	public Creator(StoryRoom room) {
		this.room = room;

	}

	public String[] monsterName() {
		MonsterName[] name = MonsterName.values();
		String[] str = new String[name.length];
		for (int i = 0; i < name.length; i++) {
			str[i] = name[i].toString();
		}
		return str;
	}

	public String[] blockName() {
		BlockName[] name = BlockName.values();
		String[] str = new String[name.length];
		for (int i = 0; i < name.length; i++) {
			str[i] = name[i].toString();
		}
		return str;
	}
	static public int clearCaseNum(String clearCase) {
		ClearCase[] name = ClearCase.values();
		for(int i=0;i<name.length;i++){
			if (name[i]==ClearCase.valueOf(clearCase))
				return i;
		}
		return -1;
	}
	static public int monsterNum(String monsterName) {
		MonsterName[] name = MonsterName.values();
		for(int i=0;i<name.length;i++){
			if (name[i]==MonsterName.valueOf(monsterName))
				return i;
		}
		return -1;
	}
	static public int blockNum(String blockName) {
		BlockName[] name = BlockName.values();
		for(int i=0;i<name.length;i++){
			if (name[i]==BlockName.valueOf(blockName))
				return i;
		}
		return -1;
	}

	public Monster getMonster(int num, Point point) {
		Monster monster = null;
		switch (num) { // 여기서 몬스터 종류가 갈리지
		case 0:
			monster = new Bet(point, room);
			break;
		case 1:
			monster = new DeathReaper(point, room);
			break;
		case 2:
			monster = new Slime(point, room);
			break;
		case 3:
			monster = new SlimeR(point, room);
			break;
		case 4:
			monster = new SlimeH(point, room);
			break;
		case 5:
			monster = new fireBall(point, room);
			break;
		case 6:
			monster = new BulletBet(point, room);
			break;
		}
		monster.moveHp();
		return monster;
	}

	public Block getBlock(int num, Point xy, Point wh) {
		Block block = null;
		switch (num) { //
		case 0:
			block = new Block1(xy, wh, room);
			break;
		case 1:
			block = new Block2(xy, wh, room);
			break;
		case 2:
			block=new PassBlock(xy,wh,room);
			break;
		case 3:
			block = new ClearSite(xy,wh, room);
			break;
		}
		return block;

	}

}
