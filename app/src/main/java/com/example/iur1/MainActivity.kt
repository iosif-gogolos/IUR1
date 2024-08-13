package com.example.iur1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.iur1.ui.theme.IUR1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SongCard(CurrentTitleInfo("No Longer Slaves", "Zach Williams (Live from Harding Prison"))
        }
    }
}

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