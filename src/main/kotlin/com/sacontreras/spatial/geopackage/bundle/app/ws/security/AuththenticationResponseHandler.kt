package com.sacontreras.spatial.geopackage.bundle.app.ws.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.sacontreras.spatial.geopackage.bundle.app.ws.ApplicationPropertiesManager
import com.sacontreras.spatial.geopackage.bundle.app.ws.SpringApplicationContext
import com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response.AccessDeniedResponseModel
import com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response.AuthenticationResponseModel
import com.sacontreras.spatial.geopackage.bundle.app.ws.service.AdminUserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuththenticationResponseHandler : AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @Autowired
    private val springApplicationContext: SpringApplicationContext? = null

    @Throws(IOException::class)
    private fun responseText(response: HttpServletResponse, content: String, contextType: String) {
        response.contentType = contextType
        val bytes = content.toByteArray(StandardCharsets.UTF_8)
        response.setContentLength(bytes.size)
        response.outputStream.write(bytes)
        response.flushBuffer()
    }

    //auth entrypoint - full authentication is required
    @Throws(IOException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        val hdr_accept = request.getHeader(HttpHeaders.ACCEPT)
        val acceptXML = hdr_accept == MediaType.APPLICATION_XML_VALUE
        if (hdr_accept == MediaType.APPLICATION_JSON_VALUE || acceptXML) {
            responseText(
                response,
                (if (acceptXML) XmlMapper() else ObjectMapper()).writeValueAsString(
                    AuthenticationResponseModel(
                        null,
                        false,
                        authException.message ?: ""
                    )
                ),
                hdr_accept
            )
        } else
            responseText(
                response,
                authException.message ?: "",
                MediaType.TEXT_PLAIN_VALUE
            )
    }

    //access denied
    @Throws(IOException::class, ServletException::class)
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        val hdr_accept = request.getHeader(HttpHeaders.ACCEPT)
        val acceptXML = hdr_accept == MediaType.APPLICATION_XML_VALUE
        if (hdr_accept == MediaType.APPLICATION_JSON_VALUE || acceptXML) {
            responseText(
                response,
                (if (acceptXML) XmlMapper() else ObjectMapper()).writeValueAsString(
                    AccessDeniedResponseModel(
                        null,
                        accessDeniedException.message ?: ""
                    )
                ),
                hdr_accept
            )
        } else
            responseText(
                response,
                accessDeniedException.message ?: "",
                MediaType.TEXT_PLAIN_VALUE
            )
    }

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        val hdr_accept = request.getHeader(HttpHeaders.ACCEPT)
        val acceptXML = hdr_accept == MediaType.APPLICATION_XML_VALUE
        if (hdr_accept == MediaType.APPLICATION_JSON_VALUE || acceptXML) {
            responseText(
                response,
                (if (acceptXML) XmlMapper() else ObjectMapper()).writeValueAsString(
                    AuthenticationResponseModel(
                        null,
                        false,
                        authException.message ?: ""
                    )
                ),
                hdr_accept
            )
        } else
            responseText(
                response,
                authException.message ?: "",
                MediaType.TEXT_PLAIN_VALUE
            )
    }

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authResult: Authentication) {
        val username = (authResult.principal as User).username
        val applicationPropertiesManager = springApplicationContext?.getBean("applicationPropertiesManager") as ApplicationPropertiesManager
        val token = Jwts.builder()
            .setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + applicationPropertiesManager.getJWTTTL()))
            .signWith(SignatureAlgorithm.HS512, applicationPropertiesManager.getJWTSecret())
            .compact()
        val adminUserService = springApplicationContext?.getBean("adminUserServiceImpl") as AdminUserService
        val adminUserDTO = adminUserService.getUser(username)    //note that this is email in this case
        response.addHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.AUTHORIZATION_TOKEN_PREFIX + token)
        response.addHeader("UserId", adminUserDTO.user_id)
        val hdr_accept = request.getHeader(HttpHeaders.ACCEPT)
        var acceptXML = hdr_accept == MediaType.APPLICATION_XML_VALUE
        if (hdr_accept == MediaType.APPLICATION_JSON_VALUE || acceptXML) {
            responseText(
                response,
                (if (acceptXML) XmlMapper() else ObjectMapper()).writeValueAsString(
                    AuthenticationResponseModel(
                        adminUserDTO.user_id,
                        true,
                        String.format("Admin user '%s' authenticated successfully", username)
                    )
                ),
                hdr_accept
            )
        } else
            responseText(
                response,
                String.format("Admin user '%s' authenticated successfully", username),
                MediaType.TEXT_PLAIN_VALUE
            )
    }
}