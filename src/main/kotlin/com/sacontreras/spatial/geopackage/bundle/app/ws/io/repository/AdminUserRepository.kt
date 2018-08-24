package com.sacontreras.spatial.geopackage.bundle.app.ws.io.repository

import com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.AdminUserEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminUserRepository: PagingAndSortingRepository<AdminUserEntity, Long> {
    fun findByEmail(email: String): AdminUserEntity?
}