package com.auth.kawaii.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var token: String,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var expiryDate: Date
)
