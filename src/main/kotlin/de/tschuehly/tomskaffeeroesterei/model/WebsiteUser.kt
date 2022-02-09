package de.tschuehly.tomskaffeeroesterei.model

import io.supabase.gotrue.types.GoTrueUserResponse
import org.apache.catalina.security.SecurityUtil
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.util.StringUtils
import java.util.*
import javax.persistence.*

@Entity
data class WebsiteUser(
    @Id
    @MapsId("auth.users_id")
    var uuid: UUID,
    @Column(unique = true)
    var email: String,
    @ElementCollection
    var roles: List<String> = listOf("ROLE_USER"),
    var firstName: String = "",
    var lastName: String = "",
    var tel: String = "",
    var postalCode: String = "",
    var city: String = "",
    var street: String = ""

) : Authentication {

    constructor(goTrueUserResponse: GoTrueUserResponse) : this(
        UUID.fromString(goTrueUserResponse.id),
        goTrueUserResponse.email

    )

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val auth = this.roles.toTypedArray()
        return AuthorityUtils.createAuthorityList(*auth)
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return this.email
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
    }
}
