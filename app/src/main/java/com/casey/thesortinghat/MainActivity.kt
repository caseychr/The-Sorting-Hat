package com.casey.thesortinghat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.casey.thesortinghat.di.TheSortingHatApp
import com.casey.thesortinghat.dto.CharacterDTO
import com.casey.thesortinghat.ui.theme.TheSortingHatTheme
import com.casey.thesortinghat.viewmodel.CharacterState
import com.casey.thesortinghat.viewmodel.CharacterViewModel
import com.casey.thesortinghat.viewmodel.viewModels
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelProvider: Provider<CharacterViewModel>

    private val viewModel by viewModels { viewModelProvider }

    @OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TheSortingHatApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel.getAllCharacters()
        setContent {
            TheSortingHatTheme {
                Scaffold(bottomBar = { BottomNavigation() }) {
                    val state = viewModel.uiState.collectAsState().value
                    if (state.state is CharacterState.Success) {
                        LazyColumn(modifier = Modifier.padding(it)) {
                            for (character in state.state.characters) {
                                item {
                                    Surface {
                                        CharacterCard(character)
                                    }
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
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterCard(character: CharacterDTO) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val hideHouseCrest by animateFloatAsState(targetValue = if (character.image.isEmpty()) 0f else 1f)
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (photo, name, info) = createRefs()

        createHorizontalChain(
            photo,
            info,
            chainStyle = ChainStyle.Packed(0f)
        )
        Box(
            modifier = Modifier
                .padding(12.dp)
                .clickable { expanded = !expanded }
                .constrainAs(photo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            val image =
                character.image.ifEmpty { fetchCrest(character.house) }
            GlideImage(
                model = image,
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = fetchCrest(character.house)),
                contentDescription = "",
                modifier = Modifier
                    .alpha(hideHouseCrest)
                    .size(30.dp)
                    .align(Alignment.BottomEnd)
                    .padding(2.dp)
            )
        }
        Text(
            text = character.name,
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(photo.bottom)
                    start.linkTo(photo.start)
                    end.linkTo(photo.end)
                    bottom.linkTo(parent.bottom)
                },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(
                    R.font.petitformalscript_regular,
                    FontWeight.Bold
                )
            )
        )
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .constrainAs(info) {
                    start.linkTo(photo.end)
                    top.linkTo(photo.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(photo.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            visible = expanded,
            enter = slideInHorizontally() + expandHorizontally(),
            exit = slideOutHorizontally() + shrinkHorizontally()
        ) {
            CharacterInfo(character = character)
        }

    }
}

@Composable
fun CharacterInfo(character: CharacterDTO) {
    Column(verticalArrangement = Arrangement.SpaceBetween) {

        val dob =
            if (!character.dateOfBirth.isNullOrEmpty()) character.dateOfBirth else "Unknown"
        Text(text = "DOB: $dob")

        val ancestry =
            if (!character.ancestry.isNullOrEmpty()) character.ancestry else "Unknown"
        Text(text = "Ancestry: $ancestry")

        val wand =
            "${character.wand.wood}, ${character.wand.core}, ${character.wand.length}".ifEmpty { "Unknown" }
        Text(text = "Wand: $wand")

        val patronus =
            if (!character.patronus.isNullOrEmpty()) character.patronus else "None"
        Text(text = "Patronus: $patronus")

        val actor =
            if (!character.actor.isNullOrEmpty()) character.actor else "Unknown"
        Text(text = "Actor: $actor")
    }
}

@Composable
fun BottomNavigation() {
    NavigationBar {
        NavigationBarItem(selected = true, onClick = { }, icon = {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null
            )
        })
        NavigationBarItem(selected = false, onClick = { }, icon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        })
        NavigationBarItem(selected = false, onClick = { }, icon = {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = null
            )
        })
    }
}

@Composable
fun CharacterPhoto() {

}

fun fetchCrest(house: String): Int {
    return when (house.lowercase()) {
        "gryffindor" -> R.drawable.gryffindor
        "hufflepuff" -> R.drawable.hufflepuff
        "slytherin" -> R.drawable.slytherin
        "ravenclaw" -> R.drawable.ravenclaw
        else -> R.drawable.hogwarts
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