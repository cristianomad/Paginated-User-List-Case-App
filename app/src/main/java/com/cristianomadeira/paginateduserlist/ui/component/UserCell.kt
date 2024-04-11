package com.cristianomadeira.paginateduserlist.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cristianomadeira.paginateduserlist.R
import com.cristianomadeira.paginateduserlist.domain.entity.User
import com.cristianomadeira.paginateduserlist.ui.theme.Divider

@Composable
fun UserCell(
    user: User,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.thumbnail.ifEmpty { null })
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.user_thumbnail_placeholder),
                fallback = painterResource(id = R.drawable.user_thumbnail_placeholder),
                contentDescription = "User thumbnail",
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.size(16.dp))

            Column {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.size(2.dp))

                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic
                    ),
                    color = Color.DarkGray
                )
            }
        }

        HorizontalDivider(color = Divider)
    }
}

@Preview
@Composable
fun UserCellPreview() {
    UserCell(
        user = User(
            firstName = "Cristiano",
            lastName = "Madeira",
            email = "cristiano@email.com",
            thumbnail = "https://randomuser.me/api/portraits/thumb/men/75.jpg"
        )
    )
}