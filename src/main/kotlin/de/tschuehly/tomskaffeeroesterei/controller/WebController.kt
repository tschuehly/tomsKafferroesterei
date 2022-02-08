package de.tschuehly.tomskaffeeroesterei.controller

import de.tschuehly.tomskaffeeroesterei.model.WebsiteUser
import de.tschuehly.tomskaffeeroesterei.repositories.WebsiteUserRepository
import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.GoTrueDefaultClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@Controller
class WebController(
) {
    @GetMapping("/")
    fun index(): String{
        return "index"
    }

    @GetMapping("/shop")
    fun shop(): String{
        return "shop"
    }

    @GetMapping("/login")
    fun loginSite(): String {
        return "login"
    }

    @GetMapping("/die-roesterei")
    fun dieRoesterei(): String{
        return "die-roesterei"
    }

    @GetMapping("/shop/{slug}")
    fun shopSites(@PathVariable slug: String): String{
        return "shop/$slug"
    }

}
