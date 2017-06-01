package Modify;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;

import Frame.RoomCreate;
import Listener.Creator.MonsterName;
import Main.Project;

class ObjectData {
	String name;
	int num;

	ObjectData(int num, String name) {
		this.num = num;
		this.name = name;
		}

	int getObject() {
		return num;
	}
	public String toString(){
		return name;
	}
}

public class ObjectSel extends JFrame {
	RoomCreate roomCreate;
	public int tableSel;
	JTable table0,table1;
	public ObjectSel(RoomCreate roomCreate) {
		setTitle("오브젝트");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(Project.windowSize.x / 5, Project.windowSize.y / 2); // 창 크기
		setLocation((Project.monitorSize.x - Project.windowSize.x) / 2 - Project.windowSize.x / 5,
				(Project.monitorSize.y - Project.windowSize.y) / 2);
		// setLayout(null); // 레이아웃을 null로 해줘야 맘대로 배치가 가능
		this.roomCreate = roomCreate;
		setLayout(new GridLayout(2,1)); // 동서남북중앙을 가지는 레이아웃
		String columnNames[] = { "몬스터" };
		String monsterName[]=roomCreate.creator.monsterName();
		ObjectData rowData[][]=new ObjectData[monsterName.length][1];
		
		for(int i=0;i<monsterName.length;i++){
			rowData[i][0]=new ObjectData(i,monsterName[i]);
		}
		table0 = new JTable(rowData, columnNames);
		JScrollPane scroll = new JScrollPane();
		add(scroll);
		scroll.setViewportView(table0);

		String columnNames1[] = { "벽" };
		String blockName[]=roomCreate.creator.blockName();
		ObjectData rowData1[][]=new ObjectData[blockName.length][1];
		
		for(int i=0;i<blockName.length;i++)
			rowData1[i][0]=new ObjectData(i,blockName[i]);
		table1 = new JTable(rowData1, columnNames1);
		JScrollPane scroll1 = new JScrollPane();
		add(scroll1);
		scroll1.setViewportView(table1);
		tableSel=0;
		table0.setSelectionMode((ListSelectionModel.SINGLE_SELECTION));
		table1.setSelectionMode((ListSelectionModel.SINGLE_SELECTION));
		table0.setRowSelectionInterval(0,0);
		table0.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (e.getButton()==MouseEvent.BUTTON1){
					table0.setRowSelectionAllowed(true);
					table1.setRowSelectionAllowed(false);
					tableSel=0;
				}
			}
		});
		table1.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (e.getButton()==MouseEvent.BUTTON1){
					table1.setRowSelectionAllowed(true);
					table0.setRowSelectionAllowed(false);
					tableSel=1;
					table0.editingStopped(null);
				}
			}
		});
		this.roomCreate = roomCreate;
	}
	public int getObject() {
		if (tableSel==0)
		{
			return ((ObjectData)table0.getValueAt(table0.getSelectedRow(),0)).num;
		}
		else if (tableSel==1)
			return ((ObjectData)table1.getValueAt(table1.getSelectedRow(),0)).num;
		return -1;
	}
}
