package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response

import org.springframework.hateoas.ResourceSupport

data class S3TOMLInfoResponseModel @JvmOverloads constructor(
    var toml_id: String = "",
    var s3url: String = "",
    var md5sum: String = "",
    var s3toml_env_var_names: ArrayList<String> = ArrayList()
): ResourceSupport()