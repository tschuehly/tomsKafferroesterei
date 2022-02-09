package de.tschuehly.tomskaffeeroesterei.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class WebController() {
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

    @GetMapping("/die-roesterei")
    fun dieRoesterei(): String {
        return "die-roesterei"
    }

    @GetMapping("/shop/{slug}")
    fun shopSites(@PathVariable slug: String): String {
        return "shop/$slug"
    }
}
