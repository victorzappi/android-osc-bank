#pragma once

#include <memory>
#include <mutex>

namespace wavetablesynthesizer {
class OscBank;

class OboeAudioPlayer;

constexpr auto samplingRate = 48000;

class WavetableSynthesizer {
 public:
  WavetableSynthesizer();

  ~WavetableSynthesizer();

  void play();

  void stop();

  bool isPlaying() const;

  void setBufferSize(int bufferSize);

  void setOscNum(int oscNum);

  void setMinFrequency(float minFreq);

  void setMaxFrequency(float maxFreq);

 private:
  std::atomic<bool> _isPlaying{false};
  std::mutex _mutex;
  std::shared_ptr<OscBank> _oscillatorBank;
  std::unique_ptr<OboeAudioPlayer> _audioPlayer;
  int _oscNum = 2; // two oscillators
  float _maxAmp = 0.5;
  float _minFreq = 440; // one at A4
  float _maxFreq = 1000; // the other a semitone apart
};
}  // namespace wavetablesynthesizer
