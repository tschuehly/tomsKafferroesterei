package de.tschuehly.tomskaffeeroesterei.controller

import de.tschuehly.tomskaffeeroesterei.dto.AuthDTO
import de.tschuehly.tomskaffeeroesterei.model.WebsiteUser
import de.tschuehly.tomskaffeeroesterei.repositories.WebsiteUserRepository
import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.http.GoTrueHttpException
import io.supabase.gotrue.types.GoTrueTokenResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("api/user")
class WebsiteUserController(
    private val websiteUserRepository: WebsiteUserRepository
) {
    @PostMapping("/register")
    fun register(@RequestParam credentials: Map<String, String>, response: HttpServletResponse): String? {
        if (credentials["email"] != null && credentials["password"] != null) {
            try {
                val authDTO = goTrueClient()
                    .signUpWithEmail(credentials["email"]!!, credentials["password"]!!)
                websiteUserRepository.save(WebsiteUser(authDTO))
                response.setHeader("HX-Redirect", "/konto")
                response.addCookie(
                    Cookie("JWT", authDTO.accessToken).also {
                        it.secure = true
                        it.isHttpOnly = true
                        it.path = "/"
                        it.maxAge = 6000
                    }
                )
                response.addCookie(
                    Cookie("authenticated", "true").also {
                        it.secure = true
                        it.path = "/"
                        it.maxAge = 6000
                    }
                )
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
    fun login(@RequestParam credentials: Map<String, String>, response: HttpServletResponse, request: HttpServletRequest): String {
        if (credentials["email"] != null && credentials["password"] != null) {
            try {
                val resp = goTrueClient().signInWithEmail(credentials["email"]!!, credentials["password"]!!)
                println(resp)
response.addCookie(
    Cookie("JWT", resp.accessToken).also {
        it.secure = true
        it.isHttpOnly = true
        it.path = "/"
        it.maxAge = 6000
    }
    )
                response.addCookie(
                    Cookie("authenticated", "true").also {
                        it.secure = true
                        it.path = "/"
                        it.maxAge = 6000
                    }
                )
                response.setHeader("HX-Redirect", "/konto")
            } catch (e: GoTrueHttpException) {
                if (e.data?.contains("Invalid login credentials") == true) {
                    return "/partial/invalid-login"
                } else {
                    println(e.data)
                }
            }
        }
        return "index"
    }
//
//    @GetMapping("/logout")
//    fun logout(request: HttpServletRequest, response: HttpServletResponse){
//        request.cookies?.find { it.name == "JWT" }?.let{
//            response.setHeader("Set-Cookie", "JWT=${it}; Secure; HttpOnly; Path=/;Max-Age=0;")
//            response.setHeader("HX-Redirect","/")
//        }
//    }

    private fun goTrueClient(): GoTrueClient<AuthDTO, GoTrueTokenResponse> {
        return GoTrueClient.customApacheJacksonGoTrueClient<AuthDTO, GoTrueTokenResponse>(
            url = "https://qvgwcufbvjuvyaqzpwhu.supabase.co/auth/v1",
            headers = mapOf("apiKey" to "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF2Z3djdWZidmp1dnlhcXpwd2h1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2NDQzMjgzODIsImV4cCI6MTk1OTkwNDM4Mn0.-Uzx4NWiyTkNPjlUJwEUFkrn9YKYoTiRWj7ZIwCXEjU")
        )
    }
}
