#pragma once

#include "AudioSource.h"
#include "Wavetable.h"

namespace wavetablesynthesizer {

class OscBank : public AudioSource {
 public:
  OscBank() = default;

  void setup(float sampleRate, int oscNum, float maxAmp,
             float minFreq, float maxFreq); 	// Set parameters

  float getSample() override;

  virtual void setFrequency(float frequency);

  virtual void setAmplitude(float newAmplitude);

  void onPlaybackStopped() override;

 private:
  std::vector<Wavetable> oscillators;	// bank osc oscillators, each is a wavetable
  float oscAmplitude; // all osc have same amp
};
}  // namespace wavetablesynthesizer
