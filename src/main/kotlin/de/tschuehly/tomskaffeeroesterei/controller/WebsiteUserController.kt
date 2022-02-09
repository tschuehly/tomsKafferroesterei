package de.tschuehly.tomskaffeeroesterei.controller

import de.tschuehly.tomskaffeeroesterei.model.WebsiteUser
import de.tschuehly.tomskaffeeroesterei.repositories.WebsiteUserRepository
import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.http.GoTrueHttpException
import io.supabase.gotrue.types.GoTrueTokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/user")
open class WebsiteUserController(
    private val websiteUserRepository: WebsiteUserRepository
) {
    @PostMapping("/register")
    open fun register(@RequestParam credentials: Map<String, String>): String? {
        if (credentials["email"] != null && credentials["password"] != null) {
            try {

                val websiteUser = goTrueClient()
                    .signUpWithEmail(credentials["email"]!!, credentials["password"]!!)
                websiteUserRepository.save(websiteUser)
                return "/partial/check-email"
            } catch (e: GoTrueHttpException) {
                if (e.data?.contains("User already registered") == true) {
                    return "/partial/user-already-registered"
                } else {
                    println(e.data)
                }
            }
        }

        return "index"
    }

    @PostMapping("/login")
    fun login(@RequestParam credentials: Map<String, String>, response: HttpServletResponse): String {
        val resp = goTrueClient().signInWithEmail(credentials["email"]!!, credentials["password"]!!)
        response.setHeader("Set-Cookie", "JWT=${resp.accessToken}; Secure; HttpOnly; SameSite=Strict;Max-Age=6000;")
        return "index"
    }

    private fun goTrueClient(): GoTrueClient<WebsiteUser, GoTrueTokenResponse> {
        return GoTrueClient.customApacheJacksonGoTrueClient<WebsiteUser, GoTrueTokenResponse>(
            url = "https://qvgwcufbvjuvyaqzpwhu.supabase.co/auth/v1",
            headers = mapOf("apiKey" to "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF2Z3djdWZidmp1dnlhcXpwd2h1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2NDQzMjgzODIsImV4cCI6MTk1OTkwNDM4Mn0.-Uzx4NWiyTkNPjlUJwEUFkrn9YKYoTiRWj7ZIwCXEjU")
        )
    }
}
