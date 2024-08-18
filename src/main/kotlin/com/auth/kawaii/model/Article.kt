package com.auth.kawaii.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "articles")
class Article(
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String
)
