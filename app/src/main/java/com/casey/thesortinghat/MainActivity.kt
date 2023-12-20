package com.casey.thesortinghat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.casey.thesortinghat.ui.theme.TheSortingHatTheme
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<CharacterViewModel>

    private val viewModel by viewModels { viewModelProvider }

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TheSortingHatApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel.getAllCharacters()
        setContent {
            val state = viewModel.uiState.collectAsState().value
            if (state.state is State.Success) {

                LazyColumn {
                    for (character in state.state.characters) {
                        item {
                            GlideImage(
                                model = character.image,
                                contentDescription = "",
                                modifier = Modifier
                                    .height(180.dp)
                                    .width(180.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
            /*TheSortingHatTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }*/
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheSortingHatTheme {
        Greeting("Android")
    }
}