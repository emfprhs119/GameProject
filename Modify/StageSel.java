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

public class StageSel extends JFrame {	//�̱���
	JScrollPane scroll;
	JPanel control;
	JTable table;
	Scanner scan;
	String[] scanData;
	LinkedList<String[]> monsterList;
	public StageSel() {
		setTitle("��������");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	// â �����ư ��Ȱ��ȭ
		setSize(Project.windowSize.x / 5, Project.windowSize.y / 3); // â ũ��
		//setLocation((Project.screenSize.x + Project.windowSize.x) / 2, (Project.screenSize.y - Project.windowSize.y) / 2+ Project.windowSize.y / 3);
		setLayout(new BorderLayout()); // ���������߾��� ������ ���̾ƿ� 
		scanData=new String[3];
		monsterList=new LinkedList<String[]>();
		String columnNames[] = { "stage", "�������� ����" };
		
		Object rowData[][] = { { "1", "stage 1" }, { "2", "stage 2" }, { "3", "stage 3" }, { "4", "stage 4" } };
		table = new StageTable(columnNames);
		control = new ControlPanel();
		scroll = new JScrollPane();
		
		
		add(scroll, BorderLayout.CENTER);
		add(control, BorderLayout.SOUTH);
		scroll.setViewportView(table);
		
		readStage("stage");
		
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
		up = new JButton("��");
		down = new JButton("��");
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
		getColumnModel().getColumn(0).setPreferredWidth(40); // ���̺� ���� ũ������

		getColumnModel().getColumn(1).setPreferredWidth(Project.windowSize.x / 5 - 60); // ���̺� ����
																																													// ũ������
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// ������ ���̺��� ColumnModel�� ������
		TableColumnModel tcmSchedule = getColumnModel();
		// ���̺��� ��� ���ķ� ����
		tcmSchedule.getColumn(0).setCellRenderer(tScheduleCellRenderer);
	}
}
