package Main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {	//사운드
	AudioInputStream ais;	//오디오인붓스트림
	Clip clip;	//클립
	float vol;	//볼륨
	boolean execution;	//실행여부
	boolean loop;	//루프여부
	public Sound(String file, boolean loop) {	//사운드 생성자 파일 이름과 루프여부 받아옴
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

	public void play() {	//사운드 실행
		clip.setFramePosition(0);	//사운드 시작위치를 0로 설정
		clip.start();
		execution = true;
		if (loop){
			clip.loop(-1);
			clip.stop();
		}
	}

	public void stop() {//사운드 멈춤
		if (execution)
			clip.stop();
		execution = false;
	}

	public void volUp() { // 최대 볼륨 6, 기본 0
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
