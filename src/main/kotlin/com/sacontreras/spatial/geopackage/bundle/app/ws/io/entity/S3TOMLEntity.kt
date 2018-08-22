package com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity

import com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity.jpaconverter.JPAConverter_StringListToJSON
import javax.persistence.*

@Entity(name = "s3toml")
class S3TOMLEntity @JvmOverloads constructor(
    @Id
    @GeneratedValue
    var id: Long = -1,

    @Column(nullable = false, unique = true)
    var toml_id: String = "",

    @Column(nullable = false)
    var s3url: String = "",

    @Column(nullable = false, length = 32)  //ixed-length: 32 for 128-bit hex encoded md5 hash
    var md5sum: String = "",

    @Convert(converter = JPAConverter_StringListToJSON::class)
    @Column(nullable = false)
    var s3toml_env_var_names: List<String> = ArrayList(),  //stored as JSONArray

    @OneToOne
    @JoinColumn(name = "fk__gpkgbundle__id")
    var relatedGpkgbundle: GpkgbundleEntity? = null
)