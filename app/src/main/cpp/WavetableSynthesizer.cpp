#include "WavetableSynthesizer.h"
#include <cmath>
#include "Log.h"
#include "OboeAudioPlayer.h"
#include "OscBank.h"

namespace wavetablesynthesizer {
float dBToAmplitude(float dB) {
  return std::pow(10.f, dB / 20.f);
}

WavetableSynthesizer::WavetableSynthesizer()
    : _oscillatorBank{std::make_shared<OscBank>()},
      _audioPlayer{
          std::make_unique<OboeAudioPlayer>(_oscillatorBank, samplingRate)} {}

WavetableSynthesizer::~WavetableSynthesizer() = default;

bool WavetableSynthesizer::isPlaying() const {
  LOGD("isPlaying() called");
  return _isPlaying;
}

void WavetableSynthesizer::play() {
  LOGD("play() called");

  _oscillatorBank->setup(samplingRate, _oscNum, _maxAmp, _minFreq, _maxFreq);

  std::lock_guard<std::mutex> lock(_mutex);
  const auto result = _audioPlayer->play();
  if (result == 0) {
    _isPlaying = true;
  } else {
    LOGD("Could not start playback.");
  }
}

void WavetableSynthesizer::setBufferSize(int bufferSize) {
  LOGD("Buffer size set to %d.", bufferSize);
  _audioPlayer->setBufferSize(bufferSize);
}
void WavetableSynthesizer::setOscNum(int oscNum) {
  LOGD("Number of oscillators set to %d", oscNum);
  _oscNum = oscNum;
}
void WavetableSynthesizer::setMinFrequency(float minFreq) {
  LOGD("Min frequency set to %.2f Hz.", minFreq);
  _minFreq = minFreq;
}
void WavetableSynthesizer::setMaxFrequency(float maxFreq) {
  LOGD("Max frequency set to %.2f Hz.", maxFreq);
  _maxFreq = maxFreq;
}

void WavetableSynthesizer::stop() {
  LOGD("stop() called");
  std::lock_guard<std::mutex> lock(_mutex);
  _audioPlayer->stop();
  _isPlaying = false;
}

}  // namespace wavetablesynthesizer
