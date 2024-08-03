package com.auth.kawaii.service

import com.auth.kawaii.config.JwtProperties
import com.auth.kawaii.controller.auth.AuthenticationRequest
import com.auth.kawaii.controller.auth.AuthenticationResponse
import com.auth.kawaii.model.RefreshToken
import com.auth.kawaii.repository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun authenticate(authRequest: AuthenticationRequest): AuthenticationResponse {
        val authentication: Authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.email, authRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val userDetails = authentication.principal as UserDetails

        val accessToken = tokenService.generateAccessToken(userDetails.username)
        val refreshToken = tokenService.generateRefreshToken(userDetails.username)

        refreshTokenRepository.save(RefreshToken(
            token = refreshToken,
            username = userDetails.username,
            expiryDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
        ))

        return AuthenticationResponse(accessToken, refreshToken)
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val username = tokenService.extractUsername(refreshToken)
        val refreshTokenDetails = refreshTokenRepository.findByToken(refreshToken)

        return if (refreshTokenDetails.isPresent && !tokenService.isExpired(refreshToken) && username == refreshTokenDetails.get().username) {
            tokenService.generateAccessToken(username)
        } else {
            null
        }
    }
}
