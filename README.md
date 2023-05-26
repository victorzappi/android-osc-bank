# Oscillator Bank Android App

This repository contains the source code of the oscillator bank Android app used to analyze the computational audio performance of phones in the paper:

_Victor Zappi and Carla Sophie Tapparo, "Upcycling Android Phones into Embedded Audio Platforms", Proceedings of the 26th International Conference on Digital Audio Effects (DAFx23), Copenhagen, Denmark, 4 - 7 September 2023._

In the paper, results from the Android app are compared with results obtained by running the same synthesis code via the C++ LDSP environment:
[https://github.com/victorzappi/LDSP.git](https://github.com/victorzappi/LDSP.git)


The Android app is based on the Wavetable Synthesizer Android App By Jan Wilczek from TheWolfSound.com:
[https://github.com/JanWilczek/android-wavetable-synthesizer](https://github.com/JanWilczek/android-wavetable-synthesizer)


## Setting Up Signing Configurations

This project uses a `gradle.properties` file to handle signing configurations. To set up your own signing configurations, follow these steps:

1. **Create a `gradle.properties` file in your user home directory**. This file should be located at `~/.gradle/gradle.properties` on Unix-based systems or `C:\Users\<Your username>\.gradle\gradle.properties` on Windows.

2. **Add your signing configurations to the `gradle.properties` file**. Here's an example of what your `gradle.properties` file might look like:

    ```
    OSCBANK_STORE_FILE=/path/to/your/storefile.keystore
    OSCBANK_RELEASE_STORE_PASSWORD=your_keystore_password
    OSCBANK_KEY_ALIAS=your_key_alias
    OSCBANK_KEY_PASSWORD=your_key_password
    ```

    Replace `/path/to/your/storefile.keystore` with the path to your keystore file, replace `your_keystore_password`, `your_key_alias` and `your_key_password` with your actual keystore password, key alias and key password, respectively.

After you've set up your `gradle.properties` file, you should be able to build the project in Android Studio.

![Setting Up Signing Configurations](https://showme.redstarplugin.com/s/hHvtnIUN)

If you encounter any issues or have any questions, feel free to contact me.


