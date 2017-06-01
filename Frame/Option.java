package Frame;

import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import Listener.GotoPanel;
import Main.Project;

@SuppressWarnings("serial")
public class Option extends JPanel { // 정지 메뉴
	JLabel label;
	JSlider slider1, slider2;
	JButton button;
	JRadioButton radio;
	Panel panel;
	ButtonGroup group;
	Scanner config;
	int x, y;
	private GameFrame gameFrame;
	StoryRoom room;

	public Option(GameFrame gameFrame, String panel) {
		this.gameFrame = gameFrame;

		x = (Project.windowSize.x - 400) / 2;
		y = 100;
		try {
			config = new Scanner(new File("config.ini"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addPerfOption(Integer.parseInt(config.nextLine().split("Performance=")[1]));
		addSoundOption(Integer.parseInt(config.nextLine().split("Sound_Background=")[1]),
				Integer.parseInt(config.nextLine().split("Sound_Effect=")[1]));
		addControlOption();

		button = new JButton("초기화");
		button.setBounds(x + 100, y + 600, 200, 40);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				slider1.setValue(5);
				slider2.setValue(5);
			}
		});
		
		add(button);
		button = new JButton("확인");
		button.setBounds(x + 300, y + 600, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame, panel));
		button.addActionListener(new SaveConfig());
		add(button);
		button = new JButton("취소");
		button.setBounds(x + 500, y + 600, 200, 40);
		button.addActionListener(new GotoPanel(gameFrame, panel));
		add(button);
		setLayout(null);
	}

	class SaveConfig implements ActionListener { // 옵션이 두종류이므로 동기화를 시켜주도록 하자!!

		public void actionPerformed(ActionEvent e) {
			try {
				BufferedWriter fw = new BufferedWriter(new FileWriter("config.ini"));
				Enumeration<AbstractButton> enumeration = group.getElements();
				int count = 0;
				while (enumeration.hasMoreElements()) {
					AbstractButton Abutton = enumeration.nextElement();
					if (Abutton.isSelected()){
						fw.write("Performance=" + count);
					}
					count++;
				}
				fw.write("\r\n");
				fw.write("Sound_Background=" + slider1.getValue());
				
				fw.write("\r\n");
				fw.write("Sound_Effect=" + slider2.getValue());
				
				fw.flush();
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Project.Sound_Background=slider1.getValue();
			Project.Sound_Effect=slider2.getValue();
		}
	}

	private void addPerfOption(int perf) {
		label = new JLabel("성능");
		label.setFont(new Font("신명조", Font.BOLD, 30));
		label.setBounds(x, y + 40, 100, 40);
		add(label);
		panel = new Panel();
		group = new ButtonGroup();
		for (int i = 0; i < 3; i++) {
			switch (i) {
			case 0:
				radio = new JRadioButton("낮음", false);
				radio.setFont(new Font("신명조", Font.BOLD, 12));
				group.add(radio);
				panel.add(radio);
				break;
			case 1:
				radio = new JRadioButton("중간", false);
				radio.setFont(new Font("신명조", Font.BOLD, 12));
				group.add(radio);
				panel.add(radio);
				break;
			case 2:
				radio = new JRadioButton("높음", false);
				radio.setFont(new Font("신명조", Font.BOLD, 12));
				group.add(radio);
				panel.add(radio);
				break;
			}
			if (perf == i)
				radio.setSelected(true);
		}
		panel.setBounds(x + 100, y + 50, 250, 40);
		add(panel);

	}

	private void addSoundOption(int back, int effect) {
		label = new JLabel("사운드");
		label.setFont(new Font("신명조", Font.BOLD, 30));
		label.setBounds(x, y + 110, 200, 40);
		add(label);
		label = new JLabel("배경음");
		label.setFont(new Font("신명조", Font.BOLD, 20));
		label.setBounds(x + 50, y + 160, 100, 40);
		add(label);
		label = new JLabel("효과음");
		label.setFont(new Font("신명조", Font.BOLD, 20));
		label.setBounds(x + 50, y + 190, 100, 40);
		add(label);
		slider1 = new JSlider();
		slider1.setMaximum(9);
		slider1.setValue(back);
		slider1.setBounds(x + 140, y + 160, 200, 40);
		add(slider1);
		slider2 = new JSlider();
		slider2.setMaximum(9);
		slider2.setValue(effect);
		slider2.setBounds(x + 140, y + 190, 200, 40);
		add(slider2);
	}

	private void addControlOption() {
		label = new JLabel("조작 설정");
		label.setFont(new Font("신명조", Font.BOLD, 30));
		label.setBounds(x, y + 250, 200, 40);
		add(label);
	}
	public void paintComponent(Graphics g) {
		gameFrame.paintComponents(g);
	}
}
