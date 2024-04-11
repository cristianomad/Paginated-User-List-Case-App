package com.cristianomadeira.paginateduserlist.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cristianomadeira.paginateduserlist.R
import com.cristianomadeira.paginateduserlist.ui.theme.SnackbarError

@Composable
fun ErrorSnackbar(
    modifier: Modifier = Modifier,
    errorMessage: String,
    showError: Boolean
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = showError,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .padding(25.dp)
                    .background(
                        color = SnackbarError,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 16.dp),
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = "Error",
                    tint = Color.White
                )
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ErrorSnackbarPreview() {
    ErrorSnackbar(
        errorMessage = "Error message",
        showError = true
    )
}