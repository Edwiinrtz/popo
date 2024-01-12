package xyz.edwinpalacios.po_po.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldComp( text:String, changeValue: (value:String)->Unit, label:String, onlyNumbers: Boolean = false, lastInput:Boolean = false) {
    var keyBoardtype = KeyboardType.Text
    if(onlyNumbers){
        keyBoardtype = KeyboardType.Number
    }
    var imeAction =   if (lastInput) ImeAction.Done else ImeAction.Next



    OutlinedTextField(
        value = text,
        onValueChange = { changeValue(it) },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(20.dp,5.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardtype, imeAction = imeAction),
        singleLine = true
    )
}
@Preview(showBackground = true)
@Composable
fun TextFieldCompPrev() {
    TextFieldComp(text="", changeValue = {}, label="Nombre")
}
