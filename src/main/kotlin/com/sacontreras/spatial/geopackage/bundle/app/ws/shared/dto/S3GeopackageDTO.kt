package com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto

import java.io.Serializable
import java.net.URL

data class S3GeopackageDTO @JvmOverloads constructor(
    var geopackage_id: String = "",
    var s3url: String = "",
    var md5sum: String = "",
    var relatedGpkgbundle: GpkgbundleDTO? = null
)