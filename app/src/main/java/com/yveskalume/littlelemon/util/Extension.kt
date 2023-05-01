package com.yveskalume.littlelemon.util

fun String.isEmail(): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
    return emailRegex.toRegex().matches(this);
}