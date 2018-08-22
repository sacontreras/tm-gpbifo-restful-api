package com.sacontreras.spatial.geopackage.bundle.app.ws.service.impl

import com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.GpkgbundleEntity
import com.sacontreras.spatial.geopackage.bundle.app.ws.io.repository.GpkgbundleRepository
import com.sacontreras.spatial.geopackage.bundle.app.ws.service.GpkgbundleInfoService
import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.Utils
import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto.GpkgbundleDTO
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class GpkgbundleInfoServiceImpl(val gpkgbundleRepository: GpkgbundleRepository, val utils: Utils): GpkgbundleInfoService {
    override fun createGpkgbundle(newGpkgbundleDTO: GpkgbundleDTO): GpkgbundleDTO {
//        if (gpkgbundleRepository.findByEmail(newUserDTO.getEmail()) != null)
//            throw UserServiceImplException(String.format(ErrorMessages.RECORD_ALREADY_EXISTS.getMessage(), "USER", newUserDTO.getEmail()))

        val s3tomlDTO = newGpkgbundleDTO.s3toml
        s3tomlDTO.relatedGpkgbundle = newGpkgbundleDTO
        s3tomlDTO.toml_id = utils.generateRandomString(30)
        newGpkgbundleDTO.s3toml = s3tomlDTO

        for (i in 0 until newGpkgbundleDTO.s3geopackages.size) {
            val s3geopackageDTO = newGpkgbundleDTO.s3geopackages[i]
            s3geopackageDTO.relatedGpkgbundle = newGpkgbundleDTO
            s3geopackageDTO.geopackage_id = utils.generateRandomString(30)
            (ArrayList(newGpkgbundleDTO.s3geopackages))[i] = s3geopackageDTO
        }

        val modelMapper = ModelMapper()

        //generate an id (public) for saving with repo
        val newGpkgbundleEntity = modelMapper.map(newGpkgbundleDTO, GpkgbundleEntity::class.java)
        newGpkgbundleEntity.gpkgbundle_id = utils.generateRandomString(30)

        //save in repo
        val savedGpkgbundleEntity = gpkgbundleRepository.save(newGpkgbundleEntity)

        return modelMapper.map(savedGpkgbundleEntity, GpkgbundleDTO::class.java)
    }

    override fun getGpkgbundles(page: Int, limit: Int): List<GpkgbundleDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}