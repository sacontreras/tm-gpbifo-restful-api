package com.sacontreras.spatial.geopackage.bundle.app.ws.service

import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto.AdminUserDTO
import org.springframework.security.core.userdetails.UserDetailsService

interface AdminUserService: UserDetailsService {
    fun createAdminUser(newAdminUserDTO: AdminUserDTO): AdminUserDTO
    fun getUser(email: String): AdminUserDTO
}