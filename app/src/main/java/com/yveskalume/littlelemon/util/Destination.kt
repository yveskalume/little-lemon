package com.yveskalume.littlelemon.util

sealed class Destination(val route: String) {
    object OnBoarding : Destination("onboarding")
    object Home : Destination("home")
    object Profile : Destination("profile")

    override fun toString(): String {
        return this.route
    }
}