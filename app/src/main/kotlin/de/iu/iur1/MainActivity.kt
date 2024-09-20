package de.iu.iur1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Radio
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import de.iu.iur1.profile.AccountView
import de.iu.iur1.theme.IUR1Theme
import android.app.Application
import dagger.hilt.android.HiltAndroidApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { IUR1() }
    }
}

enum class IUR1View {
    ACCOUNT, RADIO, REVIEW
}

@Composable
fun IUR1() {
    var currentView by remember { mutableStateOf(IUR1View.RADIO) }
    var darkTheme by remember { mutableStateOf(true) }
    IUR1Theme(darkTheme = darkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                IUR1NavigationBar(
                    currentView = currentView,
                    onNavigate = { view -> currentView = view })
            },
            content = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentView) {
                        IUR1View.ACCOUNT -> AccountView(onGoBack = {currentView = IUR1View.RADIO})
                        IUR1View.RADIO -> RadioView()
                        IUR1View.REVIEW -> {}
                    }
                }
            },
        )
    }
}

@Composable
fun IUR1NavigationBar(currentView: IUR1View, onNavigate: (IUR1View) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = currentView == IUR1View.RADIO,
            onClick = { onNavigate.invoke(IUR1View.RADIO) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Radio,
                    contentDescription = "Radio"
                )
            },
            label = { Text(text = "Radio") })
        NavigationBarItem(
            selected = currentView == IUR1View.REVIEW,
            onClick = { onNavigate.invoke(IUR1View.REVIEW) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.RateReview,
                    contentDescription = "Review"
                )
            },
            label = { Text(text = "Review") })
        NavigationBarItem(
            selected = currentView == IUR1View.ACCOUNT,
            onClick = { onNavigate.invoke(IUR1View.ACCOUNT) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Account"
                )
            },
            label = { Text(text = "Account") })
    }
}