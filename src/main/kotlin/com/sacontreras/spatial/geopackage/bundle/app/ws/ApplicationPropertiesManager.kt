package com.sacontreras.spatial.geopackage.bundle.app.ws

import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ApplicationPropertiesManager(var env: Environment) {
    fun getJWTSecret(): String {
        return env.getProperty("jwtsecret") ?: ""
    }

    fun getJWTTTL(): Long {
        return env.getProperty("jwtttl")?.toLong() ?: 0
    }
}