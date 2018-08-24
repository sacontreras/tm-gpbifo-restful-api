package com.sacontreras.spatial.geopackage.bundle.app.ws.security

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
class WebSecurityConfigurer(
        val adminUserService: AdminUserService,
        val bCryptPasswordEncoder: BCryptPasswordEncoder
): WebSecurityConfigurerAdapter() {

    @Autowired
    private var auththenticationResponseHandler: AuththenticationResponseHandler? = null

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(adminUserService).passwordEncoder(bCryptPasswordEncoder)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.exceptionHandling()
            .accessDeniedHandler(auththenticationResponseHandler)
            .authenticationEntryPoint(auththenticationResponseHandler)
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
            .addFilter(AuthorizationRequestFilter(authenticationManager()))
    }

    @Throws(Exception::class)
    fun getAuthenticationFilter(authenticationManager: AuthenticationManager): AuthenticationRequestFilter {
        val authenticationRequestFilter = AuthenticationRequestFilter(authenticationManager)
        authenticationRequestFilter.setFilterProcessesUrl("/admin/login")
        authenticationRequestFilter.setAuthenticationSuccessHandler(auththenticationResponseHandler)
        authenticationRequestFilter.setAuthenticationFailureHandler(auththenticationResponseHandler)
        return authenticationRequestFilter
    }
}