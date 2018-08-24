package com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service

class AdminUserServiceRecordAlreadyExistsException(
    val type: String,
    val detail: String
): AdminUserServiceException(if (!detail.isEmpty()) String.format("$BASE_MSG$DETAIL", type, detail) else String.format(BASE_MSG, type)) {
    companion object {
        private const val BASE_MSG = "%s record already exists"
        private const val DETAIL = ": %s"
    }

    constructor(type: String): this(type, "")
}