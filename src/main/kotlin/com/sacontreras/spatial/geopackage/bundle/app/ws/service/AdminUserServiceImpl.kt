package com.sacontreras.spatial.geopackage.bundle.app.ws.service

import com.sacontreras.spatial.geopackage.bundle.app.ws.ApplicationPropertiesManager
import com.sacontreras.spatial.geopackage.bundle.app.ws.SpringApplicationContext
import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.AdminUserServiceRecordAlreadyExistsException
import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.AdminUserServiceRecordNotFoundException
import com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.AdminUserEntity
import com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.EntityConstants
import com.sacontreras.spatial.geopackage.bundle.app.ws.io.repository.AdminUserRepository
import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.Utils
import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto.AdminUserDTO
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class AdminUserServiceImpl: AdminUserService {
    @Autowired
    private lateinit var adminUserRepository: AdminUserRepository

    @Autowired
    private lateinit var utils: Utils

    @Autowired
    private var bCryptPasswordEncoder: BCryptPasswordEncoder? = null

    @Autowired
    private val springApplicationContext: SpringApplicationContext? = null


    override fun createAdminUser(newAdminUserDTO: AdminUserDTO): AdminUserDTO {
        if (adminUserRepository.findByEmail(newAdminUserDTO.email) != null)
            throw AdminUserServiceRecordAlreadyExistsException("adminuser", String.format("email=='%s'", newAdminUserDTO.email))

        val modelMapper = ModelMapper()
        val newAdminUserEntity = modelMapper.map(newAdminUserDTO, AdminUserEntity::class.java)
        newAdminUserEntity.user_id = utils.generateRandomString(EntityConstants.FIELD_LEN__ID)
        //encode user-provided password with blowfish cipher provided by BCryptPasswordEncoder
        newAdminUserEntity.password_encrypted = bCryptPasswordEncoder?.encode(newAdminUserDTO.password)!!
        return modelMapper.map(adminUserRepository.save(newAdminUserEntity), AdminUserDTO::class.java)
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val existingUserEntity = adminUserRepository.findByEmail(email) ?: throw AdminUserServiceRecordNotFoundException("USER", "email=='$email'")
        return User(existingUserEntity.email, existingUserEntity.password_encrypted, ArrayList<GrantedAuthority>())
    }

    override fun getUser(email: String): AdminUserDTO {
        val existingUserEntity = adminUserRepository.findByEmail(email) ?: throw AdminUserServiceRecordNotFoundException("USER", "email=='$email'")
        return ModelMapper().map(existingUserEntity, AdminUserDTO::class.java)
    }

    @PostConstruct
    private fun validateCreateDefaultAdminUser() {
        val page_adminUserEntity = adminUserRepository.findAll(PageRequest.of(0, 1))    //we just want to know that at least one admin user exists
        val list_adminUserEntity = page_adminUserEntity.content
        if (list_adminUserEntity.isEmpty()) {
            val applicationPropertiesManager = springApplicationContext?.getBean("applicationPropertiesManager") as ApplicationPropertiesManager
            createAdminUser(
                AdminUserDTO(
                    null,
                    applicationPropertiesManager.getProperty("gpkgbundleadmin.email") ?: "",
                    applicationPropertiesManager.getProperty("gpkgbundleadmin.password") ?: "",
                    null
                )
            )
        }
    }
}