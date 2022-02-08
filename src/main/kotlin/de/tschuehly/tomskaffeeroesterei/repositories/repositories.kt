package de.tschuehly.tomskaffeeroesterei.repositories

import de.tschuehly.tomskaffeeroesterei.model.WebsiteUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WebsiteUserRepository: JpaRepository<WebsiteUser, UUID>
