package com.frost.lecture.interfaces

interface OAuthServiceInterface {
    val providerName: String
    fun getToken(code : String): String
}