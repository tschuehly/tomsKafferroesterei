package de.tschuehly.tomskaffeeroesterei.security

import de.tschuehly.tomskaffeeroesterei.model.WebsiteUser
import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.types.GoTrueTokenResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter() : OncePerRequestFilter() {
    @Throws
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.cookies?.find { it.name == "JWT" }?.let {
            SecurityContextHolder.getContext().authentication = GoTrueClient.customApacheJacksonGoTrueClient<WebsiteUser, GoTrueTokenResponse>(
                url = "https://qvgwcufbvjuvyaqzpwhu.supabase.co/auth/v1",
                headers = mapOf("apiKey" to "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF2Z3djdWZidmp1dnlhcXpwd2h1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2NDQzMjgzODIsImV4cCI6MTk1OTkwNDM4Mn0.-Uzx4NWiyTkNPjlUJwEUFkrn9YKYoTiRWj7ZIwCXEjU")
            ).getUser(it.value)
        }
        filterChain.doFilter(request, response)
    }
}
