package navid.multiplash.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import navid.multiplash.core.ui.Greeting

@Composable
fun App() {
    val text = Greeting().greet()
    MaterialTheme {
        Scaffold {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = text)
                }
            }
        }
    }
}
