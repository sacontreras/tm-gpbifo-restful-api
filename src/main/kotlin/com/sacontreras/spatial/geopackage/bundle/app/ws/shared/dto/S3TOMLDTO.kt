package com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto

data class S3TOMLDTO @JvmOverloads constructor(
    var toml_id: String = "",
    var s3url: String = "",
    var md5sum: String = "",
    var s3toml_env_var_names: List<String> = ArrayList(),
    var relatedGpkgbundle: GpkgbundleDTO? = null
)