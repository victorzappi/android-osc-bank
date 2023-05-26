package com.thewolfsound.wavetablesynthesizer

import android.content.pm.ActivityInfo
//import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.rotate
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewolfsound.wavetablesynthesizer.ui.theme.WavetableSynthesizerTheme


class MainActivity : ComponentActivity() {

  private val synthesizer = NativeWavetableSynthesizer()
  private val synthesizerViewModel: WavetableSynthesizerViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    lifecycle.addObserver(synthesizer)
    // pass the synthesizer to the ViewModel
    synthesizerViewModel.wavetableSynthesizer = synthesizer
    setContent {
      WavetableSynthesizerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          // pass the ViewModel down the composables' hierarchy
          WavetableSynthesizerApp(Modifier, synthesizerViewModel)
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycle.removeObserver(synthesizer)
  }

  override fun onResume() {
    super.onResume()
    synthesizerViewModel.applyParameters()
  }
}

@Composable
fun WavetableSynthesizerApp(
  modifier: Modifier,
  synthesizerViewModel: WavetableSynthesizerViewModel = viewModel()
) {
    Column(
      modifier = modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Top,
    ) {
      SettingsPanel(modifier, synthesizerViewModel)
      PlayControl(modifier, synthesizerViewModel)
    }
}



@Composable
private fun SettingsPanel(
  modifier: Modifier,
  synthesizerViewModel: WavetableSynthesizerViewModel
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight(0.8f),
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(
      modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(),
      verticalArrangement = Arrangement.SpaceEvenly,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      Row(
        modifier = modifier
          .fillMaxWidth()
          .fillMaxHeight(0.1f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(stringResource(R.string.app_name))
      }

      Row(
        modifier = modifier
          .fillMaxWidth()
          .fillMaxHeight(0.45f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {

        // Buffer size text entry
        NumberIntEntry(
          modifier = Modifier.width(200.dp),
          onValueEntered = { value ->
            synthesizerViewModel.setBufferSize(value)
          },
          labeltext = "Buffer size (max 5 digits)",
          startvalue = "256"
        )

        // Number of oscillators text entry
        NumberIntEntry(
          modifier = Modifier.width(200.dp),
          onValueEntered = { value ->
            synthesizerViewModel.setOscNum(value)
          },
          labeltext = "Num of osc (max 5 digits)",
          startvalue = "2"
        )
      }
      Row(
        modifier = modifier
          .fillMaxWidth()
          .fillMaxHeight(0.45f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {

        // Minimun frequency text entry
        NumberFloatEntry(
          modifier = Modifier.width(200.dp),
          onValueEntered = { value ->
            synthesizerViewModel.setMinFrequency(value)
          },
          labeltext = "Minimum frequency",
          startvalue = "440.0"
        )

        // Maximum frequency text entry
        NumberFloatEntry(
          modifier = Modifier.width(200.dp),
          onValueEntered = { value ->
            synthesizerViewModel.setMaxFrequency(value)
          },
          labeltext = "Maximum frequency",
          startvalue = "1000"
        )

      }

      //WavetableSelectionButtons(modifier, synthesizerViewModel)
    }
  }
}

@Composable
fun NumberIntEntry(
  modifier: Modifier = Modifier,
  labeltext: String,
  startvalue: String,
  onValueEntered: (Int) -> Unit
) {
  var textState by remember { mutableStateOf(startvalue) }
  val focusManager = LocalFocusManager.current

  TextField(
    value = textState,
    onValueChange = { newValue ->
      if (newValue.length <= 5 && newValue.all { it.isDigit() }) {
        textState = newValue
      }
    },
    label = { Text( labeltext) },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number,
      imeAction = ImeAction.Done
    ),
    keyboardActions = KeyboardActions(
      onDone = {
        val enteredValue = textState.toIntOrNull() ?: 1
        onValueEntered(enteredValue)
        textState = enteredValue.toString()
        focusManager.clearFocus()
      }
    ),
    modifier = modifier.width(200.dp)
  )
}

@Composable
fun NumberFloatEntry(
  modifier: Modifier = Modifier,
  labeltext: String,
  startvalue: String,
  onValueEntered: (Float) -> Unit
) {
  var textState by remember { mutableStateOf(startvalue) }
  val focusManager = LocalFocusManager.current

  TextField(
    value = textState,
    onValueChange = { newValue ->
      textState = newValue
    },
    label = { Text(labeltext) },
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Number,
      imeAction = ImeAction.Done
    ),
    keyboardActions = KeyboardActions(
      onDone = {
        val enteredValue = textState.toFloatOrNull() ?: 0f
        onValueEntered(enteredValue)
        textState = enteredValue.toString()
        focusManager.clearFocus()
      }
    ),
    modifier = modifier
  )
}


@Composable
private fun PlayControl(modifier: Modifier, synthesizerViewModel: WavetableSynthesizerViewModel) {
  // The label of the play button is now an observable state, an instance of State<Int?>.
  // State<Int?> is used because the label is the id value of the resource string.
  // Thanks to the fact that the composable observes the label,
  // the composable will be recomposed (redrawn) when the observed state changes.
  val playButtonLabel = synthesizerViewModel.playButtonLabel.observeAsState()

  PlayControlContent(modifier = modifier,
    // onClick handler now simply notifies the ViewModel that it has been clicked
    onClick = {
      synthesizerViewModel.playClicked()
    },
    // playButtonLabel will never be null; if it is, then we have a serious implementation issue
    buttonLabel = stringResource(playButtonLabel.value!!))
}

@Composable
private fun PlayControlContent(modifier: Modifier, onClick: () -> Unit, buttonLabel: String) {
  Button(modifier = modifier,
    onClick = onClick) {
    Text(buttonLabel)
  }
}


//@Composable
//private fun WavetableSelectionButtons(
//  modifier: Modifier,
//  synthesizerViewModel: WavetableSynthesizerViewModel
//) {
//  Row(
//    modifier = modifier.fillMaxWidth(),
//    horizontalArrangement = Arrangement.SpaceEvenly
//  ) {
//    for (wavetable in Wavetable.values()) {
//      WavetableButton(
//        modifier = modifier,
//        // update the ViewModel when the given wavetable is clicked
//        onClick = {
//          //synthesizerViewModel.setWavetable(wavetable)
//        },
//        // set the label to the resource string that corresponds to the wavetable
//        label = stringResource(wavetable.toResourceString()),
//      )
//    }
//  }
//}
//
//@Composable
//private fun WavetableButton(
//  modifier: Modifier,
//  onClick: () -> Unit,
//  label: String,
//) {
//  Button(modifier = modifier, onClick = onClick) {
//    Text(label)
//  }
//}

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 1024, heightDp = 720)
@Composable
fun WavetableSynthesizerPreview() {
  WavetableSynthesizerTheme {
    WavetableSynthesizerApp(Modifier, WavetableSynthesizerViewModel())
  }
}
