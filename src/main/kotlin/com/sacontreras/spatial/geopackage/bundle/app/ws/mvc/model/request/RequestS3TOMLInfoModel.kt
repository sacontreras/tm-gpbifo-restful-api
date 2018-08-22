package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.request

data class RequestS3TOMLInfoModel @JvmOverloads constructor(
    var s3url: String = "",
    var md5sum: String = "",
    var s3toml_env_var_names: List<String> = ArrayList()
)