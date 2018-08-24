package com.sacontreras.spatial.geopackage.bundle.app.ws

import com.sacontreras.spatial.geopackage.bundle.app.ws.security.AuththenticationResponseHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class GeopackageBundleAppWsApplication {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun springApplicationContext(): SpringApplicationContext {
        return SpringApplicationContext()
    }

    @Bean
    fun authenticationResponseHandler(): AuththenticationResponseHandler {
        return AuththenticationResponseHandler()
    }
}

fun main(args: Array<String>) {
    runApplication<GeopackageBundleAppWsApplication>(*args)
}
