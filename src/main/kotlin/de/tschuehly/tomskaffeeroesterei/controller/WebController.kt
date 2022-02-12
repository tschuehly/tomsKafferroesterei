package de.tschuehly.tomskaffeeroesterei.controller

import de.tschuehly.tomskaffeeroesterei.model.WebsiteUser
import de.tschuehly.tomskaffeeroesterei.repositories.WebsiteUserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView

@Controller
class WebController(
    val websiteUserRepository: WebsiteUserRepository
) {
    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/shop")
    fun shop(): String {
        return "shop"
    }

    @GetMapping("/login")
    fun loginSite(): String {
        return "login"
    }
    @GetMapping("/konto")
    fun order(): String {
        return "account"
    }
    @GetMapping("/verwaltung")
    fun management(model: MutableMap<String, List<WebsiteUser>>): ModelAndView {

        model["websiteUserList"] = websiteUserRepository.findAll()
        return ModelAndView("verwaltung",model)
    }
    @GetMapping("/admin/editor")
    fun editor(): String {
        return "editor"
    }

    @GetMapping("/die-roesterei")
    fun dieRoesterei(): String {
        return "die-roesterei"
    }

    @GetMapping("/shop/{slug}")
    fun shopSites(@PathVariable slug: String): String {
        return "shop/$slug"
    }
}
