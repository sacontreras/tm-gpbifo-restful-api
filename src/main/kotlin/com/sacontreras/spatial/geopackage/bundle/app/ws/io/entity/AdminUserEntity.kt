package com.sacontreras.spatial.geopackage.bundle.app.ws.io.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "adminuser")
class AdminUserEntity @JvmOverloads constructor(
    @Id
    @GeneratedValue
    var id: Long = -1,

    @Column(nullable = false, length = EntityConstants.FIELD_LEN__ID, unique = true)
    var user_id: String = "",

    @Column(nullable = false, length = FIELD_LEN__EMAIL, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password_encrypted: String = ""
) {
    companion object {
        const val FIELD_LEN__EMAIL = 128
    }
}