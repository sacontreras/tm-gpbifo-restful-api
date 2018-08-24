package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response

import org.springframework.hateoas.ResourceSupport

data class AuthenticationResponseModel @JvmOverloads constructor(
    private var userId: String? = null,
    var authenticated: Boolean = false,
    var message: String = ""
)