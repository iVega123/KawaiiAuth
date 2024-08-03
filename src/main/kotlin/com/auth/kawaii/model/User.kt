package com.auth.kawaii.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role
) {
    constructor() : this(UUID.randomUUID(), "", "", Role.User)
}

enum class Role {
    User, Admin
}
