package com.bigdog.mobile.android.Interphone.voice.receiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.util.Log;

import com.bigdog.mobile.android.Interphone.voice.data.AudioData;
import com.bigdog.mobile.android.Interphone.utils.SystemUtil;
import com.bigdog.mobile.android.Interphone.voice.AudioConfig;

public class AudioPlayer implements Runnable {
	String LOG = "AudioPlayer ";
	private static AudioPlayer player;

	private List<AudioData> dataList = null;
	private AudioData playData;
	private boolean isPlaying = false;

	private AudioTrack audioTrack;
    private Thread play_thread ;
    private Object thread_lock = new Object();

    public Object getThread_lock() {
        return thread_lock;
    }

    //
	private File file;
	private FileOutputStream fos;

	private AudioPlayer() {
		dataList = Collections.synchronizedList(new LinkedList<AudioData>());

		file = new File("/sdcard/audio/decode.amr");
		try {
			if (!file.exists())
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static AudioPlayer getInstance() {
		if (player == null) {
			player = new AudioPlayer();
		}
		return player;
	}

	public void addData(byte[] rawData, int size) {
		AudioData decodedData = new AudioData();
		decodedData.setSize(size);
		byte[] tempData = new byte[size];
		System.arraycopy(rawData, 0, tempData, 0, size);
		decodedData.setRealData(tempData);
		dataList.add(decodedData);
        synchronized (thread_lock) {
                thread_lock.notify();
        }



        SystemUtil.log( "Player添加一次数据 " + dataList.size());
	}

	/*
	 * init Player parameters
	 */
	/*private boolean initAudioTrack() {
		int bufferSize = AudioRecord.getMinBufferSize(AudioConfig.SAMPLERATE,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
		if (bufferSize < 0) {
            SystemUtil.log( LOG + "initialize error!buffer size:"+bufferSize);
			return false;
		}
        SystemUtil.log("Player初始化的 buffersize是 " + bufferSize);
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				AudioConfig.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                bufferSize, AudioTrack.MODE_STREAM);
		// set volume:设置播放音量
		audioTrack.setStereoVolume(1.0f, 1.0f);
		audioTrack.play();
		return true;
	}
    */
    private static final int sampleRate = AudioConfig.SAMPLERATE;
    // 注意：参数配置
    private static final int channelConfig = AudioConfig.PLAYER_CHANNEL_CONFIG;
    private static final int audioFormat = AudioConfig.AUDIO_FORMAT;
    private boolean initAudioTrack() {
        int bufferSize = AudioRecord.getMinBufferSize(sampleRate,
                channelConfig, audioFormat);
        if (bufferSize < 0) {
            SystemUtil.log( LOG + "initialize error!");
            return false;
        }
        SystemUtil.log( LOG + "buffer size is:"+bufferSize);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                channelConfig, audioFormat, bufferSize*5, AudioTrack.MODE_STREAM);
        // set volume:设置播放音量
//        audioTrack.setStereoVolume(1.0f, 1.0f);
        audioTrack.play();
        return true;
    }

	private void playFromList() throws IOException {
		while (isPlaying) {

			while (dataList.size() > 0) {
				playData = dataList.remove(0);
				SystemUtil.log( "play data fragment: " + dataList.size());
				audioTrack.write(playData.getRealData(), 0, playData.getSize());
//				fos.write(playData.getRealData(), 0, playData.getSize());
//				fos.flush();
			}
            synchronized(thread_lock){
            try {
                thread_lock.wait();
                continue;
            } catch (InterruptedException e) {
               SystemUtil.log("play thread error:"+e.getMessage());
            }
            }

        }
	}

	public void startPlaying() {
		if (isPlaying) {
            SystemUtil.log( "验证播放器是否打开" + isPlaying);
			return;
		}
        play_thread = new Thread(this);
        play_thread.start();
	}

	public void run() {
		this.isPlaying = true;
		if (!initAudioTrack()) {
            SystemUtil.log( "播放器初始化失败");
			return;
		}
        SystemUtil.log( "开始播放");
		try {
			playFromList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// while (isPlaying) {
		// if (dataList.size() > 0) {
		// playFromList();
		// } else {
		//
		// }
		// }
		if (this.audioTrack != null) {
			if (this.audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
				this.audioTrack.stop();
				this.audioTrack.release();
			}
		}
        SystemUtil.log( LOG + "end playing");
	}

	public void stopPlaying() {
		this.isPlaying = false;
	}
}
