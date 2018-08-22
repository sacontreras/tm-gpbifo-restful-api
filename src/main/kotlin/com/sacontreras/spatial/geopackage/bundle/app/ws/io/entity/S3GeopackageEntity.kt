package com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity

import javax.persistence.*

@Entity(name = "s3geopackage")
class S3GeopackageEntity @JvmOverloads constructor(
    @Id
    @GeneratedValue
    var id: Long = -1,

    @Column(nullable = false, length = EntityConstants.FIELD_LEN__ID, unique = true)    //NOTE: "unique" column-behavior is not enforced in MySQL unless "length" is also given
    var geopackage_id: String = "",

    @Column(nullable = false)
    var s3url: String = "",

    @Column(nullable = false, length = EntityConstants.FIELD_LEN__MD5SUM)
    var md5sum: String = "",

    @ManyToOne
    @JoinColumn(name = "fk__gpkgbundle__id")
    var relatedGpkgbundle: GpkgbundleEntity? = null
)