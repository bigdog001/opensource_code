/**
 * 
 */
package com.gauss.speex.encode;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * @author Gauss
 * 
 */
public class SpeexPlayer {
	private String fileName = null;
	private AudioTrack audioTrack;
	private Speex speex = new Speex();

	private BufferedInputStream buffInStream = null;

	public SpeexPlayer(String fileName) {

		this.fileName = fileName;
		System.out.println(this.fileName);
		speex.init();
	}

	public void startPlay() {
		RecordPlayThread rpt = new RecordPlayThread();

		Thread th = new Thread(rpt);
		th.start();
	}

	boolean isPlay = true;

	class RecordPlayThread extends Thread {

		public void run() {
			try {
				int bufsize = AudioTrack.getMinBufferSize(8000,// ÿ��8K����
						AudioFormat.CHANNEL_CONFIGURATION_STEREO,// ˫����

						AudioFormat.ENCODING_PCM_16BIT);// һ��������16����-2���ֽ�

				// ע�⣬����������Ƶ��֪ʶ��������������һ����buffer�Ĵ�С��

				// ����AudioTrack

				audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,

				AudioFormat.CHANNEL_CONFIGURATION_STEREO,

				AudioFormat.ENCODING_PCM_16BIT,

				bufsize,

				AudioTrack.MODE_STREAM);

				System.out.println("start to play.................");
				byte[] buffer = new byte[bufsize];
				audioTrack.play();// ��ʼ����
				buffInStream = new BufferedInputStream(new FileInputStream(fileName));
				System.out.println("Available bytes:" + buffInStream.available());
				int size = 0;
				short[] tempBuffer;
				while ((size = buffInStream.read(buffer)) != -1) {
					// out.write(buffer, 0, size);
					System.out.println("size=============" + size);
					tempBuffer = new short[size];
					speex.decode(buffer, tempBuffer, size);
					audioTrack.write(tempBuffer, 0, tempBuffer.length);
				}

			} catch (Exception t) {
				t.printStackTrace();
			} finally {
				audioTrack.stop();
				if (buffInStream != null)
					try {
						buffInStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	};
}
