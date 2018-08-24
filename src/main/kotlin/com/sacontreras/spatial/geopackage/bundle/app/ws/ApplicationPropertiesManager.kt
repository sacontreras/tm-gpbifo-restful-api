package com.sacontreras.spatial.geopackage.bundle.app.ws

import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ApplicationPropertiesManager(private var env: Environment) {
    fun getProperty(prop: String): String? {
        return env.getProperty(prop)
    }

    fun getJWTSecret(): String {
        return getProperty("jwtsecret") ?: ""
    }

    fun getJWTTTL(): Long {
        return getProperty("jwtttl")?.toLong() ?: 0
    }

    fun getDefaultAdminUsername(): String {
        return getProperty("gpkgbundleadmin.email") ?: ""
    }

    fun getDefaultAdminPassword(): String {
        return getProperty("gpkgbundleadmin.password") ?: ""
    }
}