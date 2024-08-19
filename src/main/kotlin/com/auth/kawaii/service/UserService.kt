package com.auth.kawaii.service

import com.auth.kawaii.model.User
import com.auth.kawaii.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createUser(user: User): User? {
        val found = userRepository.findByEmail(user.email).orElse(null)
        user.password = passwordEncoder.encode(user.password)
        return if (found == null) {
            userRepository.save(user)
        } else null
    }

    fun findByUUID(uuid: UUID): Optional<User> =
        userRepository.findById(uuid)

    fun findAll(): List<User> =
        userRepository.findAll()

    fun deleteByUUID(uuid: UUID) {
        userRepository.deleteById(uuid)

    }
}
