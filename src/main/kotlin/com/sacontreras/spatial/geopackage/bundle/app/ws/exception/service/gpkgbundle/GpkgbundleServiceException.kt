package com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.gpkgbundle

import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.ServiceException

open class GpkgbundleServiceException(override val message: String): ServiceException(message)