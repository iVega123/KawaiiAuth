package com.auth.kawaii.controller.user


import com.auth.kawaii.model.Role
import com.auth.kawaii.model.User
import com.auth.kawaii.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController (
    private val userService: UserService

){
    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): UserResponse =
        userService.createUser(
            user = userRequest.toModel()
        )
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user")

    @GetMapping
    fun ListAll(): List<UserResponse> =
        userService.findAll()
            .map { it.toResponse() }
@GetMapping("/{uuid}")
fun findByUUID(@PathVariable uuid: UUID) : UserResponse =
    userService.findByUUID(uuid)
        ?.toResponse()
        ?:throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

    @DeleteMapping("/{uuid}")
    fun deleteByUUID(@PathVariable uuid: UUID) : ResponseEntity<Boolean> {
        val sucess = userService.deleteByUUID(uuid)

        return if (sucess)
            ResponseEntity.noContent()
                .build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
    }

    private fun UserRequest.toModel(): User =
        User(
            id = UUID.randomUUID(),
            email = this.email,
            password = this.password,
            role = Role.User
        )

    private fun User.toResponse(): UserResponse =
        UserResponse(
            uuid = this.id,
            email = this.email

        )
}