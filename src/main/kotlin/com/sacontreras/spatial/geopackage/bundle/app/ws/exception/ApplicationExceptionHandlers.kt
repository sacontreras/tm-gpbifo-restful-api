package com.sacontreras.spatial.geopackage.bundle.app.ws.exception

import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.controller.ControllerException
import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.ServiceException
import com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response.ErrorMessageResponseModel
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
class ApplicationExceptionHandlers {
//    @ExceptionHandler(value = *arrayOf(AuthenticationException::class))
//    fun handlerAuthenticationException(ex: AuthenticationException, request: WebRequest): ResponseEntity<Any> {
//        return ResponseEntity(ErrorMessageResponseModel(Date(), ex.message), HttpHeaders(), HttpStatus.FORBIDDEN)
//    }

    @ExceptionHandler(value = [ControllerException::class])
    fun handleUserControllerException(ex: ControllerException, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ErrorMessageResponseModel(ex.message), HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [ServiceException::class])
    fun handleUserControllerException(ex: ServiceException, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ErrorMessageResponseModel(ex.message), HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleOtherException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ErrorMessageResponseModel(ex.message?: ""), HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}