package com.uticodes.compose_otp_input_field

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uticodes.compose_otp_input_field.ui.theme.Compose_otp_input_fieldTheme

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_otp_input_fieldTheme {
                Scaffold() {
                    val value = listOf("","","","","")
                    var pinString by remember { mutableStateOf("") }
                    var isInvalidPin by remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 38.dp,
                                end = 38.dp
                            )
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PinFields(
                            value = pinString,
                            length = 3,
                            isInvalidPin = isInvalidPin,
                            onValueChanged = {  pinString = it
                            } )
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun PinFields(
    value: String,
    length: Int = 1,
    isInvalidPin : Boolean = false,
    onValueChanged: (String) -> Unit,
) {
    var textValue by remember { mutableStateOf(TextFieldValue()) }
    var text by remember { mutableStateOf(ArrayList<String>()) }
    text.add(value)

    Surface(
        Modifier
            .fillMaxWidth()
            .heightIn(70.dp, 80.dp)
        //shape = RoundedCornerShape(50),
        //color = VendorGrayC
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            val focusManager = LocalFocusManager.current

            for (i in 0 .. length) {
                TextField(
                    value = if(value.isNotEmpty()) text[i] else "",
                    onValueChange = onValueChanged.apply {
                        when {
                            ( value.length == 1)  -> focusManager.moveFocus(FocusDirection.Right)
                        }
                    },
                    modifier = Modifier
                        //.size(50.dp)
                        .width(70.dp)
                        .height(50.dp)
                        .padding(horizontal = 12.dp)
                        .clip(shape = RoundedCornerShape(6.dp)),
                    singleLine = true,
                    isError = isInvalidPin,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            when {
                                ( value.length == 1)  -> focusManager.moveFocus(FocusDirection.Right)
                            }
                        }
                    ),
                    maxLines = 1,
                    interactionSource = MutableInteractionSource() ,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        errorCursorColor = Color.Red,
                        focusedLabelColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        disabledTextColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        backgroundColor = if (!isInvalidPin) Color.Blue else Color.Gray.copy(alpha = mobileVendingGreyAlphaColor),
                    ),
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun PinFieldsView(){
    val value = "1"
    PinFields(
        value = value,
        length = 3,
        isInvalidPin = false,
        onValueChanged = {  }
    )
}

const val mobileVendingGreyAlphaColor = 0.2f