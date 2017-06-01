package Modify;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Frame.RoomCreate;
import Listener.Creator;
import Listener.Creator.BlockName;
import Listener.Creator.MonsterName;
import Main.Project;
import Object.Block;
import Object.Monster;
import Object.Player;

class StageNum {
	String no;
	String st;

	StageNum(String no, String st) {
		this.no = no;
		this.st = st;
	}
}

class StageObject {
	int num;
	int x, y;
	int w, h;

	StageObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	StageObject(int num, int x, int y) {
		this(x, y);
		this.num = num;
	}

	StageObject(int num, int x, int y, int w, int h) {
		this(num, x, y);
		this.w = w;
		this.h = h;
	}
}

class Stage {
	String stageNum;
	String name;
	StageObject player;
	int clearNum;
	String clearObject;
	String clearTime;
	LinkedList<StageObject> monsterList;
	LinkedList<StageObject> blockList;
	

	Stage(String stageNum, String name) {
		monsterList = new LinkedList<StageObject>();
		blockList = new LinkedList<StageObject>();
		this.stageNum = stageNum;
		this.name = name;
		clearNum=0;
		clearObject="null";
		clearTime="0";
	}

	public void setPlayer(int x, int y) {
		player = new StageObject(x, y);
	}

	public void addMonster(int num, int x, int y) {
		monsterList.add(new StageObject(num, x, y));
	}

	public void addBlock(int num, int x, int y, int w, int h) {
		blockList.add(new StageObject(num, x, y, w, h));
	}

