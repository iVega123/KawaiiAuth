package com.auth.kawaii.controller.auth


data class AuthenticationRequest (
        val email: String,
        val password: String,
)