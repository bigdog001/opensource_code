/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec */

#ifndef _Included_com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec
#define _Included_com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec
 * Method:    audio_codec_init
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec_audio_1codec_1init
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec
 * Method:    audio_encode
 * Signature: ([BII[BI)I
 */
JNIEXPORT jint JNICALL Java_com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec_audio_1encode
  (JNIEnv *, jclass, jbyteArray, jint, jint, jbyteArray, jint);

/*
 * Class:     com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec
 * Method:    audio_decode
 * Signature: ([BII[BI)I
 */
JNIEXPORT jint JNICALL Java_com_bigdog_mobile_android_Interphone_voice_ndk_AudioCodec_audio_1decode
  (JNIEnv *, jclass, jbyteArray, jint, jint, jbyteArray, jint);

#ifdef __cplusplus
}
#endif
#endif
