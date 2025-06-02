import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.mealmate.navigation.Graph
import com.example.mealmate.navigation.graphes.authNavGraph

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Graph.AuthGraph.route) {
        authNavGraph(navController)
//        composable(Screen.HomeScreen.route) { HomePage(navController) }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

fun NavController.to(
    route: String, builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(route, navOptions {
        builder()
    })
}

fun NavController.off(
    route: String, builder: NavOptionsBuilder.() -> Unit = {}
) {
    val currentRoute = this.currentDestination?.route ?: return
    this.navigate(route, navOptions {
        popUpTo(currentRoute) { inclusive = true }
        builder()
    })
}

fun NavController.offAll(
    route: String, builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(route, navOptions {
        popUpTo(0) { inclusive = true } // Pop entire backstack
        builder()
    })
}

fun NavController.offAllUntil(
    route: String,
    popUpToRoute: String,
    inclusive: Boolean = false,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    this.navigate(route, navOptions {
        popUpTo(popUpToRoute) {
            this.inclusive = inclusive
        }
        builder()
    })
}
