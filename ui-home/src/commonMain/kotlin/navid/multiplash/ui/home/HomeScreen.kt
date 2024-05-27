package navid.multiplash.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import navid.multiplash.ui.home.di.HomeModule

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel { HomeModule.component.homeViewModel },
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = viewModel.text, modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}
