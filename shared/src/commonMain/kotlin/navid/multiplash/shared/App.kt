package navid.multiplash.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import navid.multiplash.core.ui.Greeting
import navid.multiplash.core.ui.designsystem.AppTheme

@Composable
fun App(
    dynamicColor: Boolean,
) {
    val text = Greeting().greet()
    AppTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = dynamicColor
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = text, modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }
    }
}
