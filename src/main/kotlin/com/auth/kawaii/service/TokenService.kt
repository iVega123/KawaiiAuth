package com.auth.kawaii.service

import com.auth.kawaii.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(private val jwtProperties: JwtProperties) {
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())

    fun generateAccessToken(username: String): String = Jwts.builder()
        .subject(username)
        .expiration(Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration))
        .signWith(secretKey, Jwts.SIG.HS512)
        .compact()

    fun generateRefreshToken(username: String): String = Jwts.builder()
        .subject(username)
        .expiration(Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration))
        .signWith(secretKey, Jwts.SIG.HS512)
        .compact()

    fun extractUsername(token: String): String? = getAllClaims(token).subject

    fun isExpired(token: String): Boolean = getAllClaims(token).expiration.before(Date())

    private fun getAllClaims(token: String): Claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .payload
}
