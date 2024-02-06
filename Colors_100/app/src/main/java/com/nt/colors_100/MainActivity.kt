package com.nt.colors_100

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.ImageColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.nt.colors_100.ui.theme.Colors_100Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Colors_100Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // BoxWrap { Example01() }
                    // BoxWrap { Example02() }
                    BoxWrap { Example03() }
                }
            }
        }
    }
}

@Composable
fun BoxWrap(content: @Composable () -> Unit) {
    Box(modifier = Modifier
        .width(250.dp)
        .height(250.dp)) {
        content()
    }
}

@Composable
fun Example01() {
    val controller = rememberColorPickerController()
    ImageColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        paletteImageBitmap = ImageBitmap.imageResource(R.drawable.palettebar),
        controller = controller
    )
}

@Composable
fun Example02() {
    val controller = rememberColorPickerController()
    HsvColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
        controller = controller,
        onColorChanged = { colorEnvelope: ColorEnvelope ->
            // do something
        }
    )
}

@Composable
fun HsvColorPicker01(controller: ColorPickerController, colorChange: (colorEnvelope: ColorEnvelope) -> Unit) {
    HsvColorPicker(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(10.dp),
        controller = controller,
        onColorChanged = { colorEnvelope: ColorEnvelope ->
            colorChange(colorEnvelope)
        }
    )
}

@Composable
fun ColoredBox(color: Color, hexCode: String) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(color = color)) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Color: ${hexCode}")
        }
    }
}


@Composable
fun Example03() {
    val color = remember { mutableStateOf(ColorEnvelope(color= Color(1.0f, 1.0f, 1.0f, 1.0f), hexCode="FFFFFFFF", fromUser=false)) }
    val alphaController = rememberColorPickerController()
    val brightnessController = rememberColorPickerController()
    val colorPickerController = rememberColorPickerController()

    fun colorChange(colorEnvelope: ColorEnvelope) {
        color.value = colorEnvelope
        // alphaController.setWheelColor(colorEnvelope.color)
        // brightnessController.setWheelColor(colorEnvelope.color)
        alphaController.selectByColor(colorEnvelope.color, true)
        Log.d("Ethan", colorEnvelope.toString())
    }

    Column {
        HsvColorPicker01(controller = colorPickerController, colorChange = ::colorChange)
        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = alphaController,
            wheelColor = color.value.color,
            initialColor = color.value.color
        )
        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = brightnessController,
            wheelColor = color.value.color,
            initialColor = color.value.color
        )
        ColoredBox(color = color.value.color, hexCode = color.value.hexCode)
    }

}