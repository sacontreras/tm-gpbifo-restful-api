package com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service

import org.hibernate.service.spi.ServiceException

open class AdminUserServiceException(override val message: String): ServiceException(message)