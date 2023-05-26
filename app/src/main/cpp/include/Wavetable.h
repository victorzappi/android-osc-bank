//
// Created by vic on 3/19/23.
//

#pragma once

#include <vector>

class Wavetable {
 public:
  Wavetable() {}													// Default constructor

  void setup(float sampleRate, std::vector<float>& table,			// Set parameters
             bool useInterpolation);

  void setFrequency(float f);	// Set the oscillator frequency
  float getFrequency();		// Get the oscillator frequency

  float process();				// Get the next sample and update the phase

  ~Wavetable() {}				// Destructor

 private:
  std::vector<float> table_;	// Buffer holding the wavetable

  float inverseSampleRate_;	// 1 divided by the audio sample rate
  float frequency_;			// Frequency of the oscillator
  float readPointer_;			// Location of the read pointer (phase of oscillator)
  bool useInterpolation_;		// Whether to use linear interpolation
};
