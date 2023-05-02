package com.yveskalume.littlelemon.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMenuItem(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("price")
    val price: String = "",
    @SerialName("image")
    val image: String = "",
    @SerialName("category")
    val category: String = ""
)

fun NetworkMenuItem.toRoomMenuItem(): MenuItem {
    return MenuItem(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = category
    )
}
