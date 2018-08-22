package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response

import org.springframework.hateoas.ResourceSupport

data class S3GeopackageInfoResponseModel @JvmOverloads constructor(
    var geopackage_id: String = "",
    var s3url: String = "",
    var md5sum: String = ""
): ResourceSupport()