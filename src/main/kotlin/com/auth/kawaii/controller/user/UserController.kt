package com.auth.kawaii.controller.user

import com.auth.kawaii.model.Role
import com.auth.kawaii.model.User
import com.auth.kawaii.service.UserService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(userRequest.toModel(Role.User))
        return user?.let {
            ResponseEntity.ok(it.toResponse())
        } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
    }

    @PostMapping("/admin")
    fun createAdmin(@RequestBody userRequest: UserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(userRequest.toModel(Role.Admin))
        return user?.let {
            ResponseEntity.ok(it.toResponse())
        } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
    }

    @GetMapping
    fun listAll(): List<UserResponse> =
        userService.findAll().map { it.toResponse() }

    @GetMapping("/{uuid}")
    fun findByUUID(@PathVariable uuid: UUID): ResponseEntity<UserResponse> =
        userService.findByUUID(uuid).map { user ->
            ResponseEntity.ok(user.toResponse())
        }.orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }

    @DeleteMapping("/{uuid}")
    fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Void> {
        userService.deleteByUUID(uuid)
        return ResponseEntity.noContent().build()
    }

    private fun UserRequest.toModel(role: Role): User = User(
        id = UUID.randomUUID(),
        email = this.email,
        password = this.password,
        role = role
    )

    private fun User.toResponse(): UserResponse = UserResponse(
        uuid = this.id,
        email = this.email
    )
}
