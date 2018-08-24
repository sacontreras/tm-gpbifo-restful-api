package com.sacontreras.spatial.geopackage.bundle.app.ws.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.request.RequestAdminUserLoginModel
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationRequestFilter(): UsernamePasswordAuthenticationFilter() {
    constructor(authManager: AuthenticationManager) : this() {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
        val authToken: UsernamePasswordAuthenticationToken
        authToken = try {
            val creds = ObjectMapper().readValue(request.inputStream, RequestAdminUserLoginModel::class.java)
            UsernamePasswordAuthenticationToken(
                creds.email,
                creds.password
            )
        } catch (e: IOException) {
            e.printStackTrace()
            UsernamePasswordAuthenticationToken("", "")
        }
        return authenticationManager.authenticate(authToken)
    }
}