	public void write(BufferedWriter fw) {
		StageObject object;
		try {
			fw.write("S " + stageNum + " " + name);
			fw.write("\r\n");
			fw.write("P " + player.x + " " + player.y);
			fw.write("\r\n");
			while (!monsterList.isEmpty()) {
				object = monsterList.poll();
				fw.write("M " + object.num + " " + object.x + " " + object.y);
				fw.write("\r\n");
			}
			while (!blockList.isEmpty()) {
				object = blockList.poll();
				fw.write("B " + object.num + " " + object.x + " " + object.y + " " + object.w + " " + object.h);
				fw.write("\r\n");
			}
			fw.write("C "+clearNum +" "+clearObject+" "+clearTime);
			fw.write("\r\n");
			fw.write("end");
			fw.write("\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class StageSel extends JFrame {
	JScrollPane scroll;
	ControlPanel control;
	public JTable table;
	String columnNames[] = { "No.", "스테이지" };
	String rowData[][];
	LinkedList<String[]> monsterList;
	private RoomCreate roomCreate;

	public StageSel(RoomCreate roomCreate) {
		setTitle("스테이지");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 창 종료버튼 비활성화
		setSize(Project.windowSize.x / 5, Project.windowSize.y / 3); // 창 크기
		setLocation((Project.monitorSize.x + Project.windowSize.x) / 2, (Project.monitorSize.y - Project.windowSize.y) / 2
				+ Project.windowSize.y / 3);
		setLayout(new BorderLayout()); // 동서남북중앙을 가지는 레이아웃
		monsterList = new LinkedList<String[]>();
		rowData = makeArray(readStage());
		table = new StageTable(rowData, columnNames);
		control = new ControlPanel();
		scroll = new JScrollPane();
		add(scroll, BorderLayout.CENTER);
		add(control, BorderLayout.SOUTH);
		scroll.setViewportView(table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.roomCreate = roomCreate;
		loadStage(0);
	}

	private String[][] makeArray(LinkedList readStage) {
		String arr[][];
		StageNum stage;
		arr = new String[readStage.size()][2];
		for (int i = 0; i < arr.length; i++) {
			stage = (StageNum) readStage.poll();
			arr[i][0] = stage.no;
			arr[i][1] = stage.st;
		}
		return arr;
	}

	LinkedList readStage() {
		String st;
		String stArr[];
		Scanner scan = null;
		LinkedList list = new LinkedList();
		int num = 0;
		File file=new File("stage.txt");
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
				return list;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		while (scan.hasNextLine()) {
			st = scan.nextLine();
			if (st.charAt(0) == 'S') {
				num++;
				stArr = st.split(" ");
				list.add(new StageNum(stArr[1], stArr[2]));
			}
		}
		return list;
	}
	void outBlockInit(){
			roomCreate.addBlock(1, new Point(0, 0), new Point(1280, 64));
			roomCreate.addBlock(1, new Point(0, 0), new Point(64, 800));
			roomCreate.addBlock(1, new Point(1280-96, 0), new Point(96, 800));
			roomCreate.addBlock(1, new Point(0, 800-96), new Point(1280, 64));
	}
	
	public void loadStage(int selRow) {
		roomCreate.removeAllObject();
		roomCreate.stageInit(selRow);
		roomCreate.stageNext(0);
		control.menuClear.setText(Creator.ClearCase.values()[roomCreate.clearNum].toString());
		control.menuObject.setText(roomCreate.clearObject);
		control.text.setText(roomCreate.clearTime);
		roomCreate.stop = true;
	}

	class ControlAction implements ActionListener {
		int selRow;

		public void actionPerformed(ActionEvent e) {
			selRow = table.getSelectedRow();
			if (((JButton) e.getSource()).getText() == "+") {
				String arr[][];
				arr = new String[rowData.length + 1][2];
				for (int i = 0; i < rowData.length; i++) {
					arr[i][0] = rowData[i][0];
					arr[i][1] = rowData[i][1];
				}
				arr[rowData.length][0] = new Integer(rowData.length).toString();
				arr[rowData.length][1] = "stage";
				rowData = arr;
				table = new StageTable(rowData, columnNames);
				saveStage(selRow);
				loadStage(rowData.length - 1);
				table.setRowSelectionInterval(0, rowData.length - 1);
				outBlockInit();
				saveStage(rowData.length - 1);
				loadStage(rowData.length - 1);
				
				
				scroll.setViewportView(table);
			}

			if (((JButton) e.getSource()).getText() == "-") {
				selRow = table.getSelectedRow();
				if (selRow != -1) {
					String arr[][];
					arr = new String[rowData.length - 1][2];
					for (int i = 0; i < selRow; i++) {
						arr[i][0] = rowData[i][0];
						arr[i][1] = rowData[i][1];
					}
					for (int i = selRow; i < rowData.length - 1; i++) {
						arr[i][0] = rowData[i][0];
						arr[i][1] = rowData[i + 1][1];
					}
					rowData = arr;
					table = new StageTable(rowData, columnNames);
					
					if (selRow == rowData.length) {
						removeStage(selRow);
						loadStage(selRow - 1);
						if (selRow>0)
						table.setRowSelectionInterval(0, selRow - 1);
					} else {
						removeStage(selRow);
						loadStage(selRow);
						table.setRowSelectionInterval(0, selRow);
					}

					scroll.setViewportView(table);

				}
			}
			if (((JButton) e.getSource()).getText() == "△") {
				selRow = table.getSelectedRow();
				if (selRow > 0) {
					String tmp;// swap
					tmp = rowData[selRow][1];
					rowData[selRow][1] = rowData[selRow - 1][1];
					rowData[selRow - 1][1] = tmp;
					table = new StageTable(rowData, columnNames);
				// swap+++ +++++ `
					Stage stageTmp=saveTmpStage(selRow);
					loadStage(selRow-1);
					saveStage(selRow);
					table.setRowSelectionInterval(0, selRow - 1);
					stageTmp.stageNum = String.valueOf(Integer.parseInt(stageTmp.stageNum) - 1);
					saveStage(selRow,stageTmp);
					loadStage(selRow-1);
					scroll.setViewportView(table);
				}

			}
			if (((JButton) e.getSource()).getText() == "▽") {
				selRow = table.getSelectedRow();
				if (selRow < rowData.length - 1) {
					String tmp;// swap
					tmp = rowData[selRow][1];
					rowData[selRow][1] = rowData[selRow + 1][1];
					rowData[selRow + 1][1] = tmp;
					table = new StageTable(rowData, columnNames);
					
					// swap+++ +++++ `
					Stage stageTmp=saveTmpStage(selRow);
					loadStage(selRow+1);
					saveStage(selRow);
					table.setRowSelectionInterval(0, selRow + 1);
					stageTmp.stageNum = String.valueOf(Integer.parseInt(stageTmp.stageNum) + 1);
					saveStage(selRow,stageTmp);
					loadStage(selRow+1);
					scroll.setViewportView(table);
				}
			}
			if (((JButton) e.getSource()).getText() == "load") {
				selRow = table.getSelectedRow();
				loadStage(selRow);
			}
			if (((JButton) e.getSource()).getText() == "save") {
				selRow = table.getSelectedRow();
				saveTmpStage(selRow);
				saveStage(selRow);
			}
		}

	}

	public Stage saveTmpStage(int selRow) {
		
		Stage stage = new Stage(String.valueOf(selRow), (String) table.getValueAt(selRow, 1));
		Iterator it;
		Block block;
		Monster monster;
		stage.setPlayer((int) roomCreate.player.x, (int) roomCreate.player.y);
		it = roomCreate.blockList.iterator();
		
		stage.clearNum=Creator.clearCaseNum(control.menuClear.getText( ));
		stage.clearObject=control.menuObject.getText();
		stage.clearTime=control.text.getText();
		if (stage.clearTime.equals(""))
			stage.clearTime="0";
		roomCreate.clearNum=stage.clearNum;
		roomCreate.clearObject=stage.clearObject;
		roomCreate.clearTime=stage.clearTime;
		while (it.hasNext()) {
			block = (Block) it.next();
			stage.addBlock(Creator.blockNum(block.name), (int) block.x, (int) block.y, (int) block.width, (int) block.height);
		}
		it = roomCreate.monsterList.iterator();
		while (it.hasNext()) {
			monster = (Monster) it.next();
			stage.addMonster(Creator.monsterNum(monster.name), (int) monster.x, (int) monster.y);
		}
		return stage;
	}

	public void saveStage(int selRow) {
		if (selRow==-1)
			return;
		Stage selStage = saveTmpStage(selRow);
		saveStage(selRow, false,selStage);
	}
	public void saveStage(int selRow,Stage selStage) {
		saveStage(selRow, false,selStage);
	}
	public void removeStage(int selRow) {
		Stage selStage = new Stage(String.valueOf(selRow),"NaN");
		
		saveStage(selRow, true,selStage);
	}

	public void saveStage(int selRow, boolean remove,Stage selStage) {
		LinkedList<Stage> stageList = new LinkedList<Stage>();
		Stage tmpStage = null;
		Scanner scan = null;
		String line;
		String arr[];
		try {
			scan = new Scanner(new File("stage.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			arr = line.split(" ");
			if (arr.length != 0) {
				if (arr[0].equals("S")) {
					tmpStage = new Stage(arr[1], arr[2]);
				} else if (arr[0].equals("P")) {
					tmpStage.setPlayer(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
				} else if (arr[0].equals("M")) {
					tmpStage.addMonster(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
				} else if (arr[0].equals("B")) {
					tmpStage.addBlock(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]),
							Integer.parseInt(arr[4]), Integer.parseInt(arr[5]));
				} else if (arr[0].equals("C")) {
					tmpStage.clearNum=Integer.parseInt(arr[1]);
					tmpStage.clearObject=arr[2];
					tmpStage.clearTime=arr[3];
				}else if (arr[0].equals("end")) {
					stageList.add(tmpStage);
				}
			}
		}
		writeStage(stageList, selStage, remove);
	}

	public void writeStage(LinkedList<Stage> stageList, Stage selStage, boolean remove) {
		BufferedWriter fw = null;
		Stage tmpStage;
		boolean flag = false;
		Iterator<Stage> it = stageList.iterator();
		try {
			fw = new BufferedWriter(new FileWriter("stage.txt"));
			while (it.hasNext()) {
				tmpStage = it.next();
				if (tmpStage.stageNum.equals(selStage.stageNum)) {
					flag = true;
					if (!remove){
						selStage.write(fw);
					}
				} else {
					if (remove) {
						if (flag) {
							tmpStage.stageNum = String.valueOf(Integer.parseInt(tmpStage.stageNum) - 1);
						}
					}
					tmpStage.write(fw);
				}
			}
			if (!flag)
				selStage.write(fw);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ControlPanel extends JPanel {
		
		JButton plus, minus, up, down, save, load;
		JMenuBar menuBarClear;
		JMenuBar menuBarMonster;
		public JMenu menuClear;
		public JMenu menuObject;
		JMenuItem item;
		JTextField text;
		ControlPanel() {
			setLayout(new GridLayout(3, 4));
			plus = new JButton("+");
			minus = new JButton("-");
			up = new JButton("△");
			down = new JButton("▽");
			save = new JButton("save");
			load = new JButton("load");
			menuBarClear=new JMenuBar();
			menuBarMonster=new JMenuBar();
			plus.addActionListener(new ControlAction());
			minus.addActionListener(new ControlAction());
			up.addActionListener(new ControlAction());
			down.addActionListener(new ControlAction());
			save.addActionListener(new ControlAction());
			load.addActionListener(new ControlAction());
			
			Creator.ClearCase[] clearCase=Creator.ClearCase.values();
			menuClear=new  JMenu(clearCase[0].toString());
			for(int i=0;i<clearCase.length;i++){
				item=new JMenuItem(clearCase[i].toString());
				item.addActionListener(new SelMenu(menuClear));
				menuClear.add(item);
			}
			menuBarClear.add(menuClear);
			
			item=new JMenuItem("null");
			menuObject=new  JMenu(item.getText());
			item.addActionListener(new SelMenu(menuObject));
			menuObject.add(item);
			MonsterName[] monsterCase=Creator.MonsterName.values();
			
			for(int i=0;i<monsterCase.length;i++){
				item=new JMenuItem(monsterCase[i].toString());
				item.addActionListener(new SelMenu(menuObject));
				menuObject.add(item);
			}
			BlockName[] blockCase=Creator.BlockName.values();
			
			for(int i=0;i<blockCase.length;i++){
				item=new JMenuItem(blockCase[i].toString());
				item.addActionListener(new SelMenu(menuObject));
				menuObject.add(item);
			}
			menuBarMonster.add(menuObject);
			text=new JTextField("0");
			add(plus);
			add(minus);
			add(up);
			add(save);
			add(load);
			add(down);
			add(menuBarClear);
			add(menuBarMonster);
			add(text);
		}
	}
	class SelMenu implements ActionListener {
		JMenu menu;
		public SelMenu(JMenu menu) {
			this.menu=menu;
		}

		public void actionPerformed(ActionEvent e) {
			menu.setText(((JMenuItem)(e.getSource())).getText());
		}
	}
	class StageTable extends JTable {
		StageTable(String[][] rowData, String[] columnNames) {
			super(rowData, columnNames);
			setSelectionMode((ListSelectionModel.SINGLE_SELECTION));
			setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			getColumnModel().getColumn(0).setPreferredWidth(25); // 테이블 가로 크기조정
			getColumnModel().getColumn(1).setPreferredWidth(Project.windowSize.x / 5 - 60); // 테이블 가로
																																											// 크기조정
			DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
			// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
			tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			// 정렬할 테이블의 ColumnModel을 가져옴
			TableColumnModel tcmSchedule = getColumnModel();
			// 테이블을 가운데 정렬로 지정
			tcmSchedule.getColumn(0).setCellRenderer(tScheduleCellRenderer);
			if (rowData.length != 0)
				setRowSelectionInterval(0, 0);
		}

		public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
			int selRow;
			selRow = getSelectedRow();
			if (selRow != -1) {
				saveStage(selRow);
			}
			super.changeSelection(rowIndex, columnIndex, toggle, extend);
			selRow = getSelectedRow();
			loadStage(selRow);
		}
	}
}
