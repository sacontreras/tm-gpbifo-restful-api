package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.request

data class RequestGpkgBundleInfoModel @JvmOverloads constructor(
    var name: String = "",
    var s3toml: RequestS3TOMLInfoModel = RequestS3TOMLInfoModel(),
    var s3geopackages: List<RequestS3GeopackageInfoModel> = ArrayList()
)