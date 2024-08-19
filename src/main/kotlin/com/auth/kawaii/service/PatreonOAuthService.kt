package com.auth.kawaii.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import com.patreon.PatreonAPI
import com.patreon.PatreonOAuth
import com.patreon.resources.User
import com.github.jasminb.jsonapi.JSONAPIDocument
import com.auth.kawaii.model.PatreonUserModel

@Service
class PatreonService(
    @Value("\${patreon.creator.client.id}") private val clientId: String,
    @Value("\${patreon.client.secret}") private val clientSecret: String,
    @Value("\${patreon.redirect.uri}") private val redirectUri: String
) {
    fun authenticateWithPatreon(code: String): PatreonUserModel {
        val oauthClient = PatreonOAuth(clientId, clientSecret, redirectUri)
        val tokens = oauthClient.getTokens(code)
        val accessToken = tokens.accessToken

        val apiClient = PatreonAPI(accessToken)
        val userResponse: JSONAPIDocument<User> = apiClient.fetchUser()
        val user = userResponse.get()

        val pledgeAmountCents = user.pledges?.firstOrNull()?.amountCents

        return PatreonUserModel(
            fullName = user.fullName,
            pledgeAmountCents = pledgeAmountCents
        )
    }
}