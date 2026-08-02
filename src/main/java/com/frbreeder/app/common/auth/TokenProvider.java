package com.frbreeder.app.common.auth;

import com.frbreeder.app.domain.entity.Workspace;

public interface TokenProvider {

    String createToken(final Workspace workspace);

    Long getWorkspaceIdFromToken(final String token);

}
