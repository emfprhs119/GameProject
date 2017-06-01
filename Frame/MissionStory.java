package Frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;

import Frame.StopMenu.VisibleFalse;
import Frame.StoryMode.StageAction;
import Listener.GotoPanel;
import Main.Project;

@SuppressWarnings("serial")
public class MissionStory extends Container { // 정지 메뉴
	JButton button;
	int x, y;
	StoryRoom room;
	JLabel label;
	ArrayList<String> story;
	ArrayList<String> mission;
	ArrayList<String> hint;

	MissionStory(StoryRoom storyRoom) { // 생성자
		this.room = storyRoom;
		story = new ArrayList<String>();
		mission = new ArrayList<String>();
		hint = new ArrayList<String>();
		x = (Project.windowSize.x - 200) / 2;
		y = 200;

		setSize(Project.windowSize.x, Project.windowSize.y); // 창 크기
		setLayout(null);
	}

	class VisibleFalse extends MouseAdapter {
		@SuppressWarnings("deprecation")
		public void mouseReleased(MouseEvent e) {
			setVisible(false);
			room.stop = false;
			room.requestFocus();
		}
	}

	public void paint(Graphics g) {
		Image img = Toolkit.getDefaultToolkit().getImage("resource/base/mission.png"); // 이미지 불러오기
		g.drawImage(img, 450, 120, 350, 500, this); // 이미지 입력
		int line0 = story.size();
		int line1 = mission.size();
		final int turm = 2;
		final int line = 5;
		final Point xy = new Point(500, 230);

		for (int i = 0; i < story.size(); i++) {
			if (i == 0) {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
				g.setColor(Color.RED);
				g.drawString(story.get(i).toString(), xy.x, xy.y);
			} else {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
				g.setColor(Color.BLACK);
				g.drawString(story.get(i).toString(), xy.x, xy.y + turm + (i * 20));
			}
		}

		for (int i = 0; i < mission.size(); i++) {
			if (i == 0) {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
				g.setColor(Color.RED);
				g.drawString(mission.get(0).toString(), xy.x, xy.y + ((line0 + 1) * 20));

			} else if (i == 1) {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 17));
				g.setColor(Color.BLUE);
				g.drawString(mission.get(1).toString(), xy.x, xy.y + ((line0 + 2) * 20));
			} else {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
				g.setColor(Color.BLACK);
				g.drawString(mission.get(i).toString(), xy.x, xy.y + turm + ((line0 + i + 1) * 20));
			}
		}

		for (int i = 0; i < hint.size(); i++) {
			if (i == 0) {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
				g.setColor(Color.RED);
				g.drawString(hint.get(i).toString(), xy.x, xy.y + ((line0 + line1 + 2) * 20));
			} else {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
				g.setColor(Color.BLACK);
				g.drawString(hint.get(i).toString(), xy.x, xy.y + turm + ((line0 + line1 + i + 2) * 20));
			}
		}
		label = new JLabel();
		Project.setLabelImage(label, "stage.png");
		label.addMouseListener(new VisibleFalse());
		label.setLocation(705, 470);
		add(label);
	}

	public void Init(int stage, int clearNum) {
		String line = null;
		Scanner scan = null;
		story = new ArrayList<String>();
		mission = new ArrayList<String>();
		hint = new ArrayList<String>();
		mission.add("mission");
		try {
			scan = new Scanner(new File("clearCase.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			if (line.charAt(0) == 'C') // 클리어 번호
				if (Integer.parseInt(line.split(" ")[1]) == clearNum) {
					line = scan.nextLine();
					while (line.charAt(0) != 'C' && scan.hasNextLine()) {
						mission.add(line);
						line = scan.nextLine();
					}
					if (!scan.hasNextLine())
						mission.add(line);
					break;
				}
		}
		try {
			scan = new Scanner(new File("clearAdd.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			if (line.charAt(0) == 'S') // 스테이지 번호
				if (Integer.parseInt(line.split(" ")[1]) == stage) {
					line = scan.nextLine();
					while (line.charAt(0) != 'S' && scan.hasNextLine() && !line.equals("hint")) {
						story.add(line);
						line = scan.nextLine();
					}
					while (line.charAt(0) != 'S' && scan.hasNextLine()) {
						hint.add(line);
						line = scan.nextLine();
					}
					break;
				}
		}

	}

}
