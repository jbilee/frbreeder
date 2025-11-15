package com.frbreeder.app.domain;

import com.frbreeder.app.common.auth.TokenProvider;
import com.frbreeder.app.common.error.InvalidRequestException;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.infrastructure.WorkspaceRepository;
import com.frbreeder.app.ui.dto.NewWorkspace;
import com.frbreeder.app.ui.dto.NewWorkspaceRequest;
import com.frbreeder.app.ui.dto.TokenResponse;
import com.frbreeder.app.ui.dto.WorkspaceLoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkspaceService {

    private static final String NAME_PATTERN = "^[A-Za-z-]+$";
    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]{5,}$";

    private final WorkspaceRepository workspaceRepository;
    private final TokenProvider jwtTokenProvider;

    public WorkspaceService(final WorkspaceRepository workspaceRepository, final TokenProvider jwtTokenProvider) {
        this.workspaceRepository = workspaceRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public NewWorkspace register(final NewWorkspaceRequest request) {
        if (!request.name().matches(NAME_PATTERN)) {
            throw new InvalidRequestException("Workspace name can only have alphabets and dashes.");
        }
        if (!request.password().matches(PASSWORD_PATTERN)) {
            throw new InvalidRequestException("Password contains unaccepted characters or is too short.");
        }
        if (workspaceRepository.existsByName(request.name())) {
            throw new InvalidRequestException("Workspace by that name already exists.");
        }

        String secret = "w-" + (int) (Math.random() * 1_000_000);

        Workspace workspace = workspaceRepository.save(new Workspace(request.name().toLowerCase(), request.password(), secret));
        String token = jwtTokenProvider.createToken(workspace);

        return new NewWorkspace(workspace.getName(), workspace.getSecret(), token);
    }

    public Workspace findWorkspaceById(final Long id) {
        return workspaceRepository.findById(id).orElseThrow();
    }

    public TokenResponse login(final WorkspaceLoginRequest request) {
        Workspace workspace = workspaceRepository.findByNameAndPassword(request.name(), request.password()).orElseThrow();
        String token = jwtTokenProvider.createToken(workspace);
        return new TokenResponse(token);
    }

}
