package com.auth.kawaii.service

import com.auth.kawaii.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.Collections

typealias ApplicationUser = com.auth.kawaii.model.User

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        // Convert Optional to nullable and handle the null case
        val applicationUser = userRepository.findByEmail(username).orElseThrow {
            UsernameNotFoundException("User with email $username not found")
        }

        return applicationUser.mapToUserDetails()
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails {
        return User.withUsername(this.email)
            .password(this.password)
            .authorities(Collections.singletonList(SimpleGrantedAuthority("ROLE_${this.role.name}")))
            .build()
    }
}
