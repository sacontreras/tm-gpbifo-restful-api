package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.request

data class RequestS3GeopackageInfoModel @JvmOverloads constructor(
    var s3url: String = "",
    var md5sum: String = ""
)