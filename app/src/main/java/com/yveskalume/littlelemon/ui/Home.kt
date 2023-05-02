package com.yveskalume.littlelemon.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.yveskalume.littlelemon.R
import com.yveskalume.littlelemon.data.model.MenuItem
import com.yveskalume.littlelemon.data.rememberMenuDataProvider
import com.yveskalume.littlelemon.ui.theme.Gray100
import com.yveskalume.littlelemon.ui.theme.Green200
import com.yveskalume.littlelemon.ui.theme.White
import com.yveskalume.littlelemon.ui.theme.Yellow
import com.yveskalume.littlelemon.util.Destination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val menuDataProvider = rememberMenuDataProvider(context)

    var searchText by remember {
        mutableStateOf("")
    }

    val menuItems by menuDataProvider.menu.collectAsState()
    val categories by menuDataProvider.categories.collectAsState()

    LaunchedEffect(searchText) {
        menuDataProvider.getData(searchText)
    }

    BackHandler {
        (context as Activity).finish()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Box(modifier = Modifier.size(50.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.height(50.dp),
                    contentScale = ContentScale.FillHeight,
                )
                Surface(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    onClick = {
                        navController.navigate(Destination.Profile.route)
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        item {
            HeroSection(searchText = searchText, onTextSearchChange = { searchText = it })
        }
        item {
            MenuBreakDownSection(
                categories = categories,
                onCategoryClick = {
                    coroutineScope.launch {
                        if (it != null) {
                            menuDataProvider.getByCategory(it)
                        } else {
                            menuDataProvider.getData("")
                        }
                    }
                }
            )
        }
        items(menuItems) { item ->
            Divider()
            MenuItem(item)
        }
    }
}

@Composable
fun HeroSection(searchText: String, onTextSearchChange: (String) -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = Green200
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Text(
                text = "Little Lemon",
                fontSize = 42.sp,
                color = Yellow,
                fontFamily = FontFamily(Font(R.font.markazi_text_regular)),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Chicago", color = White, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "We are a family owned\n" +
                                "Mediterranean restaurant,\n" +
                                "focused on traditional\n" +
                                "recipes served with a\n" +
                                "modern twist.",
                        color = White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = searchText,
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = White),
                shape = RoundedCornerShape(8.dp),
                onValueChange = {
                    onTextSearchChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Enter search text")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuBreakDownSection(categories: List<String>, onCategoryClick: (String?) -> Unit) {
    var selectedCategory: String? by remember {
        mutableStateOf(null)
    }
    Text(
        text = "ORDER FOR DELIVERY",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) {
            val color by animateColorAsState(
                targetValue = if (selectedCategory == it) {
                    Green200
                } else {
                    Gray100
                },
                label = ""
            )
            Chip(
                colors = ChipDefaults.chipColors(backgroundColor = color),
                onClick = {
                    selectedCategory = if (selectedCategory == it) {
                        null
                    } else {
                        it
                    }
                    onCategoryClick(selectedCategory)
                }
            ) {
                Text(text = it)
            }
        }
    }
}

@Composable
fun MenuItem(menuItem: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.7f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(text = menuItem.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = menuItem.description,
                maxLines = 2
            )
            Text(text = "$${menuItem.price}", fontSize = 18.sp)
        }
        AsyncImage(
            model = menuItem.image,
            contentDescription = null,
            modifier = Modifier.size(90.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    MaterialTheme {
        Home(navController = rememberNavController())
    }
}