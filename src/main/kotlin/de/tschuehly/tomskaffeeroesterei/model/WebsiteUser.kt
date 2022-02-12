package de.tschuehly.tomskaffeeroesterei.model

import de.tschuehly.tomskaffeeroesterei.dto.AuthDTO
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
    var id: UUID,
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

    constructor(authDTO: AuthDTO) : this(
        id = authDTO.user.id,
        email = authDTO.user.email
    )

    override fun getName(): String {
        return email
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
