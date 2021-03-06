package com.sacontreras.spatial.geopackage.bundle.app.ws.security

import com.sacontreras.spatial.geopackage.bundle.app.ws.ApplicationPropertiesManager
import com.sacontreras.spatial.geopackage.bundle.app.ws.SpringApplicationContext
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.ArrayList
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationRequestFilter(
    authenticationManager: AuthenticationManager,
    private val applicationPropertiesManager: ApplicationPropertiesManager
): BasicAuthenticationFilter(authenticationManager) {

    private fun getAuthenticationToken(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        var token: UsernamePasswordAuthenticationToken? = null
        val s_token = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (s_token != null) {
            val user = Jwts.parser()
                .setSigningKey(applicationPropertiesManager.getJWTSecret())
                .parseClaimsJws(s_token.replace(SecurityConstants.AUTHORIZATION_TOKEN_PREFIX, ""))
                .body
                .subject
            if (user != null)
                token = UsernamePasswordAuthenticationToken(user, null, ArrayList<GrantedAuthority>())
        }
        return token
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        val authenticationToken: UsernamePasswordAuthenticationToken? = (
            if (header != null && header.startsWith(SecurityConstants.AUTHORIZATION_TOKEN_PREFIX))
                getAuthenticationToken(request)
            else
                null
        )
        SecurityContextHolder.getContext().authentication = authenticationToken
        chain.doFilter(request, response)
    }
}