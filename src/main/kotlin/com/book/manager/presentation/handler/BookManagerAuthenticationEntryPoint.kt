package com.book.manager.presentation.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class BookManagerAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: jakarta.servlet.http.HttpServletRequest,
        response: jakarta.servlet.http.HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED
    }
}