package de.iu.iur1.info

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SongCard(CurrentTitleInfo("No Longer Slaves", "Zach Williams (Live from Harding Prison"))
        }
    }
}
*/

data class Song (
    val titel: String,
    val interpret: String
)

data class CurrentTitleInfo(val title: String, val interpret: String)

@Composable
fun SongCard(msg: CurrentTitleInfo){
    Text(text = msg.title)
    Text(text = msg.interpret)
}

@Preview
@Composable
fun PreviewSongCard() {
    SongCard(
        msg = CurrentTitleInfo("Iosif", "Hey, save this song to favorites")

    )
}
