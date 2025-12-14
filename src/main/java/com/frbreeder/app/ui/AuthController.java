package com.frbreeder.app.ui;

import com.frbreeder.app.common.auth.AuthStatusResponse;
import com.frbreeder.app.domain.WorkspaceService;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.ui.dto.NewWorkspace;
import com.frbreeder.app.ui.dto.NewWorkspaceDetails;
import com.frbreeder.app.ui.dto.NewWorkspaceRequest;
import com.frbreeder.app.ui.dto.TokenResponse;
import com.frbreeder.app.ui.dto.WorkspaceLoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private final WorkspaceService workspaceService;

    public AuthController(final WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping("/register")
    public ResponseEntity<NewWorkspaceDetails> registerWorkspace(@RequestBody final NewWorkspaceRequest request,
                                                                 final HttpServletResponse response
    ) {
        NewWorkspace newWorkspace = workspaceService.register(request);

        ResponseCookie cookie = ResponseCookie.from("token", newWorkspace.token())
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(new NewWorkspaceDetails(newWorkspace.name(), newWorkspace.secret()));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginToWorkspace(@RequestBody final WorkspaceLoginRequest request,
                                                 final HttpServletResponse response
    ) {
        TokenResponse tokenResponse = workspaceService.login(request);

        ResponseCookie cookie = ResponseCookie.from("token", tokenResponse.accessToken())
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutFromWorkspace(final HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("token", null)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/auth")
    public ResponseEntity<AuthStatusResponse> checkAuthStatus(final Workspace workspace) {
        return ResponseEntity.ok(new AuthStatusResponse("OK", workspace.getName()));
    }

    @DeleteMapping("/auth/remove")
    public ResponseEntity<Void> deleteWorkspace(final Workspace workspace, final HttpServletResponse response) {
        workspaceService.deleteWorkspace(workspace.getId());

        ResponseCookie cookie = ResponseCookie.from("token", null)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }

}
