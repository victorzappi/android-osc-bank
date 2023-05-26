#include "OscBank.h"
#include <cmath>
#include "Log.h"

namespace wavetablesynthesizer {

constexpr auto WAVETABLE_SIZE = 512;

void OscBank::setup(float sampleRate, int oscNum, float maxAmp, float minFreq, float maxFreq)
{
  // at least one osc!
  if(oscNum < 1)
    oscNum = 1;


  std::vector<float> wavetable;

  wavetable.resize(WAVETABLE_SIZE);
  for(unsigned int n = 0; n < wavetable.size(); n++) {
    wavetable[n] = sinf(2.0 * M_PI * (float)n / (float)wavetable.size());
  }

  float freqInc = 0;
  if(oscNum > 1)
    freqInc = (maxFreq-minFreq) / (oscNum-1);

  oscillators.clear();

  // Create an array of oscillators
  for(unsigned int n = 0; n < oscNum; n++) {
    Wavetable oscillator;
    oscillator.setup(sampleRate, wavetable, true);
    oscillator.setFrequency(minFreq + n*freqInc);
    oscillators.push_back(oscillator);
  }

  LOGD("Number of oscillators %d", oscillators.size());


  // when summed, all osc will yield to the max amp value that was selected
  oscAmplitude = maxAmp/oscNum;
}

float OscBank::getSample() {
//  index = std::fmod(index, static_cast<float>(waveTable.size()));
//  const auto sample = interpolateLinearly();
//  index += indexIncrement;
//  return amplitude * sample;
  float out = 0;

  for(unsigned int n = 0; n < oscillators.size(); n++)
    out += oscAmplitude * oscillators[n].process();

  return out;
}

void OscBank::setFrequency(float frequency) {
//  indexIncrement = frequency * static_cast<float>(waveTable.size()) /
//                   static_cast<float>(sampleRate);
}

void OscBank::onPlaybackStopped() {
  //index = 0.f;
}


void OscBank::setAmplitude(float newAmplitude) {
  //amplitude.store(newAmplitude);
}
}  // namespace wavetablesynthesizer
