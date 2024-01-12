package xyz.edwinpalacios.po_po.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.edwinpalacios.po_po.R
import xyz.edwinpalacios.po_po.model.User
import xyz.edwinpalacios.po_po.view.components.UserInfoCompo
import xyz.edwinpalacios.po_po.view.theme.Color_kk

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreeen(
    goToUserHomeScreen: () -> Unit, list: MutableList<User>, groupId: String, totalKks: Int
) {

    Scaffold(topBar = {
        TopAppBar(title = { /*TODO*/ }, actions = {
            Row {
                IconButton(
                    onClick = { /*TODO*/ },
                    Modifier
                        .clip(shape = CircleShape)
                        .background(color = Color(0, 0, 0, 10))
                ) {
                    Icon(
                        Icons.Rounded.Share,
                        contentDescription = "Generate general report",
                        tint = Color_kk
                    )
                }
            }

        })
    }, bottomBar = {
        BottomAppBar(
            modifier = Modifier.padding(10.dp),
            containerColor = Color(0, 0, 0, 0),
            actions = {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { goToUserHomeScreen() }, Modifier.clip(shape = CircleShape)
                    ) {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = "Home icon",
                            tint = Color_kk
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    IconButton(
                        onClick = { /*TODO*/ },
                        Modifier
                            .clip(shape = CircleShape)
                            .background(color = Color(0, 0, 0, 10))
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
        Column(modifier = Modifier.padding(bottom = 10.dp)) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxHeight(.8f)
            ) {
                itemsIndexed(list) { index, item ->
                    UserInfoCompo(user = item, action = { /*TODO*/ }, groupId = groupId)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.button_icon),
                    contentDescription = "popo icon",
                    modifier = Modifier
                        .size(32.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = totalKks.toString(),
                    fontSize = 24.sp,
                    color = Color_kk
                )
            }

        }


    })
}


@Preview
@Composable
fun UserListPrev() {
    val list = mutableListOf(User("Edwin"), User("Leidy"))
    UserListScreeen(goToUserHomeScreen = {}, list = list, "001", 1000000000)
}