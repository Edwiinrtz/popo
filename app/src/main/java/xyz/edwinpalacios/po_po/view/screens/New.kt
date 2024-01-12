package xyz.edwinpalacios.po_po.view.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import xyz.edwinpalacios.po_po.R
import xyz.edwinpalacios.po_po.view.components.TextFieldComp
import xyz.edwinpalacios.po_po.view.theme.Color_kk

@Composable
fun NewComp(createUser: (name:String, idGroup:String)  -> Unit) {
    var name by remember { mutableStateOf("") }
    var idGroup by remember { mutableStateOf("") }
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Scaffold(topBar = {}, bottomBar = {
        BottomAppBar(
            modifier = Modifier.wrapContentSize(),
            containerColor = Color.Transparent,
            contentColor = Color_kk
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Created by @EdwiinRtz",
                    modifier = Modifier.clickable { uriHandler.openUri("https://edwiinrtz.github.io/") })
            }

        }
    }, content = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                Image(
                    painter = painterResource(id = R.drawable.button_icon),
                    contentDescription = "Popo icon"
                )
                TextFieldComp(text = name, changeValue = { name = it }, label = "Name")
                TextFieldComp(
                    text = idGroup,
                    changeValue = { idGroup = it },
                    label = "Id Group",
                    onlyNumbers = true,
                    lastInput = true
                )
                Text(text = "*Dejar en blanco para crear un nuevo grupo", color = Color.LightGray)

                TextButton(onClick = {

                    createUser(name, idGroup)
                    //Toast.makeText(context, "Usuario agregado con exito", Toast.LENGTH_LONG).show()
                    //goToDashboard()
                }
                ) {
                    Text(text = "Go in", fontSize = 18.sp, color = Color_kk)
                }
            }
        }
    })
}


@Preview(showBackground = true)
@Composable
fun NewCompPrev() {
    //NewComp(goToDashboard = {}, createUser = {("","")->{}})

}