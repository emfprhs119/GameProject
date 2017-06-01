package Main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class Sound {	//����
	public enum Kind{background,Effect};
	AudioInputStream ais;	//������κ׽�Ʈ��
	Clip clip;	//Ŭ��
	float vol;	//����
	boolean execution;	//���࿩��
	boolean loop;	//��������
	private Kind kind;
	public Sound(String file,Kind kind,  boolean loop) {	//���� ������ ���� �̸��� �������� �޾ƿ�
		file=new String("resource/sound/"+file);
		try {
			ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.kind=kind;
		this.loop = loop;
		vol();
	}

	public void play() {	//���� ����
		vol();
		clip.setFramePosition(0);	//���� ������ġ�� 0�� ����
		clip.start();
		execution = true;
		if (loop){
			clip.loop(-1);
			//clip.stop();
		}
	}

	public void stop() {//���� ����
		if (execution)
			clip.stop();
		execution = false;
	}

	public void vol() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		if (kind==Kind.background)
			gainControl.setValue((float) ((Project.Sound_Background-6)*2));
		else if (kind==Kind.Effect)
			gainControl.setValue((float) ((Project.Sound_Effect-6)*2));
		vol = gainControl.getValue();
		if (vol<=(-12))
			vol=-80;
		gainControl.setValue(vol); // Reduce volume by 10 decibels.
	}
	
	/*
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
*/
}
