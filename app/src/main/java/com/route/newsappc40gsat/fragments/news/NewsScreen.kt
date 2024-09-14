package com.route.newsappc40gsat.fragments.news

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.route.newsappc40gsat.Colors
import com.route.newsappc40gsat.R
import com.route.newsappc40gsat.model.Category
import com.route.newsappc40gsat.model.SourcesItem
import com.route.newsappc40gsat.model.SourcesResponse
import com.route.newsappc40gsat.utils.ErrorDialog
import com.route.newsappc40gsat.utils.NewsLazyColumn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun NewsScreen(
    category: Category, modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {

    if (viewModel.isLoading.value)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Colors.green)
        }

    ErrorDialog(
        error = viewModel.errorStateString,
        errorIntState = viewModel.errorStateResId
    ) {
        viewModel.isLoading.value = true
        viewModel.retry.value = !viewModel.retry.value
    }
    NewsContent(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.pattern),
                contentScale = ContentScale.FillBounds
            ),
        sourcesList = viewModel.sourcesListState
    )
    LaunchedEffect(viewModel.retry.value) {
        viewModel.getSources(category)
    }
}
// Maintainable - Scalable - Testable

@Composable
fun NewsContent(
    sourcesList: List<SourcesItem>,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val selectedModifier = Modifier
        .padding(2.dp)
        .background(color = Colors.green, shape = CircleShape)
        .padding(horizontal = 12.dp, vertical = 4.dp)
    val nonSelectedModifier = Modifier
        .padding(2.dp)
        .border(2.dp, color = Colors.green, CircleShape)
        .padding(horizontal = 12.dp, vertical = 4.dp)
    if (sourcesList.isNotEmpty())
        Column(modifier = modifier) {
            LaunchedEffect(key1 = Unit) {
                viewModel.selectedSourceId.value = sourcesList[0].id ?: ""
            }
            ScrollableTabRow(
                selectedTabIndex = viewModel.selectedTabIndex.value,
                divider = {},
                indicator = {},
                edgePadding = 0.dp
            ) {
                sourcesList.forEachIndexed { index, sourceItem ->
                    Tab(
                        selected = index == viewModel.selectedTabIndex.intValue,
                        onClick = {
                            viewModel.selectedSourceId.value = sourceItem.id ?: ""
                            viewModel.selectedTabIndex.intValue = index
                        },
                        modifier = if (viewModel.selectedTabIndex.intValue == index) selectedModifier else nonSelectedModifier

                    ) {
                        Text(
                            text = "${sourceItem.name}",
                            color = if (viewModel.selectedTabIndex.intValue == index) Color.White else Colors.green
                        )
                    }
                }
            }
            if (viewModel.selectedSourceId.value.isNotEmpty())
                NewsLazyColumn(viewModel.selectedSourceId.value)
            // SOLID -> "D"ependency Inversion Principle

        }
}

@Preview
@Composable
private fun NewsContentPreview() {
    NewsContent(listOf())
}
/**
 * News App  (APIs concept - RESTful APIS concept-> Retrofit )
 * MVVM -> Model View View Model -> ViewModel
 * Chat Application (Firebase)
 *
 * News App -> Dependency Injection using Dagger Hilt & Repository Pattern
 * News App -> Coroutines
 * MVI UI Architecture Pattern
 * Clean Architecture (Multi Module App)
 * Unit Testing
 * UI Testing
 * Flows and StateFlow
 *
 *
 *
 *
 *
 */
