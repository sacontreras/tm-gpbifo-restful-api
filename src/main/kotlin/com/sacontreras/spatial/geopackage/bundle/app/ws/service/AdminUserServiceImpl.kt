package com.sacontreras.spatial.geopackage.bundle.app.ws.service

import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.AdminUserServiceRecordNotFoundException
import com.sacontreras.spatial.geopackage.bundle.app.ws.io.repository.AdminUserRepository
import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto.AdminUserDTO
import org.modelmapper.ModelMapper
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AdminUserServiceImpl(val adminUserRepository: AdminUserRepository): AdminUserService {
    override fun loadUserByUsername(email: String): UserDetails {
        val existingUserEntity = adminUserRepository.findByEmail(email) ?: throw AdminUserServiceRecordNotFoundException("USER", "email=='$email'")
        return User(existingUserEntity.email, existingUserEntity.password_encrypted, ArrayList<GrantedAuthority>())
    }

    override fun getUser(email: String): AdminUserDTO {
        val existingUserEntity = adminUserRepository.findByEmail(email) ?: throw AdminUserServiceRecordNotFoundException("USER", "email=='$email'")
        return ModelMapper().map(existingUserEntity, AdminUserDTO::class.java)
    }
}