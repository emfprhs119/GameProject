package Main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {	//����
	AudioInputStream ais;	//������κ׽�Ʈ��
	Clip clip;	//Ŭ��
	float vol;	//����
	boolean execution;	//���࿩��
	boolean loop;	//��������
	public Sound(String file, boolean loop) {	//���� ������ ���� �̸��� �������� �޾ƿ�
		try {
			ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.loop = loop;
		vol();
	}

	public void play() {	//���� ����
		clip.setFramePosition(0);	//���� ������ġ�� 0�� ����
		clip.start();
		execution = true;
		if (loop){
			clip.loop(-1);
			clip.stop();
		}
	}

	public void stop() {//���� ����
		if (execution)
			clip.stop();
		execution = false;
	}

	public void volUp() { // �ִ� ���� 6, �⺻ 0
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		vol += 0.5;
		gainControl.setValue(vol); // Reduce volume by 10 decibels.
	}

	public void volDown() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		vol -= 0.5;
		gainControl.setValue(vol); // Reduce volume by 10 decibels.
	}

	public void vol() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		vol = gainControl.getValue();
		gainControl.setValue(vol); // Reduce volume by 10 decibels.
	}
}
