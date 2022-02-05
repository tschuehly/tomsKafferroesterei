package de.tschuehly.tomskaffeeroesterei.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

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

    @GetMapping("/shop/ganzebohnen")
    fun ganzebohnen(): String{
        return "ganzebohnen"
    }

    @GetMapping("/shop/filterkaffeemaschine")
    fun filterkaffeemaschine(): String{
        return "filterkaffeemaschine"
    }
}
