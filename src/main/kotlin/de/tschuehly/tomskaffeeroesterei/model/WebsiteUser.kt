package de.tschuehly.tomskaffeeroesterei.model

import io.supabase.gotrue.types.GoTrueUserResponse
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
data class WebsiteUser(
    @Id
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
) : UserDetails {

    constructor(goTrueUserResponse: GoTrueUserResponse) : this(
        UUID.fromString(goTrueUserResponse.id),
        goTrueUserResponse.email

    )
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }.toMutableList()
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
