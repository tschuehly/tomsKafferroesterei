package de.tschuehly.tomskaffeeroesterei.security

import de.tschuehly.tomskaffeeroesterei.dto.AuthDTO
import de.tschuehly.tomskaffeeroesterei.dto.JWTVerifyDTO
import de.tschuehly.tomskaffeeroesterei.model.WebsiteUser
import de.tschuehly.tomskaffeeroesterei.repositories.WebsiteUserRepository
import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.http.GoTrueHttpException
import io.supabase.gotrue.types.GoTrueTokenResponse
import io.supabase.gotrue.types.GoTrueUserResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    val websiteUserRepository: WebsiteUserRepository) : OncePerRequestFilter() {
    @Throws
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if(SecurityContextHolder.getContext().authentication == null){
            val auth = SecurityContextHolder.getContext()
            request.cookies?.find { it.name == "JWT" }?.let { cookie ->
                try{
                    GoTrueClient.customApacheJacksonGoTrueClient<JWTVerifyDTO, GoTrueTokenResponse>(
                        url = "https://qvgwcufbvjuvyaqzpwhu.supabase.co/auth/v1",
                        headers = mapOf("apiKey" to "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF2Z3djdWZidmp1dnlhcXpwd2h1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2NDQzMjgzODIsImV4cCI6MTk1OTkwNDM4Mn0.-Uzx4NWiyTkNPjlUJwEUFkrn9YKYoTiRWj7ZIwCXEjU")

                    ).getUser(cookie.value).let {
                        SecurityContextHolder.getContext().authentication = websiteUserRepository.findByIdWithJPQL(it.id)
                    }
                }catch (e: GoTrueHttpException){
                    println("error2")
                    if(e.data?.contains("Invalid token") == true){
                        val oldCookie = request.cookies.find { it.name == "JWT" }
                        oldCookie?.maxAge = 0

                        response.addCookie(oldCookie)
                        response.addCookie(Cookie("authenticated","false"))
                        println("error")
                        response.sendRedirect("/")
                    }
                    println(e.data?.toString())
                }

            }
        }


        filterChain.doFilter(request, response)
    }
}
