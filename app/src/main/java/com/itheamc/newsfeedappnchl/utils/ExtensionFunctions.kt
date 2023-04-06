package com.itheamc.newsfeedappnchl.utils

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"
    return emailRegex.toRegex().matches(this)
}

fun String.isValidUsername(): Boolean {
    val usernameRegex = "^[a-zA-Z0-9_.]{3,20}$"
    return usernameRegex.toRegex().matches(this)
}