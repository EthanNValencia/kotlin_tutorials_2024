package com.nt.unitconverter_006

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nt.unitconverter_006.ui.theme.UnitConverter_006Theme
import androidx.compose.ui.text.TextStyle
import kotlin.math.roundToInt
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverter_006Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

data class Controls(var selectOne: String  = "Select", var selectTwo: String = "Select")

data class Units(
    val centimeters: String = "Centimeters",
    val meters: String = "Meters",
    val feet: String = "Feet",
    val millimeters: String = "Millimeters"
) {
    val labelArray = arrayListOf(centimeters, meters, feet, millimeters)

    fun conversionFactor(unit: String): Double {
        if(unit == centimeters) {
            return 0.01;
        }
        if(unit == meters) {
            return 1.00;
        }
        if(unit == feet) {
            return 0.3048;
        }
        if(unit == millimeters) {
            return 0.001;
        }
        println("An error occurred! Provided unit was: $unit")
        return 0.0;
    }
}

@Composable
fun UnitConverter() {
    val units = Units()
    var textInput by remember { mutableStateOf("") }
    var calculation by remember { mutableStateOf(0.0) }
    var selectOne by remember { mutableStateOf(units.centimeters) }
    var selectTwo by remember { mutableStateOf(units.feet) }
    var selectOneExpanded by remember { mutableStateOf(false) }
    var selectTwoExpanded by remember { mutableStateOf(false) }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 34.sp,
        color = Color.DarkGray
    )

    fun convertUnits() {
        val inputValueToDouble = textInput.toDoubleOrNull() ?: 0.0
        val result = (inputValueToDouble * units.conversionFactor(selectOne) * 100.0 / units.conversionFactor(selectTwo)).roundToInt() / 100.0
        calculation = result
    }

    fun setSelectOne(option: String) {
        selectOne = option
        selectOneExpanded = !selectOneExpanded
        convertUnits()
    }

    fun setSelectTwo(option: String) {
        selectTwo = option
        selectTwoExpanded = !selectTwoExpanded
        convertUnits()
    }

    fun processTextInput(inputVal: String) {
        if(inputVal.isEmpty()) {
            textInput = ""
        } else { // not empty, process input
            val lastCharInInputVal = inputVal.lastOrNull().toString()
            val checkIfDouble = lastCharInInputVal?.toDoubleOrNull()
            if (checkIfDouble != null) {
                textInput = inputVal
            } else {
                textInput = inputVal.dropLast(1)
            }
        }
        convertUnits()
    }
    Column {
        Text("Available units: $units")
        Text("textInput: $textInput")
        Text("calculation: $calculation")
        Text("selectOne: $selectOne")
        Text("selectTwo: $selectTwo")
        Text("selectOneExpanded: $selectOneExpanded")
        Text("selectTwoExpanded: $selectTwoExpanded")
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Unit Converter", style = customTextStyle)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = textInput, onValueChange = { processTextInput(it) }, label = { Text(text = "Enter number(s)")})
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box {
                Button(onClick = { selectOneExpanded = !selectOneExpanded }) {
                    Text(selectOne)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Drop Down")
                }
                DropdownMenu(expanded = selectOneExpanded, onDismissRequest = { selectOneExpanded = !selectOneExpanded }) {
                    units.labelArray.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = { setSelectOne(label) })
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { selectTwoExpanded = !selectTwoExpanded }) {
                    Text(selectTwo)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Drop Down")
                }
                DropdownMenu(expanded = selectTwoExpanded, onDismissRequest = { selectTwoExpanded = !selectTwoExpanded }) {
                    units.labelArray.forEach { label ->
                        DropdownMenuItem(text = { Text(text = label) }, onClick = { setSelectTwo(label) })
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Result: $calculation", style = MaterialTheme.typography.headlineMedium)
    }
    // The row below is detached from the column above.
    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom) {
        ButtonWithToast()
    }
}

@Composable
fun ButtonWithToast() {
    val context = LocalContext.current
    Button(onClick = { toastPopUp(context, "Having fun?") }) {
        Text("Click Me")
    }
}

fun toastPopUp(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}