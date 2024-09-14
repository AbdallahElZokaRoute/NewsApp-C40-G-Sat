package com.route.newsappc40gsat.fragments.news

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.newsappc40gsat.R
import com.route.newsappc40gsat.api.ApiManager
import com.route.newsappc40gsat.api.NewsService
import com.route.newsappc40gsat.api.fromJson
import com.route.newsappc40gsat.api.handleError
import com.route.newsappc40gsat.model.ArticlesItem
import com.route.newsappc40gsat.model.Category
import com.route.newsappc40gsat.model.NewsResponse
import com.route.newsappc40gsat.model.SourcesItem
import com.route.newsappc40gsat.model.SourcesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsService: NewsService
) : ViewModel() {
    val sourcesListState = mutableStateListOf<SourcesItem>()
    val errorStateString = mutableStateOf("")
    val errorStateResId = mutableIntStateOf(R.string.empty)
    val isLoading = mutableStateOf(true)
    val retry = mutableStateOf(false)
    val articlesList = mutableStateListOf<ArticlesItem>()
    val selectedTabIndex = mutableIntStateOf(0)
    val selectedSourceId = mutableStateOf("")

    fun getSources(category: Category) {
        // Readibility and Concurrency

        // parallelism or Concurrency
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading.value = true
                val response = newsService.getNewsSources(categoryId = category.apiID)
                isLoading.value = false
                if (response.isSuccessful) {
                    val sourcesResponse = response.body()
                    if (sourcesResponse?.sources?.isNotEmpty() == true)
                        sourcesListState.addAll(sourcesResponse.sources)
                } else {
                    //                String -> {     }
                    val jsonResponse: String? = response.errorBody()?.string()
                    val sourcesResponse =
                        jsonResponse?.fromJson(SourcesResponse::class.java)
                    errorStateString.value = sourcesResponse?.message ?: ""
                }
            } catch (e: Exception) {
                isLoading.value = false
                Log.e("TAG", "onFailure: ${e.message}")
                errorStateResId.value = handleError(e)
            }
        }
    }

    // Compose or Hilt
    fun getNewsBySource() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = newsService.getNewsBySource(sourceId = selectedSourceId.value)
                val articlesResponse = response.body()
                if (response.isSuccessful) {
                    if (articlesResponse?.articles?.isNotEmpty() == true) {
                        articlesList.clear()
                        articlesList.addAll(articlesResponse.articles)
                    }
                } else {
                    val jsonResponse: String? = response.errorBody()?.string()
                    val sourcesResponse =
                        jsonResponse?.fromJson(SourcesResponse::class.java)
                    errorStateString.value = sourcesResponse?.message ?: ""
                }
            } catch (e: Exception) {
                isLoading.value = false
                Log.e("TAG", "onFailure: ${e.message}")
                errorStateResId.value = handleError(e)

            }
        }

    }
}
