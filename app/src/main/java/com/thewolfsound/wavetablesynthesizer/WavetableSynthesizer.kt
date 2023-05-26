 package com.thewolfsound.wavetablesynthesizer

import androidx.annotation.StringRes

enum class Wavetable {
  SINE {
    @StringRes
    override fun toResourceString(): Int {
      return R.string.sine
    }
  },

  TRIANGLE {
    @StringRes
    override fun toResourceString(): Int {
      return R.string.triangle
    }
  },

  SQUARE {
    @StringRes
    override fun toResourceString(): Int {
      return R.string.square
    }
  },

  SAW {
    @StringRes
    override fun toResourceString(): Int {
      return R.string.sawtooth
    }
  };

  @StringRes
  abstract fun toResourceString(): Int
}

interface WavetableSynthesizer {
  suspend fun play()
  suspend fun stop()
  suspend fun isPlaying() : Boolean
//  suspend fun setFrequency(frequencyInHz: Float)
//  suspend fun setVolume(volumeInDb: Float)
  suspend fun setBufferSize(bufferSize: Int)
  suspend fun setOscNum(oscNum: Int)
  suspend fun setMinFrequency(minFreq: Float)
  suspend fun setMaxFrequency(maxFreq: Float)
}