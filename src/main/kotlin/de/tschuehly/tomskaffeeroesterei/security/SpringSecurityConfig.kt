package de.tschuehly.tomskaffeeroesterei.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true,jsr250Enabled = true)
@EnableWebSecurity(debug = false)
class SpringSecurityConfig(
    val jwtFilter: JwtFilter
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .headers().frameOptions().sameOrigin().and()
            .cors().configurationSource(corsConfigurationSource()).and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin()
            .loginPage("/login")
            .and()
            .logout()
            .deleteCookies("JWT","authenticated")
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .and()
            .authorizeRequests()
            // Our public endpoints
            .antMatchers(
                "/index.html", "/favicon**.webp", "/*.js",
                "/*.css", "/static/**", "/h2-console/*","/login","/signup","/"
            ).permitAll()
            .antMatchers(HttpMethod.GET, "/","/partial/**", "/shop/**","/logout","/die-roesterei").permitAll()
            .antMatchers(HttpMethod.POST, "/api/user/register","/api/user/login").permitAll()
            // Our private endpoints
            .antMatchers("/konto").hasRole("USER")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
        configuration.addAllowedOrigin("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
