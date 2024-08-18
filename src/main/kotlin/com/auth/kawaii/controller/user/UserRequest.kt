package com.auth.kawaii.controller.user

import jakarta.validation.constraints.NotBlank

data class UserRequest(
    @field:NotBlank(message = "digite pelo menos um caracte")
    val email: String,
    @field:NotBlank(message = "digite pelo menos um carectere")
    val password: String,
)
