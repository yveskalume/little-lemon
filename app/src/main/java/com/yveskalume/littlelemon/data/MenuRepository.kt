package com.yveskalume.littlelemon.data

import android.content.Context
import android.util.Log
import com.yveskalume.littlelemon.data.model.MenuItem
import com.yveskalume.littlelemon.data.model.NetworkMenu
import com.yveskalume.littlelemon.data.model.toRoomMenuItem
import com.yveskalume.littlelemon.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MenuRepository {

    val httpClient by lazy {
        HttpClient(Android) {
            expectSuccess = true
            install(Logging) {
                level = LogLevel.HEADERS
            }
            install(ContentNegotiation) {
                json(contentType = ContentType("text", "plain"))
            }
        }
    }

    private suspend fun getRemoteData(): NetworkMenu {
        return httpClient.get(Constants.serverEndpoint) {
            contentType(ContentType.Application.Json)
        }.body()
    }

    private suspend fun updateLocalData(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val networkMenu = getRemoteData()
                val roomMenu = networkMenu.menu.map { it.toRoomMenuItem() }
                AppDatabase.getInstance(context).menuDao().insertAll(roomMenu)
            } catch (e: Exception) {
                Log.e("UpdateLocalData", e.toString())
            }
        }
    }

    suspend fun getData(context: Context): Flow<List<MenuItem>> {
        updateLocalData(context)
        return AppDatabase.getInstance(context).menuDao().getAllMenuItems()
    }

}