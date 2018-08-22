package com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity

import javax.persistence.*

@Entity(name = "s3geopackage")
class S3GeopackageEntity @JvmOverloads constructor(
    @Id
    @GeneratedValue
    var id: Long = -1,

    @Column(nullable = false, unique = true)
    var geopackage_id: String = "",

    @Column(nullable = false)
    var s3url: String = "",

    @Column(nullable = false, length = 32)  //ixed-length: 32 for 128-bit hex encoded md5 hash
    var md5sum: String = "",

    @ManyToOne
    @JoinColumn(name = "fk__gpkgbundle__id")
    var relatedGpkgbundle: GpkgbundleEntity? = null
)