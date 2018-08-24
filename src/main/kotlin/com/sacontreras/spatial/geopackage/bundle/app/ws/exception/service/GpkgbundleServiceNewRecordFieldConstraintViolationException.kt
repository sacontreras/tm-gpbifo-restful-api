package com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service

class GpkgbundleServiceNewRecordFieldConstraintViolationException(val field: String): GpkgbundleServiceException(String.format(MSG, field)) {
    companion object {
        private const val MSG = "Field constraint violation: %s"
    }
}