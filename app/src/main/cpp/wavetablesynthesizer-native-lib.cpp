#include <jni.h>
#include <memory>
#include "Log.h"
#include "WavetableSynthesizer.h"

extern "C" {
JNIEXPORT jlong JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_create(
    JNIEnv* env,
    jobject obj) {
  auto synthesizer =
      std::make_unique<wavetablesynthesizer::WavetableSynthesizer>();

  if (not synthesizer) {
    LOGD("Failed to create the synthesizer.");
    synthesizer.reset(nullptr);
  }

  return reinterpret_cast<jlong>(synthesizer.release());
}

JNIEXPORT void JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_delete(
    JNIEnv* env,
    jobject obj,
    jlong synthesizerHandle) {
  auto* synthesizer =
      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
          synthesizerHandle);

  if (not synthesizer) {
    LOGD("Attempt to destroy an unitialized synthesizer.");
    return;
  }

  delete synthesizer;
}

JNIEXPORT void JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_play(
    JNIEnv* env,
    jobject obj,
    jlong synthesizerHandle) {
  auto* synthesizer =
      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
          synthesizerHandle);

  if (synthesizer) {
    synthesizer->play();
  } else {
    LOGD(
        "Synthesizer not created. Please, create the synthesizer first by "
        "calling create().");
  }
}

JNIEXPORT void JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_stop(
    JNIEnv* env,
    jobject obj,
    jlong synthesizerHandle) {
  auto* synthesizer =
      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
          synthesizerHandle);

  if (synthesizer) {
    synthesizer->stop();
  } else {
    LOGD(
        "Synthesizer not created. Please, create the synthesizer first by "
        "calling create().");
  }
}

JNIEXPORT jboolean JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_isPlaying(
    JNIEnv* env,
    jobject obj,
    jlong synthesizerHandle) {
  auto* synthesizer =
      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
          synthesizerHandle);

  if (not synthesizer) {
    LOGD(
        "Synthesizer not created. Please, create the synthesizer first by "
        "calling create().");
    return false;
  }

  return synthesizer->isPlaying();
}

//JNIEXPORT void JNICALL
//Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_setFrequency(
//    JNIEnv* env,
//    jobject obj,
//    jlong synthesizerHandle,
//    jfloat frequencyInHz) {
//  auto* synthesizer =
//      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
//          synthesizerHandle);
//  const auto nativeFrequency = static_cast<float>(frequencyInHz);
//
//  if (synthesizer) {
//    synthesizer->setFrequency(nativeFrequency);
//  } else {
//    LOGD(
//        "Synthesizer not created. Please, create the synthesizer first by "
//        "calling create().");
//  }
//}

//JNIEXPORT void JNICALL
//Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_setVolume(
//    JNIEnv* env,
//    jobject obj,
//    jlong synthesizerHandle,
//    jfloat volumeInDb) {
//  auto* synthesizer =
//      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
//          synthesizerHandle);
//  const auto nativeVolume = static_cast<float>(volumeInDb);
//
//  if (synthesizer) {
//    synthesizer->setVolume(nativeVolume);
//  } else {
//    LOGD(
//        "Synthesizer not created. Please, create the synthesizer first by "
//        "calling create().");
//  }
//}

//JNIEXPORT void JNICALL
//Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_setWavetable(
//        JNIEnv* env,
//        jobject obj,
//        jlong synthesizerHandle,
//        jint wavetable) {
//    auto* synthesizer =
//            reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
//                    synthesizerHandle);
//    const auto nativeWavetable = static_cast<wavetablesynthesizer::Wavetable>(wavetable);
//
//    if (synthesizer) {
//        synthesizer->setWavetable(nativeWavetable);
//    } else {
//        LOGD(
//                "Synthesizer not created. Please, create the synthesizer first by "
//                "calling create().");
//    }
//}

JNIEXPORT void JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_setBufferSize__JI(
    JNIEnv *env,
    jobject thiz,
    jlong synthesizerHandle,
    jint buffer_size) {
  auto* synthesizer =
      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
          synthesizerHandle);
  const auto nativeBufferSize = static_cast<int>(buffer_size);

  if (synthesizer) {
    synthesizer->setBufferSize(nativeBufferSize);
  } else {
    LOGD(
        "Synthesizer not created. Please, create the synthesizer first by "
        "calling create().");
  }
}

JNIEXPORT void JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_setOscNum(
      JNIEnv *env,
      jobject thiz,
      jlong synthesizerHandle,
      jint osc_num) {
  auto* synthesizer =
      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
          synthesizerHandle);
  const auto nativeOscNum = static_cast<int>(osc_num);

  if (synthesizer) {
    synthesizer->setOscNum(nativeOscNum);
  } else {
    LOGD(
        "Synthesizer not created. Please, create the synthesizer first by "
        "calling create().");
  }
}

JNIEXPORT void JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_setMinFrequency(
    JNIEnv *env,
    jobject thiz,
    jlong synthesizerHandle,
    jfloat min_freq) {
  auto* synthesizer =
      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
          synthesizerHandle);
  const auto nativeMinFreq = static_cast<float>(min_freq);

  if (synthesizer) {
    synthesizer->setMinFrequency(nativeMinFreq);
  } else {
    LOGD(
        "Synthesizer not created. Please, create the synthesizer first by "
        "calling create().");
  }
}

JNIEXPORT void JNICALL
Java_com_thewolfsound_wavetablesynthesizer_NativeWavetableSynthesizer_setMaxFrequency(
    JNIEnv *env,
    jobject thiz,
    jlong synthesizerHandle,
    jfloat max_freq) {
  auto* synthesizer =
      reinterpret_cast<wavetablesynthesizer::WavetableSynthesizer*>(
          synthesizerHandle);
  const auto nativeMaxFreq = static_cast<float>(max_freq);

  if (synthesizer) {
    synthesizer->setMaxFrequency(nativeMaxFreq);
  } else {
    LOGD(
        "Synthesizer not created. Please, create the synthesizer first by "
        "calling create().");
  }
}

}
