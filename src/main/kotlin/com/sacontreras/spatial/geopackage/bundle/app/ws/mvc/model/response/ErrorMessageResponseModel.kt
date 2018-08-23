package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.hateoas.ResourceSupport
import java.util.*

data class ErrorMessageResponseModel @JvmOverloads constructor(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MMM-dd HH:mm:ss z")
    var timestap: Date = Date(),
    var message: String = ""
): ResourceSupport() {
    constructor(message: String): this(Date(), message)
}