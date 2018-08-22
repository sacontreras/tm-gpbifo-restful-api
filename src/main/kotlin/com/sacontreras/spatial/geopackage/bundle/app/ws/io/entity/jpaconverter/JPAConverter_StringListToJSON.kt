package com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.jpaconverter

import javax.persistence.AttributeConverter
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException

//class JPAConverter_StringListToJSON: AttributeConverter<JvmType.Object, String> {
//    override fun convertToDatabaseColumn(attribute: JvmType.Object?): String? {
//        try {
//            return ObjectMapper().writeValueAsString(attribute)
//        } catch (ex: JsonProcessingException) {
//            ex.printStackTrace()
//            return null
//        }
//    }
//
//    override fun convertToEntityAttribute(dbData: String?): JvmType.Object? {
//        try {
//            return ObjectMapper().readValue(dbData, JvmType.Object::class.java)
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            return null
//        }
//    }
//}

class JPAConverter_StringListToJSON: AttributeConverter<List<String>, String> {
    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        try {
            return ObjectMapper().writeValueAsString(attribute)
        } catch (ex: JsonProcessingException) {
            ex.printStackTrace()
            return null
        }
    }

    override fun convertToEntityAttribute(dbData: String?): List<String>? {
        try {
            val objectMapper = ObjectMapper()
            return objectMapper.readValue<List<String>>(dbData, objectMapper.getTypeFactory().constructCollectionType(List::class.java, String::class.java))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}