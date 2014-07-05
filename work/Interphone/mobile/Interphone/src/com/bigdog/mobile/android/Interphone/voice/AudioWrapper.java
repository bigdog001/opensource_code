package com.bigdog.mobile.android.Interphone.voice;

import com.bigdog.mobile.android.Interphone.voice.receiver.AudioReceiver;
import com.bigdog.mobile.android.Interphone.voice.sender.AudioRecorder;

public class AudioWrapper {

	private AudioRecorder audioRecorder;
	private AudioReceiver audioReceiver;

	private static AudioWrapper instanceAudioWrapper;

	private AudioWrapper() {
	}

	public static AudioWrapper getInstance() {
		if (null == instanceAudioWrapper) {
			instanceAudioWrapper = new AudioWrapper();
		}
		return instanceAudioWrapper;
	}

	public void startRecord() {
		if (null == audioRecorder) {
			audioRecorder = new AudioRecorder();
		}
		audioRecorder.startRecording();
	}

	public void stopRecord() {
		if (audioRecorder != null)
			audioRecorder.stopRecording();
	}

	public void startListen() {
		if (null == audioReceiver) {
			audioReceiver = new AudioReceiver();
		}
		audioReceiver.startRecieving();
	}

	public void stopListen() {
		if (audioRecorder != null)
			audioRecorder.stopRecording();
	}
}
