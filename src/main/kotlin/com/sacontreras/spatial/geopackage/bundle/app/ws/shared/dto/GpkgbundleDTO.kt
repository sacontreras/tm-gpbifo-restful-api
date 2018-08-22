package com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto

data class GpkgbundleDTO @JvmOverloads constructor(
    var id: Long = -1,
    var gpkgbundle_id: String = "",
    var name: String = "",
    var s3toml: S3TOMLDTO = S3TOMLDTO(),
    var s3geopackages: List<S3GeopackageDTO> = ArrayList()
)