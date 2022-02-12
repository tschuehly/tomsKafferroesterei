package de.tschuehly.tomskaffeeroesterei.repositories

import de.tschuehly.tomskaffeeroesterei.model.WebsiteUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface WebsiteUserRepository: JpaRepository<WebsiteUser, UUID>{

    @Query(
        "SELECT u FROM WebsiteUser AS u JOIN FETCH u.roles WHERE u.id=:id"
    )
    fun findByIdWithJPQL(id: UUID): WebsiteUser
}
