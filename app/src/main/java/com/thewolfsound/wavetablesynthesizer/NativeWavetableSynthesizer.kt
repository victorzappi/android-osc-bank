package com.thewolfsound.wavetablesynthesizer

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NativeWavetableSynthesizer : WavetableSynthesizer, DefaultLifecycleObserver {

  private var synthesizerHandle: Long = 0
  private val synthesizerMutex = Object()
  private external fun create(): Long
  private external fun delete(synthesizerHandle: Long)
  private external fun play(synthesizerHandle: Long)
  private external fun stop(synthesizerHandle: Long)
  private external fun isPlaying(synthesizerHandle: Long): Boolean
//  private external fun setFrequency(synthesizerHandle: Long, frequencyInHz: Float)
//  private external fun setVolume(synthesizerHandle: Long, amplitudeInDb: Float)
  //private external fun setWavetable(synthesizerHandle: Long, wavetable: Int)
  private external fun setBufferSize(synthesizerHandle: Long, bufferSize: Int)
  private external fun setOscNum(synthesizerHandle: Long, oscNum: Int)
  private external fun setMinFrequency(synthesizerHandle: Long, minFreq: Float)
  private external fun setMaxFrequency(synthesizerHandle: Long, maxFreq: Float)

  companion object {
    init {
      System.loadLibrary("wavetablesynthesizer")
    }
  }

  override fun onResume(owner: LifecycleOwner) {
    super.onResume(owner)

    synchronized(synthesizerMutex) {
      Log.d("NativeWavetableSynth", "onResume() called")
      createNativeHandleIfNotExists()
    }
  }

  override fun onPause(owner: LifecycleOwner) {
    super.onPause(owner)

    synchronized(synthesizerMutex) {
      Log.d("NativeWavetableSynth", "onPause() called")

      if (synthesizerHandle == 0L) {
        Log.e("NativeWavetableSynth", "Attempting to destroy a null synthesizer.")
        return
      }

      // Destroy the synthesizer
      delete(synthesizerHandle)
      synthesizerHandle = 0L
    }
  }

  override suspend fun play() = withContext(Dispatchers.Default) {
    synchronized(synthesizerMutex) {
      createNativeHandleIfNotExists()
      play(synthesizerHandle)
    }
  }

  override suspend fun stop() = withContext(Dispatchers.Default) {
    synchronized(synthesizerMutex) {
      createNativeHandleIfNotExists()
      stop(synthesizerHandle)
    }
  }

  override suspend fun isPlaying(): Boolean = withContext(Dispatchers.Default) {
    synchronized(synthesizerMutex) {
      createNativeHandleIfNotExists()
      return@withContext isPlaying(synthesizerHandle)
    }
  }

//  override suspend fun setFrequency(frequencyInHz: Float) = withContext(Dispatchers.Default) {
//    synchronized(synthesizerMutex) {
//      createNativeHandleIfNotExists()
//      setFrequency(synthesizerHandle, frequencyInHz)
//    }
//  }

//  override suspend fun setVolume(volumeInDb: Float) = withContext(Dispatchers.Default) {
//    synchronized(synthesizerMutex) {
//      createNativeHandleIfNotExists()
//      setVolume(synthesizerHandle, volumeInDb)
//    }
//  }

//  override suspend fun setWavetable(wavetable: Wavetable) = withContext(Dispatchers.Default) {
//    synchronized(synthesizerMutex) {
//      createNativeHandleIfNotExists()
//      setWavetable(synthesizerHandle, wavetable.ordinal)
//    }
//  }


  override suspend fun setBufferSize(bufferSize: Int) = withContext(Dispatchers.Default) {
    synchronized(synthesizerMutex) {
      createNativeHandleIfNotExists()
      setBufferSize(synthesizerHandle, bufferSize)
    }
  }

  override suspend fun setOscNum(oscNum: Int) = withContext(Dispatchers.Default) {
    synchronized(synthesizerMutex) {
      createNativeHandleIfNotExists()
      setOscNum(synthesizerHandle, oscNum)
    }
  }


  override suspend fun setMinFrequency(minFreq: Float) = withContext(Dispatchers.Default) {
      synchronized(synthesizerMutex) {
        createNativeHandleIfNotExists()
        setMinFrequency(synthesizerHandle, minFreq)
      }
    }

  override suspend fun setMaxFrequency(maxFreq: Float) = withContext(Dispatchers.Default) {
    synchronized(synthesizerMutex) {
      createNativeHandleIfNotExists()
      setMaxFrequency(synthesizerHandle, maxFreq)
    }
  }

  private fun createNativeHandleIfNotExists() {
    if (synthesizerHandle != 0L) {
      return
    }

    // create the synthesizer
    synthesizerHandle = create()
  }
}