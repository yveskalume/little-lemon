package com.yveskalume.littlelemon.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.yveskalume.littlelemon.data.model.MenuItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

@Composable
fun rememberMenuDataProvider(context: Context) : MenuDataProvider = remember {
    MenuDataProvider(context)
}

@Stable
class MenuDataProvider(private val context: Context) {
    private val repository = MenuRepository()

    private val _menu: MutableStateFlow<List<MenuItem>> = MutableStateFlow(emptyList())
    val menu: StateFlow<List<MenuItem>>
        get() = _menu

    suspend fun getData() {
        withContext(Dispatchers.IO) {
            repository.getData(context).collect {
                _menu.emit(it)
            }
        }
    }
}