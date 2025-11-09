package com.frbreeder.app.common.auth;

import com.frbreeder.app.domain.WorkspaceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final WorkspaceService workspaceService;
    private final CookieTokenExtractor authorizationExtractor;

    public LoginInterceptor(final WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
        this.authorizationExtractor = new CookieTokenExtractor();
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        String token = authorizationExtractor.extract(request);
        if (token == null || token.isBlank()) {
            throw new RuntimeException("Need to log in.");
        }

        Long workspaceId = jwtTokenProvider.getWorkspaceIdFromToken(token);
        workspaceService.findWorkspaceById(workspaceId);

        return true;
    }

}

