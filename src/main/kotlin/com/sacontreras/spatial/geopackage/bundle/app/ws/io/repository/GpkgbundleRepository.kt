package com.sacontreras.spatial.geopackage.bundle.app.ws.io.repository

import com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.GpkgbundleEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface GpkgbundleRepository: PagingAndSortingRepository<GpkgbundleEntity, Long> {
    fun findByName(name: String): GpkgbundleEntity?
}