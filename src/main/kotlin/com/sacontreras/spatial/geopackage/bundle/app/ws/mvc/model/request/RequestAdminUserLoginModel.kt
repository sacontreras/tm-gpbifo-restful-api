package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.request

data class RequestAdminUserLoginModel @JvmOverloads constructor(
    var email: String = "",
    var password: String = ""
)