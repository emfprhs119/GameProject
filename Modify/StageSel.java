package Modify;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Main.Project;

public class StageSel extends JFrame {
	JScrollPane scroll;
	JPanel control;
	JTable table;
	Scanner scan;
	String[] scanData;
	LinkedList<String[]> monsterList;
	public StageSel() {
		setTitle("스테이지");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	// 창 종료버튼 비활성화
		setSize(Project.windowSize.x / 5, Project.windowSize.y / 3); // 창 크기
		setLocation((Project.screenSize.x + Project.windowSize.x) / 2, (Project.screenSize.y - Project.windowSize.y) / 2
				+ Project.windowSize.y / 3);
		setLayout(new BorderLayout()); // 동서남북중앙을 가지는 레이아웃 
		scanData=new String[3];
		monsterList=new LinkedList<String[]>();
		String columnNames[] = { "stage", "스테이지 선택" };
		
		Object rowData[][] = { { "1", "stage 1" }, { "2", "stage 2" }, { "3", "stage 3" }, { "4", "stage 4" } };
		table = new StageTable(columnNames);
		control = new ControlPanel();
		scroll = new JScrollPane();
		
		
		add(scroll, BorderLayout.CENTER);
		add(control, BorderLayout.SOUTH);
		scroll.setViewportView(table);
		
		readStage("stage1");
		while(!monsterList.isEmpty()){
			scanData=monsterList.poll();
			System.out.printf("%s/%s,%s\n",scanData[0],scanData[1],scanData[2]);
		}
		
	}
	void readStage(String s){
		try {
			scan=new Scanner(new File(s+".txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(scan.hasNextLine()){
			scanData=scan.nextLine().split("/|,");
			monsterList.add(scanData);
		}
	}
}

class ControlPanel extends JPanel {
	JButton plus, minus, up, down;
	ControlPanel() {
		plus = new JButton("+");
		minus = new JButton("-");
		up = new JButton("△");
		down = new JButton("▽");
		add(plus);
		add(minus);
		add(up);
		add(down);
	}
}

class StageTable extends JTable {
	StageTable(String[] columnNames) {
		super(1,2);

		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getColumnModel().getColumn(0).setPreferredWidth(40); // 테이블 가로 크기조정

		getColumnModel().getColumn(1).setPreferredWidth(Project.windowSize.x / 5 - 60); // 테이블 가로
																																													// 크기조정
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = getColumnModel();
		// 테이블을 가운데 정렬로 지정
		tcmSchedule.getColumn(0).setCellRenderer(tScheduleCellRenderer);
	}
}
