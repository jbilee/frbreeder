package com.frbreeder.app.ui;

import com.frbreeder.app.domain.WorkspaceService;
import com.frbreeder.app.ui.dto.NewWorkspaceRequest;
import com.frbreeder.app.ui.dto.TokenResponse;
import com.frbreeder.app.ui.dto.WorkspaceLoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private final WorkspaceService workspaceService;

    public AuthController(final WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerWorkspace(@RequestBody final NewWorkspaceRequest request, final HttpServletResponse response) {
        TokenResponse tokenResponse = workspaceService.register(request);

        Cookie cookie = new Cookie("token", tokenResponse.accessToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginToWorkspace(@RequestBody final WorkspaceLoginRequest request, final HttpServletResponse response) {
        TokenResponse tokenResponse = workspaceService.login(request);

        Cookie cookie = new Cookie("token", tokenResponse.accessToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutFromWorkspace(final HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
