package com.frbreeder.app.common.auth;

import com.frbreeder.app.common.error.UnauthorizedException;
import com.frbreeder.app.domain.WorkspaceService;
import com.frbreeder.app.domain.entity.Workspace;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final WorkspaceService workspaceService;
    private final TokenProvider jwtTokenProvider;
    private final CookieTokenExtractor authorizationExtractor;

    public LoginInterceptor(final WorkspaceService workspaceService, final TokenProvider jwtTokenProvider) {
        this.workspaceService = workspaceService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authorizationExtractor = new CookieTokenExtractor();
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String token = authorizationExtractor.extract(request);
        if (token == null || token.isBlank()) {
            throw new UnauthorizedException("Need to log in.");
        }

        Long workspaceId = jwtTokenProvider.getWorkspaceIdFromToken(token);
        Workspace workspace = workspaceService.findWorkspaceById(workspaceId);

        request.setAttribute("workspace", workspace);

        return true;
    }

}
