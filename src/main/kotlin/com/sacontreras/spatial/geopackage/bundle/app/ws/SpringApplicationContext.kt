package com.sacontreras.spatial.geopackage.bundle.app.ws

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class SpringApplicationContext: ApplicationContextAware {
    private var applicationContext: ApplicationContext? = null

    @Throws(BeansException::class)
    override fun setApplicationContext(appContext: ApplicationContext) {
        applicationContext = appContext
    }

    fun getBean(beanName: String): Any {
        return applicationContext!!.getBean(beanName)
    }
}