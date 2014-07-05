package com.bigdog.mobile.android.Interphone.voice.ndk;

import android.util.Log;
import com.bigdog.mobile.android.Interphone.utils.SystemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/22/13
 * Time: 12:15 PM
 */
public class AudioCodec {
    static {
        System.loadLibrary("ilbc-codec");
        SystemUtil.log( " audioWraper库加载完毕");
    }
    // initialize decoder and encoder
    public static native int audio_codec_init(int mode);

    // encode
    public static native int audio_encode(byte[] sample, int sampleOffset,
                                          int sampleLength, byte[] data, int dataOffset);

    // decode
    public static native int audio_decode(byte[] data, int dataOffset,
                                          int dataLength, byte[] sample, int sampleLength);
}
