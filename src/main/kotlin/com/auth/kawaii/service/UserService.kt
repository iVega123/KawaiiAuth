package com.auth.kawaii.service

import com.auth.kawaii.model.User
import com.auth.kawaii.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService (
    private val userRepository: UserRepository
){
    fun createUser(user: User): User? {
        val found = userRepository.findByEmail(user.email)
    return  if (found == null) {
      userRepository.save(user)
        user

    }else null
}
    fun findByUUID(uuid: UUID): User? =
        userRepository.findByUUID(uuid)

    fun findAll(): List<User> =
        userRepository.findAll()

    fun deleteByUUID(uuid: UUID): Boolean =
        userRepository.deleteByUUID(uuid)
}