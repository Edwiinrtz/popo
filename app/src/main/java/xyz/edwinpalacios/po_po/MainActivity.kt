package xyz.edwinpalacios.po_po

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import xyz.edwinpalacios.po_po.model.Group
import xyz.edwinpalacios.po_po.model.User
import xyz.edwinpalacios.po_po.view.screens.Dashboard
import xyz.edwinpalacios.po_po.view.screens.NewComp
import xyz.edwinpalacios.po_po.view.screens.UserListScreeen
import xyz.edwinpalacios.po_po.view.theme.PopoTheme
import xyz.edwinpalacios.po_po.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainViewModel

    private val PERMISSION_REQUEST_CODE = 112

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        viewModel = MainViewModel(auth)

        val startDestination = if (currentUser != null) "dashboard" else "login"
        if (Build.VERSION.SDK_INT > 32) {

            if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                requestPermission(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            PopoTheme {
                InitScreen(startDestination, viewModel)
            }
        }


    }
    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(
            this, arrayOf(permission),
            PERMISSION_REQUEST_CODE
        )
    }


}


@Composable
fun InitScreen(startDestination: String, viewModel: MainViewModel) {
    val nv: NavHostController = rememberNavController()

    NavigationComponent(
        navController = nv,
        startDestination = startDestination,
        viewModel = viewModel
    )
}


@Composable
fun NavigationComponent(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "notification",
    viewModel: MainViewModel
) {
    val group by viewModel.getGroup().observeAsState(initial = Group("", mutableListOf()))
    val user by viewModel.getUser().observeAsState(initial = User(""))

    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable("login") {
            NewComp { name, groupId ->
                viewModel.login(name, groupId) {
                    navController.navigate("dashboard")
                }
            }
        }
        composable("dashboard") {

            viewModel.updateInfo()

            Dashboard(goToUserListScreen = { navController.navigate("userlist") },
                user = user,
                addkk = {
                    viewModel.addKk()
                })
        }
        composable("userlist") {
            UserListScreeen(
                goToUserHomeScreen = { navController.popBackStack() },
                group.members!!,
                group.id!!,
                group.totalKks!!
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InitPrev() {
    //InitScreen("login")
}
