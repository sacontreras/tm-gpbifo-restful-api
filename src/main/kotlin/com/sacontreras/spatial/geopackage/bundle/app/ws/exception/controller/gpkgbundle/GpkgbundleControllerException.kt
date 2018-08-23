package com.sacontreras.spatial.geopackage.bundle.app.ws.exception.controller.gpkgbundle

import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.controller.ControllerException

open class GpkgbundleControllerException(override val message: String): ControllerException(message)