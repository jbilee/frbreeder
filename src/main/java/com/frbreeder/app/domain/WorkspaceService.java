package com.frbreeder.app.domain;

import com.frbreeder.app.common.auth.TokenProvider;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.infrastructure.WorkspaceRepository;
import com.frbreeder.app.ui.dto.NewWorkspaceRequest;
import com.frbreeder.app.ui.dto.TokenResponse;
import com.frbreeder.app.ui.dto.WorkspaceLoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final TokenProvider jwtTokenProvider;

    public WorkspaceService(final WorkspaceRepository workspaceRepository, final TokenProvider jwtTokenProvider) {
        this.workspaceRepository = workspaceRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public TokenResponse register(final NewWorkspaceRequest request) {
        if (workspaceRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Workspace by that name already exists.");
        }
        Workspace workspace = workspaceRepository.save(new Workspace(request.name(), request.password(), "a" + Math.random()));
        String token = jwtTokenProvider.createToken(workspace);
        return new TokenResponse(token);
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
