import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.mealmate.modules.home.ui.screen.HomePage
import com.example.mealmate.modules.meal_plan.ui.screen.MealPlanPage
import com.example.mealmate.modules.new_meal.ui.screen.NewMealPage
import com.example.mealmate.modules.profile.ProfilePage
import com.example.mealmate.modules.shopping_list.ui.screen.ShoppingListPage
import com.example.mealmate.modules.splashscreen.ui.screen.SplashScreen
import com.example.mealmate.navigation.Screen
import com.example.mealmate.navigation.graphes.authNavGraph
import com.example.mealmate.shared.LocalDialogState
import com.example.mealmate.shared.widget.ConfirmationDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val dialogState = LocalDialogState.current
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) { SplashScreen(navController) }
        authNavGraph(navController)

        composable(Screen.HomeScreen.route) {
            BottomNavWrapper(navController) { HomePage(navController) }
        }
        composable(Screen.MealPlan.route) {
            BottomNavWrapper(navController) { MealPlanPage(navController) }
        }

        composable(Screen.ShoppingList.route) {
            BottomNavWrapper(navController) { ShoppingListPage(navController) }
        }

        composable(Screen.Profile.route) {
            BottomNavWrapper(navController) { ProfilePage(navController) }
        }

        composable(Screen.NewMeal.route) { NewMealPage(navController) }
    }
    // Add the dialog at the root level
    ConfirmationDialog(
        state = dialogState.value,
        onDismissRequest = { dialogState.value = dialogState.value.copy(isVisible = false) }
    )
}

@Composable
fun BottomNavWrapper(navController: NavHostController, content: @Composable () -> Unit) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
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
