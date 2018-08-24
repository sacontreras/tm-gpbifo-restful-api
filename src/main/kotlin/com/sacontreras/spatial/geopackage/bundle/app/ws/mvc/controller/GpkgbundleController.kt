package com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.controller

import com.sacontreras.spatial.geopackage.bundle.app.ws.io.repository.RepositoryConstants
import com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.request.RequestGpkgBundleInfoModel
import com.sacontreras.spatial.geopackage.bundle.app.ws.mvc.model.response.GpkgbundleInfoResponseModel
import com.sacontreras.spatial.geopackage.bundle.app.ws.service.GpkgbundleInfoService
import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto.GpkgbundleDTO
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/gpkgbundles")    //http://localhost:8080/gpkgbundles
class GpkgbundleController {

    @Autowired
    private lateinit var gpkgbundleInfoService: GpkgbundleInfoService

    @PostMapping(consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    fun createGpkgbundle(@RequestBody requestNewGpkgbundleInfo: RequestGpkgBundleInfoModel): Resource<GpkgbundleInfoResponseModel> {
        val modelMapper = ModelMapper()

        //save info (mapped to DTO) to repository using the gpkgbundleinfo service
        val savedNewGpkgbundleInfoDTO = gpkgbundleInfoService.createGpkgbundle(modelMapper.map(requestNewGpkgbundleInfo, GpkgbundleDTO::class.java))

        //map DTO of saved info (has new values - e.g. id) to base response
        val savedNewGpkgbundleInfoResponse = modelMapper.map(
            savedNewGpkgbundleInfoDTO,
            GpkgbundleInfoResponseModel::class.java
        )

        //wrap the response within a Resource object for HATEOAS (nav links) support
        return Resource(savedNewGpkgbundleInfoResponse)
    }

    @GetMapping(produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    fun getGpkgbundles(
        @RequestParam(value = "page", defaultValue = "0")
        page: Int,
        @RequestParam(value = "limit", defaultValue = RepositoryConstants.PAGE__LIMIT_SIZE__DEFAULT__AS_STRING)
        limit: Int
    ): Resources<GpkgbundleInfoResponseModel> {
        val list_gpkgbundleInfoResponse = ArrayList<GpkgbundleInfoResponseModel>()
        val list_gpkgbundleInfoDTO = gpkgbundleInfoService.getGpkgbundles(page, limit)
        if (!list_gpkgbundleInfoDTO.isEmpty()) {
            val modelMapper = ModelMapper()
            for (gpkgbundleInfoDTO in list_gpkgbundleInfoDTO) {
                val gpkgbundleInfoResponse = modelMapper.map(gpkgbundleInfoDTO, GpkgbundleInfoResponseModel::class.java)
                //gpkgbundleInfoResponse.add(linkTo(methodOn<GpkgbundleController>(GpkgbundleController::class.java).getGpkgbundle(gpkgbundleInfoDTO.getGpkgbundle_id())).withSelfRel())
                list_gpkgbundleInfoResponse.add(gpkgbundleInfoResponse)
            }
        }
        return Resources(list_gpkgbundleInfoResponse)
    }
}

