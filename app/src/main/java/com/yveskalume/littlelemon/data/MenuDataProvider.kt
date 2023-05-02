package com.yveskalume.littlelemon.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.yveskalume.littlelemon.data.model.MenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun rememberMenuDataProvider(context: Context): MenuDataProvider = remember {
    MenuDataProvider(context)
}

@Stable
class MenuDataProvider(private val context: Context) {
    private val repository = MenuRepository()

    private val _menu: MutableStateFlow<List<MenuItem>> = MutableStateFlow(emptyList())
    val menu: StateFlow<List<MenuItem>>
        get() = _menu

    private val _categories: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<String>>
        get() = _categories

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getData(context).collect { items ->
                _categories.emit(items.groupBy(MenuItem::category).keys.toList())
            }
        }
    }


    suspend fun getData(searchKeyWord: String) {
        withContext(Dispatchers.IO) {
            repository.getData(context).collect { items ->
                if (searchKeyWord.isNotBlank()) {
                    _menu.emit(items.filter { it.title.contains(searchKeyWord, true) })
                } else {
                    _menu.emit(items)
                }
            }
        }
    }

    suspend fun getByCategory(category: String) {
        withContext(Dispatchers.IO) {
            repository.getData(context).collect { item ->
                _menu.emit(item.filter { it.category == category })
            }
        }
    }
}