package com.asdf.bookpediamodulasi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.asdf.bookpediamodulasi.ui.SelectedBookViewModel
import com.asdf.bookpediamodulasi.ui.detail.model.BookDetailCallback
import com.asdf.bookpediamodulasi.ui.detail.view.DetailScreen
import com.asdf.bookpediamodulasi.ui.detail.viewmodel.DetailViewModel
import com.asdf.bookpediamodulasi.ui.home.view.HomeScreen
import com.asdf.bookpediamodulasi.ui.home.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = androidx.navigation.compose.rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.BookGraph.route,
        modifier = modifier
    ) {
        navigation(
            startDestination = NavigationRoute.BookList.route,
            route = NavigationRoute.BookGraph.route
        ) {
            composable(route = NavigationRoute.BookList.route) {
                val viewModel = koinViewModel<HomeViewModel>()
                val selectedBookViewModel =
                    it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                LaunchedEffect(true) {
                    selectedBookViewModel.onSelectBook(null)
                }

                HomeScreen (
                    viewModel = viewModel,
                    onBookClick = { book ->
                        selectedBookViewModel.onSelectBook(book)
                        navController.navigate(
                            NavigationRoute.BookDetail(book.id).route
                        )
                    }
                )
            }

            composable(
                route = NavigationRoute.BookDetail.routeWithArg(),
                arguments = listOf(
                    navArgument("bookId") { type = NavType.StringType }
                )
            ) {
                val selectedBookViewModel =
                    it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                val viewModel = koinViewModel<DetailViewModel>()
                val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                LaunchedEffect(selectedBook) {
                    selectedBook?.let { book ->
                        viewModel.onAction(BookDetailCallback.OnSelectedBookChange(book))
                    }
                }

                DetailScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}

@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}