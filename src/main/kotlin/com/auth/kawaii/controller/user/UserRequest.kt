package com.auth.kawaii.controller.user

import jakarta.validation.constraints.NotBlank

data class UserRequest(
    @field:NotBlank(message = "Email must not be blank")
    val email: String,
    @field:NotBlank(message = "Password must not be blank")
    val password: String,
)
