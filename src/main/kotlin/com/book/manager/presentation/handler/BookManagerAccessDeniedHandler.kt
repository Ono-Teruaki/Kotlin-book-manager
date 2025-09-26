package com.book.manager.presentation.handler

import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.access.AccessDeniedException

class BookManagerAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: jakarta.servlet.http.HttpServletRequest,
        response: jakarta.servlet.http.HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN
    }
}