package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response

import org.springframework.hateoas.ResourceSupport

data class GpkgbundleInfoResponseModel @JvmOverloads constructor(
    var gpkgbundle_id: String = "",
    var name: String = "",
    var s3toml: S3TOMLInfoResponseModel = S3TOMLInfoResponseModel(),
    var s3geopackages: ArrayList<S3GeopackageInfoResponseModel> = ArrayList()
): ResourceSupport()

