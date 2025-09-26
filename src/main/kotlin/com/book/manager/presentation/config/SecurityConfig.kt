package com.book.manager.presentation.config

import com.book.manager.application.service.AuthenticationService
import com.book.manager.domain.enums.RoleType
import com.book.manager.presentation.handler.BookManagerAccessDeniedHandler
import com.book.manager.presentation.handler.BookManagerAuthenticationEntryPoint
import com.book.manager.presentation.handler.BookManagerAuthenticationFailureHandler
import com.book.manager.presentation.handler.BookManagerAuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import com.book.manager.application.service.BookManagerUserDetailsService
import org.springframework.http.HttpMethod
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun userDetailsService(authenticationService: AuthenticationService): UserDetailsService {
        return BookManagerUserDetailsService(authenticationService)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            // 認可設定
            authorizeHttpRequests {
                authorize(HttpMethod.OPTIONS, "/**", permitAll)
                authorize("/login", permitAll)
                authorize("/admin/**", hasAuthority(RoleType.ADMIN.toString()))
                authorize(anyRequest, authenticated)
            }

            // CSRFを無効化
            csrf { disable() }

            // フォームベース認証設定
            formLogin {
                loginProcessingUrl = "/login"
                usernameParameter = "email"
                passwordParameter = "pass"
                authenticationSuccessHandler = BookManagerAuthenticationSuccessHandler()
                authenticationFailureHandler = BookManagerAuthenticationFailureHandler()
            }

            // 例外処理設定
            exceptionHandling {
                authenticationEntryPoint = BookManagerAuthenticationEntryPoint()
                accessDeniedHandler = BookManagerAccessDeniedHandler()
            }

            // CORS設定
            cors {
                configurationSource = corsConfigurationSource()
            }
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf("http://localhost:8081")
        configuration.allowedMethods = listOf(CorsConfiguration.ALL)
        configuration.allowedHeaders = listOf(CorsConfiguration.ALL)
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}