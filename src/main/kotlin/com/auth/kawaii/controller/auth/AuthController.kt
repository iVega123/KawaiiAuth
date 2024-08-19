package com.auth.kawaii.controller.auth

import com.auth.kawaii.service.AuthenticationService
import com.auth.kawaii.service.PatreonService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val patreonAuthenticationService: PatreonService
) {

    @Value("\${patreon.authorize.url}")
    private lateinit var authorizeUrl: String

    @Value("\${patreon.creator.client.id}")
    private lateinit var clientId: String

    @Value("\${patreon.redirect.uri}")
    private lateinit var redirectUri: String


    @PostMapping
    fun authenticate(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse =
        authenticationService.authenticate(authRequest)


    @PostMapping("/patreon-auth")
    fun authenticatePatreon(@RequestParam(name = "code", defaultValue = "") code: String,
                            @RequestParam(name = "state", defaultValue = "None") state: String): ResponseEntity<Any> {
        return try {
            val authResponse = patreonAuthenticationService.authenticateWithPatreon(code)
            ResponseEntity.ok(authResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to authenticate with Patreon: ${e.message}")
        }
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): TokenReponse =
        authenticationService.refreshAccessToken(request.token)
            ?.mapToTokenResponse()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "invalid refresh token!")

    @GetMapping("/patreon")
    fun redirectToPatreon(): String {
        try {
            val url = "$authorizeUrl?response_type=code&client_id=$clientId&redirect_uri=$redirectUri&scope=identity"
            return url
        } catch (e: Exception) {
            e.printStackTrace()
            return e.message.toString()
        }
    }

  private fun String.mapToTokenResponse(): TokenReponse =
      TokenReponse(
          token = this
      )
}