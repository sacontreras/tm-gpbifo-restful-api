package com.sacontreras.spatial.geopackage.bundle.app.ws.security

import com.sacontreras.spatial.geopackage.bundle.app.ws.ApplicationPropertiesManager
import com.sacontreras.spatial.geopackage.bundle.app.ws.service.AdminUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
class WebSecurityConfigurer: WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var adminUserService: AdminUserService

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var authenticationResponseHandler: AuththenticationResponseHandler

    @Autowired
    private lateinit var applicationPropertiesManager: ApplicationPropertiesManager


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(adminUserService).passwordEncoder(bCryptPasswordEncoder)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.exceptionHandling()
            .accessDeniedHandler(authenticationResponseHandler)
            .authenticationEntryPoint(authenticationResponseHandler)
        http.csrf()
            .disable() //when enabled will result in "Could not verify the provided CSRF token because your session was not found." without supporting implementation - so disable for now
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/gpkgbundles")
            .permitAll()
        http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(getAuthenticationFilter(authenticationManager()))
            .addFilter(AuthorizationRequestFilter(authenticationManager(), applicationPropertiesManager))
    }

    @Throws(Exception::class)
    fun getAuthenticationFilter(authenticationManager: AuthenticationManager): AuthenticationRequestFilter {
        val authenticationRequestFilter = AuthenticationRequestFilter(authenticationManager)
        authenticationRequestFilter.setFilterProcessesUrl("/admin/login")
        authenticationRequestFilter.setAuthenticationSuccessHandler(authenticationResponseHandler)
        authenticationRequestFilter.setAuthenticationFailureHandler(authenticationResponseHandler)
        return authenticationRequestFilter
    }
}