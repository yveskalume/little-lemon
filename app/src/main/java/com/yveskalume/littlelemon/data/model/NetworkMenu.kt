package com.yveskalume.littlelemon.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMenu(
    @SerialName("menu")
    val menu: List<NetworkMenuItem> = emptyList()
)
