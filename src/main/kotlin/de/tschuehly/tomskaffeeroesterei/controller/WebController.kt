package de.tschuehly.tomskaffeeroesterei.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class WebController {
    @GetMapping
    fun index(): String{
        return "index"
    }

    @GetMapping("/shop")
    fun shop(): String{
        return "shop"
    }
    @GetMapping("/die-roesterei")
    fun dieRoesterei(): String{
        return "die-roesterei"
    }

    @GetMapping("/shop/{slug}")
    fun ganzebohnen(@PathVariable slug: String): String{
        return "shop/$slug"
    }

}
