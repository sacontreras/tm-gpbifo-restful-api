package com.sacontreras.spatial.geopackage.bundle.app.ws.service

import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.GpkgbundleServiceNewRecordFieldConstraintViolationException
import com.sacontreras.spatial.geopackage.bundle.app.ws.exception.service.GpkgbundleServiceRecordAlreadyExists
import com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.EntityConstants
import com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.GpkgbundleEntity
import com.sacontreras.spatial.geopackage.bundle.app.ws.io.repository.GpkgbundleRepository
import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.Utils
import com.sacontreras.spatial.geopackage.bundle.app.ws.shared.dto.GpkgbundleDTO
import org.modelmapper.ModelMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class GpkgbundleInfoServiceImpl(val gpkgbundleRepository: GpkgbundleRepository, val utils: Utils): GpkgbundleInfoService {
    override fun createGpkgbundle(newGpkgbundleDTO: GpkgbundleDTO): GpkgbundleDTO {
        //validate business rules on DTO
        if (gpkgbundleRepository.findByName(newGpkgbundleDTO.name) != null)
            throw GpkgbundleServiceRecordAlreadyExists("gpkgbundle", String.format("name=='%s'", newGpkgbundleDTO.name))
        if (newGpkgbundleDTO.name.trim().isEmpty())
            throw GpkgbundleServiceNewRecordFieldConstraintViolationException("name is required")
        if (newGpkgbundleDTO.s3toml.s3url.trim().isEmpty())
            throw GpkgbundleServiceNewRecordFieldConstraintViolationException("s3toml.s3url is required")
        val md5sum_len = newGpkgbundleDTO.s3toml.md5sum.trim().length
        if (md5sum_len == 0)
            throw GpkgbundleServiceNewRecordFieldConstraintViolationException("s3toml.md5sum is required")
        else if(md5sum_len < EntityConstants.FIELD_LEN__MD5SUM)
            throw GpkgbundleServiceNewRecordFieldConstraintViolationException(String.format("s3toml.md5sum must be %d characters in length but is only %d", EntityConstants.FIELD_LEN__MD5SUM, md5sum_len))
        if (newGpkgbundleDTO.s3geopackages.isEmpty()) {
            throw GpkgbundleServiceNewRecordFieldConstraintViolationException("s3geopackages is empty is required")
        } else {
            for (i in 0 until newGpkgbundleDTO.s3geopackages.size) {
                val s3geopackage = newGpkgbundleDTO.s3geopackages[i]
                if (s3geopackage.s3url.trim().isEmpty())
                    throw GpkgbundleServiceNewRecordFieldConstraintViolationException(String.format("s3geopackage[%d].s3url is required", i))
                if (s3geopackage.md5sum.trim().isEmpty())
                    throw GpkgbundleServiceNewRecordFieldConstraintViolationException(String.format("s3geopackage[%d].md5sum is required", i))
            }
        }

        val s3tomlDTO = newGpkgbundleDTO.s3toml
        s3tomlDTO.relatedGpkgbundle = newGpkgbundleDTO
        s3tomlDTO.toml_id = utils.generateRandomString(EntityConstants.FIELD_LEN__ID)
        newGpkgbundleDTO.s3toml = s3tomlDTO

        for (i in 0 until newGpkgbundleDTO.s3geopackages.size) {
            val s3geopackageDTO = newGpkgbundleDTO.s3geopackages[i]
            s3geopackageDTO.relatedGpkgbundle = newGpkgbundleDTO
            s3geopackageDTO.geopackage_id = utils.generateRandomString(EntityConstants.FIELD_LEN__ID)
            (ArrayList(newGpkgbundleDTO.s3geopackages))[i] = s3geopackageDTO
        }

        val modelMapper = ModelMapper()

        //generate an id (public) for saving with repo
        val newGpkgbundleEntity = modelMapper.map(newGpkgbundleDTO, GpkgbundleEntity::class.java)
        newGpkgbundleEntity.gpkgbundle_id = utils.generateRandomString(EntityConstants.FIELD_LEN__ID)

        //save in repo
        val savedGpkgbundleEntity = gpkgbundleRepository.save(newGpkgbundleEntity)

        return modelMapper.map(savedGpkgbundleEntity, GpkgbundleDTO::class.java)
    }

    override fun getGpkgbundles(page: Int, limit: Int): List<GpkgbundleDTO> {
        val list_gpkgbundleDTO = ArrayList<GpkgbundleDTO>()
        val page_gpkgbundleEntity = gpkgbundleRepository.findAll(PageRequest.of(page, limit))
        val list_gpkgbundleEntity = page_gpkgbundleEntity.getContent()
        if (!list_gpkgbundleEntity.isEmpty()) {
            val modelMapper = ModelMapper()
            for (gpkgbundleEntity in list_gpkgbundleEntity)
                list_gpkgbundleDTO.add(modelMapper.map(gpkgbundleEntity, GpkgbundleDTO::class.java))
        }
        return list_gpkgbundleDTO
    }
}