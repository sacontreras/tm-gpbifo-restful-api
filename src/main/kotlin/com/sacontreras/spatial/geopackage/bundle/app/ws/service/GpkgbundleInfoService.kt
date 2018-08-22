package com.sacontreras.spatial.geopackage.bundle.app.ws.service

import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto.GpkgbundleDTO

interface GpkgbundleInfoService {
    fun createGpkgbundle(requestGpkgbundleDTO: GpkgbundleDTO): GpkgbundleDTO
    fun getGpkgbundles(page: Int, limit: Int): List<GpkgbundleDTO>
}