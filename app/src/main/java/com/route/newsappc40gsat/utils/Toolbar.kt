package com.route.newsappc40gsat.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.route.newsappc40gsat.Colors
import com.route.newsappc40gsat.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolbar(
    toolbarTitle: String,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = toolbarTitle,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }, modifier = modifier.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.green,
            navigationIconContentColor = Color.White
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = stringResource(
                    R.string.navigation_icon_menu
                ),
                modifier = Modifier
                    .padding(
                        start = 8.dp
                    )
                    .clickable {
                        onNavigationIconClick()
                    }
                    .padding(8.dp),

                )
        }
    )
}

@Preview
@Composable
private fun NewsToolbarPreview() {
    NewsToolbar("Sports", {})
}

