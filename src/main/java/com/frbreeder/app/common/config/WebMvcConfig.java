package com.frbreeder.app.common.config;

import com.frbreeder.app.common.auth.LoginInterceptor;
import com.frbreeder.app.common.auth.TokenProvider;
import com.frbreeder.app.common.auth.WorkspaceArgumentResolver;
import com.frbreeder.app.domain.WorkspaceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private TokenProvider jwtTokenProvider;

    @Autowired
    private WorkspaceService workspaceService;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(workspaceService, jwtTokenProvider))
                .addPathPatterns("/breeding/**", "/dragons/**", "/projects/**", "/auth");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new WorkspaceArgumentResolver(workspaceService, jwtTokenProvider));
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://frbreeder.site",
                        "https://frbreeder.vercel.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600L);
    }

}
