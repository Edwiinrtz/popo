package xyz.edwinpalacios.po_po.view.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import xyz.edwinpalacios.po_po.R
import xyz.edwinpalacios.po_po.model.User
import xyz.edwinpalacios.po_po.view.components.UserInfoCompo
import xyz.edwinpalacios.po_po.view.theme.Color_kk

@Composable
fun Dashboard(goToUserListScreen: () -> Unit, user: User, addkk: () -> Unit) {



    Scaffold(topBar = {}, bottomBar = {
        BottomAppBar(modifier = Modifier.padding(10.dp),
            containerColor = Color(0, 0, 0, 0),
            actions = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        Modifier
                            .clip(shape = CircleShape)
                            .background(color = Color(0, 0, 0, 10))
                    ) {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = "Home icon",
                            tint = Color_kk
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    IconButton(
                        onClick = { goToUserListScreen() },
                        Modifier
                            .clip(shape = CircleShape)
                    ) {
                        Icon(
                            Icons.Outlined.List,
                            contentDescription = "List icon",
                            tint = Color_kk
                        )
                    }
                }
            })
    }, content = {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .padding(it),
        ) {
            UserInfoCompo(user = user, action = { /*TODO*/ }, groupId = "", showImage = false, showGroup = false)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 80.dp),
            ) {
                Image(painter = painterResource(id = R.drawable.button_icon),
                    contentDescription = "add kk button",
                    modifier = Modifier
                        .clickable { addkk() }
                        .background(color = Color(0, 0, 0, 15), shape = CircleShape)
                )
                Text(text = "Presione para reportar una nueva caquita", color = Color.LightGray)

            }


        }
    })
}


@Preview
@Composable
fun DashboardPrev() {
    val user = User("Edwin", mutableMapOf(), "", 0)
    val nv: NavHostController = rememberNavController()
    Dashboard(goToUserListScreen = {}, user = user, addkk = {})
}