package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response

import org.springframework.hateoas.ResourceSupport

data class AccessDeniedResponseModel @JvmOverloads constructor(
    var userId: String? = null,
    var message: String = ""
)