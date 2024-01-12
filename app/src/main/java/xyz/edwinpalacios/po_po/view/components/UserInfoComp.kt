package xyz.edwinpalacios.po_po.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import xyz.edwinpalacios.po_po.R
import xyz.edwinpalacios.po_po.model.User
import xyz.edwinpalacios.po_po.view.theme.Color_kk

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun UserInfoCompo(
    user: User,
    action: () -> Unit,
    groupId: String,
    showImage: Boolean = true,
    showGroup: Boolean = true
) {

    Card(
        onClick = {
            action()
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(255, 255, 255)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(showImage) {
                GlideImage(
                    model = user.profileUrl,
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(
                            CircleShape
                        )
                        .background(color = Color(0, 0, 0, 20))
                )
            }
            Spacer(modifier = Modifier.padding(2.dp))
            Column(modifier = Modifier.fillMaxWidth(.5f)) {
                Text(text = user.name?:"", modifier = Modifier.fillMaxWidth(), color = Color_kk)
                if (showGroup) {
                    Text(
                        text = "$groupId",
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
            Row(
                horizontalArrangement = Arrangement.End,
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
                    text = user.totalKks.toString(),
                    fontSize = 24.sp,
                    color = Color_kk

                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NeedcompPrev() {
    val user = User("Edwiin Palacios", totalKks = 9999999)
    UserInfoCompo(user = user, groupId = "001", action = {})
}