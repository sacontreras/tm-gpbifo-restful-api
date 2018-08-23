package com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.gpkgbundle

class GpkgbundleServiceNewRecordFieldConstraintViolationException(val field: String): GpkgbundleServiceException(String.format(MSG, field)) {
    companion object {
        private const val MSG = "Field constraint violation: %s"
    }
}