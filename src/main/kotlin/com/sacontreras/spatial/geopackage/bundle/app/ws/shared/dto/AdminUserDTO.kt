package com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto

data class AdminUserDTO @JvmOverloads constructor(
    var user_id: String = "",
    var email: String = "",
    var password_encrypted: String = ""
)