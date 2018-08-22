package com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity

import javax.persistence.*

@Entity(name = "gpkgbundle")
class GpkgbundleEntity @JvmOverloads constructor(
    @Id
    @GeneratedValue
    var id: Long = -1,

    @Column(nullable = false, unique = true)
    var gpkgbundle_id: String = "",

    @Column(nullable = false, unique = true)
    var name: String = "",

    @OneToOne(mappedBy = "relatedGpkgbundle", cascade = arrayOf(CascadeType.ALL))
    var s3toml: S3TOMLEntity = S3TOMLEntity(),

    @OneToMany(mappedBy = "relatedGpkgbundle", cascade = arrayOf(CascadeType.MERGE))
    var s3geopackages: List<S3GeopackageEntity> = ArrayList()
)