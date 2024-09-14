package com.route.newsappc40gsat.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.route.newsappc40gsat.R

@Composable
fun ErrorDialog(
    error: MutableState<String>,
    errorIntState: MutableIntState,
    modifier: Modifier = Modifier,
    onPositiveClick: () -> Unit
) {
    val emptyString = stringResource(id = R.string.empty)
    if (error.value.isNotEmpty() || errorIntState.intValue != R.string.empty)
        AlertDialog(
            onDismissRequest = {
                errorIntState.intValue = R.string.empty
                error.value = emptyString
            },
            confirmButton = {
                TextButton(onClick = {
                    error.value = emptyString
                    errorIntState.intValue = R.string.empty
                    onPositiveClick()
                }) {
                    Text(text = stringResource(R.string.ok))
                }

            },
            title = {
                Text(text = stringResource(R.string.error))
            },
            text = {
                Text(text = if (error.value.isNotEmpty()) error.value else stringResource(id = errorIntState.intValue))
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
        )
}

@Preview
@Composable
private fun ErrorDialogPreview() {
    val previewError = remember {
        mutableStateOf("Something went wrong")
    }
    val previewErrorIntState = remember {
        mutableIntStateOf(R.string.ok)
    }
    ErrorDialog(previewError, previewErrorIntState) {}
}