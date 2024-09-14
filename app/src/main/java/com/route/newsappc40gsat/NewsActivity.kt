package com.route.newsappc40gsat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.route.newsappc40gsat.fragments.CATEGORIES_SCREEN
import com.route.newsappc40gsat.fragments.CategoriesScreen
import com.route.newsappc40gsat.fragments.news.NewsScreen
import com.route.newsappc40gsat.model.Category
import com.route.newsappc40gsat.ui.theme.NewsAppC40GSatTheme
import com.route.newsappc40gsat.utils.NewsDrawerContent
import com.route.newsappc40gsat.utils.NewsToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsActivity : ComponentActivity() {
    // MVC <-> Model View Controller
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                ResourcesCompat.getColor(resources, R.color.green, null),
            )
        )

        setContent {
            NewsAppC40GSatTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()
                val navController = rememberNavController()
                ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                    NewsDrawerContent(navHostController = navController) {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                }) {
                    Scaffold(
                        modifier = Modifier
                            .statusBarsPadding()
                            .fillMaxSize(),
                        topBar = {
                            NewsToolbar(
                                toolbarTitle = stringResource(id = R.string.sports),
                                onNavigationIconClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                })
                        }
                    ) { innerPadding ->

                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = CATEGORIES_SCREEN

                        ) {
                            composable(CATEGORIES_SCREEN) {
                                CategoriesScreen(navController)
                            }
                            composable<Category> { backstackEntry ->
                                val category: Category = backstackEntry.toRoute()
                                NewsScreen(category)
                            }
                        }
                    }
                }
            }
        }
    }
}


