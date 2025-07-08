package by.alerus.tms_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import by.alerus.tms_android.ui.theme.TMS_AndroidTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMS_AndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserProfile(
                        user = User(
                            name="Куджо Джотаро",
                            description="Уехал в Египет в поисках DIO\nЖенщинам не писать",
                            avatarId = R.drawable.avatar,
                            birthDate = Date(1971 - 1900, 4, 9)
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun UserProfile(user: User = User()) {
    Column (modifier = Modifier
        .fillMaxSize()) {
        UserAvatar(user.name, user.avatarId)
        UserInfo(user.description, user.birthDate)
        UserActionButtons()
    }
}

@Composable
fun UserAvatar(name: String, avatarId: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Image(
            painter = painterResource(id = avatarId),
            contentDescription = "Profile photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(text = name, modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun UserInfo(userDescription: String, birthDate: Date) {
    Text(
        text = "День рождения:",
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 0.dp
            ),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
        text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(birthDate),
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 0.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 0.dp
            ),
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = "О себе:",
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 0.dp
            ),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
        text = userDescription,
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 0.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 0.dp
            ),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun UserActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Call",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Message",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add friend",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
