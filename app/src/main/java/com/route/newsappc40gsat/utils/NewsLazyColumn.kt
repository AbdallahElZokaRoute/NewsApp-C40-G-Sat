package com.route.newsappc40gsat.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder
import com.route.newsappc40gsat.R
import com.route.newsappc40gsat.api.ApiManager
import com.route.newsappc40gsat.fragments.news.NewsViewModel
import com.route.newsappc40gsat.model.ArticlesItem
import com.route.newsappc40gsat.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsLazyColumn(
    sourceId: String,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = sourceId) {
        viewModel.getNewsBySource()
    }
    LazyColumn(modifier) {
        items(viewModel.articlesList) { articleItem ->
            NewsCard(articleItem = articleItem)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(articleItem: ArticlesItem, modifier: Modifier = Modifier) {
    Card(
        modifier.padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        GlideImage(
            model = articleItem.urlToImage,
            contentDescription = stringResource(R.string.news_picture),
            loading = placeholder(R.drawable.logo),
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = articleItem.title ?: "")
        Text(text = articleItem.publishedAt ?: "")

    }
}

@Preview
@Composable
private fun NewsCardPreview() {
    NewsCard(articleItem = ArticlesItem())
}

@Preview
@Composable
private fun NewsLazyColumnPreview() {
    NewsLazyColumn("")
}
