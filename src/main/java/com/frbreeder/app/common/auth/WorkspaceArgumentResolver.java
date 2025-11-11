package com.frbreeder.app.common.auth;

import com.frbreeder.app.domain.WorkspaceService;
import com.frbreeder.app.domain.entity.Workspace;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class WorkspaceArgumentResolver implements HandlerMethodArgumentResolver {

    private final WorkspaceService workspaceService;
    private final TokenProvider jwtTokenProvider;
    private final CookieTokenExtractor authorizationExtractor;

    public WorkspaceArgumentResolver(final WorkspaceService workspaceService, final TokenProvider jwtTokenProvider) {
        this.workspaceService = workspaceService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authorizationExtractor = new CookieTokenExtractor();
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(Workspace.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getAttribute("workspace");
    }

}
